package kr.review.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.review.dao.ReviewDAO;
import kr.review.vo.ReviewVO;

public class UpdateReviewAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/user/loginForm.do";
		} // if
		
		long review_num = Long.parseLong(request.getParameter("review_num"));
		
		ReviewDAO dao = ReviewDAO.getInstance();
		
		// 리뷰 정보 가져오기
		ReviewVO db_review = dao.getReview(review_num);
		if (db_review == null || !user_num.equals(db_review.getUser_num())) {
			return "common/accessDenied.jsp";
		} // if
		
		ReviewVO review = new ReviewVO();
		review.setReview_num(review_num);
		review.setContent(request.getParameter("content"));
		review.setRating(Integer.parseInt(request.getParameter("rating")));
		
		dao.updateReview(review);
		
		return "redirect:/review/myList.do";
		
		/* ajax로 어떻게 해보려 했으나 포기
		Map<String, String> mapAjax = new HashMap<String, String>();
		
		if (user_num == null) {
			mapAjax.put("result", "logout");
		} // if
		
		ReviewVO review = new ReviewVO();
		review.setReview_num(review_num);
		review.setContent(request.getParameter("content"));
//		review.setRating(Integer.parseInt(request.getParameter("rating")));
		
		ReviewDAO dao = ReviewDAO.getInstance();
		
		// 리뷰 정보 가져오기
		ReviewVO db_review = dao.getReview(review_num);

		// 잘못된 접근 (리뷰 작성자가 아님)
		if(db_review == null || !user_num.equals(db_review.getUser_num())) {
		    mapAjax.put("result", "wrongAccess");
		    return StringUtil.parseJSON(request, mapAjax);
		}
		
		dao.updateReview(review);
		
		mapAjax.put("result", "success");
		
		return StringUtil.parseJSON(request, mapAjax);
		*/
		
		
		
	}

}








































