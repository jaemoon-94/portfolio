package kr.user.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.report.dao.ReportDAO;
import kr.report.vo.ReportVO;
import kr.util.PagingUtil;

public class AdminReportListAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 관리자 체크
        HttpSession session = request.getSession();
        Integer user_auth = (Integer)session.getAttribute("user_auth");
        
        if(user_auth == null || user_auth != 2) { // 관리자가 아니면 
            return "redirect:/main/main.do";
        }
        
        // 검색 파라미터 처리
        String keyfield = request.getParameter("keyfield");
        String keyword = request.getParameter("keyword");
        
        // 페이지 처리를 위한 변수 선언
        int count = 0;
        int currentPage = 1;
        int rowCount = 10; // 한 페이지에 표시할 신고 수
        int pageCount = 10; // 한 화면에 표시할 페이지 수
        
        if(request.getParameter("pageNum") != null) {
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }
        
        // 전체 신고수 또는 검색된 신고수
        ReportDAO dao = ReportDAO.getInstance();
        count = dao.getReportCount(null);
        
        // 페이지 처리
        PagingUtil page = new PagingUtil(null, null, currentPage, count, rowCount, pageCount, "adminReportList.do");
        
        // 목록 데이터 가져오기
        List<ReportVO> list = null;
        if(count > 0) {
            list = dao.getAllReports(page.getStartRow(), page.getEndRow());
        }
        
        // 뷰에서 사용할 데이터 세팅
        request.setAttribute("count", count);
        request.setAttribute("list", list);
        request.setAttribute("pagingHtml", page.getPage());
        
        return "user/adminReportList.jsp";
    }
}
