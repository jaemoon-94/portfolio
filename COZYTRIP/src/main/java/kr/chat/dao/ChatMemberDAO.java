package kr.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.chat.vo.ChatMemberVO;
import kr.util.DBUtil;

public class ChatMemberDAO {
    // 싱글턴 패턴
    private static ChatMemberDAO instance = new ChatMemberDAO();
    
    public static ChatMemberDAO getInstance() {
        return instance;
    }
    
    private ChatMemberDAO() {}
    
    // 채팅 멤버 추가
    public void insertChatMember(ChatMemberVO member) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "INSERT INTO chat_member (chatmember_num, chatroom_num, user_num, join_date) "
                + "VALUES (SEQ_CHAT_MEMBER.nextval, ?, ?, SYSDATE)";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql, new String[] {"chatmember_num"});
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, member.getChatroom_num());
            pstmt.setLong(2, member.getUser_num());
            
            // SQL문 실행
            pstmt.executeUpdate();
            
            // 생성된 채팅 멤버 번호 가져오기
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                member.setChatmember_num(rs.getLong(1));
            }
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
    }
    
    // 채팅방의 멤버 목록 조회
    public List<ChatMemberVO> getChatMemberList(long chatroom_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ChatMemberVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM chat_member WHERE chatroom_num = ? ORDER BY join_date";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, chatroom_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<ChatMemberVO>();
            while(rs.next()) {
                ChatMemberVO member = new ChatMemberVO();
                member.setChatmember_num(rs.getLong("chatmember_num"));
                member.setChatroom_num(rs.getLong("chatroom_num"));
                member.setUser_num(rs.getLong("user_num"));
                member.setJoin_date(rs.getDate("join_date"));
                
                list.add(member);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 채팅 멤버 조회 (특정 사용자가 채팅방에 참여했는지 확인)
    public ChatMemberVO getChatMember(long chatroom_num, long user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ChatMemberVO member = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM chat_member WHERE chatroom_num = ? AND user_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, chatroom_num);
            pstmt.setLong(2, user_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                member = new ChatMemberVO();
                member.setChatmember_num(rs.getLong("chatmember_num"));
                member.setChatroom_num(rs.getLong("chatroom_num"));
                member.setUser_num(rs.getLong("user_num"));
                member.setJoin_date(rs.getDate("join_date"));
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return member;
    }
    
    // 채팅 멤버 삭제 (채팅방 나가기)
    public void deleteChatMember(long chatmember_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "DELETE FROM chat_member WHERE chatmember_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, chatmember_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 사용자가 참여한 채팅방 목록 조회
    public List<Long> getUserChatRoomList(long user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Long> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT chatroom_num FROM chat_member WHERE user_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, user_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<Long>();
            while(rs.next()) {
                list.add(rs.getLong("chatroom_num"));
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 채팅방의 멤버 수 조회
    public int getChatMemberCount(long chatroom_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        int count = 0;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT COUNT(*) FROM chat_member WHERE chatroom_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, chatroom_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                count = rs.getInt(1);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return count;
    }
}