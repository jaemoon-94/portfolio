package kr.host.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.host.vo.HostReservDetailVO;
import kr.host.vo.HostReservVO;
public class HostModifyReservStatusAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long host_num = (Long) session.getAttribute("user_num");
		if(host_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		HostDAO dao = HostDAO.getInstance();
		HostReservDetailVO reserv = new HostReservDetailVO();
		reserv.setReserv_status(Integer.parseInt(request.getParameter("reserv_status")));
		reserv.setReserv_num(Long.parseLong(request.getParameter("reserv_num")));
		
		HostReservDetailVO db_reserv = dao.getHostReservDetail(reserv.getReserv_num());
		if(db_reserv.getHost_num() != host_num) {
			return "redirect:/member/loginForm.do";
		}
		//상태가 2(취소)로 변경되어있는지 체크
		if(db_reserv.getReserv_status()==2) {
			request.setAttribute("notice_msg", "예약이 취소되어 예약 상태를 수정할 수 없습니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/host/reservDetail.do?reserv_num="+reserv.getReserv_num());
			return "common/alert_view.jsp";
		}

		
		//배송상태 수정
		dao.modifyReservStatus(reserv);
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다");
		request.setAttribute("notice_url", request.getContextPath()+"/host/reservDetail.do?reserv_num="+reserv.getReserv_num());
		
		return "common/alert_view.jsp";
	}

}
