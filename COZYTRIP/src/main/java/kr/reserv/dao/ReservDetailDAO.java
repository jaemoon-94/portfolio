package kr.reserv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.reserv.vo.ReservDetailVO;
import kr.util.DBUtil;

public class ReservDetailDAO {
    // 싱글톤 패턴
    private static ReservDetailDAO instance = new ReservDetailDAO();
    
    public static ReservDetailDAO getInstance() {
        return instance;
    }
    
    private ReservDetailDAO() {}
    
    // 예약 상세 정보 추가
    public void insertReservDetail(ReservDetailVO detail) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "INSERT INTO reserv_detail (reserv_num, check_in_date, check_out_date, "
                + "people_count, reserv_date, reserv_status, payment_status, request, total_price) "
                + "VALUES (?, ?, ?, ?, SYSDATE, ?, ?, ?, ?)";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, detail.getReserv_num());
            pstmt.setDate(2, detail.getCheck_in_date());
            pstmt.setDate(3, detail.getCheck_out_date());
            pstmt.setInt(4, detail.getPeople_count());
            pstmt.setInt(5, detail.getReserv_status());
            pstmt.setInt(6, detail.getPayment_status());
            pstmt.setString(7, detail.getRequest());
            pstmt.setInt(8, detail.getTotal_price());
            
            // SQL문 실행
            pstmt.executeUpdate();
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 예약 번호로 예약 상세 정보 조회
    public ReservDetailVO getReservDetail(long reserv_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReservDetailVO detail = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM reserv_detail WHERE reserv_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, reserv_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                detail = new ReservDetailVO();
                detail.setReserv_num(rs.getLong("reserv_num"));
                detail.setCheck_in_date(rs.getDate("check_in_date"));
                detail.setCheck_out_date(rs.getDate("check_out_date"));
                detail.setPeople_count(rs.getInt("people_count"));
                detail.setReserv_date(rs.getDate("reserv_date"));
                detail.setReserv_status(rs.getInt("reserv_status"));
                detail.setPayment_status(rs.getInt("payment_status"));
                detail.setRequest(rs.getString("request"));
                detail.setTotal_price(rs.getInt("total_price"));
            }
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return detail;
    }
    
    // 예약 상태 업데이트
    public void updateReservStatus(int reserv_num, int status) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "UPDATE reserv_detail SET reserv_status = ? WHERE reserv_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, status);
            pstmt.setInt(2, reserv_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 결제 상태 업데이트
    public void updatePaymentStatus(int reserv_num, int status) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "UPDATE reserv_detail SET payment_status = ? WHERE reserv_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, status);
            pstmt.setInt(2, reserv_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 사용자별 예약 상세 목록 조회
    public List<ReservDetailVO> getReservDetailListByUser(int user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReservDetailVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성 (예약 테이블과 조인)
            sql = "SELECT d.* FROM reserv_detail d "
                + "JOIN reserv r ON d.reserv_num = r.reserv_num "
                + "WHERE r.user_num = ? "
                + "ORDER BY d.reserv_date DESC";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, user_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<ReservDetailVO>();
            while(rs.next()) {
                ReservDetailVO detail = new ReservDetailVO();
                detail.setReserv_num(rs.getInt("reserv_num"));
                detail.setCheck_in_date(rs.getDate("check_in_date"));
                detail.setCheck_out_date(rs.getDate("check_out_date"));
                detail.setPeople_count(rs.getInt("people_count"));
                detail.setReserv_date(rs.getDate("reserv_date"));
                detail.setReserv_status(rs.getInt("reserv_status"));
                detail.setPayment_status(rs.getInt("payment_status"));
                detail.setRequest(rs.getString("request"));
                detail.setTotal_price(rs.getInt("total_price"));
                
                list.add(detail);
            }
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 숙소별 예약 상세 목록 조회 (호스트용)
    public List<ReservDetailVO> getReservDetailListByAccom(int accom_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReservDetailVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성 (예약 테이블과 조인)
            sql = "SELECT d.* FROM reserv_detail d "
                + "JOIN reserv r ON d.reserv_num = r.reserv_num "
                + "WHERE r.accom_num = ? "
                + "ORDER BY d.check_in_date ASC";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, accom_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<ReservDetailVO>();
            while(rs.next()) {
                ReservDetailVO detail = new ReservDetailVO();
                detail.setReserv_num(rs.getInt("reserv_num"));
                detail.setCheck_in_date(rs.getDate("check_in_date"));
                detail.setCheck_out_date(rs.getDate("check_out_date"));
                detail.setPeople_count(rs.getInt("people_count"));
                detail.setReserv_date(rs.getDate("reserv_date"));
                detail.setReserv_status(rs.getInt("reserv_status"));
                detail.setPayment_status(rs.getInt("payment_status"));
                detail.setRequest(rs.getString("request"));
                detail.setTotal_price(rs.getInt("total_price"));
                
                list.add(detail);
            }
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 예약 상세 정보 삭제 (예약 취소시)
    public void deleteReservDetail(int reserv_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "DELETE FROM reserv_detail WHERE reserv_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, reserv_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 날짜 범위로 예약 확인 (중복 예약 방지)
    public boolean isDateRangeAvailable(int accom_num, java.sql.Date checkInDate, java.sql.Date checkOutDate) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        boolean isAvailable = true;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성 (기존 예약과 날짜가 겹치는지 확인)
            sql = "SELECT COUNT(*) FROM reserv_detail d "
                + "JOIN reserv r ON d.reserv_num = r.reserv_num "
                + "WHERE r.accom_num = ? AND d.reserv_status != 2 "
                + "AND ((d.check_in_date <= ? AND d.check_out_date > ?) "
                + "OR (d.check_in_date < ? AND d.check_out_date >= ?))";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, accom_num);
            pstmt.setDate(2, checkOutDate);
            pstmt.setDate(3, checkInDate);
            pstmt.setDate(4, checkOutDate);
            pstmt.setDate(5, checkInDate);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next() && rs.getInt(1) > 0) {
                isAvailable = false; // 날짜 범위가 겹치는 예약이 있음
            }
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return isAvailable;
    }
    
    // 예약 요청 사항 업데이트
    public void updateRequest(int reserv_num, String request) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "UPDATE reserv_detail SET request = ? WHERE reserv_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setString(1, request);
            pstmt.setInt(2, reserv_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
            
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
}