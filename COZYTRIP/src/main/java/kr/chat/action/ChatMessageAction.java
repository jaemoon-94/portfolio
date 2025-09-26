package kr.chat.action;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.chat.dao.ChatMessageDAO;
import kr.chat.vo.ChatMessageVO;
import kr.chat.dao.ChatRoomDAO;
import kr.chat.vo.ChatRoomVO;

public class ChatMessageAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 채팅방 번호 가져오기
        long chatroom_num = 0;
        if(request.getParameter("chatroom_num") != null) {
            chatroom_num = Long.parseLong(request.getParameter("chatroom_num"));
        }
        
        // 채팅방 정보 가져오기
        ChatRoomDAO chatRoomDao = ChatRoomDAO.getInstance();
        ChatRoomVO chatRoom = chatRoomDao.getChatRoom(chatroom_num);
        
        if(chatRoom == null) {
            // 채팅방이 없는 경우
            request.setAttribute("notice_msg", "존재하지 않는 채팅방입니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/main.do");
            return "common/alert_singlebutton.jsp";
        }
        
        // 메시지 목록 가져오기
        ChatMessageDAO messageDao = ChatMessageDAO.getInstance();
        List<ChatMessageVO> messageList = messageDao.getChatMessageList(chatroom_num, 50); // 최근 50개 메시지
        
        // 요청 객체에 데이터 저장
        request.setAttribute("chatRoom", chatRoom);
        request.setAttribute("messageList", messageList);
        
        // JSP 페이지 경로 반환
        return "chat/room.jsp";
    }
}