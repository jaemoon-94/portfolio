package kr.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.user.vo.UserVO;
import kr.util.DBUtil;

public class UserDAO {

	// 싱글턴 패턴
	private static UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}

	public UserDAO() {
	}

	// 회원가입
	public void insertUser(UserVO user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		long num = 0; // 시퀀스 번호 저장

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// 오토 커밋 해제
			conn.setAutoCommit(false);

			// 회원번호(user_num) 생성
			sql = "SELECT seq_ctuser.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getLong(1);
			}

			// ctuser 테이블에 데이터 삽입
			sql = "INSERT INTO ctuser (user_num, user_id, auth) VALUES (?, ?, 0)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, num); // 회원번호
			pstmt2.setString(2, user.getUserId()); // 회원 아이디
			pstmt2.executeUpdate();

			// user_detail 테이블에 데이터 삽입
			sql = "INSERT INTO user_detail (user_num, user_name, user_pw, user_phone, user_email, gender) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, num); // 회원번호
			pstmt3.setString(2, user.getUserName()); // 회원 이름
			pstmt3.setString(3, user.getUserPw()); // 회원 비밀번호
			pstmt3.setString(4, user.getUserPhone()); // 회원 전화번호
			pstmt3.setString(5, user.getUserEmail()); // 회원 이메일
			pstmt3.setInt(6, user.getGender()); // 성별
			pstmt3.executeUpdate();

			// SQL 실행시 모두 성공하면 commit
			conn.commit();
		} catch (Exception e) {
			// SQL문이 하나라도 실패하면 rollback
			if (conn != null) {
				conn.rollback();
			}
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}

	// ID, Email, Phone 중복 체크
	public boolean checkUniqueInfo(String id, String email, String phone) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";

		// id, email, phone 중 중복 체크할 항목을 설정
		if (id != null && email == null && phone == null) {
			sub_sql += "user_id=?";
		} else if (id == null && email != null && phone == null) {
			sub_sql += "user_email=?";
		} else if (id == null && email == null && phone != null) {
			sub_sql += "user_phone=?";
		}

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM ctuser LEFT OUTER JOIN user_detail USING(user_num) WHERE " + sub_sql;
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			if (id != null && email == null && phone == null) {
				pstmt.setString(1, id);
			} else if (id == null && email != null && phone == null) {
				pstmt.setString(1, email);
			} else if (id == null && email == null && phone != null) {
				pstmt.setString(1, phone);
			}
			// SQL문 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true; // id, email, phone 중복
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return false; // id, email, phone 미중복
	}

	// 로그인 처리
	public UserVO loginUser(String id) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO user = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM ctuser JOIN user_detail " + "USING(user_num) WHERE user_id=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, id);
			// SQL문 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new UserVO();
				user.setUserNum(rs.getLong("user_num")); // 회원번호
				user.setUserId(rs.getString("user_id")); // 아이디
				user.setAuth(rs.getInt("auth")); // 권한
				user.setUserPw(rs.getString("user_pw")); // 비밀번호
				user.setUserPhone(rs.getString("user_phone")); // 전화번호
				user.setUserEmail(rs.getString("user_email")); // 이메일
				user.setGender(rs.getInt("gender")); // 성별
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return user;
	}

	// 회원상세정보
	public UserVO getUser(long user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserVO user = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "SELECT * FROM ctuser JOIN user_detail " + "USING(user_num) WHERE user_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setLong(1, user_num);
			// SQL문 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new UserVO();
				user.setUserNum(rs.getLong("user_num"));
				user.setUserId(rs.getString("user_id"));
				user.setAuth(rs.getInt("auth"));
				user.setUserPw(rs.getString("user_pw"));
				user.setUserName(rs.getString("user_name"));
				user.setUserPhone(rs.getString("user_phone"));
				user.setUserEmail(rs.getString("user_email"));
				user.setGender(rs.getInt("gender"));
				user.setRegDate(rs.getDate("reg_date"));
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return user;
	}

	// 회원정보수정
	public void updateUser(UserVO user) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "UPDATE user_detail SET user_name=?, user_phone=?, "
					+ "user_email=?, gender=?, reg_date=SYSDATE WHERE user_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(++cnt, user.getUserName());
			pstmt.setString(++cnt, user.getUserPhone());
			pstmt.setString(++cnt, user.getUserEmail());
			pstmt.setInt(++cnt, user.getGender());
			pstmt.setLong(++cnt, user.getUserNum());
			// SQL문 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 비밀번호수정
	public void updatePassword(String userPw, long user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "UPDATE user_detail SET user_pw=? WHERE user_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, userPw); // 새 비밀번호
			pstmt.setLong(2, user_num); // 회원번호
			// SQL문 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 프로필 사진 수정
	public void updateMyPhoto(String photo, long user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			// 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "UPDATE user_detail SET photo=? WHERE user_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setString(1, photo);
			pstmt.setLong(2, user_num);
			// SQL문 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 회원탈퇴(회원정보삭제)
	public void deleteUser(long user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			// 자동 커밋 비활성화
			conn.setAutoCommit(false);

			// 예약 상세 정보 삭제
			sql = "DELETE FROM reserv_detail WHERE reserv_num IN (SELECT reserv_num FROM accom_reserv WHERE user_num=?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.executeUpdate();

			// 예약 정보 삭제
			sql = "DELETE FROM accom_reserv WHERE user_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, user_num);
			pstmt2.executeUpdate();

			// user_detail의 레코드 삭제
			sql = "DELETE FROM user_detail WHERE user_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, user_num);
			pstmt3.executeUpdate();

			// user의 레코드 삭제
			sql = "DELETE FROM ctuser WHERE user_num=?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setLong(1, user_num);
			pstmt4.executeUpdate();

			// 모든 SQL문의 실행이 성공하면 커밋
			conn.commit();
		} catch (Exception e) {
			// SQL문이 하나라도 실패하면 롤백
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 전체 내용 개수, 검색 내용 개수
	public int getUserCountByAdmin(String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if (keyword != null && !"".equals(keyword)) {
				if (keyfield.equals("1"))
					sub_sql += "WHERE user_id LIKE '%' || ? || '%'";
				else if (keyfield.equals("2"))
					sub_sql += "WHERE user_name LIKE '%' || ? || '%'";
				else if (keyfield.equals("3"))
					sub_sql += "WHERE user_email LIKE '%' || ? || '%'";
			}

			sql = "SELECT COUNT(*) FROM ctuser LEFT OUTER JOIN " + "user_detail USING(user_num) " + sub_sql;
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
			}
			// SQL문 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}

	// 목록, 검색 목록
	public List<UserVO> getListUserByAdmin(int start, int end, String keyfield, String keyword) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;

		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if (keyword != null && !"".equals(keyword)) {
				if (keyfield.equals("1"))
					sub_sql += "WHERE user_id LIKE '%' || ? || '%'";
				else if (keyfield.equals("2"))
					sub_sql += "WHERE user_name LIKE '%' || ? || '%'";
				else if (keyfield.equals("3"))
					sub_sql += "WHERE user_email LIKE '%' || ? || '%'";
			}

			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM " + "(SELECT * FROM ctuser LEFT OUTER JOIN user_detail "
					+ "USING(user_num) " + sub_sql + " ORDER BY reg_date DESC NULLS LAST)a) "
					+ "WHERE rnum >= ? AND rnum <= ?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			// SQL문 실행
			rs = pstmt.executeQuery();

			list = new ArrayList<UserVO>();
			while (rs.next()) {
				UserVO user = new UserVO();
				user.setUserNum(rs.getLong("user_num"));
				user.setUserId(rs.getString("user_id"));
				user.setAuth(rs.getInt("auth"));
				user.setUserName(rs.getString("user_name"));
				user.setUserPhone(rs.getString("user_phone"));
				user.setUserEmail(rs.getString("user_email"));
				user.setRegDate(rs.getDate("reg_date"));

				list.add(user);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}

	// 회원등급 수정
	public void updateUserByAdmin(int auth, long user_num) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			// 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			// SQL문 작성
			sql = "UPDATE ctuser SET auth=? WHERE user_num=?";
			// PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, auth);
			pstmt.setLong(2, user_num);
			// SQL문 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}