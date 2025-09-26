package kr.travelgroup.action;

import java.util.Arrays;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.travelgroup.vo.Region;

public class TravelGroupFormAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 숙소 번호가 있으면 파라미터로 가져옴 (특정 숙소에 대한 동행 그룹 생성)
        String accom_num = request.getParameter("accom_num");
        if(accom_num != null) {
            request.setAttribute("accom_num", accom_num);
        }
        
        // 지역 정보 설정
        List<Region> regionList = Arrays.asList(
            new Region(1L, "서울, 경기도"),
            new Region(2L, "강원도"),
            new Region(3L, "경상도"),
            new Region(4L, "전라도"),
            new Region(5L, "제주도"),
            new Region(6L, "기타")
        );
        request.setAttribute("regionList", regionList);
        
        // JSP 페이지 경로 반환
        return "travelgroup/form.jsp";
    }
}