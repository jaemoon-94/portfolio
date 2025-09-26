package kr.accom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.accom.vo.AccomVO;
import kr.accom.vo.WishListVO;
import kr.util.DBUtil;

public class WishListDAO {
	
	private static WishListDAO instance = new WishListDAO();
	public static WishListDAO getInstance() {
		return instance;
	}
	private WishListDAO() {}
	
	// 위시리스트 체크 여부
	public WishListVO selectWish(WishListVO wishVO) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WishListVO wish = null;
		String sql = null;
		
		try {
			
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM wishlist WHERE accom_num=? AND user_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, wishVO.getAccom_num());
			pstmt.setLong(2, wishVO.getUser_num());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("진입~~");
				wish = new WishListVO();
				wish.setAccom_num(rs.getLong("accom_num"));
				wish.setUser_num(rs.getLong("user_num"));
				wish.setWish_num(rs.getLong("wish_num"));
			} // if
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		} // try_catch
		
		return wish;
	}
	
	// 위시리스트 등록 
	public void insertWish(WishListVO wishVO) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO wishlist (wish_num,accom_num,user_num) VALUES (SEQ_WISHLIST.nextval,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, wishVO.getAccom_num());
			pstmt.setLong(2, wishVO.getUser_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		} // try_catch
	}
	
	// 위시리스트 삭제
	public void deleteWish(WishListVO wishVO) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM wishlist WHERE accom_num=? AND user_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, wishVO.getAccom_num());
			pstmt.setLong(2, wishVO.getUser_num());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		} // try_catch
		
	}

	// 내가 선택한 위시리스트 목록
	public List<AccomVO> getListAccomWishList(int start, int end, long user_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AccomVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*, rownum rnum "
					+ "FROM (SELECT * FROM accom_detail JOIN wishlist "
					+ "USING(accom_num) JOIN ctuser USING (user_num) "
					+ "JOIN accom_image USING(accom_num) "
					+ "WHERE main = 1 AND user_num = ? ORDER BY accom_num DESC)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<AccomVO>();
			while (rs.next()) {
				AccomVO accom = new AccomVO();
				accom.setAccom_num(rs.getLong("accom_num"));
				accom.setAccom_name(rs.getString("accom_name"));
				accom.setAccom_date(rs.getDate("accom_date"));
				accom.setAddress1(rs.getString("address1"));
				accom.setImage_name(rs.getString("image_name"));
				
				list.add(accom);
			} // while
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		} // try_catch
		
		return list;
	}
}

































