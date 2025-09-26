package kr.user.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.accom.dao.AccomDAO;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class AdminAccomListAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 관리자 권한 확인
        HttpSession session = request.getSession();
        Integer user_auth = (Integer) session.getAttribute("user_auth");
        if (user_auth == null || user_auth != 2) {
            return "redirect:/main/main.do";
        }

        // 검색 처리
        String keyfield = request.getParameter("keyfield");
        String keyword = request.getParameter("keyword");

        int count = 0;
        int currentPage = 1;
        int rowCount = 10;
        int pageCount = 10;

        if (request.getParameter("pageNum") != null) {
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
        }

        AccomDAO dao = AccomDAO.getInstance();
        count = dao.getAccomCount(keyfield, keyword, 0);

        PagingUtil page = new PagingUtil(keyfield, keyword, currentPage, count, rowCount, pageCount, "adminAccomList.do");

        List<AccomVO> list = null;
        if (count > 0) {
            list = dao.getListAccom(page.getStartRow(), page.getEndRow(), keyfield, keyword, 0);
        }

        request.setAttribute("count", count);
        request.setAttribute("list", list);
        request.setAttribute("pagingHtml", page.getPage());

        return "user/adminAccomList.jsp";

    }
}
