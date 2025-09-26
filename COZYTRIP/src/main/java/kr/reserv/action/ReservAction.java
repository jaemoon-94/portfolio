package kr.reserv.action;

import java.sql.Date;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;
import kr.reserv.vo.ReservDetailVO;

public class ReservAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        // 로그인 체크
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // POST 방식 요청 체크
        if(!"POST".equals(request.getMethod())) {
            return "redirect:/main/main.do";
        }
        
        // 파라미터 받기
        int accom_num = Integer.parseInt(request.getParameter("accom_num"));
        Date check_in_date = Date.valueOf(request.getParameter("check_in_date"));
        Date check_out_date = Date.valueOf(request.getParameter("check_out_date"));
        int people_count = Integer.parseInt(request.getParameter("people_count"));
        int total_price = Integer.parseInt(request.getParameter("total_price"));
        String request_msg = request.getParameter("request");
        
        // ReservVO 객체 생성
        ReservVO reservVO = new ReservVO();
        reservVO.setUser_num(user_num);
        reservVO.setAccom_num(accom_num);
        
        // ReservDetailVO 객체 생성
        ReservDetailVO detailVO = new ReservDetailVO();
        detailVO.setRequest(request_msg);
        detailVO.setPayment_status(0); // 미결제 상태
        detailVO.setCheck_in_date(check_in_date);
        detailVO.setCheck_out_date(check_out_date);
        detailVO.setReserv_status(0); // 대기 상태
        detailVO.setPeople_count(people_count);
        detailVO.setTotal_price(total_price);
        
        // DAO를 이용해 예약 정보 저장
        ReservDAO reservDAO = ReservDAO.getInstance();
        String reserv_code = reservDAO.insertReserv(reservVO, detailVO);
        
        // 예약 정보를 세션에 저장 (결제 페이지에서 사용)
        session.setAttribute("reserv_code", reserv_code);
        
        // 결제 페이지로 이동
        return "redirect:/payment/form.do";
    }
}