package kr.chat.action;

import java.util.List;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.chat.dao.ChatMessageDAO;
import kr.chat.dao.ChatMemberDAO;
import kr.chat.vo.ChatMessageVO;
import kr.chat.vo.ChatMemberVO;
import kr.user.dao.UserDAO;
import kr.controller.Action;

public class ChatMessageListAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        StringBuilder sb = new StringBuilder();
        if(user_num == null) {
            sb.append("{\"result\":\"logout\"}");
        } else {
            // 채팅방 번호 구하기
            long chatroom_num = Long.parseLong(request.getParameter("chatroom_num"));
            
            // 채팅 메시지 목록 가져오기 (최근 100개)
            List<ChatMessageVO> list = ChatMessageDAO.getInstance().getChatMessageList(chatroom_num, 100);
            
            // 채팅방 멤버 정보 가져오기
            ChatMemberDAO chatMemberDao = ChatMemberDAO.getInstance();
            List<ChatMemberVO> memberList = chatMemberDao.getChatMemberList(chatroom_num);
            
            // 사용자 ID 정보를 담을 임시 맵 생성
            java.util.Map<Long, String> userIdMap = new java.util.HashMap<>();
            for(ChatMemberVO member : memberList) {
                try {
                    // 사용자 정보 가져오기
                    kr.user.vo.UserVO user = UserDAO.getInstance().getUser(member.getUser_num());
                    if(user != null) {
                        userIdMap.put(member.getUser_num(), user.getUserId());
                    } else {
                        userIdMap.put(member.getUser_num(), "사용자" + member.getUser_num());
                    }
                } catch(Exception e) {
                    // 예외 발생 시 기본값 사용
                    userIdMap.put(member.getUser_num(), "사용자" + member.getUser_num());
                }
            }
            
            sb.append("{\"result\":\"success\",");
            sb.append("\"list\":[");
            
            // 날짜 포맷 설정
            SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            
            String currentDate = "";
            
            for(int i=0;i<list.size();i++) {
                ChatMessageVO message = list.get(i);
                if(i>0) sb.append(",");
                
                // 날짜 추출 및 포맷팅
                Date messageDate = message.getMessage_date();
                String messageDateStr = fullFormat.format(messageDate);
                String messageOnlyDate = dateFormat.format(messageDate);
                String messageOnlyTime = timeFormat.format(messageDate);
                
                sb.append("{");
                // 날짜가 바뀌었는지 확인
                sb.append("\"new_date\":" + (currentDate.equals(messageOnlyDate) ? "false" : "true") + ",");
                currentDate = messageOnlyDate;
                
                sb.append("\"chat_date\":\"" + messageDateStr + "\",");
                sb.append("\"message_date\":\"" + messageOnlyDate + "\",");
                sb.append("\"message_time\":\"" + messageOnlyTime + "\",");
                sb.append("\"message\":\"" + message.getMessage().replace("\"", "\\\"") + "\",");
                sb.append("\"sender_num\":" + message.getSender_num() + ",");
                
                // 사용자 ID 추가
                String userId = userIdMap.get(message.getSender_num());
                sb.append("\"sender_name\":\"" + (userId != null ? userId : "Unknown") + "\"");
                
                sb.append("}");
            }
            sb.append("],");
            sb.append("\"user_num\":" + user_num);
            sb.append("}");
        }
        
        out.print(sb.toString());
        out.flush();
        
        return "ajax";
    }
}