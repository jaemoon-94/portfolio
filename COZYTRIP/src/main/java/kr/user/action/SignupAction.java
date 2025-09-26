package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;

public class SignupAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 자바빈(VO) 생성
        UserVO user = new UserVO();
        user.setUserId(request.getParameter("user_id"));
        user.setUserName(request.getParameter("user_name"));
        user.setUserPw(request.getParameter("passwd"));
        user.setUserPhone(request.getParameter("user_phone"));
        user.setUserEmail(request.getParameter("user_email"));
        	
        UserDAO dao = UserDAO.getInstance();
        dao.insertUser(user); // 회원 등록 메서드

        request.setAttribute("result_title", "회원가입 완료");
        request.setAttribute("result_msg", 
                            "회원가입이 완료되었습니다.");
        request.setAttribute("result_url", 
                             request.getContextPath() + "/main/main.do");
        
        // JSP 경로 반환
        return "common/result_view.jsp";
    }
}