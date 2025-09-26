package kr.review.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.review.dao.ReviewDAO;

public class WriteReviewFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/user/loginForm.do";
		} // if
		
		// 1. accom_num 파라미터 받기
		String accomNumParam = request.getParameter("accom_num");
		if (accomNumParam != null) {
			try {
				long accom_num = Long.parseLong(accomNumParam);

				// 2. 평균 평점 가져오기
				ReviewDAO dao = ReviewDAO.getInstance();
				Double avgRating = dao.getAverageRatingByAccomNum(accom_num);

				// 3. request에 저장
				request.setAttribute("avgRating", avgRating != null ? String.format("%.1f", avgRating) : "평점 없음");
			} catch (NumberFormatException e) {
				// accom_num이 잘못된 경우
				request.setAttribute("avgRating", "평점 없음");
			}
		}
		
		return "/review/writeReviewForm.jsp";
	}

}






























