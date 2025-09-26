package kr.user.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;

public class LoginAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 폼에서 전송된 데이터 수신
        String userId = request.getParameter("user_id");
        String userPw = request.getParameter("passwd");
        
        UserDAO dao = UserDAO.getInstance();
        UserVO user = dao.loginUser(userId);
        
        boolean check = false;
        if(user != null) {
            // 비밀번호 일치 여부 체크
            check = user.isCheckedPassword(userPw);
            // 정지회원의 경우 상태 표시
            request.setAttribute("auth", user.getAuth());
        }
        
        if(check) { // 인증 성공
            // 로그인 처리
            HttpSession session = request.getSession();
            session.setAttribute("user_num", user.getUserNum());
            session.setAttribute("user_id", user.getUserId());
            session.setAttribute("user_auth", user.getAuth());
            session.setAttribute("user_photo", user.getPhoto());
            
            // 로그 출력
            System.out.println("[DEBUG] 로그인 성공 - ID: " + user.getUserId() + ", AUTH: " + user.getAuth());

            
            // 사용자 권한에 따라 다른 페이지로 리다이렉트
            if(user.getAuth() == 2) { // 관리자인 경우
                return "redirect:/user/adminForm.do";
            } else if(user.getAuth() == 9) { // 탈퇴한 회원인 경우
                // 세션 무효화
                session.invalidate();
                // 알림 메시지 설정
                request.setAttribute("notice_msg", "탈퇴한 회원입니다.");
                request.setAttribute("notice_url", request.getContextPath() + "/user/loginForm.do");
                return "/WEB-INF/views/common/alert_singleView.jsp";
            } else { // 일반 회원인 경우
                return "redirect:/main/main.do";
            }
        }
        
        // 인증 실패
        request.setAttribute("notice_msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
        request.setAttribute("notice_url", request.getContextPath() + "/user/loginForm.do");
        return "/WEB-INF/views/common/alert_singleView.jsp";
    }
}