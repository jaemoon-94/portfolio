package kr.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.websocket.server.ServerContainer;

/**
 * 웹소켓 서버를 초기화하는 리스너 클래스
 * 웹 애플리케이션 시작 시 자동으로 실행됨
 */
@WebListener
public class WebSocketConfig implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("===== 웹소켓 서버 초기화 시작 =====");
        try {
            // 웹소켓 서버 초기화 로직
            ServerContainer container = (ServerContainer) sce.getServletContext()
                .getAttribute("jakarta.websocket.server.ServerContainer");
                
            if (container == null) {
                System.err.println("웹소켓 서버 컨테이너를 찾을 수 없습니다!");
                return;
            }
            
            // 웹소켓 설정
            container.setDefaultMaxSessionIdleTimeout(0); // 세션 유후 시간 없음
            container.setDefaultMaxTextMessageBufferSize(65536); // 64KB
            
            System.out.println("웹소켓 엔드포인트 등록 확인:");
            System.out.println("- /websocket-chat (그룹 채팅)");
            System.out.println("- /webSocket2 (1:1 채팅)");
            
            // 웹소켓 서버가 올바르게 초기화되었는지 확인
            System.out.println("웹소켓 서버 초기화 완료");
        } catch (Exception e) {
            System.err.println("웹소켓 서버 초기화 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("===== 웹소켓 서버 종료 =====");
    }
}
