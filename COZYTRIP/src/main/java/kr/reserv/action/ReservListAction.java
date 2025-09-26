package kr.reserv.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;
import kr.util.PagingUtil;


public class ReservListAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        // 로그인 체크
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 페이지 처리
        String pageNum = request.getParameter("pageNum");
        if(pageNum == null) pageNum = "1";
        
        // 총 예약 수 구하기
        ReservDAO reservDAO = ReservDAO.getInstance();
        int count = reservDAO.getReservCountByUser(user_num);
        
        // 페이지 유틸 생성
        int currentPage = Integer.parseInt(pageNum);
        int rowCount = 10; // 한 페이지에 표시할 레코드 수
        PagingUtil page = new PagingUtil(currentPage, count, rowCount);
        
        // 예약 목록 가져오기
        List<ReservVO> reservList = null;
        if(count > 0) {
            reservList = reservDAO.getReservListByUser(user_num, page.getStartRow(), page.getEndRow());
        }
        
        // 요청 객체에 정보 저장
        request.setAttribute("count", count);
        request.setAttribute("reservList", reservList);
        request.setAttribute("page", currentPage);
        request.setAttribute("pageCount", (count + rowCount - 1) / rowCount);
        
        // 예약 목록 JSP 페이지로 이동
        return "reserv/reservList.jsp";
    }
}