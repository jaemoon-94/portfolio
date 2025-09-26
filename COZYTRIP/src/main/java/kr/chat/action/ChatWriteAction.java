package kr.chat.action;

import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.chat.dao.ChatMessageDAO;
import kr.chat.vo.ChatMessageVO;
import kr.controller.Action;

public class ChatWriteAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        StringBuilder sb = new StringBuilder();
        if(user_num == null) {
            sb.append("{\"result\":\"logout\"}");
        } else {
            // 전송된 데이터 인코딩 처리
            request.setCharacterEncoding("utf-8");
            
            //로그조회용 출력문
            long chatroom_num = Long.parseLong(request.getParameter("chatroom_num"));
            System.out.println("메시지 삽입을 시도하는 채팅방 번호: " + chatroom_num);
            
            // 채팅 메시지 저장
            ChatMessageVO message = new ChatMessageVO();
            message.setChatroom_num(Long.parseLong(request.getParameter("chatroom_num")));
            message.setSender_num(user_num);
            message.setMessage(request.getParameter("message"));
            
            ChatMessageDAO.getInstance().insertChatMessage(message);
            
            sb.append("{\"result\":\"success\"}");
        }
        
        out.print(sb.toString());
        out.flush();
        
        return "ajax";
    }
}
