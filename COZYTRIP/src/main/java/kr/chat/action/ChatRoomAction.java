package kr.chat.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.chat.dao.ChatRoomDAO;
import kr.chat.dao.ChatMemberDAO;
import kr.chat.dao.ChatMessageDAO;
import kr.chat.vo.ChatRoomVO;
import kr.chat.vo.ChatMemberVO;
import kr.chat.vo.ChatMessageVO;
import kr.travelgroup.dao.TravelGroupDAO;
import kr.travelgroup.vo.TravelGroupVO;
import kr.user.dao.UserDAO;

public class ChatRoomAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 채팅방 번호 또는 동행 그룹 번호 가져오기
        long chatroom_num = 0;
        int group_num = 0;
        
        if(request.getParameter("chatroom_num") != null) {
            chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
        } else if(request.getParameter("group_num") != null) {
            group_num = Integer.parseInt(request.getParameter("group_num"));
            
            // 동행 그룹에 해당하는 채팅방 찾기
            ChatRoomDAO chatRoomDao = ChatRoomDAO.getInstance();
            ChatRoomVO chatRoom = chatRoomDao.getChatRoomByGroup(group_num);
            
            if(chatRoom != null) {
                chatroom_num = chatRoom.getChatroom_num();
            } else {
                // 채팅방이 없는 경우 오류 메시지
                request.setAttribute("notice_msg", "해당 동행 그룹의 채팅방을 찾을 수 없습니다.");
                request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group_num);
                return "/WEB-INF/views/common/alert_singlebutton.jsp";
            }
        }
        
        if(chatroom_num == 0) {
            // 채팅방 번호가 없는 경우 오류 메시지
            request.setAttribute("notice_msg", "잘못된 접근입니다.");
            request.setAttribute("notice_url", "main.do");
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        // 채팅방 정보 가져오기
        ChatRoomDAO chatRoomDao = ChatRoomDAO.getInstance();
        ChatRoomVO chatRoom = chatRoomDao.getChatRoom(chatroom_num);
        
        if(chatRoom == null) {
            // 존재하지 않는 채팅방인 경우
            request.setAttribute("notice_msg", "존재하지 않는 채팅방입니다.");
            request.setAttribute("notice_url", "main.do");
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        // 해당 채팅방에 참여 권한이 있는지 확인
        ChatMemberDAO chatMemberDao = ChatMemberDAO.getInstance();
        ChatMemberVO chatMember = chatMemberDao.getChatMember(chatroom_num, user_num);
        
        if(chatMember == null) {
            // 권한이 없는 경우, 동행 그룹의 채팅방인지 확인
            if(chatRoom.getGroup_num() > 0) {
                // 동행 그룹 정보 가져오기
                TravelGroupDAO groupDao = TravelGroupDAO.getInstance();
                TravelGroupVO group = groupDao.getTravelGroup(chatRoom.getGroup_num());
                
                if(group != null) {
                    request.setAttribute("notice_msg", "동행 그룹에 가입 후 채팅방에 입장해주세요.");
                    request.setAttribute("notice_url", "travelgroup/detail.do?group_num=" + group.getGroup_num());
                    return "/WEB-INF/views/common/alert_singlebutton.jsp";
                }
            }
            

            request.setAttribute("notice_msg", "채팅방에 참여할 수 없습니다.");
            request.setAttribute("notice_url", "main.do");
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        // 최근 메시지 가져오기 (최대 100개)
        ChatMessageDAO chatMessageDao = ChatMessageDAO.getInstance();
        List<ChatMessageVO> messageList = chatMessageDao.getChatMessageList(chatroom_num, 100);
        
        // 동행 그룹 정보 가져오기 (채팅방이 동행 그룹에 속한 경우)
        TravelGroupVO group = null;
        if(chatRoom.getGroup_num() > 0) {
            TravelGroupDAO groupDao = TravelGroupDAO.getInstance();
            group = groupDao.getTravelGroup(chatRoom.getGroup_num());
        }
        
        // 채팅방 멤버 목록 가져오기
        List<ChatMemberVO> memberList = chatMemberDao.getChatMemberList(chatroom_num);
        
        request.setAttribute("chatRoom", chatRoom);
        request.setAttribute("messageList", messageList);
        request.setAttribute("group", group);
        request.setAttribute("memberList", memberList);
        
        // JSP 페이지 경로 반환
        return "chat/room.jsp";
    }
}