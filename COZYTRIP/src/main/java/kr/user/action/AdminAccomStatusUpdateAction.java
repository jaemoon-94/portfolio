package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.accom.dao.AccomDAO;

public class AdminAccomStatusUpdateAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 관리자 권한 확인
        HttpSession session = request.getSession();
        Integer user_auth = (Integer) session.getAttribute("user_auth");
        if (user_auth == null || user_auth != 2) {
            return "redirect:/main/main.do";
        }


        try {
            // 숙소 번호와 상태값 받기
            long accom_num = Long.parseLong(request.getParameter("accom_num"));
            int accom_status = Integer.parseInt(request.getParameter("accom_status"));

            // DAO를 통해 상태 업데이트
            AccomDAO dao = AccomDAO.getInstance();
            int result = dao.updateAccomStatus(accom_num, accom_status);

            if(result > 0) {
                // 성공 시 다시 리스트 페이지로 리다이렉트
                String keyfield = request.getParameter("keyfield");
                String keyword = request.getParameter("keyword");
                
                StringBuilder redirectUrl = new StringBuilder("/user/adminAccomList.do");
                if(keyfield != null && !keyfield.isEmpty()) {
                    redirectUrl.append("?keyfield=").append(keyfield);
                }
                if(keyword != null && !keyword.isEmpty()) {
                    if(redirectUrl.indexOf("?") == -1) {
                        redirectUrl.append("?");
                    } else {
                        redirectUrl.append("&");
                    }
                    redirectUrl.append("keyword=").append(keyword);
                }
                
                return "redirect:" + redirectUrl.toString();
            } else {
                return "redirect:/user/adminAccomList.do?error=update_failed";
            }
            
        } catch(Exception e) {
            e.printStackTrace();
            return "redirect:/user/adminAccomList.do?error=exception";
        }
    }
}
