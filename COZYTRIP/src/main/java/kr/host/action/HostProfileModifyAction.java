package kr.host.action;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.host.vo.HostProfileVO;

public class HostProfileModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");
        if (user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        HostDAO dao = HostDAO.getInstance();
        HostProfileVO profile = new HostProfileVO();
        
        profile.setHost_intro(request.getParameter("host_intro"));
        profile.setUser_num(user_num);
        
        dao.modifyHostProfile(profile);
        
        request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/host/profile.do");
		
		return "common/alert_view.jsp";
    }

}
