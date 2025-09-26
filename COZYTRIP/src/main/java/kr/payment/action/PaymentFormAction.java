package kr.payment.action;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;

public class PaymentFormAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        // 로그인 체크
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 세션에서 예약 코드 가져오기
        String reserv_code = (String)session.getAttribute("reserv_code");
        if(reserv_code == null) {
            // 예약 코드가 없으면 메인 페이지로 리다이렉트
            return "redirect:/main/main.do";
        }
        
        // 예약 정보 가져오기
        ReservDAO reservDAO = ReservDAO.getInstance();
        ReservVO reserv = reservDAO.getReservByCode(reserv_code);
        
        // 예약 정보가 없거나 본인의 예약이 아니면 메인 페이지로 리다이렉트
        if(reserv == null || reserv.getUser_num() != user_num) {
            return "redirect:/main/main.do";
        }
        
        // 요청 객체에 정보 저장
        request.setAttribute("reserv", reserv);
        
        // 결제 폼 JSP 페이지로 이동
        return "payment/paymentForm.jsp";
    }
}