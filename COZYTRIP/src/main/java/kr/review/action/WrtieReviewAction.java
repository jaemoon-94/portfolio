package kr.review.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.review.dao.ReviewDAO;
import kr.review.vo.ReviewVO;

public class WrtieReviewAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/user/loginForm.do";
		} // if
		
		ReviewVO review = new ReviewVO();
		review.setAccom_name(request.getParameter("accom_name"));
		review.setRating(Integer.parseInt(request.getParameter("rating")));
		review.setContent(request.getParameter("content"));
		review.setReserv_num(Long.parseLong(request.getParameter("reserv_num")));
		
		ReviewDAO dao = ReviewDAO.getInstance();
		dao.insertReview(review);
		
		request.setAttribute("notice_msg", "리뷰 작성 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/review/myList.do");
		
		return "common/alert_view.jsp";
	}

}




























