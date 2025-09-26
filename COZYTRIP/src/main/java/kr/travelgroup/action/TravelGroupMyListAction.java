package kr.travelgroup.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.travelgroup.dao.TravelGroupDAO;
import kr.travelgroup.vo.TravelGroupVO;

public class TravelGroupMyListAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) { // 로그인이 되지 않은 경우
            return "redirect:/user/loginForm.do";
        }
        
        // 사용자가 참여한 동행 그룹 목록 조회
        TravelGroupDAO travelGroupDAO = TravelGroupDAO.getInstance();
        List<TravelGroupVO> myGroupList = travelGroupDAO.getMyTravelGroupList(user_num);
        
        // 요청 객체에 목록 저장
        request.setAttribute("myGroupList", myGroupList);
        
        // JSP 경로 반환
        return "travelgroup/myList.jsp";
    }
}
