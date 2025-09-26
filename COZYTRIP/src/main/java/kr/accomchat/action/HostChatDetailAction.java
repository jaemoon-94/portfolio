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

public class HostChatDetailAction implements Action{

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
		room = dao.selectChatRoom(Long.parseLong(chatroom_num));

		//제목 HTML태그 불허
		request.setAttribute("AccomChatRoom", room);
		//JSP경로 반환
		return "host/hostChat_detail.jsp";
	}

}
