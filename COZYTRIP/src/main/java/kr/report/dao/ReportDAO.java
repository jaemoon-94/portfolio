package kr.report.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.report.vo.ReportVO;
import kr.util.DBUtil;

public class ReportDAO {
	
	private static ReportDAO instance = new ReportDAO();
	
	public static ReportDAO getInstance() {
			return instance;
		}
	private ReportDAO() {}
	
	// 전체 신고 목록 조회
	public List<ReportVO> getAllReports(int start, int end) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReportVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
				+ "(SELECT * FROM accom_report JOIN accom_detail USING(accom_num) "
				+ "ORDER BY report_num DESC)a) "
				+ "WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<ReportVO>();
			while (rs.next()) {
				ReportVO report = new ReportVO();
				report.setReport_num(rs.getLong("report_num"));
				report.setAccom_num(rs.getLong("accom_num"));
				report.setUser_num(rs.getLong("user_num"));
				report.setAccom_name(rs.getString("accom_name"));
				report.setAddress1(rs.getString("address1"));
				report.setReason(rs.getString("reason"));
				report.setReport_date(rs.getDate("report_date"));
				report.setStatus(rs.getInt("status"));
				
				list.add(report);
			} // while
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		} // try_catch
		
		return list;
	}
	
	// 신고 상태 업데이트
	public void updateReportStatus(long report_num, int status) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE accom_report SET status = ? WHERE report_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status);
			pstmt.setLong(2, report_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		} // try_catch
	}
	
	// 신고 삭제
	public void deleteReport(long report_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM accom_report WHERE report_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, report_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		} // try_catch
	}
	
	// 전체 신고 수 조회
    public int getReportCount(Long user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        int count = 0;
        
        try {
            conn = DBUtil.getConnection();
            sql = "SELECT COUNT(*) FROM accom_report";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            } // if
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        } // try_catch
        
        return count;
    }
	
	// 신고 목록 보기
	public List<ReportVO> getListReport(int start, int end, long user_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReportVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM accom_report JOIN accom_detail USING(accom_num) "
					+ "WHERE user_num = ? ORDER BY report_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<ReportVO>();
			while (rs.next()) {
				ReportVO report = new ReportVO();
				report.setReport_num(rs.getLong("report_num"));
				report.setAccom_num(rs.getLong("accom_num"));
				report.setUser_num(rs.getLong("user_num"));
				report.setAccom_name(rs.getString("accom_name"));
				report.setAddress1(rs.getString("address1"));
				report.setReason(rs.getString("reason"));
				report.setReport_date(rs.getDate("report_date"));
				report.setStatus(rs.getInt("status"));
				
				list.add(report);
			} // while
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		} // try_catch
		
		return list;
	}
	
	// 신고 작성
	public void insertReport(ReportVO report) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO accom_report (report_num,user_num,accom_num,"
					+ "reason,status,report_date) VALUES "
					+ "(seq_accom_report.nextval,?,?,?,?,SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, report.getUser_num());
			pstmt.setLong(2, report.getAccom_num());
			pstmt.setString(3, report.getReason());
			pstmt.setInt(4, report.getStatus());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		} // try_catch
	}

}

































