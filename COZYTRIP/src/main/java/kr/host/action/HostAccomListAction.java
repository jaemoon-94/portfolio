package kr.host.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;

public class HostAccomListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		HostDAO dao = HostDAO.getInstance();
		List<AccomVO> vo =dao.getListAccomByHost(user_num);
		request.setAttribute("myAccomList", vo);
		return "host/hostAccomList.jsp";
	}

}
