package kr.payment.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.accom.dao.AccomDAO;
import kr.accom.vo.AccomVO;
import kr.payment.dao.PaymentDAO;
import kr.payment.vo.PaymentVO;
import kr.reserv.dao.ReservDAO;
import kr.reserv.dao.ReservDetailDAO;
import kr.reserv.vo.ReservVO;
import kr.reserv.vo.ReservDetailVO;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;

public class PaymentSuccessAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 세션에서 결제 정보 가져오기
        Long reserv_num = (Long)session.getAttribute("reserv_num");
        String tid = (String)session.getAttribute("tid");
        String payment_method = (String)session.getAttribute("payment_method");
        Integer amount = (Integer)session.getAttribute("amount");
        
        // 결제 정보가 없는 경우
        if(tid == null || reserv_num == null || payment_method == null || amount == null) {
            request.setAttribute("notice_msg", "결제 정보가 유효하지 않습니다.");
            request.setAttribute("notice_url", "main.do");
            return "/WEB-INF/views/common/alert_singlebutton.jsp";
        }
        
        try {
            // 예약 정보 가져오기
            ReservDetailDAO reservDetailDAO = ReservDetailDAO.getInstance();
            ReservDAO reservDAO = ReservDAO.getInstance();
            
            // ReservDetailVO 객체 가져오기 - getReservDetail 메소드는 long 타입 파라미터를 받음
            ReservVO reserv = reservDAO.getReserv(reserv_num);
            ReservDetailVO reservDetail = reservDetailDAO.getReservDetail(reserv_num);
            
            if(reservDetail == null) {
                throw new Exception("예약 상세 정보를 찾을 수 없습니다.");
            }
            
            // 숙소 정보 가져오기
            AccomDAO accomDAO = AccomDAO.getInstance();
            Long accom_num = reserv.getAccom_num(); // ReservDetailVO에서 accom_num 필드가 있다고 가정
            AccomVO accom = accomDAO.getAccom(accom_num);
            
            // 결제 정보 저장
            PaymentDAO paymentDAO = PaymentDAO.getInstance();
            PaymentVO payment = new PaymentVO();
            payment.setReserv_num(reserv_num);
            payment.setAmount(amount);
            payment.setPayment_method(payment_method);
            payment.setTid(tid);
            payment.setPayment_status("1"); // 결제 완료 상태
            
            // DB에 결제 정보 저장
            paymentDAO.insertPayment(payment);
            
            // 예약 상태 및 결제 상태 업데이트

            reservDetailDAO.updateReservStatus(reserv_num.intValue(), 1); // 예약 확정 상태
            reservDetailDAO.updatePaymentStatus(reserv_num.intValue(), 1); // 결제 완료 상태
            
            // 세션에서 결제 정보만 제거하고 로그인 정보는 유지
            session.removeAttribute("tid");
            session.removeAttribute("reserv_num");
            session.removeAttribute("payment_method");
            session.removeAttribute("amount");
            
            // 로그인 상태 유지를 위해 세션에 사용자 정보 다시 설정
            session.setAttribute("user_num", user_num);
            
            // 사용자 정보 가져오기
            UserDAO userDAO = UserDAO.getInstance();
            UserVO user = userDAO.getUser(user_num);
            request.setAttribute("reserv", reservDetail); // reservDetail로 통일
            request.setAttribute("reservDetail", reservDetail);
            request.setAttribute("accom", accom);
            request.setAttribute("payment", payment);
            request.setAttribute("user", user);
            
            // 체크인, 체크아웃 날짜 사이의 일수 계산
            long diff = reservDetail.getCheck_out_date().getTime() - reservDetail.getCheck_in_date().getTime();
            int nights = (int)(diff / (24 * 60 * 60 * 1000));
            request.setAttribute("nights", nights);
            
            return "/WEB-INF/views/payment/paymentSuccess.jsp";
            
        } catch(Exception e) {
            // 오류 발생 시
            request.setAttribute("notice_msg", "결제 처리 중 오류가 발생했습니다: " + e.getMessage());
            request.setAttribute("notice_url", "reserv/detail.do?reserv_num=" + reserv_num);
            return "common/alert_singlebutton.jsp";
        }
    }
}