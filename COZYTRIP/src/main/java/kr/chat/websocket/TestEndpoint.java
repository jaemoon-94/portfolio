package kr.chat.websocket;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/test")
public class TestEndpoint {
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("테스트 웹소켓 연결확인" + session.getId());
    }
    
    @OnMessage
    public String onMessage(String message) {
        System.out.println("테스트 웹소켓 메시지 수신: " + message);
        return "서버 응답: " + message;
    }
}
