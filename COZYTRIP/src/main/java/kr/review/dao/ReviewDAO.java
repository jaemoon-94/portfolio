package kr.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.review.vo.ReviewVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;

public class ReviewDAO {
	
	public static ReviewDAO instance = new ReviewDAO();
	
	public static ReviewDAO getInstance() {
		return instance;
	}
	
	private ReviewDAO() {}
	
	// 리뷰 정보
	public ReviewVO getReview(long review_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ReviewVO review = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM accom_review JOIN accom_reserv USING(reserv_num) WHERE review_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, review_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				review = new ReviewVO();
				review.setReview_num(rs.getLong("review_num"));
				review.setUser_num(rs.getLong("user_num"));
				review.setAccom_num(rs.getLong("accom_num"));
			} // if
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		} // try_catch
		
		return review;
	}
	
	// 리뷰 목록(숙소 상세)
	public List<ReviewVO> getListReviewByAccom_num(long accom_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReviewVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM accom_review JOIN accom_reserv USING(reserv_num) "
					+ "JOIN user_detail USING(user_num) WHERE accom_num=?)a)"; 
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, accom_num);
			rs = pstmt.executeQuery();
			list = new ArrayList<ReviewVO>();
			while (rs.next()) {
				ReviewVO review = new ReviewVO();
				review.setAccom_num(rs.getLong("accom_num"));
				review.setUser_name(rs.getString("user_name"));
				review.setContent(rs.getString("content"));
				review.setRating(rs.getInt("rating"));
				review.setReview_date(DurationFromNow.getTimeDiffLabel(rs.getString("review_date")));
				if (rs.getString("review_modifydate")!=null) {
					review.setReview_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("review_modifydate")));
				} // if
				
				list.add(review);
			} // while
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	// 리뷰 작성 가능 숙소 목록
	public List<ReviewVO> getListReviewable(int start, int end, long user_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReviewVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM reserv_detail JOIN accom_reserv USING(reserv_num) JOIN accom_detail USING(accom_num) "
					+ "LEFT OUTER JOIN accom_review USING(reserv_num) JOIN accom_image USING(accom_num) "
					+ "WHERE main = 1 AND reserv_status = 1 AND check_in_date <= SYSDATE AND review_num IS NULL AND "
					+ "reserv_num IN (SELECT reserv_num FROM accom_reserv WHERE user_num = ?) "
					+ "ORDER BY reserv_num DESC)a)WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<ReviewVO>();
			while (rs.next()) {
				ReviewVO review = new ReviewVO();
				review.setReserv_code(rs.getString("reserv_code"));
				review.setReserv_num(rs.getLong("reserv_num"));
				review.setAccom_name(rs.getString("accom_name"));
				review.setAccom_num(rs.getLong("accom_num"));
				review.setCheck_in_date(rs.getDate("check_in_date"));
				review.setCheck_out_date(rs.getDate("check_out_date"));
				review.setImage_name(rs.getString("image_name"));
				review.setReview_num(rs.getLong("review_num"));
				
				list.add(review);
			} // while
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		} // try_catch
		
		return list;
	}
	
	// 리뷰 작성
	public void insertReview(ReviewVO review) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO accom_review (review_num,reserv_num,"
					+ "content,rating) VALUES "
					+ "(seq_accom_review.nextval,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(++cnt, review.getReserv_num());
			pstmt.setString(++cnt, review.getContent());
			pstmt.setInt(++cnt, review.getRating());
			
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		} // try_catch
	}
	
	// 내가 쓴 리뷰 개수
	public int getReviewCount(long user_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM accom_review JOIN "
					+ "reserv_detail USING(reserv_num)JOIN accom_reserv USING(reserv_num) JOIN "
					+ "user_detail USING(user_num)WHERE user_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
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
	
	// 내가 쓴 리뷰 글 목록
	public List<ReviewVO> getListReview(int start, int end, long user_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ReviewVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM accom_review "
					+ "JOIN reserv_detail USING(reserv_num) "
					+ "JOIN accom_reserv USING(reserv_num) JOIN accom_detail USING(accom_num) "
					+ "JOIN user_detail USING(user_num) JOIN accom_image USING(accom_num) "
					+ "WHERE main = 1 AND user_num = ? ORDER BY reserv_num DESC)a)WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, user_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<ReviewVO>();
			while (rs.next()) {
				ReviewVO review = new ReviewVO();
				review.setReview_num(rs.getLong("review_num"));
				review.setReview_date(DurationFromNow.getTimeDiffLabel(rs.getString("review_date")));
				if (rs.getString("review_modifydate")!=null) {
					review.setReview_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("review_modifydate")));
				} // if
				review.setRating(rs.getInt("rating"));
				review.setContent(rs.getString("content"));
				review.setAccom_name(rs.getString("accom_name"));
				review.setImage_name(rs.getString("image_name"));
				review.setImage_main(rs.getInt("main"));
				review.setReserv_num(rs.getLong("reserv_num"));
				
				list.add(review);
			} // while
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		} // try_catch
		
		return list;
	}
	
	// 숙소 평균 평점
	public Double getAverageRatingByAccomNum(long accom_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Double avgRating = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT AVG(rating) FROM accom_review "
				+ "JOIN reserv_detail USING(reserv_num) "
				+ "JOIN accom_reserv USING(reserv_num) "
				+ "WHERE accom_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, accom_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				double result = rs.getDouble(1);
				if (!rs.wasNull()) {
					avgRating = result;
				}
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return avgRating; // 평균이 없으면 null 반환
	}
	
	// 리뷰 삭제
	public void deleteReview(long review_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM accom_review WHERE review_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, review_num);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		} // try_catch
	}
	
	//리뷰 수정
	public void updateReview(ReviewVO review) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE accom_review SET content=?,"
					+ "review_modifydate=SYSDATE,rating=? WHERE review_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, review.getContent());
			pstmt.setInt(++cnt, review.getRating());
			pstmt.setLong(++cnt, review.getReview_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		} // try_catch
	}

}


































