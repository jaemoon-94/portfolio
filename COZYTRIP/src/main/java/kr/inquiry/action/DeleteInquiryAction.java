package kr.inquiry.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;

public class DeleteInquiryAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num =(Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		//로그인이 된 경우
		long inquiry_num = Long.parseLong(request.getParameter("inquiry_num"));
		InquiryDAO dao = InquiryDAO.getInstance();
		
		//로그인한 회원번호와 작성자 회원번호 일치
		dao.deleteMyInquiry(inquiry_num);
		
		request.setAttribute("notice_msg", "문의 삭제 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/user/myPage/inquiry.do");
		
		return "common/alert_view.jsp";
	}
}
