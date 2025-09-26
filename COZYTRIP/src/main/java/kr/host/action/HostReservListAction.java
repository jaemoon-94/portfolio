package kr.host.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.host.vo.HostReservVO;
import kr.util.PagingUtil;

public class HostReservListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		HostDAO dao = HostDAO.getInstance();
		//셀렉트박스 목록
		List<AccomVO> vo =dao.getListAccomByHost(user_num);
		request.setAttribute("myAccomList", vo);
		
		//페이징처리 및 목록
		String accom = request.getParameter("accom");
        String status = request.getParameter("status");
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || pageNum.isEmpty()) pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		
		
		int count = dao.getReservationCount(user_num, accom, status);
		String addKey = "";
		if (accom != null && !accom.isEmpty()) addKey += "&accom=" + accom;
		if (status != null && !status.isEmpty()) addKey += "&status=" + status;
		PagingUtil page = new PagingUtil(currentPage, count, 20, 10, "reservList.do",addKey);

		List<HostReservVO> list = null;
        if (count > 0) {
            list = dao.getReservationList(user_num, accom, status, page.getStartRow(), page.getEndRow());
        }

        request.setAttribute("count", count);
        request.setAttribute("list", list);
        request.setAttribute("page", page.getPage());

		
		
		return "host/hostReservList.jsp";
	}

}
