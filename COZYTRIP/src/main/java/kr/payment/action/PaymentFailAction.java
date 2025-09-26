package kr.payment.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;

public class PaymentFailAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 세션에서 결제 정보 가져오기
        Integer reserv_num = (Integer)session.getAttribute("reserv_num");
        
        // 결제 정보를 세션에서 삭제
        session.removeAttribute("tid");
        session.removeAttribute("reserv_num");
        session.removeAttribute("payment_method");
        session.removeAttribute("amount");
        
        // 오류 메시지 가져오기 (있는 경우)
        String error_msg = request.getParameter("message");
        if(error_msg == null || error_msg.trim().isEmpty()) {
            error_msg = "결제 처리 중 오류가 발생했습니다.";
        }
        
        // 예약 정보 가져오기 (있는 경우)
        if(reserv_num != null) {
            ReservDAO reservDAO = ReservDAO.getInstance();
            ReservVO reserv = reservDAO.getReserv(reserv_num);
            request.setAttribute("reserv", reserv);
        }
        
        // 오류 메시지 설정
        request.setAttribute("error_message", error_msg);
        
        // 실패 페이지로 이동
        return "payment/fail.jsp";
    }
}