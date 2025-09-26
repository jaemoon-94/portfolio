package kr.reserv.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.payment.dao.PaymentDAO;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;

public class ReservCancelAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        // 로그인 체크
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 파라미터 받기
        int reserv_num = Integer.parseInt(request.getParameter("reserv_num"));
        String cancel_reason = request.getParameter("cancel_reason");
        
        // 예약 정보 가져오기
        ReservDAO reservDAO = ReservDAO.getInstance();
        ReservVO reserv = reservDAO.getReserv(reserv_num);
        
        // 예약 정보가 없거나 본인의 예약이 아니면 목록 페이지로 리다이렉트
        if(reserv == null || reserv.getUser_num() != user_num) {
            return "redirect:/reserv/reservList.do";
        }
        
        // 이미 취소된 예약이면 상세 페이지로 리다이렉트
        if(reserv.getReservDetailVO().getReserv_status() == 2) {
            request.setAttribute("message", "이미 취소된 예약입니다.");
            request.setAttribute("url", request.getContextPath() + "/reserv/reservDetail.do?reserv_num=" + reserv_num);
            return "common/alert_view.jsp";
        }
        
        // 예약 취소 처리
        reservDAO.cancelReserv(reserv_num, cancel_reason);
        
        // 결제 취소 처리 (결제 완료 상태인 경우)
        if(reserv.getReservDetailVO().getPayment_status() == 1) {
            PaymentDAO paymentDAO = PaymentDAO.getInstance();
            paymentDAO.cancelPayment(paymentDAO.getPaymentByReserv(reserv_num).getPayment_num());
        }
        
        // 예약 목록으로 바로 리다이렉트
        return "redirect:/reserv/list.do";
    }
}