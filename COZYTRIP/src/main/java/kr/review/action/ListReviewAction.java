package kr.review.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.review.dao.ReviewDAO;
import kr.review.vo.ReviewVO;
import kr.util.PagingUtil;
import kr.util.StringUtil;

public class ListReviewAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/user/loginForm.do";
		} // if
		
		String pageNum = request.getParameter("pageNum");
		if (pageNum==null) pageNum = "1";
		
		String rowCount = request.getParameter("rowCount");
		if (rowCount==null) rowCount = "10";
		
		ReviewDAO dao = ReviewDAO.getInstance();
		int count = dao.getReviewCount(user_num);
		
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, Integer.parseInt(rowCount));
		
		List<ReviewVO> list = null;
		List<ReviewVO> reviewableList = dao.getListReviewable(page.getStartRow(), page.getEndRow(), user_num);
		if (count > 0) {
			list = dao.getListReview(page.getStartRow(), page.getEndRow(), user_num);
		} else {
			list = Collections.emptyList();
		} // if
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("reviewableList", reviewableList);
		request.setAttribute("page", page.getPage());
		
		return "review/listReview.jsp";
	}

}


























