package kr.payment.action;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.payment.dao.PaymentDAO;
import kr.payment.vo.PaymentVO;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;
import kr.util.KakaoPayUtil;

public class PaymentReadyAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        // 세션 타임아웃 연장 (30분)
        session.setMaxInactiveInterval(1800);
        
        System.out.println("✅ PaymentReadyAction - 세션 정보:");
        System.out.println("사용자 번호: " + user_num);
        System.out.println("session ID: " + session.getId());
        System.out.println("세션 만료 시간: " + session.getMaxInactiveInterval() + "초");
        
        // 로그인 체크
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // POST 방식 요청 체크
        if(!"POST".equals(request.getMethod())) {
            return "redirect:/main/main.do";
        }
        
        // 세션에서 예약 코드 가져오기
        String reserv_code = (String)session.getAttribute("reserv_code");
        System.out.println("로그: 예약 코드: " + reserv_code);
        if(reserv_code == null) {
            return "redirect:/main/main.do";
        }
        
        // 예약 정보 가져오기
        ReservDAO reservDAO = ReservDAO.getInstance();
        ReservVO reserv = reservDAO.getReservByCode(reserv_code);
        System.out.println("로그: 예약 정보: " + reserv);

        // 예약 정보가 없거나 본인의 예약이 아니면 메인 페이지로 리다이렉트
        if(reserv == null || reserv.getUser_num() != user_num) {
            return "redirect:/main/main.do";
        }
        
        // 예약 정보와 숙소 정보를 request에 저장
        request.setAttribute("reserv", reserv);
        request.setAttribute("reservDetail", reserv.getReservDetailVO());
        request.setAttribute("accom", reserv.getAccomVO());
        
        // 숙박 일수 계산
        long nights = (reserv.getReservDetailVO().getCheck_out_date().getTime() - 
                      reserv.getReservDetailVO().getCheck_in_date().getTime()) / (1000 * 60 * 60 * 24);
        request.setAttribute("nights", nights);
        
        // 결제 방법 파라미터 받기
        String payment_method = request.getParameter("payment_method");
        System.out.println("로그: 결제 방법: " + payment_method);
        
        // 카카오페이 결제 준비 요청
        String item_name = "숙소 예약 - " + reserv_code;
        String user_id = String.valueOf(user_num);
        
        // PaymentVO 객체 생성
        PaymentVO payment = new PaymentVO();
        payment.setReserv_num(reserv.getReserv_num());
        payment.setAmount(reserv.getReservDetailVO().getTotal_price());
        payment.setPayment_status("ready"); // 준비 상태
        payment.setPayment_method(payment_method);
        
        // ngrok URL 가져오기
        String baseUrl = "https://" + request.getHeader("X-Forwarded-Host");
        if (baseUrl.equals("https://null")) {
            baseUrl = "https://76ac-106-241-247-83.ngrok-free.app";
        }
        
        // 먼저 payment_num 발급
        PaymentDAO paymentDAO = PaymentDAO.getInstance();
        payment.setPayment_num(paymentDAO.getNextPaymentNum());
        
        // 카카오페이 요청
        System.out.println("✅ KakaoPayUtil.kakaoPayReady() 호출 전");
        // context path 추가
        String contextPath = request.getContextPath();
        
        Map<String, String> resultMap = KakaoPayUtil.kakaoPayReady(
            String.valueOf(reserv_code),
            item_name,
            1,
            payment.getAmount(),
            baseUrl + contextPath + "/payment/approve.do?p=" + payment.getPayment_num(),
            baseUrl + contextPath + "/payment/cancel.do?p=" + payment.getPayment_num(),
            baseUrl + contextPath + "/payment/fail.do?p=" + payment.getPayment_num()
        );
        
        // 카카오페이로부터 받은 TID 설정
        String tid = resultMap.get("tid");
        if (tid == null || tid.isEmpty()) {
            throw new Exception("카카오페이로부터 TID를 받지 못했습니다.");
        }
        
        // TID 설정
        payment.setTid(tid);
        
        // 결제 정보를 DB에 저장
        paymentDAO.insertPayment(payment);
        
        // 결제 정보를 세션에 저장
        session.setAttribute("payment_num", payment.getPayment_num());
        session.setAttribute("reserv_num", reserv.getReserv_num());
        
        System.out.println("✅ 콜백 URL 설정:");
        System.out.println("승인 URL: " + baseUrl + contextPath + "/payment/approve.do?p=" + payment.getPayment_num());
        System.out.println("취소 URL: " + baseUrl + contextPath + "/payment/cancel.do?p=" + payment.getPayment_num());
        System.out.println("실패 URL: " + baseUrl + contextPath + "/payment/fail.do?p=" + payment.getPayment_num());
        
        // 카카오페이 결제 페이지로 리다이렉트
        String redirectUrl = resultMap.get("next_redirect_pc_url");
        if (redirectUrl == null || redirectUrl.isEmpty()) {
            throw new Exception("카카오페이 리다이렉트 URL을 받지 못했습니다.");
        }
        System.out.println("카카오페이 리다이렉트 URL: " + redirectUrl);

        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            // JavaScript로 리다이렉트
            request.setAttribute("redirect_url", redirectUrl);
            return "payment/redirect.jsp";
        } else {
            // 에러 처리
            request.setAttribute("notice_msg", "결제 페이지 URL을 가져오는데 실패했습니다.");
            request.setAttribute("notice_url", request.getContextPath() + "/main/main.do");
            return "common/alert_singlebutton.jsp";
        }
        
        // 카카오페이 결제 페이지로 리다이렉트
  //      return "redirect:" + kakaoPayResult.get("next_redirect_pc_url");
        

        
    }
}