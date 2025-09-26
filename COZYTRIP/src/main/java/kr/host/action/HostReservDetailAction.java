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
import kr.host.vo.HostReservDetailVO;

public class HostReservDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long host_num = (Long)session.getAttribute("user_num");
		long reserv_num = Long.parseLong(request.getParameter("reserv_num"));
		HostDAO dao = HostDAO.getInstance();
		HostReservDetailVO reserv = dao.getHostReservDetail(reserv_num);
		
		if(host_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
//		if(host_num != reserv.getHost_num()) {
//			return "common/accessDenied.jsp";
//		}
	
		request.setAttribute("reserv",reserv);
		return "host/hostReservDetail.jsp";
	}

}
