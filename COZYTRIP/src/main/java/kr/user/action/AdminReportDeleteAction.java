package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.report.dao.ReportDAO;

public class AdminReportDeleteAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 관리자 체크
        HttpSession session = request.getSession();
        Integer user_auth = (Integer)session.getAttribute("user_auth");
        
        if(user_auth == null || user_auth != 2) { // 관리자가 아니면 
            return "redirect:/main/main.do";
        }
        
        // 파라미터 받기
        long report_num = Long.parseLong(request.getParameter("report_num"));
        
        // DAO를 통한 신고 삭제
        ReportDAO dao = ReportDAO.getInstance();
        dao.deleteReport(report_num);
        
        // 신고 목록 페이지로 리다이렉트
        return "redirect:/user/adminReportList.do";
    }
}
