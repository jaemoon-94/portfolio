package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.user.dao.UserDAO;

public class AdminUserAuthAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 관리자 체크
        HttpSession session = request.getSession();
        Integer user_auth = (Integer)session.getAttribute("user_auth");
        
        if(user_auth == null || user_auth != 2) { // 관리자가 아니면 
            return "redirect:/main/main.do";
        }
        
        request.setCharacterEncoding("UTF-8");
        
        // 파라미터 받기
        long user_num = Long.parseLong(request.getParameter("user_num"));
        int auth = Integer.parseInt(request.getParameter("auth"));
        
        // 권한 수정
        UserDAO dao = UserDAO.getInstance();
        dao.updateUserByAdmin(auth, user_num);
        
        return "redirect:/user/adminUserList.do";
    }
}