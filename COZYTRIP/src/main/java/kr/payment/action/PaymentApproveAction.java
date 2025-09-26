package kr.payment.action;

import java.util.HashMap;
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

public class PaymentApproveAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 세션 체크 및 갱신
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        System.out.println("✅ PaymentApproveAction - 시작");
        System.out.println("전체 URL: " + request.getRequestURL() + "?" + request.getQueryString());
        System.out.println("Session ID: " + session.getId());
        System.out.println("User Num: " + user_num);
        // URL 파라미터에서 정보 가져오기
        String baseUrl = "https://" + request.getHeader("X-Forwarded-Host");
        if (baseUrl.equals("https://null")) {
            baseUrl = "https://76ac-106-241-247-83.ngrok-free.app";
        }
        String payment_num_str = request.getParameter("p");
        String pg_token = request.getParameter("pg_token");
        
        System.out.println("✅ PaymentApproveAction - 파라미터 확인:");
        System.out.println("payment_num: " + payment_num_str);
        System.out.println("pg_token: " + pg_token);
        
        // 필수 파라미터 체크
        if(payment_num_str == null || pg_token == null) {
            System.out.println("❌ 필수 파라미터 누락");
            request.setAttribute("payment_result", "fail");
            request.setAttribute("error_msg", "결제 정보가 잘못되었습니다.");
            return "payment/paymentResult.jsp";
        }
        
        try {
            long payment_num = Long.parseLong(payment_num_str);
            System.out.println("✅ 결제 번호: " + payment_num);
            
            // 결제 정보로 나머지 정보 가져오기
            PaymentDAO paymentDAO = PaymentDAO.getInstance();
            PaymentVO payment = paymentDAO.getPayment(payment_num);
            
            if(payment == null) {
                throw new IllegalArgumentException("존재하지 않는 결제 정보");
            }
            
            // tid 조회
            String tid = paymentDAO.getTidByPaymentNum(payment_num);
            if(tid == null) {
                throw new IllegalArgumentException("결제 정보가 올바르지 않습니다. (거래ID 누락)");            
            }
            
            // 예약 정보로 reserv_code 가져오기
            ReservDAO reservDAO = ReservDAO.getInstance();
            ReservVO reserv = reservDAO.getReserv(payment.getReserv_num());
            
            if(reserv == null) {
                throw new IllegalArgumentException("예약 정보가 존재하지 않습니다.");
            }
            
            // 카카오페이 결제 승인 요청
            Map<String, String> kakaoPayResult = KakaoPayUtil.kakaoPayApprove(
                tid,
                reserv.getReserv_code(),
                pg_token
            );
            
            System.out.println("✅ PaymentApproveAction - 카카오페이 응답:");
            System.out.println(kakaoPayResult);
            
            if (kakaoPayResult == null || kakaoPayResult.get("aid") == null) {
                throw new Exception("카카오페이 승인 응답이 올바르지 않습니다");
            }
            
            // 결제 정보 업데이트
            payment.setPayment_status("completed");
            paymentDAO.updatePaymentStatus(payment_num, "completed");
            
            // 예약 상태 업데이트
            reservDAO.updateReservStatus(payment.getReserv_num(), 1); // 1: 예약 완료 상태
            
            // 결제 결과 표시를 위한 데이터 저장
            request.setAttribute("payment_result", "success");
            
            // 결제 정보를 request에 저장
            Map<String, Object> paymentInfo = new HashMap<>();
            paymentInfo.put("amount", payment.getAmount());
            paymentInfo.put("payment_method", "카카오페이");
            paymentInfo.put("created_at", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
            paymentInfo.put("partner_order_id", reserv.getReserv_code());
            request.setAttribute("payment_info", paymentInfo);
            
            // 세션 갱신 및 강화
            if(user_num != null) {
                // 사용자 정보 유지
                session.setAttribute("user_num", user_num);
                
                // 세션 타임아웃을 30분으로 설정
                session.setMaxInactiveInterval(1800);
                
                // 세션 상태 확인
                System.out.println("✅ 결제 성공 후 세션 정보:");
                System.out.println("Session ID: " + session.getId());
                System.out.println("User Num: " + session.getAttribute("user_num"));
                System.out.println("세션 만료 시간: " + session.getMaxInactiveInterval() + "초");
            }
            
            // 결제 관련 세션 정보 정리
            session.removeAttribute("payment_num");
            session.removeAttribute("reserv_num");
            
            return "payment/paymentResult.jsp";
            
        } catch (Exception e) {
            System.out.println("❌ 에러 발생: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("payment_result", "fail");
            
            // 에러 메시지 상세화
            String errorMsg = e.getMessage();
            if (e instanceof IllegalArgumentException) {
                errorMsg = "유효하지 않은 결제 정보입니다.";
            } else if (errorMsg != null && !errorMsg.isEmpty()) {
                errorMsg = "결제 처리 중 오류: " + errorMsg;
            } else {
                errorMsg = "결제 처리 중 오류가 발생했습니다.";
            }
            
            request.setAttribute("error_msg", errorMsg);
            return "payment/paymentResult.jsp";
        }

        

    }
}