package kr.travelgroup.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.controller.Action;
import kr.travelgroup.dao.TravelGroupDAO;
import kr.travelgroup.vo.TravelGroupVO;

public class TravelGroupCloseAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 로그인 체크
        HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");
        
        if(user_num == null) {
            return "redirect:/user/loginForm.do";
        }
        
        // 그룹 번호 가져오기
        long group_num = Long.parseLong(request.getParameter("group_num"));
        
        // 그룹 정보 가져오기
        TravelGroupDAO groupDao = TravelGroupDAO.getInstance();
        TravelGroupVO group = groupDao.getTravelGroup(group_num);
        
        if(group == null) {
            // 존재하지 않는 그룹일 경우
            request.setAttribute("notice_msg", "존재하지 않는 동행 그룹입니다.");
            request.setAttribute("notice_url", "/travelgroup/list.do");
            return "common/alert_singlebutton.jsp";
        }
        
        // 그룹 생성자인지 확인
        if(user_num.longValue() != group.getCreator_num()) {
            // 생성자가 아닌 경우 권한 없음
            request.setAttribute("notice_msg", "동행 그룹 모집 마감 권한이 없습니다.");
            request.setAttribute("notice_url", "/travelgroup/detail.do?group_num=" + group_num);
            return "common/alert_singlebutton.jsp";
        }
        
        // 이미 모집 마감 상태인지 확인
        if(group.getStatus() != 0) {
            request.setAttribute("notice_msg", "이미 모집이 마감된 동행 그룹입니다.");
            request.setAttribute("notice_url", "/travelgroup/detail.do?group_num=" + group_num);
            return "common/alert_singlebutton.jsp";
        }
        
        try {
            // 그룹 상태를 모집 마감(1)으로 변경
            groupDao.updateTravelGroupStatus((int)group_num, 1);
            
            // 성공 메시지 표시 대신 직접 리다이렉트
            return "redirect:/travelgroup/detail.do?group_num=" + group_num;
            
        } catch(Exception e) {
            e.printStackTrace();
            
            // 실패 메시지 설정
            request.setAttribute("notice_msg", "동행 그룹 모집 마감 중 오류가 발생했습니다.");
            request.setAttribute("notice_url", "/travelgroup/detail.do?group_num=" + group_num);
            
            return "common/alert_singlebutton.jsp";
        }
    }
}
