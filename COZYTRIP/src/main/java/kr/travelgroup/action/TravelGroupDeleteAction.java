package kr.travelgroup.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.travelgroup.dao.TravelGroupDAO;
import kr.travelgroup.vo.TravelGroupVO;
import kr.chat.dao.ChatRoomDAO;
import kr.chat.dao.ChatMessageDAO;
import kr.chat.vo.ChatRoomVO;

public class TravelGroupDeleteAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 그룹 번호 가져오기
        long group_num = Long.parseLong(request.getParameter("group_num"));
        
        // 그룹 정보 가져오기
        TravelGroupDAO groupDao = TravelGroupDAO.getInstance();
        TravelGroupVO group = groupDao.getTravelGroup(group_num);
        
        if(group == null) {
            // 존재하지 않는 그룹일 경우
            request.setAttribute("notice_msg", "존재하지 않는 동행 그룹입니다.");
            request.setAttribute("notice_url", "/travelgroup/list.do");
            return "common/alert_singlebutton.jsp";
        }
        
        // 그룹 생성자인지 확인
        if(user_num.longValue() != group.getCreator_num()) {
            // 생성자가 아닌 경우 삭제 권한 없음
            request.setAttribute("notice_msg", "동행 그룹 삭제 권한이 없습니다.");
            request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
            return "/common/alert_singlebutton.jsp";
        }
        
        try {
            // 채팅방 정보 가져오기
            ChatRoomDAO chatRoomDao = ChatRoomDAO.getInstance();
            ChatRoomVO chatRoom = chatRoomDao.getChatRoomByGroup(group_num);
            
            if(chatRoom != null) {
                // 채팅 메시지 삭제
                ChatMessageDAO chatMessageDao = ChatMessageDAO.getInstance();
                chatMessageDao.deleteAllChatMessages(chatRoom.getChatroom_num());
                
                // 채팅방 삭제 (외래키 제약조건 CASCADE로 인해 채팅 멤버도 자동 삭제됨)
                chatRoomDao.deleteChatRoom(chatRoom.getChatroom_num());
            }
            
            // 동행 그룹 삭제 (외래키 제약조건으로 인해 그룹 멤버도 자동 삭제됨)
            groupDao.deleteTravelGroup(group_num);
            
            // 성공 후 직접 리다이렉트
            return "redirect:/travelgroup/list.do";
            
        } catch(Exception e) {
            e.printStackTrace();
            
            // 실패 메시지 설정
            request.setAttribute("notice_msg", "동행 그룹 삭제 중 오류가 발생했습니다.");
            request.setAttribute("notice_url", "/travelgroup/detail.do?group_num=" + group_num);
            
            return "common/alert_singlebutton.jsp";
        }
    }
}