package kr.attraction.action;

import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kr.attraction.dao.AttractionDAO;
import kr.attraction.vo.AttractionVO;
import kr.controller.Action;

public class AttractionDetailAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String view = null;
        
        try {
            // 파라미터 추출
            long attractionNum = Long.parseLong(request.getParameter("attraction_num"));
            
            // DAO를 통해 데이터베이스에서 데이터 가져오기
            AttractionDAO dao = AttractionDAO.getInstance();
            List<AttractionVO> list = dao.getAttractionList();
            
            // attraction_num을 가진 데이터 찾기
            AttractionVO attraction = null;
            for (AttractionVO vo : list) {
                if (vo.getAttraction_num() == attractionNum) {
                    attraction = vo;
                    break;
                }
            }
            
            // 데이터를 request에 저장
            request.setAttribute("attraction", attraction);
            
            // forward 방식으로 view 호출
            view = "user/attractionDetail.jsp";
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        
        return view;
    }
}
