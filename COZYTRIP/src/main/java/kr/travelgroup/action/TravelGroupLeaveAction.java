package kr.travelgroup.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.travelgroup.dao.TravelGroupDAO;
import kr.travelgroup.dao.GroupMemberDAO;
import kr.travelgroup.vo.TravelGroupVO;
import kr.travelgroup.vo.GroupMemberVO;
import kr.chat.dao.ChatRoomDAO;
import kr.chat.dao.ChatMemberDAO;
import kr.chat.vo.ChatRoomVO;
import kr.chat.vo.ChatMemberVO;

public class TravelGroupLeaveAction implements Action {
    
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
            request.setAttribute("notice_url", "travelgroup/list.do");
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        // 현재 참여 중인지 확인
        GroupMemberDAO memberDao = GroupMemberDAO.getInstance();
        GroupMemberVO member = memberDao.getGroupMember(group_num, user_num);
        
        if(member == null) {
            // 참여하지 않은 경우
            request.setAttribute("notice_msg", "참여하지 않은 동행 그룹입니다.");
            request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        // 그룹 생성자인지 확인
        if(user_num.longValue() == group.getCreator_num()) {
            // 생성자는 탈퇴할 수 없음
            request.setAttribute("notice_msg", "동행 그룹 생성자는 탈퇴할 수 없습니다. 그룹을 삭제하려면 삭제 기능을 이용해주세요.");
            request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        try {
            // 1. 그룹 멤버에서 제거
            memberDao.deleteGroupMember(member.getMember_num());
            
            // 2. 채팅방에서 제거
            ChatRoomDAO chatRoomDao = ChatRoomDAO.getInstance();
            ChatRoomVO chatRoom = chatRoomDao.getChatRoomByGroup(group_num);
            
            if(chatRoom != null) {
                ChatMemberDAO chatMemberDao = ChatMemberDAO.getInstance();
                ChatMemberVO chatMember = chatMemberDao.getChatMember(chatRoom.getChatroom_num(), user_num);
                
                if(chatMember != null) {
                    chatMemberDao.deleteChatMember(chatMember.getChatmember_num());
                }
            }
            
            // 성공 메시지 설정
            request.setAttribute("notice_msg", "동행 그룹에서 탈퇴했습니다.");
            request.setAttribute("notice_url", "travelgroup/list.do");
            
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
            
        } catch(Exception e) {
            e.printStackTrace();
            
            // 실패 메시지 설정
            request.setAttribute("notice_msg", "동행 그룹 탈퇴 중 오류가 발생했습니다.");
            request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
            
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
    }
}