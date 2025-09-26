package kr.host.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.host.vo.HostInquiryVO;

public class HostModifyAnswerAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long host_num = 
				(Long)session.getAttribute("user_num");
		if(host_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		HostInquiryVO inq = new HostInquiryVO();
		
		inq.setAnswer_content(request.getParameter("answer_content"));
		System.out.println("1answer_num: " + request.getParameter("answer_num"));
		inq.setAnswer_num(Long.parseLong(request.getParameter("answer_num")));
		HostDAO dao = HostDAO.getInstance();
		dao.modifyAnswer(inq);
		request.setAttribute("notice_msg", "답변 수정 완료");
		request.setAttribute("notice_url", request.getContextPath()+"/host/inquiryCompleted.do");		
				
		return "common/alert_view.jsp";
	}

}
