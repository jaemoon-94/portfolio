package kr.attraction.action;

import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kr.attraction.dao.AttractionDAO;
import kr.attraction.vo.AttractionVO;
import kr.controller.Action;

public class AttractionEditAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String view = null;
        
        try {
            // 파라미터 추출
            long attractionNum = Long.parseLong(request.getParameter("attraction_num"));
            
            // DAO를 통해 데이터베이스에서 데이터 가져오기
            AttractionDAO dao = AttractionDAO.getInstance();
            AttractionVO attraction = dao.getAttractionByNum(attractionNum);
            
            // 데이터를 request에 저장
            request.setAttribute("attraction", attraction);
            
            // forward 방식으로 view 호출
            view = "user/attractionEdit.jsp";
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        
        return view;
    }
}
