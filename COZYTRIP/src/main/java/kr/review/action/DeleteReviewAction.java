package kr.review.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.review.dao.ReviewDAO;

public class DeleteReviewAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/user/loginForm.do";
		} // if
		
		long review_num = Long.parseLong(request.getParameter("review_num"));
		
		ReviewDAO dao = ReviewDAO.getInstance();
		dao.deleteReview(review_num);
		
		request.setAttribute("notice_msg", "문의 삭제 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/review/myList.do");
		
		return "common/alert_view.jsp";
	}

}

































