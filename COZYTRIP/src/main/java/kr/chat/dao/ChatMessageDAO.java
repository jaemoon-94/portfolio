package kr.chat.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.chat.vo.ChatMessageVO;
import kr.util.DBUtil;

public class ChatMessageDAO {
    // 싱글턴 패턴
    private static ChatMessageDAO instance = new ChatMessageDAO();
    
    public static ChatMessageDAO getInstance() {
        return instance;
    }
    
    private ChatMessageDAO() {}
    
    // 채팅 메시지 저장
    public void insertChatMessage(ChatMessageVO message) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "INSERT INTO chat_message (message_num, chatroom_num, sender_num, message, message_date) "
                + "VALUES (SEQ_CHAT_MESSAGE.nextval, ?, ?, ?, SYSDATE)";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql, new String[] {"message_num"});
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, message.getChatroom_num());
            pstmt.setLong(2, message.getSender_num());
            pstmt.setString(3, message.getMessage());
            
            // SQL문 실행
            pstmt.executeUpdate();
            
            // 생성된 메시지 번호 가져오기
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                message.setMessage_num(rs.getLong(1));
            }
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
    }
    
    // 채팅방의 메시지 목록 조회 (최신 순으로 제한된 개수)
    public List<ChatMessageVO> getChatMessageList(long chatroom_num, int limit) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ChatMessageVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성 (최신 메시지 limit 개수만큼 가져오기)
            sql = "SELECT * FROM (SELECT * FROM chat_message WHERE chatroom_num = ? "
                + "ORDER BY message_date DESC) WHERE ROWNUM <= ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, chatroom_num);
            pstmt.setInt(2, limit);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<ChatMessageVO>();
            while(rs.next()) {
                ChatMessageVO message = new ChatMessageVO();
                message.setMessage_num(rs.getLong("message_num"));
                message.setChatroom_num(rs.getLong("chatroom_num"));
                message.setSender_num(rs.getLong("sender_num"));
                message.setMessage(rs.getString("message"));
                message.setMessage_date(rs.getDate("message_date"));
                
                list.add(message);
            }
            
            // 시간순으로 정렬 (오래된 메시지부터)
            java.util.Collections.reverse(list);
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 특정 시간 이후의 메시지 조회
    public List<ChatMessageVO> getNewChatMessages(long chatroom_num, Date after) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ChatMessageVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM chat_message WHERE chatroom_num = ? "
                + "AND message_date > ? ORDER BY message_date";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, chatroom_num);
            pstmt.setDate(2, after);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<ChatMessageVO>();
            while(rs.next()) {
                ChatMessageVO message = new ChatMessageVO();
                message.setMessage_num(rs.getLong("message_num"));
                message.setChatroom_num(rs.getLong("chatroom_num"));
                message.setSender_num(rs.getLong("sender_num"));
                message.setMessage(rs.getString("message"));
                message.setMessage_date(rs.getDate("message_date"));
                
                list.add(message);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 메시지 삭제
    public void deleteChatMessage(long message_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "DELETE FROM chat_message WHERE message_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, message_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 채팅방의 모든 메시지 삭제
    public void deleteAllChatMessages(long chatroom_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "DELETE FROM chat_message WHERE chatroom_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, chatroom_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
}