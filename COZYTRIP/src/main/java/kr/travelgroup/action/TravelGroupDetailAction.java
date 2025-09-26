package kr.travelgroup.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.travelgroup.dao.TravelGroupDAO;
import kr.travelgroup.dao.GroupMemberDAO;
import kr.travelgroup.vo.TravelGroupVO;
import kr.travelgroup.vo.GroupMemberVO;
import kr.chat.dao.ChatRoomDAO;
import kr.chat.vo.ChatRoomVO;

public class TravelGroupDetailAction implements Action {
    
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
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        // 그룹 멤버 목록 가져오기
        GroupMemberDAO memberDao = GroupMemberDAO.getInstance();
        List<GroupMemberVO> memberList = memberDao.getGroupMemberList(group_num);
        
        // 현재 로그인한 사용자가 이 그룹에 참여 중인지 확인
        GroupMemberVO userMember = memberDao.getGroupMember(group_num, user_num);
        boolean isMember = (userMember != null);
        
        // 그룹 생성자인지 확인
        boolean isCreator = (user_num.longValue() == group.getCreator_num());
        
        // 채팅방 정보 가져오기
        ChatRoomDAO chatDao = ChatRoomDAO.getInstance();
        ChatRoomVO chatRoom = chatDao.getChatRoomByGroup(group_num);
        
        // 요청 객체에 데이터 저장
        request.setAttribute("group", group);
        request.setAttribute("memberList", memberList);
        request.setAttribute("isMember", isMember);
        request.setAttribute("isCreator", isCreator);
        request.setAttribute("chatRoom", chatRoom);
        
        // JSP 페이지 경로 반환
        return "travelgroup/detail.jsp";
    }
}