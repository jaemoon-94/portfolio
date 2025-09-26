package kr.travelgroup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.travelgroup.vo.GroupMemberVO;
import kr.util.DBUtil;

public class GroupMemberDAO {
    // 싱글턴 패턴
    private static GroupMemberDAO instance = new GroupMemberDAO();
    
    public static GroupMemberDAO getInstance() {
        return instance;
    }
    
    private GroupMemberDAO() {}
    
    // 그룹 멤버 추가 (동행 그룹 참여)
    public void insertGroupMember(GroupMemberVO member) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "INSERT INTO group_member (member_num, group_num, user_num, join_date) "
                + "VALUES (seq_group_member.nextval, ?, ?, SYSDATE)";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql, new String[] {"member_num"});
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, member.getGroup_num());
            pstmt.setLong(2, member.getUser_num());
            
            // SQL문 실행
            pstmt.executeUpdate();
            
            // 생성된 멤버 번호 가져오기
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                member.setMember_num(rs.getLong(1));
            }
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
    }
    
    // 그룹 멤버 목록 조회
    public List<GroupMemberVO> getGroupMemberList(long group_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<GroupMemberVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM group_member WHERE group_num = ? ORDER BY join_date";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, group_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<GroupMemberVO>();
            while(rs.next()) {
                GroupMemberVO member = new GroupMemberVO();
                member.setMember_num(rs.getLong("member_num"));
                member.setGroup_num(rs.getLong("group_num"));
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
    
    // 그룹 멤버 조회 (특정 사용자가 해당 그룹에 참여했는지 확인)
    public GroupMemberVO getGroupMember(long group_num, long user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        GroupMemberVO member = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM group_member WHERE group_num = ? AND user_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, group_num);
            pstmt.setLong(2, user_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                member = new GroupMemberVO();
                member.setMember_num(rs.getLong("member_num"));
                member.setGroup_num(rs.getLong("group_num"));
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
    
    // 그룹 멤버 삭제 (동행 그룹 탈퇴)
    public void deleteGroupMember(long member_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "DELETE FROM group_member WHERE member_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, member_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 사용자가 참여한 동행 그룹 목록 조회
    public List<Long> getUserGroupList(long user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Long> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT group_num FROM group_member WHERE user_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, user_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<Long>();
            while(rs.next()) {
                list.add(rs.getLong("group_num"));
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 그룹 멤버 수 조회
    public long getGroupMemberCount(long group_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        long count = 0;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT COUNT(*) FROM group_member WHERE group_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, group_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                count = rs.getLong(1);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return count;
    }
}