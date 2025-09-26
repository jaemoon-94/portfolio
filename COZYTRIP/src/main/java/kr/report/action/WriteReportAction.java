package kr.report.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.report.dao.ReportDAO;
import kr.report.vo.ReportVO;

public class WriteReportAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/user/loginForm.do";
		} // if
		long accom_num = Long.parseLong(request.getParameter("accom_num"));
		ReportVO report = new ReportVO();
		report.setReason(request.getParameter("reason"));
		report.setAccom_num(Long.parseLong(request.getParameter("accom_num")));
		report.setUser_num(user_num);
		
		ReportDAO dao = ReportDAO.getInstance();
		dao.insertReport(report);
		
		request.setAttribute("notice_msg", "신고 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/report/reportList.do");
		
		System.out.println("세션 user_num = " + user_num);
		
		return "common/alert_view.jsp";
	}
	
}
