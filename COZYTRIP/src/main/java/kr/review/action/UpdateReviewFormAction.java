package kr.review.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.review.dao.ReviewDAO;
import kr.review.vo.ReviewVO;

public class UpdateReviewFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/user/loginForm.do";
		} // if
		
		long review_num = Long.parseLong(request.getParameter("review_num"));
		ReviewDAO dao = ReviewDAO.getInstance();
		ReviewVO review = dao.getReview(review_num);
		if (review == null || !user_num.equals(review.getUser_num())) {
			return "common/accessDenied.jsp";
		} // if
		
		long accom_num = review.getAccom_num();
				
		if (accom_num != 0) {
			try {
//				long accom_num = Long.parseLong(accomNumParam);

				// 2. 평균 평점 가져오기
				Double avgRating = dao.getAverageRatingByAccomNum(accom_num);

				// 3. request에 저장
				request.setAttribute("avgRating", avgRating != null ? String.format("%.1f", avgRating) : "평점 없음");
			} catch (NumberFormatException e) {
				// accom_num이 잘못된 경우
				request.setAttribute("avgRating", "평점 없음");
			}
		}
		
		request.setAttribute("review", review);
		
		
		return "review/updateReviewForm.jsp";
	}

}

































