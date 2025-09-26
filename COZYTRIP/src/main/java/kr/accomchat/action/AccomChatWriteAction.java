package kr.accomchat.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accomchat.dao.AccomChatDAO;
import kr.accomchat.vo.AccomChatVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class AccomChatWriteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) { //로그인이 되지않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인된 경우
			AccomChatVO chat =new AccomChatVO();
			chat.setChatroom_num(Long.parseLong(request.getParameter("chatroom_num")));
			chat.setUser_num(user_num);;
			chat.setMessage(request.getParameter("message"));
			
			AccomChatDAO dao = AccomChatDAO.getInstance();
			dao.insertChat(chat);
			
			mapAjax.put("result", "success");
		}
		//Json문자열로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}
