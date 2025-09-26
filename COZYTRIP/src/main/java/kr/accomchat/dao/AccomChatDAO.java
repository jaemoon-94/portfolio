package kr.accomchat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.accomchat.vo.AccomChatRoomVO;
import kr.accomchat.vo.AccomChatVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class AccomChatDAO {
	private static AccomChatDAO instance = new AccomChatDAO();
	
	public static AccomChatDAO getInstance() {
		return instance;
	}
	private AccomChatDAO() {}
	
	//채팅 - 내가 쓴 게시글 총레코드 수 (검색 레코드 수)
//	public int getBoardCountByUser(String keyfield,String keyword,long mem_num)throws Exception{
//		Connection conn= null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String sql =null;
//		String sub_sql="";
//		int count = 0;
//		
//		try {
//			conn = DBUtil.getConnection();
//			if(keyword!=null &&!"".equals(keyword)) {
//				if(keyfield.equals("1")) {
//					sub_sql += "AND title LIKE '%'||?||'%'";
//				}else if(keyfield.equals("2")) {
//					sub_sql += "AND content LIKE '%'||?||'%'";
//				}
//			}
//			sql = "SELECT COUNT(*) FROM zboard JOIN (SELECT board_num, "
//					+ "MAX(chat_num) chat_num FROM zboard_chatroom JOIN "
//					+ "zboard_chat USING(chatroom_num) GROUP BY board_num) "
//					+ "USING(board_num) WHERE mem_num=? "+sub_sql;
//			pstmt = conn.prepareStatement(sql);
//			//?에 데이터 바인딩
//			pstmt.setLong(1, mem_num);
//			if(keyword!=null &&!"".equals(keyword)) {
//				pstmt.setString(2, keyword);
//			}
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				count = rs.getInt(1);
//			}
//		}catch(Exception e) {
//			throw new Exception(e);
//		}finally {
//			DBUtil.executeClose(rs, pstmt, conn);
//		}
//		
//		return count;
//	}
//	//채팅 - 내가 쓴 글 게시글 글 목록(검색 글 목록)
//	public List<BoardVO> getListBoardByUser(int start,int end,String keyfield,String keyword,long mem_num)throws Exception{
//		Connection conn= null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<BoardVO> list = null;
//		String sql =null;
//		String sub_sql="";
//		int cnt = 0;
//		try {
//			conn = DBUtil.getConnection();
//			if(keyword!=null && !"".equals(keyword)) {
//				if(keyfield.equals("1"))
//					sub_sql += "AND title LIKE '%'||?||'%'";
//				else if(keyfield.equals("2"))
//					sub_sql += "AND content LIKE '%'||?||'%'";
//			}
//			sql="SELECT * FROM(SELECT a.*, rownum rnum FROM "
//					+ "(SELECT * FROM zboard JOIN"
//					+ "(SELECT board_num,MAX(chat_num) chat_num "
//					+ "FROM zboard_chatroom JOIN zboard_chat USING(chatroom_num) "
//					+ "GROUP BY board_num) USING(board_num) "
//					+ "LEFT OUTER JOIN (SELECT board_num, SUM(read_check) unread_cnt"
//					+ " FROM zboard_chatroom JOIN zboard_chat USING(chatroom_num) "
//					+ "WHERE mem_num!=? GROUP BY board_num) USING(board_num) "
//					+ "WHERE mem_num=? "+sub_sql+" ORDER BY chat_num DESC)a) "
//					+ "WHERE rnum>=? AND rnum<=?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setLong(++cnt, mem_num);
//			pstmt.setLong(++cnt, mem_num);
//			if(keyword!=null && !"".equals(keyword)) {
//				pstmt.setString(++cnt, keyword);
//			}
//			pstmt.setInt(++cnt,start);
//			pstmt.setInt(++cnt,end);
//			rs = pstmt.executeQuery();
//			list =new ArrayList<BoardVO>();
//			while(rs.next()) {
//				BoardVO board = new BoardVO();
//				board.setBoard_num(rs.getLong("board_num"));
//				board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
//				board.setHit(rs.getInt("hit"));
//				board.setReg_date(rs.getDate("reg_date"));
//				//읽지않은 채팅수
//				board.setUnread_cnt(rs.getInt("unread_cnt"));
//				list.add(board);
//			}
//		}catch(Exception e) {
//			throw new Exception(e);
//		}finally {
//			DBUtil.executeClose(rs, pstmt, conn);
//		}
//		return list;
//	}
//	//채팅 - 채팅방 개수 (검색 갯수)
//	public int getChatCount(String keyfield,String keyword, long board_num) throws Exception{
//		Connection conn= null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String sql =null;
//		String sub_sql="";
//		int count=0;
//		
//		try {
//			conn = DBUtil.getConnection();
//			if(keyword !=null &&!"".equals(keyword)) {
//				if(keyfield.equals("1"))
//					sub_sql += "AND id LIKE '%'||?||'%'";
//				else if(keyfield.equals("2"))
//					sub_sql += "AND message LIKE '%'||?||'%'";
//			}
//			//SQL문 작성
//			sql="SELECT COUNT(*) FROM zboard_chat "
//					+ "JOIN zboard_chatroom USING(chatroom_num) "
//					+ "JOIN (SELECT SUM(read_check) cnt, "
//					+ "MAX(chat_num) chat_num FROM zboard_chat GROUP BY chatroom_num) "
//					+ "USING(chat_num) JOIN zmember USING(mem_num) "
//					+ "WHERE board_num=? "+sub_sql;
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setLong(1, board_num);
//			if(keyword !=null &&!"".equals(keyword)) {
//				pstmt.setString(2, keyword);
//			}
//			//sql문 실행
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				count = rs.getInt(1);
//			}
//		}catch(Exception e) {
//			throw new Exception(e);
//		}finally {
//			DBUtil.executeClose(rs, pstmt, conn);
//		}
//		return count;
//	}
	//채팅 - 채팅방 목록 -- user(고객꺼)
	public List<AccomChatVO> getUserChatList(long user_num) throws Exception {
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
	                "    c.user_num AS room_user_num, " +  // 채팅방 소유자 (user_num) → 별칭 필요
	                "    ad.accom_name, " +
	                "    ud_host.user_name AS host_name, " +
	                "    cu.user_id AS user_id, " +
	                "    ac.chat_num, " +
	                "    ac.user_num, " + // 메시지 작성자
	                "    ac.read_check, " +
	                "    ac.message, " +
	                "    TO_CHAR(ac.chat_date, 'YYYY-MM-DD HH24:MI:SS') AS chat_date, " +
	                "    NVL(uc.unread_cnt, 0) AS unread_cnt " +
	                "FROM accom_chatroom c " +
	                "JOIN accom a ON c.accom_num = a.accom_num " +
	                "JOIN accom_detail ad ON a.accom_num = ad.accom_num " +
	                "JOIN user_detail ud_host ON c.host_num = ud_host.user_num " +
	                "JOIN ctuser cu ON c.host_num = cu.user_num " +
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
	                "WHERE c.user_num = ? " +
	                "ORDER BY ac.chat_num DESC";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setLong(1, user_num); // for unread count subquery
	        pstmt.setLong(2, user_num); // for main where clause

	        rs = pstmt.executeQuery();
	        list = new ArrayList<>();

	        while (rs.next()) {
	            AccomChatVO chat = new AccomChatVO();

	            chat.setChatroom_num(rs.getLong("chatroom_num"));
	            chat.setAccom_name(rs.getString("accom_name"));
	            chat.setHost_name(rs.getString("host_name"));
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

//	//채팅 - 내가 채팅한 게시글 총 레코드 수 (검색 레코드 수)
//	public int getBoardCountForMyChat(String keyfield,String keyword,long mem_num)throws Exception{
//		Connection conn= null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String sql =null;
//		String sub_sql ="";
//		int count=0;
//		try {
//			conn=DBUtil.getConnection();
//			if(keyword != null && !"".equals(keyword)) {
//				if(keyfield.equals("1")) sub_sql += "AND title LIKE '%' || ? || '%'";
//				else if(keyfield.equals("2")) sub_sql += "AND id LIKE '%' || ? || '%'";
//				else if(keyfield.equals("3")) sub_sql += "AND content LIKE '%' || ? || '%'";
//			}
//			sql="SELECT COUNT(*) FROM zboard JOIN zmember USING(mem_num)"
//					+ "JOIN zmember USING(mem_num) "
//					+ "JOIN zboard_chatroom USING (board_num) "
//					+ "JOIN (SELECT MAX(chat_num) chat_num, chatroom_num "
//					+ "FROM zboard_chat GROUP BY chatroom_num) "
//					+ "USING(chatroom_num) WHERE reader_num=? ";
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setLong(1, mem_num);
//			if(keyword !=null && !"".equals(keyword)) {
//				pstmt.setString(2, keyword);
//			}
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				count=rs.getInt(1);
//			}
//		}catch(Exception e) {
//			throw new Exception(e);
//		}finally {
//			DBUtil.executeClose(rs, pstmt, conn);
//		}
//		return count;
//	}
//	//채팅- 내가 채팅한 게시글 목록(검색 목록)
//	public List<BoardVO> getListBoardForMyChat(int start, int end, String keyfield, String keyword , long mem_num) throws Exception{
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<BoardVO> list = null;
//		String sql= null;
//		String sub_sql ="";
//		int cnt = 0;
//		
//		try {
//			conn = DBUtil.getConnection();
//			if(keyword != null && !"".equals(keyword)) {
//				if(keyfield.equals("1")) sub_sql += "AND title LIKE '%' || ? || '%'";
//				else if(keyfield.equals("2")) sub_sql += "AND id LIKE '%' || ? || '%'";
//				else if(keyfield.equals("3")) sub_sql += "AND content LIKE '%' || ? || '%'";
//			}
//	         sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM zboard JOIN zmember USING(mem_num) "
//	                 + "JOIN zboard_chatroom USING(board_num) "
//	                 + "JOIN (SELECT MAX(chat_num) chat_num, chatroom_num FROM zboard_chat GROUP BY chatroom_num) USING(chatroom_num) "
//	                 + "LEFT OUTER JOIN (SELECT SUM(read_check) unread_cnt, chatroom_num FROM zboard_chat "
//	                 + "WHERE mem_num!=? GROUP BY chatroom_num) USING(chatroom_num) "
//	                 + "WHERE reader_num=? " + sub_sql + " ORDER BY chat_num DESC)a) WHERE rnum >= ? AND rnum<=?";
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setLong(++cnt,mem_num);
//			pstmt.setLong(++cnt,mem_num);
//			if(keyword != null && !"".equals(keyword)) {
//				pstmt.setString(++cnt,keyword);
//			}
//			pstmt.setInt(++cnt,start);
//			pstmt.setInt(++cnt,end);
//			rs = pstmt.executeQuery();
//			list= new ArrayList<BoardVO>();
//			while(rs.next()) {
//				BoardVO board = new BoardVO();
//				board.setBoard_num(rs.getLong("board_num"));
//				board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
//				board.setHit(rs.getInt("hit"));
//				board.setReg_date(rs.getDate("reg_date"));
//				board.setId(rs.getString("id"));
//				board.setUnread_cnt(rs.getInt("unread_cnt"));
//				list.add(board);
//			}
//			
//		}catch(Exception e) {
//			throw new Exception(e);
//		}finally {
//			DBUtil.executeClose(rs, pstmt, conn);
//		}
//		
//		return list;
//
//	}
//	//채팅 -  채팅방 생성 여부 체크
	public AccomChatRoomVO checkChatRoom(long accom_num,long user_num) throws Exception{
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccomChatRoomVO vo = null;
		String sql =null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT c.*, \r\n"
					+ "       ud_host.user_name AS host_name,\r\n"
					+ "       cu.user_id AS user_id,\r\n"
					+ "       ad.accom_name\r\n"
					+ "FROM accom_chatroom c\r\n"
					+ "JOIN accom a ON c.accom_num = a.accom_num\r\n"
					+ "JOIN accom_detail ad ON a.accom_num = ad.accom_num\r\n"
					+ "JOIN user_detail ud_host ON c.host_num = ud_host.user_num\r\n"
					+ "JOIN ctuser cu ON c.user_num = cu.user_num\r\n"
					+ "WHERE c.accom_num = ? AND c.user_num = ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setLong(1, accom_num);
			pstmt.setLong(2, user_num);
			//sql문 작성
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new AccomChatRoomVO();
				vo.setChatroom_num(rs.getLong("chatroom_num"));
				vo.setAccom_num(rs.getLong("accom_num"));
				vo.setHost_num(rs.getLong("host_num"));
				vo.setUser_num(rs.getLong("user_num"));
				vo.setHost_name(rs.getString("host_name"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setAccom_name(rs.getString("accom_name"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return vo;
	}
//	//채팅 - 채팅방 정보 반환
	public AccomChatRoomVO selectChatRoom(long chatroom_num) throws Exception{
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AccomChatRoomVO vo = null;
		String sql =null;
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT c.*, "
					+ "ud_host.user_name AS host_name, "
					+ "       cu.user_id AS user_id, "
					+ "       ad.accom_name "
					+ "FROM accom_chatroom c "
					+ "JOIN accom a ON c.accom_num = a.accom_num "
					+ "JOIN accom_detail ad ON a.accom_num = ad.accom_num "
					+ "JOIN user_detail ud_host ON c.host_num = ud_host.user_num "
					+ "JOIN ctuser cu ON c.user_num = cu.user_num "
					+ "WHERE chatroom_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, chatroom_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new AccomChatRoomVO();
				vo.setChatroom_num(rs.getLong("chatroom_num"));
				vo.setAccom_num(rs.getLong("accom_num"));
				vo.setHost_num(rs.getLong("host_num"));
				vo.setUser_num(rs.getLong("user_num"));
				vo.setHost_name(rs.getString("host_name"));
				vo.setUser_id(rs.getString("user_id"));
				vo.setAccom_name(rs.getString("accom_name"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return vo;
	}
//	//채팅 - 채팅방 생성
	public void insertChatRoom(AccomChatRoomVO vo)throws Exception{
		Connection conn= null;
		PreparedStatement pstmt = null;
		String sql =null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO accom_chatroom(chatroom_num, accom_num,host_num,user_num)"
					+ " VALUES(seq_a_chatroom.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getAccom_num());
			pstmt.setLong(2, vo.getHost_num());
			pstmt.setLong(3, vo.getUser_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
//	//채팅 - 채팅 등록
	public void insertChat(AccomChatVO chat) throws Exception{
		Connection conn= null;
		PreparedStatement pstmt = null;
		String sql =null;
		try {
			conn = DBUtil.getConnection();
			sql="INSERT INTO accom_chat(chat_num,chatroom_num,user_num,message) VALUES (seq_a_chat.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, chat.getChatroom_num());
			pstmt.setLong(2, chat.getUser_num());
			pstmt.setString(3, chat.getMessage());
			pstmt.executeUpdate();
		}catch(Exception e){
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
//	//채팅 - 채팅메시지 읽기(목록)
	public List<AccomChatVO> getChatDetail(long user_num,long chatroom_num) throws Exception{
		Connection conn= null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs =null;
		List<AccomChatVO> list  = null;
		String sql =null;
		try {
			conn= DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			//내가 보낸 메시지가 아닌 상대방이 보낸 메시지를 읽엇을때
			//read_check를 1에서 0으로 변경
			sql="UPDATE accom_chat SET read_check=0 "
					+ "WHERE user_num!=? AND chatroom_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, user_num);
			pstmt.setLong(2, chatroom_num);
			pstmt.executeUpdate();
			
			sql="SELECT \r\n"
					+ "    ac.*, "
					+ "    cu.*, "
					+ "    acr.accom_num, "
					+ "    ud.user_name AS host_name "
					+ "FROM accom_chat ac\r\n"
					+ "JOIN ctuser cu \r\n"
					+ "    ON ac.user_num = cu.user_num\r\n"
					+ "JOIN accom_chatroom acr \r\n"
					+ "    ON ac.chatroom_num = acr.chatroom_num\r\n"
					+ "JOIN accom a \r\n"
					+ "    ON acr.accom_num = a.accom_num\r\n"
					+ "JOIN user_detail ud \r\n"
					+ "    ON a.host_num = ud.user_num\r\n"
					+ "WHERE ac.chatroom_num = ?\r\n"
					+ "ORDER BY ac.chat_date ASC";
			pstmt2= conn.prepareStatement(sql);
			pstmt2.setLong(1,chatroom_num);
			
			rs=pstmt2.executeQuery();
			list=new ArrayList<AccomChatVO>();
			while(rs.next()) {
				AccomChatVO chat = new AccomChatVO();
				chat.setChat_num(rs.getLong("chat_num"));
				chat.setChatroom_num(rs.getLong("chatroom_num"));
				chat.setUser_num(rs.getLong("user_num"));
				chat.setMessage(rs.getString("message"));
				chat.setChat_date(rs.getString("chat_date"));
				chat.setRead_check(rs.getInt("read_check"));
				chat.setUser_id(rs.getString("user_id"));
				chat.setHost_name(rs.getString("host_name"));
				list.add(chat);
			}
			//sql문이 정상적으로 모두 성공시 커밋
			conn.commit();
		}catch(Exception e) {
			//sql문이 하나라도 실패시 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, null);
			DBUtil.executeClose(rs, pstmt2, conn);
		}
		return list;
	}
	
}
