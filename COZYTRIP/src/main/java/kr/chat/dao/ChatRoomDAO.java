package kr.chat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.chat.vo.ChatRoomVO;
import kr.util.DBUtil;

public class ChatRoomDAO {
    // 싱글턴 패턴
    private static ChatRoomDAO instance = new ChatRoomDAO();
    
    public static ChatRoomDAO getInstance() {
        return instance;
    }
    
    private ChatRoomDAO() {}
    
    // 채팅방 생성
    public void insertChatRoom(ChatRoomVO chatRoom) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "INSERT INTO chatroom (chatroom_num, group_num, chatroom_type, create_date) "
                + "VALUES (SEQ_CHATROOM.nextval, ?, ?, SYSDATE)";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql, new String[] {"chatroom_num"});
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, chatRoom.getGroup_num());
            pstmt.setString(2, chatRoom.getChatroom_type());
            
            // SQL문 실행
            pstmt.executeUpdate();
            
            // 생성된 채팅방 번호 가져오기
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                chatRoom.setChatroom_num(rs.getLong(1));
            }
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
    }
    
    // 동행 그룹에 해당하는 채팅방 조회
    public ChatRoomVO getChatRoomByGroup(long group_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ChatRoomVO chatRoom = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM chatroom WHERE group_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, group_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                chatRoom = new ChatRoomVO();
                chatRoom.setChatroom_num(rs.getLong("chatroom_num"));
                chatRoom.setGroup_num(rs.getLong("group_num"));
                chatRoom.setChatroom_type(rs.getString("chatroom_type"));
                chatRoom.setCreate_date(rs.getDate("create_date"));
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return chatRoom;
    }
    
    // 채팅방 번호로 채팅방 조회
    public ChatRoomVO getChatRoom(long chatroom_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ChatRoomVO chatRoom = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM chatroom WHERE chatroom_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, chatroom_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                chatRoom = new ChatRoomVO();
                chatRoom.setChatroom_num(rs.getLong("chatroom_num"));
                chatRoom.setGroup_num(rs.getLong("group_num"));
                chatRoom.setChatroom_type(rs.getString("chatroom_type"));
                chatRoom.setCreate_date(rs.getDate("create_date"));
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return chatRoom;
    }
    
    // 채팅방 목록 조회
    public List<ChatRoomVO> getChatRoomList() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ChatRoomVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM chatroom ORDER BY create_date DESC";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<ChatRoomVO>();
            while(rs.next()) {
                ChatRoomVO chatRoom = new ChatRoomVO();
                chatRoom.setChatroom_num(rs.getLong("chatroom_num"));
                chatRoom.setGroup_num(rs.getLong("group_num"));
                chatRoom.setChatroom_type(rs.getString("chatroom_type"));
                chatRoom.setCreate_date(rs.getDate("create_date"));
                
                list.add(chatRoom);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 채팅방 삭제
    public void deleteChatRoom(long chatroom_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "DELETE FROM chatroom WHERE chatroom_num = ?";
            
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