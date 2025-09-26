package kr.travelgroup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.travelgroup.vo.TravelGroupVO;
import kr.travelgroup.vo.GroupMemberVO;
import kr.util.DBUtil;

public class TravelGroupDAO {
    // 싱글턴 패턴
    private static TravelGroupDAO instance = new TravelGroupDAO();
    
    public static TravelGroupDAO getInstance() {
        return instance;
    }
    
    private TravelGroupDAO() {}
    
    // 동행 그룹 생성
    public void insertTravelGroup(TravelGroupVO group) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        int cnt = 0;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "INSERT INTO travelgroup (group_num, creator_num, tg_title, "
                + "content, create_date, travel_date_start, travel_date_end, "
                + "max_member_count, region_num, status) "
                + "VALUES (seq_travelgroup.nextval, ?, ?, ?, SYSDATE, ?, ?, ?, ?, ?)";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql, new String[] {"group_num"});
            
            // ?에 데이터 바인딩
            pstmt.setLong(++cnt, group.getCreator_num());
            pstmt.setString(++cnt, group.getTg_title());
            pstmt.setString(++cnt, group.getTg_content());
            pstmt.setDate(++cnt, group.getTravel_date_start());
            pstmt.setDate(++cnt, group.getTravel_date_end());
            pstmt.setInt(++cnt, group.getMax_member_count());
            pstmt.setInt(++cnt, group.getRegion_num());
            pstmt.setInt(++cnt, group.getStatus());
            
            // SQL문 실행
            pstmt.executeUpdate();
            
            // 생성된 그룹 번호 가져오기
            rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                group.setGroup_num(rs.getInt(1));
            }
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
    }
    
    // 동행 그룹 상세 정보 조회
    public TravelGroupVO getTravelGroup(long group_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TravelGroupVO group = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM travelgroup WHERE group_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, group_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                group = new TravelGroupVO();
                group.setGroup_num(rs.getInt("GROUP_NUM"));
                group.setCreator_num(rs.getInt("CREATOR_NUM"));
                group.setAccom_num(rs.getInt("ACCOM_NUM"));
                group.setTg_title(rs.getString("TG_TITLE"));
                group.setTg_content(rs.getString("CONTENT"));
                group.setCreate_date(rs.getDate("CREATE_DATE"));
                group.setTravel_date_start(rs.getDate("TRAVEL_DATE_START"));
                group.setTravel_date_end(rs.getDate("TRAVEL_DATE_END"));
                group.setMax_member_count(rs.getInt("MAX_MEMBER_COUNT"));
                group.setRegion_num(rs.getInt("REGION_NUM"));
                group.setStatus(rs.getInt("STATUS"));
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return group;
    }
    
    // 총 동행 그룹 수 조회 (지역별 필터링 가능)
    public int getTravelGroupCount(int region_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        int count = 0;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            if(region_num > 0) {
                sql = "SELECT COUNT(*) FROM travelgroup WHERE region_num = ?";
            } else {
                sql = "SELECT COUNT(*) FROM travelgroup";
            }
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            if(region_num > 0) {
                pstmt.setInt(1, region_num);
            }
            
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
    
    // 동행 그룹 목록 조회 (지역별 필터링 가능)
    public List<TravelGroupVO> getTravelGroupList(int region_num, int start, int end) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<TravelGroupVO> list = null;
        String sql = null;
        String where = "";
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // 조건절 구성
            if(region_num > 0) {
                where = " WHERE region_num = ?";
            }
            
            // SQL문 작성 (페이징 처리)
            sql = "SELECT * FROM ("
                + "SELECT a.*, ROWNUM AS rnum FROM ("
                + "SELECT * FROM travelgroup" + where
                + " ORDER BY CREATE_DATE DESC"
                + ") a WHERE ROWNUM <= ?"
                + ") WHERE rnum >= ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            int cnt = 0;
            if(region_num > 0) {
                pstmt.setInt(++cnt, region_num);
            }
            pstmt.setInt(++cnt, end);
            pstmt.setInt(++cnt, start);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<TravelGroupVO>();
            while(rs.next()) {
                TravelGroupVO group = new TravelGroupVO();
                group.setGroup_num(rs.getInt("GROUP_NUM"));
                group.setCreator_num(rs.getInt("CREATOR_NUM"));
                group.setTg_title(rs.getString("TG_TITLE"));
                group.setTg_content(rs.getString("CONTENT"));
                group.setCreate_date(rs.getDate("CREATE_DATE"));
                group.setTravel_date_start(rs.getDate("TRAVEL_DATE_START"));
                group.setTravel_date_end(rs.getDate("TRAVEL_DATE_END"));
                group.setMax_member_count(rs.getInt("MAX_MEMBER_COUNT"));
                group.setRegion_num(rs.getInt("REGION_NUM"));
                group.setStatus(rs.getInt("STATUS"));
                
                list.add(group);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 동행 그룹 업데이트
    public void updateTravelGroup(TravelGroupVO group) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        int cnt = 0;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "UPDATE travelgroup SET tg_title = ?, tg_content = ?, "
                + "travel_date_start = ?, travel_date_end = ?, "
                + "max_member_count = ?, region_num = ?, status = ? "
                + "WHERE group_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setString(++cnt, group.getTg_title());
            pstmt.setString(++cnt, group.getTg_content());
            pstmt.setDate(++cnt, group.getTravel_date_start());
            pstmt.setDate(++cnt, group.getTravel_date_end());
            pstmt.setInt(++cnt, group.getMax_member_count());
            pstmt.setInt(++cnt, group.getRegion_num());
            pstmt.setInt(++cnt, group.getStatus());
            pstmt.setLong(++cnt, group.getGroup_num());
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 동행 그룹 상태 변경
    public void updateTravelGroupStatus(int group_num, int status) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "UPDATE travelgroup SET status = ? WHERE group_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, status);
            pstmt.setInt(2, group_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 동행 그룹 삭제
    public void deleteTravelGroup(long group_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "DELETE FROM travelgroup WHERE group_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, group_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 사용자가 참여한 동행 그룹 목록 조회
    public List<TravelGroupVO> getMyTravelGroupList(long user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<TravelGroupVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성 (사용자가 참여한 그룹과 생성한 그룹 모두 포함)
            sql = "SELECT tg.* FROM travelgroup tg "
                + "LEFT JOIN group_member gm ON tg.group_num = gm.group_num "
                + "WHERE tg.creator_num = ? OR gm.user_num = ? "
                + "ORDER BY tg.create_date DESC";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, user_num);
            pstmt.setLong(2, user_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<TravelGroupVO>();
            while(rs.next()) {
                TravelGroupVO group = new TravelGroupVO();
                group.setGroup_num(rs.getInt("GROUP_NUM"));
                group.setCreator_num(rs.getInt("CREATOR_NUM"));
                group.setTg_title(rs.getString("TG_TITLE"));
                group.setTg_content(rs.getString("CONTENT"));
                group.setCreate_date(rs.getDate("CREATE_DATE"));
                group.setTravel_date_start(rs.getDate("TRAVEL_DATE_START"));
                group.setTravel_date_end(rs.getDate("TRAVEL_DATE_END"));
                group.setMax_member_count(rs.getInt("MAX_MEMBER_COUNT"));
                group.setRegion_num(rs.getInt("REGION_NUM"));
                group.setStatus(rs.getInt("STATUS"));
                
                list.add(group);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
}