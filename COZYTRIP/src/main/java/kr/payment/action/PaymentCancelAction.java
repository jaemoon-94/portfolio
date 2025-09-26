package kr.payment.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.payment.dao.PaymentDAO;
import kr.reserv.dao.ReservDAO;

public class PaymentCancelAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 세션에서 결제 정보 가져오기
        String tid = (String)session.getAttribute("tid");
        Integer reserv_num = (Integer)session.getAttribute("reserv_num");
        
        // 결제 정보가 없는 경우
        if(tid == null || reserv_num == null) {
            request.setAttribute("notice_msg", "결제가 취소되었습니다.");
            request.setAttribute("notice_url", "main.do");
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        // 세션에서 결제 관련 정보 삭제
        session.removeAttribute("tid");
        session.removeAttribute("reserv_num");
        session.removeAttribute("payment_method");
        session.removeAttribute("amount");
        
        // 취소 메시지 설정
        request.setAttribute("notice_msg", "결제가 취소되었습니다.");
        request.setAttribute("notice_url", "reserv/detail.do?reserv_num=" + reserv_num);
        
        return "/WEB-INF/views/common/alert_singlebutton.jsp";
    }
}