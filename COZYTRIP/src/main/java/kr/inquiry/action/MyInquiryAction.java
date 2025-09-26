package kr.inquiry.action;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.inquiry.vo.InquiryVO;
import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.util.PagingUtil;

public class MyInquiryAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		InquiryDAO inqDao = InquiryDAO.getInstance();
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || pageNum.isEmpty()) pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		int count = inqDao.getMyInquiryCount(user_num);
		PagingUtil page = new PagingUtil(currentPage, count, 10,10,"inquiryChatting.do");
		List<InquiryVO> inqList = null;
		if(count>0) {
			inqList = inqDao.getMyInqList(user_num, page.getStartRow(), page.getEndRow());
		}
		request.setAttribute("inqList", inqList);
		request.setAttribute("count", count);
		 request.setAttribute("page", page.getPage());
		return "mypage/inquiry.jsp";
	}

}
