package kr.attraction.action;

import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import kr.attraction.dao.AttractionDAO;
import kr.attraction.vo.AttractionVO;
import kr.controller.Action;

public class AttractionListAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String view = null;
        
        try {
            // 파라미터 추출
            String regionNumStr = request.getParameter("region_num");
            long regionNum = regionNumStr != null && !regionNumStr.trim().isEmpty() ? 
                Long.parseLong(regionNumStr) : 1; // 기본값으로 1(서울/경기) 설정
            String searchKeyword = request.getParameter("searchKeyword");
            
            // DAO를 통해 데이터베이스에서 데이터 가져오기
            AttractionDAO dao = AttractionDAO.getInstance();
            List<AttractionVO> list = null;
            
            if (regionNumStr != null && !regionNumStr.trim().isEmpty()) {
                list = dao.getAttractionsByRegionNum(regionNum, searchKeyword);
            } else {
                list = dao.getAttractionList();
            }
            
            // 데이터를 request에 저장
            request.setAttribute("attractionList", list);
            request.setAttribute("searchKeyword", searchKeyword);
            
            // forward 방식으로 view 호출
            view = "user/attractionList.jsp";
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        
        return view;
    }
}