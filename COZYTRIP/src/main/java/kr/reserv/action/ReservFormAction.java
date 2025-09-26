package kr.reserv.action;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.dao.AccomDAO;
import kr.accom.vo.AccomVO;
import kr.controller.Action;

public class ReservFormAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        // 로그인 체크
        if(user_num == null) {
            // 로그인 페이지로 리다이렉트
            return "redirect:/user/loginForm.do";
        }
        
        // 파라미터 받기
        long accom_num = Integer.parseInt(request.getParameter("accom_num"));
        
        // 숙소 정보 가져오기
        AccomDAO accomDAO = AccomDAO.getInstance();
        AccomVO accom = accomDAO.getAccom(accom_num);
        
        String fullAddress = accom.getFullAddress();
        
        // 체크인/체크아웃 날짜를 파라미터로 받음
        String check_in_date = request.getParameter("check_in_date");
        String check_out_date = request.getParameter("check_out_date");
        int people_count = Integer.parseInt(request.getParameter("people_count"));
        int total_price = Integer.parseInt(request.getParameter("total_price"));
        
        // 요청 객체에 정보 저장
        request.setAttribute("accomAddress", fullAddress);
        request.setAttribute("accom", accom);
        request.setAttribute("check_in_date", check_in_date);
        request.setAttribute("check_out_date", check_out_date);
        request.setAttribute("people_count", people_count);
        request.setAttribute("total_price", total_price);
        
        System.out.println("accom_num: " + accom_num);
        System.out.println("check_in_date: " + check_in_date);
        System.out.println("check_out_date: " + check_out_date);
        System.out.println("people_count: " + people_count);
        System.out.println("total_price: " + total_price);

        
        // 예약 폼 JSP 페이지로 이동
        return "reserv/reservForm.jsp";
    }
}