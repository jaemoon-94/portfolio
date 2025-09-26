package kr.inquiry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.inquiry.vo.InquiryVO;
import kr.util.DBUtil;

public class InquiryDAO {
	private static InquiryDAO instance = new InquiryDAO();
	public static InquiryDAO getInstance() {
		return instance;
	}
	private InquiryDAO() {}
	
	//전체 문의 개수
	public int getInquiryCount(long accomNum) throws Exception {
	    int count = 0;
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql=null; 

	    try {
	        conn = DBUtil.getConnection();
	        sql = "SELECT COUNT(*) FROM inquiry WHERE accom_num = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, accomNum);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }
	    }catch(Exception e) {
	    	throw new Exception();
	    }finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return count;
	}

	//문의 목록
	public List<InquiryVO> getInqList(long accom_num,int start, int end) throws Exception {
        List<InquiryVO> list = new ArrayList<>();	
        Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql=null;
        int count = 0;
        try {
        	conn= DBUtil.getConnection();
            sql = "SELECT * FROM (" +
                  "SELECT rownum rnum, data.* FROM (" +
                  "SELECT iq.inquiry_num, iq.user_num, iq.accom_num, iq.inq_content, iq.inq_status, iq.inq_date, iq.is_secret," +
                  "           an.answer_num, an.answer_content, an.answer_date, u.user_id " +
                  "    FROM inquiry iq " +
                  "    JOIN ctuser u ON iq.user_num = u.user_num " +
                  "    LEFT JOIN answer an ON iq.inquiry_num = an.inquiry_num " +
                  "    WHERE iq.accom_num = ? " +
                  "    ORDER BY iq.inquiry_num DESC" +
                  "  ) data" +
                  ") WHERE rnum >= ? AND rnum <= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, accom_num);
        	pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
        	rs = pstmt.executeQuery();
        	while (rs.next()) {
        		InquiryVO vo = new InquiryVO();
        		vo.setInquiry_num(rs.getInt("inquiry_num"));
                vo.setUser_num(rs.getInt("user_num"));
                vo.setAccom_num(rs.getInt("accom_num"));
                vo.setInq_content(rs.getString("inq_content"));
                vo.setInq_status(rs.getInt("inq_status"));
                vo.setInq_date(rs.getDate("inq_date"));
                vo.setIs_secret(rs.getInt("is_secret"));

                vo.setAnswer_num(rs.getInt("answer_num"));
                vo.setAnswer_content(rs.getString("answer_content"));
                vo.setAnswer_date(rs.getDate("answer_date"));

                vo.setUser_id(rs.getString("user_id"));
                list.add(vo);
            }
        	 
        }catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
        return list;
    }
		//마이페이지 문의 갯수
		public int getMyInquiryCount(long userNum) throws Exception {
		    int count = 0;
		    Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    String sql=null; 

		    try {
		        conn = DBUtil.getConnection();
		        sql = "SELECT COUNT(*) FROM inquiry WHERE user_num = ?";
		        pstmt = conn.prepareStatement(sql);
		        pstmt.setLong(1, userNum);
		        rs = pstmt.executeQuery();

		        if (rs.next()) {
		            count = rs.getInt(1);
		        }
		    }catch(Exception e) {
		    	throw new Exception();
		    }finally {
		        DBUtil.executeClose(rs, pstmt, conn);
		    }

		    return count;
		}

		//마이페이지 문의 목록
		public List<InquiryVO> getMyInqList(long userNum,int start, int end) throws Exception {
	        List<InquiryVO> list = new ArrayList<>();	
	        Connection conn = null;
		    PreparedStatement pstmt = null;
		    ResultSet rs = null;
		    String sql=null;
	        int count = 0;
	        try {
	        	conn= DBUtil.getConnection();
	            sql = "SELECT * FROM (" +
	                  "SELECT rownum rnum, data.* FROM (" +
	                  "SELECT iq.inquiry_num, iq.user_num, iq.accom_num, iq.inq_content, iq.inq_status, iq.inq_date, iq.is_secret," +
	                  "          ad.accom_name, an.answer_num, an.answer_content, an.answer_date, u.user_id " +
	                  "    FROM inquiry iq " +
	                  "    JOIN ctuser u ON iq.user_num = u.user_num " +
	                  "    JOIN accom_detail ad ON iq.accom_num = ad.accom_num " +
	                  "    LEFT JOIN answer an ON iq.inquiry_num = an.inquiry_num " +
	                  "    WHERE iq.user_num = ? " +
	                  "    ORDER BY iq.inquiry_num DESC" +
	                  "  ) data" +
	                  ") WHERE rnum >= ? AND rnum <= ?";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setLong(1, userNum);
	        	pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				
	        	rs = pstmt.executeQuery();
	        	while (rs.next()) {
	        		InquiryVO vo = new InquiryVO();
	        		vo.setInquiry_num(rs.getInt("inquiry_num"));
	                vo.setUser_num(rs.getInt("user_num"));
	                vo.setAccom_num(rs.getInt("accom_num"));
	                vo.setAccom_name(rs.getString("accom_name"));
	                vo.setInq_content(rs.getString("inq_content"));
	                vo.setInq_status(rs.getInt("inq_status"));
	                vo.setInq_date(rs.getDate("inq_date"));

	                vo.setAnswer_num(rs.getInt("answer_num"));
	                vo.setAnswer_content(rs.getString("answer_content"));
	                vo.setAnswer_date(rs.getDate("answer_date"));

	                vo.setUser_id(rs.getString("user_id"));
	                list.add(vo);
	            }
	        	 
	        }catch (Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
	        return list;
	    }
		//문의 작성하기
		public void insertInquiry(InquiryVO inq)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			int cnt = 0;
			try {
				conn = DBUtil.getConnection();
				sql = "INSERT INTO inquiry (inquiry_num, user_num, accom_num,inq_content, inq_status, inq_date,is_secret) "
					+ "VALUES (seq_inquiry.nextval,?,?,?,0,SYSDATE,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setLong(++cnt, inq.getUser_num());
				pstmt.setLong(++cnt, inq.getAccom_num());
				pstmt.setString(++cnt, inq.getInq_content());
				pstmt.setInt(++cnt, inq.getIs_secret());
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//내 문의 삭제
		public void deleteMyInquiry(long inq_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt= null;
			String sql=null;
			try {
				conn=DBUtil.getConnection();
				sql="DELETE FROM inquiry WHERE inquiry_num=?";
				pstmt=conn.prepareStatement(sql);
				pstmt.setLong(1, inq_num);
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
}
