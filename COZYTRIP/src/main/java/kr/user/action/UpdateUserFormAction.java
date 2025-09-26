package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;

public class UpdateUserFormAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("user_id");

        // 로그인이 안되어 있으면 로그인 페이지로 리디렉션
        if (userId == null) {
            return "redirect:/user/loginForm.do";
        }

        Long user_num = (Long) session.getAttribute("user_num");

        // 유저 정보 조회
        UserDAO dao = new UserDAO();
        UserVO user = dao.getUser(user_num);

        // request에 저장
        request.setAttribute("user", user);

        return "user/updateForm.jsp";
    }
}
