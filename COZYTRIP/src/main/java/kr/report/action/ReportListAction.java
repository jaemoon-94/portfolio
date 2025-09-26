package kr.report.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.report.dao.ReportDAO;
import kr.report.vo.ReportVO;

public class ReportListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			return "redirect:/user/loginForm.do";
		} // if
		
		ReportDAO dao = ReportDAO.getInstance();
		
		int count = dao.getReportCount(user_num);
		
		List<ReportVO> list = null;
		if (count > 0) {
			list = dao.getListReport(1, count, user_num);
		} // if
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		
		return "report/reportList.jsp";
	}

}





























