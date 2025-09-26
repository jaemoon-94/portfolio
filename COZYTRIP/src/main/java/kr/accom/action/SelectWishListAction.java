package kr.accom.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.dao.WishListDAO;
import kr.accom.vo.AccomVO;
import kr.controller.Action;

public class SelectWishListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/user/loginForm.do";
		} // if
		
		String keyword = request.getParameter("keyword");
	    String check_in_date = request.getParameter("check_in_date");
	    String check_out_date = request.getParameter("check_out_date");
	    String people_count = request.getParameter("people_count");
		
		WishListDAO wishListDAO = WishListDAO.getInstance();
		List<AccomVO> wishList = null;
		wishList = wishListDAO.getListAccomWishList(1, 5, user_num);
		
		request.setAttribute("list", wishList);
		request.setAttribute("keyword", keyword);
		request.setAttribute("checkin", check_in_date);
		request.setAttribute("checkout", check_out_date);
		request.setAttribute("peopleCount", people_count);
		
		return "accom/wishList.jsp";
	}

}
























