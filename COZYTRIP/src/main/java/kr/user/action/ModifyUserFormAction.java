package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;

public class ModifyUserFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		
		//로그인 된 경우
		UserDAO dao = UserDAO.getInstance();
		UserVO user = dao.getUser(user_num);
		
		request.setAttribute("user", user);
		//JSP 경로 반환
		return "user/modifyUserForm.jsp";
	}

}








