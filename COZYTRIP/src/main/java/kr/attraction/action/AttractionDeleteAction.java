package kr.attraction.action;

import java.net.URLEncoder;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kr.attraction.dao.AttractionDAO;
import kr.controller.Action;

public class AttractionDeleteAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String view = null;
        
        try {
            // 파라미터 추출
            long attractionNum = Long.parseLong(request.getParameter("attraction_num"));
            
            // DAO를 통해 데이터베이스에서 데이터 삭제
            AttractionDAO dao = AttractionDAO.getInstance();
            dao.deleteAttraction(attractionNum);
            
            // 삭제 완료 메시지 설정
            String message = "삭제가 완료되었습니다.";
            
            // 삭제 후 리스트 페이지로 리다이렉트
            view = "redirect:/user/attractionList.do?message=" + URLEncoder.encode(message, "UTF-8");
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        
        return view;
    }
}
