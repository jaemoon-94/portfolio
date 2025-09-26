package kr.accom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.accom.vo.AccomVO;
import kr.util.DBUtil;

public class AccomDAO {
	
	private static AccomDAO instance = new AccomDAO();
	
	public static AccomDAO getInstance() {
		return instance;
	}
	private AccomDAO() {}
	// 전체 숙소 개수
	public int getAccomCount(String keyfield, String keyword, int accom_status) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = null;
	    String sub_sql = "";
	    int count = 0;
	    int cnt = 0;

	    try {
	        conn = DBUtil.getConnection();

	        // keyword가 있는 경우 추가
	        if (keyword != null && !"".equals(keyword)) {
	        	sub_sql = "AND (ad.accom_name LIKE '%' || ? || '%' "
	        			+ "OR r.region_name LIKE '%' || ? || '%' "
	        			+ "OR ad.address1 LIKE '%' || ? || '%')";
	        }

	        sql = "SELECT COUNT(*) FROM accom a "
	        		+ "JOIN accom_detail ad ON a.accom_num = ad.accom_num "
	        		+ "JOIN region r ON ad.region_num = r.region_num "
	        		+ "WHERE ad.accom_status > ? " + sub_sql;
	        pstmt = conn.prepareStatement(sql);

	        // accom_status와 keyword 파라미터 설정
	        pstmt.setInt(++cnt, accom_status); // accom_status 파라미터
	        if (keyword != null && !"".equals(keyword)) {
	            pstmt.setString(++cnt, keyword); // keyword 파라미터
	            pstmt.setString(++cnt, keyword); // keyword 파라미터
	            pstmt.setString(++cnt, keyword); // keyword 파라미터
	        }

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

	// 숙소 목록 / 검색 목록
	public List<AccomVO> getListAccom(int start, int end, String keyfield, String keyword, int accom_status) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<AccomVO> list = null;
	    String sql = null;
	    String sub_sql = "";
	    int cnt = 0;

	    try {
	        conn = DBUtil.getConnection();
	        
	        if (keyword != null && !"".equals(keyword)) {
	        	sub_sql = "AND (ad.accom_name LIKE '%' || ? || '%' "
	        			+ "OR r.region_name LIKE '%' || ? || '%' "
	        			+ "OR ad.address1 LIKE '%' || ? || '%')";
	        }

	        sql = "SELECT * FROM (SELECT a.accom_num, a.host_num, ad.region_num, ad.cate_num, "
                + "ad.accom_name, ad.description, ad.zipcode, ad.address1, ad.address2, "
                + "ad.price, ad.max_people, ad.accom_status, ad.accom_date, ad.accom_hits, "
                + "c.cate_name, r.region_name, ai.image_num, ai.image_name, "
                + "rownum rnum "
                + "FROM accom a "
                + "JOIN accom_detail ad ON a.accom_num = ad.accom_num "
                + "JOIN accom_cate c ON ad.cate_num = c.cate_num "
                + "JOIN region r ON ad.region_num = r.region_num "
                + "JOIN accom_image ai ON a.accom_num = ai.accom_num "
                + "WHERE ai.main = 1 AND ad.accom_status > ? "
                + sub_sql + " ORDER BY a.accom_num DESC) "
                + "WHERE rnum >= ? AND rnum <= ?";

	        pstmt = conn.prepareStatement(sql);

	        // accom_status와 keyword 파라미터 설정
	        pstmt.setInt(++cnt, accom_status); // accom_status 파라미터
	        
	        if (keyword != null && !"".equals(keyword)) {
	            pstmt.setString(++cnt, keyword); // keyword 파라미터
	            pstmt.setString(++cnt, keyword); // keyword 파라미터
	            pstmt.setString(++cnt, keyword); // keyword 파라미터
	        }

	        // 페이징 파라미터 설정
	        pstmt.setInt(++cnt, start);
	        pstmt.setInt(++cnt, end);

	        rs = pstmt.executeQuery();
	        list = new ArrayList<AccomVO>();
	        while (rs.next()) {
	            AccomVO accom = new AccomVO();
	            accom.setAccom_num(rs.getLong("accom_num"));
	            accom.setAccom_name(rs.getString("accom_name"));
	            accom.setAccom_hits(rs.getInt("accom_hits"));
	            accom.setAddress1(rs.getString("address1"));
	            accom.setAddress2(rs.getString("address2"));
	            accom.setPrice(rs.getLong("price"));
	            accom.setAccom_date(rs.getDate("accom_date"));
	            accom.setAccom_status(rs.getInt("accom_status"));
	            accom.setCate_num(rs.getLong("cate_num"));
	            accom.setCate_name(rs.getString("cate_name"));
	            accom.setImage_name(rs.getString("image_name"));

	            list.add(accom);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return list;
	}
	
	// 지역별 숙수 개수
		public int getAccomCount(long region_num, int accom_status) throws Exception {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int count = 0;
			
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT COUNT(*) FROM accom_detail WHERE region_num = ? AND accom_status > ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, region_num);
				pstmt.setInt(2, accom_status);
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
	
	// 지역 번호 별 숙소
	public List<AccomVO> getListAccom(int start, int end, int accom_status, int region_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<AccomVO> list = null;
	    String sql = null;
	    int cnt = 0;

	    try {
	        conn = DBUtil.getConnection();

	        sql = "SELECT * FROM (SELECT a.accom_num, a.host_num, ad.region_num, ad.cate_num, "
                + "ad.accom_name, ad.description, ad.zipcode, ad.address1, ad.address2, "
                + "ad.price, ad.max_people, ad.accom_status, ad.accom_date, ad.accom_hits, "
                + "c.cate_name, r.region_name, ai.image_num, ai.image_name, "
                + "rownum rnum "
                + "FROM accom a "
                + "JOIN accom_detail ad ON a.accom_num = ad.accom_num "
                + "JOIN accom_cate c ON ad.cate_num = c.cate_num "
                + "JOIN region r ON ad.region_num = r.region_num "
                + "JOIN accom_image ai ON a.accom_num = ai.accom_num "
                + "WHERE ai.main = 1 AND ad.accom_status > ? AND r.region_num = ? "
                + "ORDER BY a.accom_num DESC) "
                + "WHERE rnum >= ? AND rnum <= ?";

	        pstmt = conn.prepareStatement(sql);

	        // accom_status와 keyword 파라미터 설정
	        pstmt.setInt(++cnt, accom_status); // accom_status 파라미터
	        pstmt.setInt(++cnt, region_num);

	        // 페이징 파라미터 설정
	        pstmt.setInt(++cnt, start);
	        pstmt.setInt(++cnt, end);

	        rs = pstmt.executeQuery();
	        list = new ArrayList<AccomVO>();
	        while (rs.next()) {
	            AccomVO accom = new AccomVO();
	            accom.setAccom_num(rs.getLong("accom_num"));
	            accom.setAccom_name(rs.getString("accom_name"));
	            accom.setAccom_hits(rs.getInt("accom_hits"));
	            accom.setAddress1(rs.getString("address1"));
	            accom.setAddress2(rs.getString("address2"));
	            accom.setPrice(rs.getLong("price"));
	            accom.setAccom_date(rs.getDate("accom_date"));
	            accom.setAccom_status(rs.getInt("accom_status"));
	            accom.setCate_num(rs.getLong("cate_num"));
	            accom.setCate_name(rs.getString("cate_name"));
	            accom.setImage_name(rs.getString("image_name")); // 추가

	            list.add(accom);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return list;
	}

    // 숙소 삭제 (실제 삭제)
    public int deleteAccom(long accom_num) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        int cnt = 0;

        try {
            conn = DBUtil.getConnection();
            
            // 1. 관련 이미지 삭제
            sql = "DELETE FROM accom_image WHERE accom_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, accom_num);
            pstmt.executeUpdate();

            // 2. 숙소 상세 정보 삭제
            sql = "DELETE FROM accom_detail WHERE accom_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, accom_num);
            pstmt.executeUpdate();

            // 3. 숙소 정보 삭제
            sql = "DELETE FROM accom WHERE accom_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, accom_num);
            cnt = pstmt.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
        return cnt;
    }

    // 숙소 상태 업데이트
    public int updateAccomStatus(long accom_num, int accom_status) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        int cnt = 0;

        try {
            conn = DBUtil.getConnection();
            
            sql = "UPDATE accom_detail SET accom_status = ? WHERE accom_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accom_status);
            pstmt.setLong(2, accom_num);
            cnt = pstmt.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
        return cnt;
    }
	
	// 숙소 상세
	public AccomVO getAccom(long accom_num) throws Exception {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		ResultSet rs = null;
		AccomVO accom = null;
		String sql = null;
		accom = new AccomVO();
		
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);

			// accomDetail
			sql = "SELECT * FROM accom_detail WHERE accom_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, accom_num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				accom.setAccom_num(rs.getLong("accom_num"));
				accom.setAccom_name(rs.getString("accom_name"));
				accom.setAddress1(rs.getString("address1"));
				accom.setAddress2(rs.getString("address2"));
				accom.setAccom_status(rs.getInt("accom_status"));
				accom.setAccom_hits(rs.getInt("accom_hits"));
				accom.setZipcode(rs.getString("zipcode"));
				accom.setPrice(rs.getLong("price"));
				accom.setMax_people(rs.getInt("max_people"));
				accom.setAccom_date(rs.getDate("accom_date"));
				accom.setDescription(rs.getString("description"));
			} // if
			
			// image
			sql = "SELECT * FROM accom_image WHERE accom_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, accom_num);
			rs = pstmt2.executeQuery();
			if (rs.next()) {
				accom.setImage_num(rs.getLong("image_num"));
				accom.setImage_name(rs.getString("image_name"));
				accom.setImage_main(rs.getInt("main"));
			} // if
			
			// 숙소 유형
			sql = "SELECT * FROM accom_cate JOIN accom_detail USING (cate_num) WHERE accom_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, accom_num);
			rs = pstmt3.executeQuery();
			if (rs.next()) {
				accom.setCate_name(rs.getString("cate_name"));
			} // if
			
			// host 정보1
			sql = "SELECT * FROM host_detail JOIN accom USING (host_num) WHERE accom_num=?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setLong(1, accom_num);
			rs = pstmt4.executeQuery();
			if (rs.next()) {
				accom.setHost_num(rs.getLong("host_num"));
				accom.setHost_intro(rs.getString("host_intro"));
				accom.setHost_rating(rs.getInt("host_rating"));
			} // if
			// host 정보2
			sql = "SELECT * FROM user_detail WHERE user_num=(SELECT host_num FROM accom WHERE accom_num=?)";
			pstmt5 = conn.prepareStatement(sql);
			pstmt5.setLong(1, accom_num);
			rs = pstmt5.executeQuery();
			if (rs.next()) {
				accom.setUser_num(rs.getLong("user_num"));
				accom.setUser_name(rs.getString("user_name"));
				accom.setUser_email(rs.getString("user_email"));
				accom.setUser_phone(rs.getString("user_phone"));
				accom.setPhoto(rs.getString("photo"));
			} // if
			
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		} // try_catch
		
		return accom;
		
	}
	

}




















