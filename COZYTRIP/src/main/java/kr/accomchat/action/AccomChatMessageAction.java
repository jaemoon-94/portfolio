package kr.accomchat.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.dao.AccomDAO;
import kr.accom.vo.AccomVO;
import kr.accomchat.dao.AccomChatDAO;
import kr.accomchat.vo.AccomChatRoomVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class AccomChatMessageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		//로그인 된 경우
		String chatroom_num  = request.getParameter("chatroom_num");
		AccomChatDAO dao = AccomChatDAO.getInstance();
		AccomDAO accomDao =AccomDAO.getInstance();
		AccomChatRoomVO room;
		if(chatroom_num == null) {//채팅방 번호가 없는 경우
			long accom_num = Long.parseLong(request.getParameter("accom_num"));
			AccomVO accom = accomDao.getAccom(accom_num);
			room = dao.checkChatRoom(accom_num, user_num);
			if(room == null) { //개설된 채팅방이 없는 경우
				AccomChatRoomVO vo = new AccomChatRoomVO();
				vo.setAccom_num(accom_num);
				vo.setHost_num(accom.getUser_num());
				vo.setUser_num(user_num);
				//채팅방 생성
				dao.insertChatRoom(vo);
				//채팅방 생성 여부를 확인하고 생성된 채팅방 정보 반환
				room= dao.checkChatRoom(accom_num, user_num);
			}
		}else {//채팅방 번호가 있는 경우
			room = dao.selectChatRoom(Long.parseLong(chatroom_num));
		}
		
		//제목 HTML태그 불허
		request.setAttribute("AccomChatRoom", room);
		//JSP경로 반환
		return "accom/chat_detail.jsp";
	}

}
