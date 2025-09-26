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
import kr.util.StringUtil;

public class HostModifyAccomFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		long accom_num = Long.parseLong(request.getParameter("accom_num"));
		HostDAO dao = HostDAO.getInstance();
		AccomVO accom = dao.getHostAccomDetail(accom_num);
		
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		if(user_num != accom.getHost_num()) {
			return "common/accessDenied.jsp";
		}
		List<AccomImageVO> imageList = dao.getAccomImages(accom_num);
		
		List<AccomCateVO> cateList = dao.getAllCategories();
		List<RegionVO> regionList = dao.getAllRegions();
        request.setAttribute("cateList", cateList);
        request.setAttribute("regionList", regionList);
		
		request.setAttribute("accom", accom);
		request.setAttribute("imageList", imageList);	
		
		return "host/hostModifyAccom.jsp";
	}

}
