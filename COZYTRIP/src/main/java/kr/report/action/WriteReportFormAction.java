package kr.report.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;

public class WriteReportFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    HttpSession session = request.getSession();
	    Long user_num = (Long)session.getAttribute("user_num");
	    if (user_num == null) {
	        return "redirect:/user/loginForm.do";
	    }

	    // request.getParameter()로 수정
	    request.setAttribute("accom_name", request.getParameter("accom_name"));
	    request.setAttribute("accom_num", request.getParameter("accom_num"));

	    return "report/writeReportForm.jsp";
	}

}
































