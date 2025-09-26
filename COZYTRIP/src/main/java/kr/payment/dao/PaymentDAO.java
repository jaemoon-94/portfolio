package kr.payment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.payment.vo.PaymentVO;
import kr.util.DBUtil;

public class PaymentDAO {
    // 싱글턴 패턴
    private static PaymentDAO instance = new PaymentDAO();
    
    public static PaymentDAO getInstance() {
        return instance;
    }
    
    private PaymentDAO() {}
    
    // 다음 payment_num 가져오기
    public long getNextPaymentNum() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        long payment_num = 0;
        
        try {
            conn = DBUtil.getConnection();
            sql = "SELECT SEQ_PAYMENT.nextval FROM dual";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                payment_num = rs.getLong(1);
            }
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return payment_num;
    }
    
    // 결제 정보 등록
    public void insertPayment(PaymentVO payment) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            conn = DBUtil.getConnection();
            // 결제 정보 삽입 (TID 포함)
            sql = "INSERT INTO payment (payment_num, reserv_num, amount, payment_method, payment_status, tid) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, payment.getPayment_num());
            pstmt.setLong(2, payment.getReserv_num());
            pstmt.setInt(3, payment.getAmount());
            pstmt.setString(4, payment.getPayment_method());
            pstmt.setString(5, payment.getPayment_status());
            pstmt.setString(6, payment.getTid());
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
 // 결제 고유ID 업데이트 메서드
    public void updatePaymentTid(long payment_num, String tid) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "UPDATE payment SET tid = ? WHERE payment_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setString(1, tid);
            pstmt.setLong(2, payment_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 결제 정보 조회
    public PaymentVO getPayment(long payment_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PaymentVO payment = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM payment WHERE payment_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, payment_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                payment = new PaymentVO();
                payment.setPayment_num(rs.getLong("payment_num"));
                payment.setReserv_num(rs.getInt("reserv_num"));
                payment.setAmount(rs.getInt("amount"));
                payment.setPayment_status(rs.getString("payment_status"));
                payment.setPayment_method(rs.getString("payment_method"));
                payment.setPayment_date(rs.getDate("payment_date"));
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return payment;
    }
    
    // 예약별 결제 정보 조회
    public PaymentVO getPaymentByReserv(int reserv_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PaymentVO payment = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM payment WHERE reserv_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, reserv_num);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                payment = new PaymentVO();
                payment.setPayment_num(rs.getLong("payment_num"));
                payment.setReserv_num(rs.getInt("reserv_num"));
                payment.setAmount(rs.getInt("amount"));
                payment.setPayment_status(rs.getString("payment_status"));
                payment.setPayment_method(rs.getString("payment_method"));
                payment.setPayment_date(rs.getDate("payment_date"));
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return payment;
    }
    
    // 결제 상태 업데이트
    public void updatePaymentStatus(Long payment_num, String payment_status) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "UPDATE payment SET payment_status = ? WHERE payment_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setString(1, payment_status);
            pstmt.setLong(2, payment_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 결제 취소
    public void cancelPayment(long payment_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "UPDATE payment SET payment_status = 'cancelled' WHERE payment_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setLong(1, payment_num);
            
            // SQL문 실행
            pstmt.executeUpdate();
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 사용자별 결제 내역 조회
    public List<PaymentVO> getPaymentListByUser(int user_num, int start, int end) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PaymentVO> list = null;
        String sql = null;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
                + "(SELECT p.* FROM payment p "
                + "JOIN accom_reserv r ON p.reserv_num = r.reserv_num "
                + "WHERE r.user_num = ? "
                + "ORDER BY p.payment_date DESC)a) "
                + "WHERE rnum >= ? AND rnum <= ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, user_num);
            pstmt.setInt(2, start);
            pstmt.setInt(3, end);
            
            // SQL문 실행
            rs = pstmt.executeQuery();
            
            list = new ArrayList<PaymentVO>();
            while(rs.next()) {
                PaymentVO payment = new PaymentVO();
                payment.setPayment_num(rs.getLong("payment_num"));
                payment.setReserv_num(rs.getInt("reserv_num"));
                payment.setAmount(rs.getInt("amount"));
                payment.setPayment_status(rs.getString("payment_status"));
                payment.setPayment_method(rs.getString("payment_method"));
                payment.setPayment_date(rs.getDate("payment_date"));
                
                list.add(payment);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // payment_num으로 tid 조회
    public String getTidByPaymentNum(long payment_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        String tid = null;
        
        try {
            conn = DBUtil.getConnection();
            sql = "SELECT tid FROM payment WHERE payment_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, payment_num);
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
                tid = rs.getString("tid");
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return tid;
    }
    
    // 총 결제 건수 조회 (사용자별)
    public int getPaymentCountByUser(int user_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        int count = 0;
        
        try {
            // 커넥션풀로부터 커넥션 할당
            conn = DBUtil.getConnection();
            
            // SQL문 작성
            sql = "SELECT COUNT(*) FROM payment p "
                + "JOIN accom_reserv r ON p.reserv_num = r.reserv_num "
                + "WHERE r.user_num = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?에 데이터 바인딩
            pstmt.setInt(1, user_num);
            
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