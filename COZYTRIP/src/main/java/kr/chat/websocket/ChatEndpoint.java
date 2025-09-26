package kr.chat.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/websocket-chat")
public class ChatEndpoint {
    // 웹소켓 세션을 관리하는 Set
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<>());
    
    @OnOpen
    public void onOpen(Session session) {
        // 클라이언트 연결 시 세션 추가
        clients.add(session);
        System.out.println("웹소켓 연결: " + session.getId());
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("메시지 수신: " + message);
        
        // 메시지 형식 처리
        if(message.startsWith("chat:")) {
            // 채팅 메시지: "chat:채팅방번호:사용자번호:사용자이름:메시지내용"
            String[] parts = message.split(":", 5);
            if(parts.length >= 5) {
                String chatroom_num = parts[1];
                String sender_num = parts[2];
                String sender_name = parts[3];
                String msg_content = parts[4];
                
                System.out.println("채팅 메시지 처리: 채팅방="+chatroom_num+", 발신자="+sender_num+", 이름="+sender_name+", 내용="+msg_content);
                
                new Thread(() -> {
                    try {
                        // ChatMessageVO 객체 생성
                        kr.chat.vo.ChatMessageVO messageVO = new kr.chat.vo.ChatMessageVO();
                        messageVO.setChatroom_num(Long.parseLong(chatroom_num));
                        messageVO.setSender_num(Long.parseLong(sender_num));
                        messageVO.setMessage(msg_content);
                        
                        // DAO를 통해 메시지 저장
                        kr.chat.dao.ChatMessageDAO.getInstance().insertChatMessage(messageVO);
                        System.out.println("메시지 DB 저장 완료");
                    } catch(Exception e) {
                        System.err.println("메시지 저장 오류: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();
                
                // 날짜 포맷팅
                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("HH:mm:ss");
                java.util.Date now = new java.util.Date();
                String currentDate = dateFormat.format(now);
                String currentTime = timeFormat.format(now);
                
                // JSON 형태로 메시지 구성
                String jsonMessage = String.format(
                	    "{\"type\":\"CHAT\",\"chatroom_num\":%s,\"sender_num\":%s,\"sender_name\":\"%s\",\"message\":\"%s\",\"message_date\":\"%s\",\"message_time\":\"%s\"}",
                	    chatroom_num, sender_num, sender_name, msg_content.replace("\"", "\\\""), currentDate, currentTime
                );
                
                System.out.println("전송할 JSON 메시지: " + jsonMessage);
                
                // 채팅방에 있는 모든 클라이언트에게 메시지 전송
                int sentCount = 0;
                for(Session client : clients) {
                    try {
                        Object clientChatroomNum = client.getUserProperties().get("chatroom_num");
                        System.out.println("클라이언트 검사: ID=" + client.getId() + ", 채팅방=" + clientChatroomNum);
                        
                        if(clientChatroomNum != null && clientChatroomNum.toString().equals(chatroom_num)) {
                            client.getBasicRemote().sendText(jsonMessage);
                            sentCount++;
                            System.out.println("클라이언트 " + client.getId() + "에게 메시지 전송 완료");
                        }
                    } catch(Exception e) {
                        System.err.println("메시지 전송 오류 (클라이언트 ID=" + client.getId() + "): " + e.getMessage());
                    }
                }
                
                System.out.println("총 " + sentCount + "명의 클라이언트에게 메시지 전송 완료");
            }
        } else if(message.startsWith("join:")) {
            // 채팅방 입장 메시지: "join:채팅방번호:사용자번호"
            String[] parts = message.split(":", 3);
            if(parts.length >= 3) {
                String chatroom_num = parts[1];
                String user_num = parts[2];
                
                // 세션에 채팅방 번호 저장
                session.getUserProperties().put("chatroom_num", chatroom_num);
                session.getUserProperties().put("user_num", user_num);
                
                System.out.println("사용자 " + user_num + "가 채팅방 " + chatroom_num + "에 입장 (세션 ID: " + session.getId() + ")");
                System.out.println("현재 연결된 총 클라이언트 수: " + clients.size());
                
                // 현재 연결된 모든 클라이언트의 채팅방 정보 출력 (디버깅용)
                for(Session client : clients) {
                    Object clientChatroomNum = client.getUserProperties().get("chatroom_num");
                    Object clientUserNum = client.getUserProperties().get("user_num");
                    System.out.println("- 클라이언트: ID=" + client.getId() + ", 채팅방=" + clientChatroomNum + ", 사용자=" + clientUserNum);
                }
                
                // 입장 알림 메시지 전송
                String joinMessage = String.format(
                    "{\"type\":\"JOIN\",\"chatroom_num\":%s,\"user_num\":%s}",
                    chatroom_num, user_num
                );
                
                // 자신에게만 입장 확인 메시지 전송 (다른 클라이언트에게는 전송하지 않음)
                try {
                    session.getBasicRemote().sendText(joinMessage);
                    System.out.println("입장 확인 메시지 전송 완료 (세션 ID: " + session.getId() + ")");
                } catch(Exception e) {
                    System.err.println("입장 메시지 전송 오류: " + e.getMessage());
                }
            }
        }
    }
    
    @OnClose
    public void onClose(Session session) {
        // 클라이언트 연결 종료 시 세션 제거
        clients.remove(session);
        System.out.println("웹소켓 종료: " + session.getId());
    }
    
    @OnError
    public void onError(Throwable error) {
        // 에러 처리
        error.printStackTrace();
    }
}