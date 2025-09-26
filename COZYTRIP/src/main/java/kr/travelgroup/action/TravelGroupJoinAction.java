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

public class TravelGroupJoinAction implements Action {
    
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
        
        // 이미 참여 중인지 확인
        GroupMemberDAO memberDao = GroupMemberDAO.getInstance();
        GroupMemberVO existingMember = memberDao.getGroupMember(group_num, user_num);
        
        if(existingMember != null) {
            // 이미 참여 중인 경우
            request.setAttribute("notice_msg", "이미 참여 중인 동행 그룹입니다.");
            request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        // 모집 인원 초과 체크
        long memberCount = memberDao.getGroupMemberCount(group_num);
        
        if(group.getMax_member_count() > 0 && memberCount >= group.getMax_member_count()) {
            // 모집 인원 초과인 경우
            request.setAttribute("notice_msg", "모집 인원이 초과되었습니다.");
            request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        // 모집 상태 체크 (0: 모집중, 1: 모집마감, 2: 동행종료)
        if(group.getStatus() != 0) {
            // 모집중이 아닌 경우
            request.setAttribute("notice_msg", "모집이 마감된 동행 그룹입니다.");
            request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        try {
            // 1. 그룹 멤버로 추가
            GroupMemberVO member = new GroupMemberVO();
            member.setGroup_num(group_num);
            member.setUser_num(user_num);
            memberDao.insertGroupMember(member);
            
            // 2. 채팅방에 추가
            ChatRoomDAO chatRoomDao = ChatRoomDAO.getInstance();
            ChatRoomVO chatRoom = chatRoomDao.getChatRoomByGroup(group_num);
            
            if(chatRoom != null) {
                ChatMemberDAO chatMemberDao = ChatMemberDAO.getInstance();
                ChatMemberVO chatMember = new ChatMemberVO();
                chatMember.setChatroom_num(chatRoom.getChatroom_num());
                chatMember.setUser_num(user_num);
                chatMemberDao.insertChatMember(chatMember);
            }
            
            // 성공 메시지 설정
            request.setAttribute("notice_msg", "동행 그룹에 참여했습니다.");
            request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
            
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
            
        } catch(Exception e) {
            e.printStackTrace();
            
            // 실패 메시지 설정
            request.setAttribute("notice_msg", "동행 그룹 참여 중 오류가 발생했습니다.");
            request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
            
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
    }
}