package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.user.dao.UserDAO;

public class AdminUserDeleteAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 관리자 체크
        HttpSession session = request.getSession();
        Integer user_auth = (Integer) session.getAttribute("user_auth");

        if (user_auth == null || user_auth != 2) {
            return "redirect:/main/main.do";
        }

        long user_num = Long.parseLong(request.getParameter("user_num"));

        UserDAO dao = UserDAO.getInstance();
        dao.deleteUser(user_num);  // 이미 정의된 메서드 사용

        return "redirect:/user/adminUserList.do";
    }
}
