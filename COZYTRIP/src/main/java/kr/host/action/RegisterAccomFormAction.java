package kr.host.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.vo.AccomCateVO;
import kr.accom.vo.RegionVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;

public class RegisterAccomFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");
        if (user_num == null) {
            return "redirect:/user/loginForm.do";
        }
			
		HostDAO dao = HostDAO.getInstance();
		List<AccomCateVO> cateList = dao.getAllCategories();
		List<RegionVO> regionList = dao.getAllRegions();
        request.setAttribute("cateList", cateList);
        request.setAttribute("regionList", regionList);
		return "host/register-accommodation.jsp";
	}

}
