package kr.host.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.vo.AccomCateVO;
import kr.accom.vo.AccomImageVO;
import kr.accom.vo.AccomVO;
import kr.accom.vo.RegionVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.host.vo.HostProfileVO;

public class HostProfileAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}

		HostDAO dao = HostDAO.getInstance();
		HostProfileVO profile = dao.getHostProfile(user_num);
		
		request.setAttribute("profile", profile);
		
		return "host/hostProfile.jsp";
	}

}
