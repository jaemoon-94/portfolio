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

public class HostMainChatMessageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		//JSP경로 반환
		return "host/hostChatting.jsp";
	}

}
