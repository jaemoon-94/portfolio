package kr.reserv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.reserv.vo.ReservVO;
import kr.reserv.vo.ReservDetailVO;
import kr.accom.vo.AccomVO;
import kr.util.DBUtil;

public class ReservDAO {
    // 싱글턴 패턴
    private static ReservDAO instance = new ReservDAO();
    
    public static ReservDAO getInstance() {
        return instance;
    }
    
    private ReservDAO() {}
    
    // 오늘 날짜의 마지막 예약 번호 조회
    private String getLastReservCodeToday() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String lastCode = null;
        
        try {
            conn = DBUtil.getConnection();
            
            String sql = "SELECT reserv_code FROM accom_reserv "
                       + "WHERE SUBSTR(reserv_code, 1, 6) = TO_CHAR(SYSDATE, 'YYMMDD') "
                       + "ORDER BY reserv_code DESC FETCH FIRST 1 ROW ONLY";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                lastCode = rs.getString(1);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return lastCode;
    }
    
    // 새로운 예약 코드 생성
    private String generateNewReservCode() throws Exception {
        String today = new java.text.SimpleDateFormat("yyMMdd").format(new java.util.Date());
        String lastCode = getLastReservCodeToday();
        
        int sequence = 1;
        if(lastCode != null) {
            try {
                sequence = Integer.parseInt(lastCode.substring(6)) + 1;
                if(sequence > 9999) sequence = 1; // Reset if exceeds 9999
            } catch(Exception e) {
                sequence = 1;
            }
        }
        
        return String.format("%s%04d", today, sequence);
    }

    // 예약 생성 (예약 + 예약 상세 정보 함께 처리)
    public String insertReserv(ReservVO reserv, ReservDetailVO detail) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        String sql = null;
        int cnt = 0;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            // 오토 커밋 해제
            conn.setAutoCommit(false);
            
            // 새로운 예약 코드 생성
            String reservCode = generateNewReservCode();
            reserv.setReserv_code(reservCode);
            
