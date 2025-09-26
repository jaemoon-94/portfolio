package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;
import kr.util.FileUtil;

public class DeleteUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		//로그인이 된 경우
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String passwd = request.getParameter("passwd");
		
		//로그인한 아이디
		String user_id = 
				(String)session.getAttribute("user_id");
		
		UserDAO dao = UserDAO.getInstance();
		UserVO db_member = dao.loginUser(id);
		boolean check = false;
		//사용자가 입력한 아이디가 존재하고 로그인한 아이디와 
		//일치하는지 체크, 입력한 이메일과 저장된 이메일 일치 
		//여부 체크
		if(db_member!=null && id.equals(user_id)
				  && email.equals(db_member.getUserEmail())) {
			//비밀번호 일치 여부 체크
			check = db_member.isCheckedPassword(passwd);
		}
		if(check) {//인증 성공
			//회원정보 삭제
			dao.deleteUser(user_num);
			//프로필 사진 삭제
			FileUtil.removeFile(request, db_member.getPhoto());
			//로그아웃
			session.invalidate();
		}
		
		request.setAttribute("check", check);
		
		return "user/deleteUser.jsp";
	}

}




