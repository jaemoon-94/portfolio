package kr.reserv.action;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.dao.AccomDAO;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.reserv.dao.ReservDAO;
import kr.reserv.vo.ReservVO;

public class ReservDetailAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Integer user_num = (Integer)session.getAttribute("user_num");
        
        // 로그인 체크
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 파라미터 받기
        int reserv_num = Integer.parseInt(request.getParameter("reserv_num"));
        
        // 예약 정보 가져오기
        ReservDAO reservDAO = ReservDAO.getInstance();
        ReservVO reserv = reservDAO.getReserv(reserv_num);
        
        // 예약 정보가 없거나 본인의 예약이 아니면 목록 페이지로 리다이렉트
        /*
        if(reserv == null || reserv.getUser_num() != user_num) {
            return "redirect:/reserv/reservList.do";
        }
        */
        
        // 숙소 정보 가져오기
        AccomDAO accomDAO = AccomDAO.getInstance();
        AccomVO accom = accomDAO.getAccom(reserv.getAccom_num());
        
        // 요청 객체에 정보 저장
        request.setAttribute("reserv", reserv);
        request.setAttribute("accom", accom);
        
        // 예약 상세 JSP 페이지로 이동
        return "reserv/reservDetail.jsp";
    }
}