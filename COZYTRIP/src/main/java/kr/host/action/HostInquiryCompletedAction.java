package kr.host.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.host.vo.HostInquiryVO;
import kr.host.vo.HostReservVO;
import kr.util.PagingUtil;

public class HostInquiryCompletedAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long host_num = (Long)session.getAttribute("user_num");
		if(host_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		HostDAO dao = HostDAO.getInstance();
		//셀렉트박스 목록getListAccomByHost
		List<AccomVO> vo =dao.getListAccomByHost(host_num);
		request.setAttribute("myAccomList", vo);
		
		//페이징처리 및 목록
		String accom = request.getParameter("accom");
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || pageNum.isEmpty()) pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		
		
		int count = dao.getInqCount(host_num, "1", accom);
		String addKey = "";
		if (accom != null && !accom.isEmpty()) addKey += "&accom=" + accom;
		PagingUtil page = new PagingUtil(currentPage, count, 20, 10, "inquiryWaiting.do",addKey);

		List<HostInquiryVO> inqList = null;
        if (count > 0) {
        	inqList = dao.getInqList(host_num,"1",accom, page.getStartRow(), page.getEndRow());
        }

        request.setAttribute("count", count);
        request.setAttribute("inqList", inqList);
        request.setAttribute("page", page.getPage());

		
		
		return "host/hostInquiryCompleted.jsp";
	}


}
