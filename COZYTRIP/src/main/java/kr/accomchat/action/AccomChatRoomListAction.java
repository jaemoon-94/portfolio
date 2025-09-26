package kr.accomchat.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accomchat.dao.AccomChatDAO;
import kr.accomchat.vo.AccomChatVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class AccomChatRoomListAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		Long user_num = (Long) session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			AccomChatDAO dao=AccomChatDAO.getInstance();
			
			List<AccomChatVO> room_list = dao.getUserChatList(user_num);
			if(room_list==null) room_list = Collections.emptyList();
			mapAjax.put("result", "success");
			mapAjax.put("list", room_list);
		}
		//json문자열로 반환
		return StringUtil.parseJSON(request, mapAjax);
	}
	
}