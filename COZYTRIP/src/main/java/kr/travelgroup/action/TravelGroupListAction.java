package kr.travelgroup.action;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.travelgroup.dao.TravelGroupDAO;
import kr.travelgroup.vo.Region;
import kr.travelgroup.vo.TravelGroupVO;
import kr.user.dao.UserDAO;
import kr.user.vo.UserVO;

public class TravelGroupListAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 페이징 처리
        String pageNum = request.getParameter("pageNum");
        if(pageNum == null) pageNum = "1";
        
        int currentPage = Integer.parseInt(pageNum);
        int count = 0;
        int rowCount = 10; // 한 페이지에 표시할 게시글 수
        
        // 필터링 옵션
        int region_num = 0;
        
        // 지역별 필터링
        if(request.getParameter("region_num") != null && !request.getParameter("region_num").equals("0")) {
            region_num = Integer.parseInt(request.getParameter("region_num"));
        }
        
        TravelGroupDAO dao = TravelGroupDAO.getInstance();
        count = dao.getTravelGroupCount(region_num);
        
        // 페이지 처리
        int start = (currentPage - 1) * rowCount + 1;
        int end = start + rowCount - 1;
        
        List<TravelGroupVO> list = null;
        if(count > 0) {
            list = dao.getTravelGroupList(region_num, start, end);
            
            // 작성자 정보 설정
            UserDAO userDAO = UserDAO.getInstance();
            
            for(TravelGroupVO group : list) {
                try {
                    UserVO user = userDAO.getUser(group.getCreator_num());
                    if(user != null) {
                        group.setCreator_id(user.getUserId());
                    } else {
                        group.setCreator_id("알 수 없음");
                    }
                } catch(Exception e) {
                    group.setCreator_id("알 수 없음");
                    e.printStackTrace();
                }
            }
        }
        
        // 페이지 수 계산
        int pageCount = count / rowCount + (count % rowCount == 0 ? 0 : 1);
        
        int startPage = (currentPage - 1) / 10 * 10 + 1;
        int endPage = startPage + 9;
        
        if(endPage > pageCount) endPage = pageCount;
        
        // 지역 정보 설정
        List<Region> regionList = Arrays.asList(
            new Region(1L, "서울, 경기도"),
            new Region(2L, "강원도"),
            new Region(3L, "경상도"),
            new Region(4L, "전라도"),
            new Region(5L, "제주도"),
            new Region(6L, "기타")
        );
        
        // 요청 객체에 데이터 저장
        request.setAttribute("count", count);
        request.setAttribute("list", list);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("region_num", region_num);
        request.setAttribute("regionList", regionList);
        
        // JSP 페이지 경로 반환
        return "travelgroup/list.jsp";
    }
}