            // 1. 예약 정보 삽입
            sql = "INSERT INTO accom_reserv (reserv_num, user_num, accom_num, reserv_code) "
                + "VALUES (SEQ_ACCOM_RESERV.nextval, ?, ?, ?)";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql, new String[] {"reserv_num"});
            
            // ?에 데이터 바인딩
            pstmt.setLong(++cnt, reserv.getUser_num());
            pstmt.setLong(++cnt, reserv.getAccom_num());
            pstmt.setString(++cnt, reserv.getReserv_code());
            
            // SQL문 실행
            pstmt.executeUpdate();
            
            // 생성된 예약 번호 구하기
            rs = pstmt.getGeneratedKeys();
            int reserv_num = 0;
            if(rs.next()) {
                reserv_num = rs.getInt(1);
                detail.setReserv_num(reserv_num);
            }
            
            // 2. 예약 상세 정보 삽입
            sql = "INSERT INTO reserv_detail (reserv_num, request, payment_status, "
                + "check_in_date, check_out_date, reserv_status, people_count, "
                + "total_price, reserv_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)";
            
            // PreparedStatement 객체 생성
            pstmt2 = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            cnt = 0; // 카운터 초기화
            pstmt2.setLong(++cnt, detail.getReserv_num());
            pstmt2.setString(++cnt, detail.getRequest());
            pstmt2.setInt(++cnt, detail.getPayment_status());
            pstmt2.setDate(++cnt, detail.getCheck_in_date());
            pstmt2.setDate(++cnt, detail.getCheck_out_date());
            pstmt2.setInt(++cnt, detail.getReserv_status());
            pstmt2.setInt(++cnt, detail.getPeople_count());
            pstmt2.setInt(++cnt, detail.getTotal_price());
            
            // SQL문 실행
            pstmt2.executeUpdate();
            
            // 커밋
            conn.commit();
            
            return reserv.getReserv_code();
        } catch(Exception e) {
            // 롤백
            conn.rollback();
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt2, null);
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 예약 정보 조회 (예약 + 예약 상세 정보)
    public ReservVO getReserv(long reserv_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReservVO reserv = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT r.reserv_num, r.user_num, r.accom_num, r.reserv_code, "
                + "d.request, d.payment_status, d.cancel_reason, d.cancel_date, "
                + "d.check_in_date, d.check_out_date, d.reserv_status, d.people_count, "
                + "d.total_price, d.reserv_date "
                + "FROM accom_reserv r "
                + "JOIN reserv_detail d ON r.reserv_num = d.reserv_num "
                + "WHERE r.reserv_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, reserv_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                reserv = new ReservVO();
                reserv.setReserv_num(rs.getInt("reserv_num"));
                reserv.setUser_num(rs.getInt("user_num"));
                reserv.setAccom_num(rs.getInt("accom_num"));
                reserv.setReserv_code(rs.getString("reserv_code"));
                
                ReservDetailVO detail = new ReservDetailVO();
                detail.setReserv_num(rs.getInt("reserv_num"));
                detail.setRequest(rs.getString("request"));
                detail.setPayment_status(rs.getInt("payment_status"));
                detail.setCancel_reason(rs.getString("cancel_reason"));
                detail.setCancel_date(rs.getDate("cancel_date"));
                detail.setCheck_in_date(rs.getDate("check_in_date"));
                detail.setCheck_out_date(rs.getDate("check_out_date"));
                detail.setReserv_status(rs.getInt("reserv_status"));
                detail.setPeople_count(rs.getInt("people_count"));
                detail.setTotal_price(rs.getInt("total_price"));
                detail.setReserv_date(rs.getDate("reserv_date"));
                
                reserv.setReservDetailVO(detail);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return reserv;
    }
    
    // 예약 목록 조회 (사용자별)
    public List<ReservVO> getReservListByUser(long user_num, int start, int end) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReservVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성 (페이징 처리) - 숙소 정보도 함께 조회
            sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
                + "(SELECT r.reserv_num, r.user_num, r.accom_num, r.reserv_code, "
                + "d.request, d.payment_status, d.cancel_reason, d.cancel_date, "
                + "d.check_in_date, d.check_out_date, d.reserv_status, d.people_count, "
                + "d.total_price, d.reserv_date, "
                + "ad.accom_name, ad.address1, ad.address2, "
                + "(SELECT ai.image_name FROM accom_image ai WHERE ai.accom_num = ad.accom_num AND ai.main = 1) as image_name "
                + "FROM accom_reserv r "
                + "JOIN reserv_detail d ON r.reserv_num = d.reserv_num "
                + "JOIN accom_detail ad ON r.accom_num = ad.accom_num "
                + "WHERE r.user_num = ? "
                + "ORDER BY r.reserv_num DESC) a) "
                + "WHERE rnum >= ? AND rnum <= ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, user_num);
            pstmt.setInt(2, start);
            pstmt.setInt(3, end);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<ReservVO>();
            while(rs.next()) {
                ReservVO reserv = new ReservVO();
                reserv.setReserv_num(rs.getInt("reserv_num"));
                reserv.setUser_num(rs.getInt("user_num"));
                reserv.setAccom_num(rs.getInt("accom_num"));
                reserv.setReserv_code(rs.getString("reserv_code"));
                
                ReservDetailVO detail = new ReservDetailVO();
                detail.setReserv_num(rs.getInt("reserv_num"));
                detail.setRequest(rs.getString("request"));
                detail.setPayment_status(rs.getInt("payment_status"));
                detail.setCancel_reason(rs.getString("cancel_reason"));
                detail.setCancel_date(rs.getDate("cancel_date"));
                detail.setCheck_in_date(rs.getDate("check_in_date"));
                detail.setCheck_out_date(rs.getDate("check_out_date"));
                detail.setReserv_status(rs.getInt("reserv_status"));
                detail.setPeople_count(rs.getInt("people_count"));
                detail.setTotal_price(rs.getInt("total_price"));
                detail.setReserv_date(rs.getDate("reserv_date"));
                
                // 숙소 정보 설정
                AccomVO accom = new AccomVO();
                accom.setAccom_name(rs.getString("accom_name"));
                accom.setAddress1(rs.getString("address1"));
                accom.setAddress2(rs.getString("address2"));
                accom.setImage_name(rs.getString("image_name"));
                
                reserv.setReservDetailVO(detail);
                reserv.setAccomVO(accom);
                list.add(reserv);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 총 예약 수 조회 (사용자별)
    public int getReservCountByUser(long user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        int count = 0;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT COUNT(*) FROM accom_reserv WHERE user_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, user_num);
            
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
    
    // 예약 취소
    public void cancelReserv(int reserv_num, String cancel_reason) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "UPDATE reserv_detail SET reserv_status = ?, "
                + "cancel_reason = ?, cancel_date = SYSDATE "
                + "WHERE reserv_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, 2); // 예약 상태를 '취소'로 설정 (상태 코드 2 가정)
            pstmt.setString(2, cancel_reason);
            pstmt.setInt(3, reserv_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 예약 상태 업데이트
    public void updateReservStatus(long reserv_num, int reserv_status) throws Exception {
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
            pstmt.setInt(1, reserv_status);
            pstmt.setLong(2, reserv_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 결제 상태 업데이트
    public void updatePaymentStatus(int reserv_num, int payment_status) throws Exception {
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
            pstmt.setInt(1, payment_status);
            pstmt.setInt(2, reserv_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 예약 목록 조회 (관리자용, 모든 예약)
    public List<ReservVO> getReservList(int start, int end) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReservVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성 (페이징 처리)
            sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
                + "(SELECT r.reserv_num, r.user_num, r.accom_num, r.reserv_code, "
                + "d.request, d.payment_status, d.cancel_reason, d.cancel_date, "
                + "d.check_in_date, d.check_out_date, d.reserv_status, d.people_count, "
                + "d.total_price, d.reserv_date "
                + "FROM accom_reserv r "
                + "JOIN reserv_detail d ON r.reserv_num = d.reserv_num "
                + "ORDER BY d.reserv_date DESC)a) "
                + "WHERE rnum >= ? AND rnum <= ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<ReservVO>();
            while(rs.next()) {
                ReservVO reserv = new ReservVO();
                reserv.setReserv_num(rs.getInt("reserv_num"));
                reserv.setUser_num(rs.getInt("user_num"));
                reserv.setAccom_num(rs.getInt("accom_num"));
                reserv.setReserv_code(rs.getString("reserv_code"));
                
                ReservDetailVO detail = new ReservDetailVO();
                detail.setReserv_num(rs.getInt("reserv_num"));
                detail.setRequest(rs.getString("request"));
                detail.setPayment_status(rs.getInt("payment_status"));
                detail.setCancel_reason(rs.getString("cancel_reason"));
                detail.setCancel_date(rs.getDate("cancel_date"));
                detail.setCheck_in_date(rs.getDate("check_in_date"));
                detail.setCheck_out_date(rs.getDate("check_out_date"));
                detail.setReserv_status(rs.getInt("reserv_status"));
                detail.setPeople_count(rs.getInt("people_count"));
                detail.setTotal_price(rs.getInt("total_price"));
                detail.setReserv_date(rs.getDate("reserv_date"));
                
                reserv.setReservDetailVO(detail);
                list.add(reserv);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
 // 예약 코드로 예약 정보 조회
    public ReservVO getReservByCode(String reserv_code) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ReservVO reserv = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT r.reserv_num, r.user_num, r.accom_num, r.reserv_code, "
                + "d.request, d.payment_status, d.cancel_reason, d.cancel_date, "
                + "d.check_in_date, d.check_out_date, d.reserv_status, d.people_count, "
                + "d.total_price, d.reserv_date, "
                + "ad.accom_name, ad.address1, ad.address2, "
                + "(SELECT ai.image_name FROM accom_image ai WHERE ai.accom_num = ad.accom_num AND ai.main = 1) as image_name "
                + "FROM accom_reserv r "
                + "JOIN reserv_detail d ON r.reserv_num = d.reserv_num "
                + "JOIN accom_detail ad ON r.accom_num = ad.accom_num "
                + "WHERE r.reserv_code = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setString(1, reserv_code);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                reserv = new ReservVO();
                reserv.setReserv_num(rs.getInt("reserv_num"));
                reserv.setUser_num(rs.getInt("user_num"));
                reserv.setAccom_num(rs.getInt("accom_num"));
                reserv.setReserv_code(rs.getString("reserv_code"));
                
                // 숙소 정보 설정
                AccomVO accom = new AccomVO();
                accom.setAccom_num(rs.getLong("accom_num"));
                accom.setAccom_name(rs.getString("accom_name"));
                accom.setAddress1(rs.getString("address1"));
                accom.setAddress2(rs.getString("address2"));
                accom.setImage_name(rs.getString("image_name"));
                reserv.setAccomVO(accom);
                
                // 예약 상세 정보 설정
                ReservDetailVO detail = new ReservDetailVO();
                detail.setReserv_num(rs.getInt("reserv_num"));
                detail.setRequest(rs.getString("request"));
                detail.setPayment_status(rs.getInt("payment_status"));
                detail.setCancel_reason(rs.getString("cancel_reason"));
                detail.setCancel_date(rs.getDate("cancel_date"));
                detail.setCheck_in_date(rs.getDate("check_in_date"));
                detail.setCheck_out_date(rs.getDate("check_out_date"));
                detail.setReserv_status(rs.getInt("reserv_status"));
                detail.setPeople_count(rs.getInt("people_count"));
                detail.setTotal_price(rs.getInt("total_price"));
                detail.setReserv_date(rs.getDate("reserv_date"));
                
                reserv.setReservDetailVO(detail);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return reserv;
    }
    
    // 총 예약 수 조회 (관리자용)
    public int getReservCount() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        int count = 0;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT COUNT(*) FROM accom_reserv";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
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