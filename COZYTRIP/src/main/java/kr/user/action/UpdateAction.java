package kr.user.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;


public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		
		
		//로그인 된 경우
		//회원정보
		UserDAO dao = UserDAO.getInstance();
		UserVO user = dao.getUser(user_num);
			
		request.setAttribute("user", user);
		
		
		//JSP 경로 반환
		return "user/updateForm.jsp";
	}

}






