package kr.host.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.accom.vo.AccomCateVO;
import kr.accom.vo.AccomImageVO;
import kr.accom.vo.AccomVO;
import kr.accom.vo.RegionVO;
import kr.accomchat.vo.AccomChatVO;
import kr.host.vo.HostIncomeVO;
import kr.host.vo.HostInquiryVO;
import kr.host.vo.HostProfileVO;
import kr.host.vo.HostReservDetailVO;
import kr.host.vo.HostReservVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class HostDAO {
	//싱글턴 패턴
	private static HostDAO instance = new HostDAO();
	public static HostDAO getInstance() {
		return instance;
	}
	private HostDAO() {}
	
	//호스트 - 숙소 등록
	public void insertAccom(AccomVO accom) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		PreparedStatement pstmt6 = null;
		ResultSet rs = null;
		String sql = null;
		long num = 0;
		int cnt = 0;
		
		try {
		conn = DBUtil.getConnection();
		conn.setAutoCommit(false);
		
		sql = "SELECT seq_accom.nextval FROM dual";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		if(rs.next()) {
		num = rs.getLong(1);
		}
		
		//host_detail 있는지 확인
		sql = "SELECT COUNT(*) FROM host_detail WHERE host_num = ?";
		pstmt1 = conn.prepareStatement(sql);
		pstmt1.setLong(1, accom.getHost_num());
		rs = pstmt1.executeQuery();
		
		boolean isNewHost = true;
		if (rs.next() && rs.getInt(1) > 0) {
		    isNewHost = false;
		}
		if (isNewHost) {
		    sql = "INSERT INTO host_detail (host_num) VALUES (?)";
		    pstmt2 = conn.prepareStatement(sql);
		    pstmt2.setLong(1, accom.getHost_num());
		    pstmt2.executeUpdate();
		    
		    sql= "UPDATE ctuser SET auth=1 WHERE user_num=?";
		    pstmt6 = conn.prepareStatement(sql);
		    pstmt6.setLong(1, accom.getHost_num());
		    pstmt6.executeUpdate();
		}
		
		sql = "INSERT INTO accom (accom_num, host_num) VALUES (?,?)";
		pstmt3 = conn.prepareStatement(sql);
		pstmt3.setLong(1, num);//숙소번호
		pstmt3.setLong(2, accom.getHost_num());
		pstmt3.executeUpdate();
		
		sql =  "INSERT INTO accom_detail (" +
			    "accom_num, region_num, cate_num, accom_name, description, zipcode, " +
			    "address1, address2, price, max_people) " +
			    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		pstmt4 = conn.prepareStatement(sql);
		pstmt4.setLong(++cnt, num);//숙소번호
		pstmt4.setLong(++cnt, accom.getRegion_num());
		pstmt4.setLong(++cnt, accom.getCate_num());
		pstmt4.setString(++cnt, accom.getAccom_name());
		pstmt4.setString(++cnt, accom.getDescription());
		pstmt4.setString(++cnt, accom.getZipcode());
		pstmt4.setString(++cnt, accom.getAddress1());
		pstmt4.setString(++cnt, accom.getAddress2());
		pstmt4.setLong(++cnt, accom.getPrice());
		pstmt4.setInt(++cnt, accom.getMax_people());
		pstmt4.executeUpdate();
		
		sql = "INSERT INTO accom_image (image_num, accom_num, image_name, main) " +
			      "VALUES (seq_accom_image.nextval, ?, ?, ?)";
		pstmt5 = conn.prepareStatement(sql);

		List<AccomImageVO> imageList = accom.getImageList();
		if (imageList != null && !imageList.isEmpty()) {
		    for (AccomImageVO image : imageList) {
		        pstmt5.setLong(1, num); // 숙소번호
		        pstmt5.setString(2, image.getImage_name());
		        pstmt5.setInt(3, image.getMain());
		        pstmt5.executeUpdate();
		    }
		}
		conn.commit();
		}catch(Exception e) {
		//SQL문이 하나라도 실패하면 rollback
		conn.rollback();
		throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt6, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt1, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	public List<AccomCateVO> getAllCategories() throws Exception {
	    List<AccomCateVO> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    String sql = null;
	    try{
	    	conn=DBUtil.getConnection();
	    	sql="SELECT cate_num, cate_name FROM accom_cate";
	    	pstmt=conn.prepareStatement(sql);
	    	rs=pstmt.executeQuery();
	        while (rs.next()) {
	            AccomCateVO vo = new AccomCateVO();
	            vo.setCate_num(rs.getLong("cate_num"));
	            vo.setCate_name(rs.getString("cate_name"));
	            list.add(vo);
	        }
	    }catch(Exception e) {
	    	throw new Exception(e);
	    }finally {
	    	DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return list;
	}
	
	public List<RegionVO> getAllRegions() throws Exception {
	    List<RegionVO> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt=null;
	    ResultSet rs=null;
	    String sql = null;
	    try{
	    	conn=DBUtil.getConnection();
	    	sql="SELECT * FROM region";
	    	pstmt=conn.prepareStatement(sql);
	    	rs=pstmt.executeQuery();
	        while (rs.next()) {
	        	RegionVO vo = new RegionVO();
	            vo.setRegion_num(rs.getLong("region_num"));
	            vo.setRegion_name(rs.getString("region_name"));
	            list.add(vo);
	        }
	    }catch(Exception e) {
	    	throw new Exception(e);
	    }finally {
	    	DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return list;
	}
	
	//호스트가 등록한 숙소 목록
	public List<AccomVO> getListAccomByHost(long host_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AccomVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT a.accom_num,"
					+ "       a.host_num,"
					+ "       ad.accom_name,"
					+ "       ad.address1,"
					+ "       ad.price,"
					+ "       ad.max_people,"
					+ "       ad.accom_status,"
					+ "       ad.accom_date,"
					+ "       i.image_num,"
					+ "       i.image_name,"
					+ "       i.main "
					+ "FROM accom a "
					+ "JOIN accom_detail ad ON a.accom_num = ad.accom_num "
					+ "LEFT JOIN accom_image i ON a.accom_num = i.accom_num AND i.main = 1 "
					+ "WHERE a.host_num = ? "
					+ "ORDER BY a.accom_num DESC";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, host_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<AccomVO>();
			
			while(rs.next()) {
				AccomVO accom = new AccomVO();
				accom.setAccom_num(rs.getLong("accom_num"));
				accom.setHost_num(rs.getLong("host_num"));
				accom.setAccom_name(rs.getString("accom_name"));
				accom.setAddress1(rs.getString("address1"));
				accom.setPrice(rs.getInt("price"));
				accom.setMax_people(rs.getInt("max_people"));
				accom.setAccom_status(rs.getInt("accom_status"));
				accom.setAccom_date(rs.getDate("accom_date"));
				
				List<AccomImageVO> imageList = new ArrayList<AccomImageVO>();
	            long image_num = rs.getLong("image_num");
	            
	            if (image_num != 0) {
	                AccomImageVO image = new AccomImageVO();
	                image.setImage_num(image_num);
	                image.setImage_name(rs.getString("image_name"));
	                image.setMain(rs.getInt("main"));
	                imageList.add(image);
	            }
	            
				accom.setImageList(imageList);
				list.add(accom);				
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}
	
	//호스트 숙소 상세
	public AccomVO getHostAccomDetail(long accom_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccomVO accom = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT   a.*, d.* FROM accom a "
					+ "JOIN accom_detail d ON a.accom_num = d.accom_num "
					+ "WHERE a.accom_num = ?";
			//PreparedSatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, accom_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				accom = new AccomVO();
				accom.setAccom_num(rs.getLong("accom_num"));
				accom.setHost_num(rs.getLong("host_num"));
				accom.setRegion_num(rs.getLong("region_num"));
				accom.setCate_num(rs.getLong("cate_num"));
				accom.setAccom_name(rs.getString("accom_name"));
				accom.setDescription(rs.getString("description"));
				accom.setZipcode(rs.getString("zipcode"));
				accom.setAddress1(rs.getString("address1"));
				accom.setAddress2(rs.getString("address2"));
				accom.setPrice(rs.getInt("price"));
				accom.setMax_people(rs.getInt("max_people"));
				accom.setAccom_status(rs.getInt("accom_status"));
				accom.setAccom_date(rs.getDate("accom_date"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return accom;
	}

	// 이미지 목록 조회
	public List<AccomImageVO> getAccomImages(long accom_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AccomImageVO> list = new ArrayList<>();
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql="SELECT * FROM accom_image WHERE accom_num = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, accom_num);
			rs=pstmt.executeQuery();
	        while (rs.next()) {
	        	AccomImageVO vo = new AccomImageVO();
	            vo.setImage_num(rs.getLong("image_num"));
	            vo.setImage_name(rs.getString("image_name"));
	            vo.setMain(rs.getInt("main"));
	            list.add(vo);
	        }
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	// 숙소 정보 수정
	//public int updateAccomDetail(AccomVO accom);
	public void modifyAccom(AccomVO accom)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "UPDATE accom_detail SET region_num = ?, cate_num = ?, accom_name = ?, description = ?, "
					+ "zipcode = ?, address1 = ?, address2 = ?, price = ?, max_people = ? WHERE accom_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(++cnt, accom.getRegion_num());
			pstmt.setLong(++cnt, accom.getCate_num());
			pstmt.setString(++cnt, accom.getAccom_name());
			pstmt.setString(++cnt, accom.getDescription());
			pstmt.setString(++cnt, accom.getZipcode());
			pstmt.setString(++cnt, accom.getAddress1());
			pstmt.setString(++cnt, accom.getAddress2());
			pstmt.setLong(++cnt, accom.getPrice());
			pstmt.setInt(++cnt, accom.getMax_people());
			pstmt.setLong(++cnt, accom.getAccom_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
			sql = "INSERT INTO accom_image (image_num, accom_num, image_name, main) " +
				      "VALUES (seq_accom_image.nextval, ?, ?, ?)";
			pstmt2 = conn.prepareStatement(sql);

			List<AccomImageVO> imageList = accom.getImageList();
			if (imageList != null && !imageList.isEmpty()) {
			    for (AccomImageVO image : imageList) {
			        pstmt2.setLong(1, accom.getAccom_num()); // 숙소번호
			        pstmt2.setString(2, image.getImage_name());
			        pstmt2.setInt(3, image.getMain());
			        pstmt2.executeUpdate();
			    }
			}
			
			conn.commit();
			
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}
	//숙소 상태 변경
	public void modifyAccomStatus(AccomVO accom)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE accom_detail SET accom_status=? WHERE accom_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, accom.getAccom_status());
			pstmt.setLong(2, accom.getAccom_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}
	//이미지 1장 가져오기(삭제용)
	public AccomImageVO getAccomImage(long image_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    AccomImageVO image = null;
	    String sql = "SELECT * FROM accom_image WHERE image_num = ?";

	    try {
	        conn = DBUtil.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, image_num);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            image = new AccomImageVO();
	            image.setImage_num(rs.getLong("image_num"));
	            image.setAccom_num(rs.getLong("accom_num"));
	            image.setImage_name(rs.getString("image_name"));
	            image.setMain(rs.getInt("main"));
	        }
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return image;
	}

	// 기존 이미지 삭제
	public void deleteAccomImage(long image_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM accom_image WHERE image_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, image_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}
	//호스트- 총 예약건수
	public int getReservationCount(long user_num, String accom, String status) throws Exception {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql=null;
	    String sub_sql="";
        int count = 0;
        int cnt=0;
        try {
        	conn= DBUtil.getConnection();
        	if (accom != null && !accom.isEmpty()) sub_sql += " AND ar.accom_num = ?";
            if (status != null && !status.isEmpty()) sub_sql += " AND rd.reserv_status = ?";
            sql = "SELECT COUNT(*) FROM accom_reserv ar " +
                    "JOIN reserv_detail rd ON ar.reserv_num = rd.reserv_num " +
                    "JOIN accom a ON ar.accom_num = a.accom_num " +               
                    "JOIN accom_detail ad ON a.accom_num = ad.accom_num " +    
                    "WHERE a.host_num = ? " + sub_sql;
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setLong(++cnt, user_num);

        	if (accom != null && !accom.isEmpty()) {
        	    pstmt.setString(++cnt, accom);
        	}
        	if (status != null && !status.isEmpty()) {
        	    pstmt.setString(++cnt, status);
        	}
            
        	rs = pstmt.executeQuery();
        	 if (rs.next()) count = rs.getInt(1);
        	 
        }catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
        return count;
    }
	//호스트 - 예약 필터링 리스트
	public List<HostReservVO> getReservationList(long user_num, String accom, String status, int start, int end) throws Exception {
        List<HostReservVO> list = new ArrayList<>();	Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql=null;
	    String sub_sql="";
        int count = 0;
        int cnt=0;
        try {
        	conn= DBUtil.getConnection();
        	if (accom != null && !accom.isEmpty()) sub_sql += " AND ar.accom_num = ?";
            if (status != null && !status.isEmpty()) sub_sql += " AND rd.reserv_status = ?";
            sql = "SELECT * FROM (" +
                    "  SELECT rownum rnum, data.* FROM (" +
                    "    SELECT ar.reserv_num, ar.reserv_code, ad.accom_name, ud.user_name, " +
                    "           rd.check_in_date, rd.check_out_date, rd.people_count, rd.total_price, rd.reserv_status " +
                    "    FROM accom_reserv ar " +
                    "    JOIN reserv_detail rd ON ar.reserv_num = rd.reserv_num " +
                    "    JOIN accom a ON ar.accom_num = a.accom_num " +
                    "    JOIN accom_detail ad ON a.accom_num = ad.accom_num " +
                    "    JOIN ctuser u ON ar.user_num = u.user_num " +
                    "    JOIN user_detail ud ON u.user_num = ud.user_num " +
                    "    WHERE a.host_num = ? " + sub_sql +
                    "    ORDER BY ar.reserv_num DESC" +
                    "  ) data" +
                    ") WHERE rnum >= ? AND rnum <= ?";
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setLong(++cnt, user_num); // 첫 번째는 항상 user_num

        	if (accom != null && !accom.isEmpty()) {
        	    pstmt.setString(++cnt, accom);
        	}
        	if (status != null && !status.isEmpty()) {
        	    pstmt.setString(++cnt, status);
        	}
        	pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
        	rs = pstmt.executeQuery();
        	while (rs.next()) {
        		HostReservVO vo = new HostReservVO();
                vo.setReserv_num(rs.getLong("reserv_num"));
                vo.setReserv_code(rs.getString("reserv_code"));
                vo.setAccom_name(rs.getString("accom_name"));
                vo.setUser_name(rs.getString("user_name"));
                vo.setCheck_in_date(rs.getDate("check_in_date"));
                vo.setCheck_out_date(rs.getDate("check_out_date"));
                vo.setPeople_count(rs.getInt("people_count"));
                vo.setTotal_price(rs.getInt("total_price"));
                vo.setReserv_status(rs.getInt("reserv_status"));
                list.add(vo);
            }
        	 
        }catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
        return list;
    }
	//호스트 예약 상세
	public HostReservDetailVO getHostReservDetail(long reserv_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HostReservDetailVO reserv = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT " +
					"    ud.user_name, ud.user_phone, " +
					"    rd.reserv_date, " +
					"    ar.reserv_code, ar.reserv_num," +
					"    p.payment_status, p.amount, p.payment_method, " +
					"    rd.check_in_date, rd.check_out_date, rd.people_count, rd.reserv_status, " +
					"    rd.cancel_date, rd.cancel_reason,"+
					"    ad.accom_name, a.host_num " +  
					"FROM accom_reserv ar " +
					"JOIN reserv_detail rd ON ar.reserv_num = rd.reserv_num " +
					"JOIN ctuser u ON ar.user_num = u.user_num " +
					"JOIN user_detail ud ON u.user_num = ud.user_num " +
					"JOIN payment p ON ar.reserv_num = p.reserv_num " +
					"JOIN accom a ON ar.accom_num = a.accom_num " + 
					"JOIN accom_detail ad ON a.accom_num = ad.accom_num " +
					"WHERE ar.reserv_num = ?";

			//PreparedSatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, reserv_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reserv = new HostReservDetailVO();
				reserv.setUser_name(rs.getString("user_name"));
				reserv.setAccom_name(rs.getString("accom_name"));              
			    reserv.setUser_phone(rs.getString("user_phone"));             
			    reserv.setReserv_date(rs.getDate("reserv_date"));            
			    reserv.setReserv_code(rs.getString("reserv_code"));           
			    reserv.setPayment_status(rs.getString("payment_status"));        // 결제상태 (String)
			    reserv.setAmount(rs.getInt("amount"));                        
			    reserv.setPayment_method(rs.getString("payment_method"));    
			    reserv.setCheck_in_date(rs.getDate("check_in_date"));         
			    reserv.setCheck_out_date(rs.getDate("check_out_date"));      
			    reserv.setCancel_date(rs.getDate("cancel_date"));      
			    reserv.setCancel_reason(rs.getString("cancel_reason"));      
			    reserv.setPeople_count(rs.getInt("people_count"));        
			    reserv.setReserv_status(rs.getInt("reserv_status"));          // 예약상태 (int)
			    reserv.setHost_num(rs.getLong("host_num"));          // 예약상태 (int)
			    reserv.setReserv_num(rs.getLong("reserv_num"));          // 예약상태 (int)
			    reserv.setReserv_status(rs.getInt("reserv_status"));          // 예약상태 (int)
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return reserv;
	}
	public void modifyReservStatus(HostReservDetailVO reserv) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE reserv_detail"
					+ " SET reserv_status=? "
					+ " WHERE reserv_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, reserv.getReserv_status());
			pstmt.setLong(++cnt, reserv.getReserv_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//월수입-그래프
	public List<HostIncomeVO> getMonthlyIncomeByHost(long host_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<HostIncomeVO> list = new ArrayList<>();
	    String sql=null;

	    try {
	        conn = DBUtil.getConnection();

	        // 1~12월 기본값 0으로 초기화
	        Map<String, Integer> incomeMap = new LinkedHashMap<String, Integer>();
	        for (int i = 1; i <= 12; i++) {
	            String month = String.format("%02d", i); // "01", "02", ..., "12"
	            incomeMap.put(month, 0);
	        }

	        // 수익 조회
	        sql = "SELECT TO_CHAR(r.check_in_date, 'MM') AS month,\r\n"
	        		+ "       SUM(r.total_price) AS total_income\r\n"
	        		+ "FROM accom_reserv ar\r\n"
	        		+ "JOIN reserv_detail r ON ar.reserv_num = r.reserv_num\r\n"
	        		+ "JOIN accom a ON ar.accom_num = a.accom_num\r\n"
	        		+ "WHERE a.host_num = ?\r\n"
	        		+ "  AND r.payment_status = 1\r\n"
	        		+ "  AND r.reserv_status = 1\r\n"
	        		+ "GROUP BY TO_CHAR(r.check_in_date, 'MM')\r\n"
	        		+ "ORDER BY month";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, host_num);
	        rs = pstmt.executeQuery();

	        // 결과 반영
	        while (rs.next()) {
	            String month = rs.getString("month");
	            int total = rs.getInt("total_income");
	            incomeMap.put(month, total);
	        }

	        // 최종 list 구성 (정렬된 월순)
	        for (Map.Entry<String, Integer> entry : incomeMap.entrySet()) {
	            HostIncomeVO vo = new HostIncomeVO();
	            vo.setMonth(entry.getKey()); // "01", "02", ...
	            vo.setTotalIncome(entry.getValue());
	            list.add(vo);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return list;
	}
	//특정숙소 수입 그래프
	public List<HostIncomeVO> getMonthlyIncomeByAccom(long accom_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<HostIncomeVO> list = new ArrayList<>();

	    try {
	        conn = DBUtil.getConnection();

	        String sql = "SELECT TO_CHAR(r.check_in_date, 'MM') AS month, " +
	                     "SUM(r.total_price) AS total_income " +
	                     "FROM accom_reserv ar " +
	                     "JOIN reserv_detail r ON ar.reserv_num = r.reserv_num " +
	                     "WHERE ar.accom_num = ? " +
	                     "AND r.payment_status = 1 " +
	                     "AND r.reserv_status = 1 " +
	                     "GROUP BY TO_CHAR(r.check_in_date, 'MM') " +
	                     "ORDER BY month";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, accom_num);
	        rs = pstmt.executeQuery();

	        // 1~12월 0으로 초기화
	        Map<String, Integer> incomeMap = new LinkedHashMap<>();
	        for (int i = 1; i <= 12; i++) {
	            incomeMap.put(String.format("%02d", i), 0);
	        }

	        while (rs.next()) {
	            incomeMap.put(rs.getString("month"), rs.getInt("total_income"));
	        }

	        for (Map.Entry<String, Integer> entry : incomeMap.entrySet()) {
	            HostIncomeVO vo = new HostIncomeVO();
	            vo.setMonth(entry.getKey());
	            vo.setTotalIncome(entry.getValue());
	            list.add(vo);
	        }
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }
	    return list;
	}
	//호스트- 총 문의건수
	public int getInqCount(long host_num, String inq_status, String accom_num) throws Exception {
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql=null;
	    String sub_sql="";
        int count = 0;
        int cnt=0;
        try {
        	conn= DBUtil.getConnection();
        	if (accom_num != null && !accom_num.isEmpty()) sub_sql = " AND a.accom_num = ?";
            
        	sql = "SELECT COUNT(*) FROM inquiry i " +
                    "JOIN accom a ON i.accom_num = a.accom_num " +
                    "WHERE a.host_num = ? AND i.inq_status=?" + sub_sql;
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setLong(++cnt, host_num);
        	pstmt.setString(++cnt, inq_status);

        	if (accom_num != null && !accom_num.isEmpty()) {
        	    pstmt.setString(++cnt, accom_num);
        	}
            
        	rs = pstmt.executeQuery();
        	 if (rs.next()) count = rs.getInt(1);
        	 
        }catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
        return count;
    }
	//호스트 - 문의 목록
	public List<HostInquiryVO> getInqList(long host_num,String inq_status, String accom_num, int start, int end) throws Exception {
        List<HostInquiryVO> list = new ArrayList<>();	
        Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql=null;
	    String sub_sql="";
        int count = 0;
        int cnt=0;
        try {
        	conn= DBUtil.getConnection();
        	if (accom_num != null && !accom_num.isEmpty()) sub_sql += " AND ad.accom_num = ?";
            sql = "SELECT * FROM (\r\n"
            		+ "    SELECT rownum rnum, data.* FROM (\r\n"
            		+ "        SELECT \r\n"
            		+ "            i.inquiry_num,\r\n"
            		+ "            ad.accom_name,\r\n"
            		+ "            ud.user_name,\r\n"
            		+ "            i.inq_content,\r\n"
            		+ "            i.inq_date\r\n,"
            		+ "			   an.answer_num, "
            		+ "			   an.answer_content "
            		+ "        FROM inquiry i\r\n"
            		+ "        JOIN accom a ON i.accom_num = a.accom_num\r\n"
            		+ "        JOIN accom_detail ad ON a.accom_num = ad.accom_num\r\n"
            		+ "        JOIN ctuser u ON i.user_num = u.user_num\r\n"
            		+ "        JOIN user_detail ud ON u.user_num = ud.user_num\r\n"
            		+ "        LEFT JOIN answer an ON i.inquiry_num = an.inquiry_num\r\n"
            		+ "        WHERE a.host_num = ?\r\n"
            		+ "          AND i.inq_status=? "+ sub_sql
            		+ "        ORDER BY i.inquiry_num DESC\r\n"
            		+ "    ) data\r\n"
            		+ ") WHERE rnum BETWEEN ? AND ?";
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setLong(++cnt, host_num);
        	pstmt.setString(++cnt, inq_status);

        	if (accom_num != null && !accom_num.isEmpty()) {
        	    pstmt.setString(++cnt, accom_num);
        	}
        	pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
        	rs = pstmt.executeQuery();
        	while (rs.next()) {
        		HostInquiryVO vo = new HostInquiryVO();
                vo.setInquiry_num(rs.getLong("inquiry_num"));
                vo.setAccom_name(rs.getString("accom_name"));
                vo.setUser_name(rs.getString("user_name"));
                vo.setInq_content(rs.getString("inq_content"));
                vo.setInq_date(rs.getDate("inq_date"));
                vo.setAnswer_num(rs.getLong("answer_num"));
                vo.setAnswer_content(rs.getString("answer_content")); 
                list.add(vo);
            }
        	 
        }catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
        return list;
    }
	//문의 - 답변하기
	public void insertAnswer(HostInquiryVO inq)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		int cnt = 0;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			sql = "INSERT INTO answer (inquiry_num,answer_num,answer_content,"
				+ "answer_date) VALUES (?,seq_answer.nextval,"
				+ "?,SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(++cnt, inq.getInquiry_num());
			pstmt.setString(++cnt, inq.getAnswer_content());
			pstmt.executeUpdate();
			
			sql="UPDATE inquiry SET inq_status=1 WHERE inquiry_num=?";
			pstmt2=conn.prepareStatement(sql);
			pstmt2.setLong(1, inq.getInquiry_num());
			pstmt2.executeUpdate();
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	public void modifyAnswer(HostInquiryVO inq) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE answer"
					+ " SET answer_content=? "
					+ " WHERE answer_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, inq.getAnswer_content());
			pstmt.setLong(++cnt, inq.getAnswer_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//호스트 프로필
	public HostProfileVO getHostProfile(long user_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HostProfileVO profile = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT ud.user_num, ud.user_name, ud.photo, "
					+ "hd.host_intro, hd.host_date, hd.host_rating\r\n"
					+ "FROM user_detail ud JOIN host_detail hd\r\n"
					+ "ON ud.user_num = hd.host_num WHERE user_num = ?";
			//PreparedSatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, user_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				profile = new HostProfileVO();
				profile.setUser_num(user_num);
				profile.setUser_name(rs.getString("user_name"));
				profile.setPhoto(rs.getString("photo"));
				profile.setHost_intro(rs.getString("host_intro"));
				profile.setHost_date(rs.getDate("host_date"));
				profile.setHost_rating(rs.getInt("host_rating"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return profile;
	}
	
	public List<AccomChatVO> getHostChatList(long host_num) throws Exception {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    List<AccomChatVO> list = null;
	    String sql = null;

	    try {
	        conn = DBUtil.getConnection();

	        sql = "SELECT " +
	                "    c.chatroom_num, " +
	                "    c.accom_num, " +
	                "    c.host_num, " +
	                "    c.user_num AS room_user_num, " +
	                "    ad.accom_name, " +
	                "    ud_guest.user_name AS guest_name, " + // 변경: 상대는 게스트니까
	                "    cu.user_id AS user_id, " +
	                "    ac.chat_num, " +
	                "    ac.user_num, " +
	                "    ac.read_check, " +
	                "    ac.message, " +
	                "    TO_CHAR(ac.chat_date, 'YYYY-MM-DD HH24:MI:SS') AS chat_date, " +
	                "    NVL(uc.unread_cnt, 0) AS unread_cnt " +
	                "FROM accom_chatroom c " +
	                "JOIN accom a ON c.accom_num = a.accom_num " +
	                "JOIN accom_detail ad ON a.accom_num = ad.accom_num " +
	                "JOIN user_detail ud_guest ON c.user_num = ud_guest.user_num " +  // 게스트 정보
	                "JOIN ctuser cu ON c.user_num = cu.user_num " + // 게스트 ID
	                "JOIN ( " +
	                "    SELECT chatroom_num, MAX(chat_num) AS chat_num " +
	                "    FROM accom_chat " +
	                "    GROUP BY chatroom_num " +
	                ") max_chat ON c.chatroom_num = max_chat.chatroom_num " +
	                "JOIN accom_chat ac ON ac.chat_num = max_chat.chat_num " +
	                "LEFT OUTER JOIN ( " +
	                "    SELECT chatroom_num, COUNT(*) AS unread_cnt " +
	                "    FROM accom_chat " +
	                "    WHERE read_check = 1 AND user_num != ? " +
	                "    GROUP BY chatroom_num " +
	                ") uc ON c.chatroom_num = uc.chatroom_num " +
	                "WHERE c.host_num = ? " +
	                "ORDER BY ac.chat_num DESC";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, host_num); // for unread count subquery
	        pstmt.setLong(2, host_num); // for main where clause

	        rs = pstmt.executeQuery();
	        list = new ArrayList<>();

	        while (rs.next()) {
	            AccomChatVO chat = new AccomChatVO();

	            chat.setChatroom_num(rs.getLong("chatroom_num"));
	            chat.setAccom_name(rs.getString("accom_name"));
	            chat.setUser_id(rs.getString("user_id"));
	            chat.setAccom_num(rs.getLong("accom_num"));
	            chat.setChat_num(rs.getLong("chat_num"));
	            chat.setUser_num(rs.getLong("user_num")); // 메시지 작성자
	            chat.setRead_check(rs.getInt("read_check"));
	            chat.setMessage(rs.getString("message"));
	            chat.setChat_date(rs.getString("chat_date"));
	            chat.setUnread_cnt(rs.getInt("unread_cnt"));

	            list.add(chat);
	        }
	    } catch (Exception e) {
	        throw new Exception(e);
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return list;
	}
	

	public void modifyHostProfile(HostProfileVO profile) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE host_detail SET host_intro=? "
			+ " WHERE host_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, profile.getHost_intro());
			pstmt.setLong(++cnt, profile.getUser_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
