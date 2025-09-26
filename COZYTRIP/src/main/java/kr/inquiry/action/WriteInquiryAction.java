package kr.inquiry.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.dao.AccomDAO;
import kr.inquiry.vo.InquiryVO;
import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.util.FileUtil;

public class WriteInquiryAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		long accom_num = Long.parseLong(request.getParameter("accom_num"));
		int is_secret = request.getParameter("is_secret") == null ? 0 : 1;
		//로그인 된 경우
		InquiryVO inq = new InquiryVO();
		inq.setUser_num(user_num);
		inq.setAccom_num(accom_num);
		inq.setInq_content(request.getParameter("inq_content"));
		inq.setIs_secret(is_secret);
		
		InquiryDAO dao = InquiryDAO.getInstance();
		dao.insertInquiry(inq);
		
		request.setAttribute("notice_msg", "문의글 작성 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/accom/accomDetail.do?accom_num="+accom_num);		
		
		return "common/alert_view.jsp";
	}

}
