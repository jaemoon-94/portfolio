package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;

public class ModifyUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) {
			return "redirect:/user/loginForm.do";
		}

		// 폼에서 전송된 데이터 받기
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");

		// UserVO 객체 생성
		UserVO user = new UserVO();
		user.setUserNum(user_num);
		user.setUserName(name);
		user.setUserPhone(phone);
		user.setUserEmail(email);

		// DAO를 통해 데이터베이스 수정
		UserDAO dao = UserDAO.getInstance();
		dao.updateUser(user);

		// 수정 완료 메시지 설정
		request.setAttribute("message", "회원 정보가 수정되었습니다.");

		// 수정이 완료되면 updateForm.do로 리다이렉트
		return "redirect:/user/updateForm.do";
	}
}
