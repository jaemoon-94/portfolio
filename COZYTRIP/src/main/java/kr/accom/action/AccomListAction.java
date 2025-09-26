package kr.accom.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.accom.dao.AccomDAO;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.review.dao.ReviewDAO;

public class AccomListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
	    String check_in_date = request.getParameter("check_in_date");
	    String check_out_date = request.getParameter("check_out_date");
	    String people_count = request.getParameter("people_count");
	    String regionNumStr = request.getParameter("region_num");
	    int region_num = 0;
		
		AccomDAO dao = AccomDAO.getInstance();
		
		int count = dao.getAccomCount(keyfield, keyword, 2); // 1:승인전,2:미표시,3:승인
		
//		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 4,10,"list.do");
		
		List<AccomVO> list = null;
		if(count > 0) {
			if (regionNumStr != null && !regionNumStr.isEmpty()) {
				region_num = Integer.parseInt(regionNumStr);
				list = dao.getListAccom(1, count, 2, region_num);
			} else {
				list = dao.getListAccom(1, count, keyfield, keyword, 2);; // 1:승인전,2:미표시,3:승인
			}
		}
		
		Map<Long, Double> avgRatings = new HashMap<>();
		ReviewDAO reviewDAO = ReviewDAO.getInstance();

		if (list != null) {
		    for (AccomVO accom : list) {
		        long accom_num = accom.getAccom_num();
		        Double avg = reviewDAO.getAverageRatingByAccomNum(accom_num);
		        avgRatings.put(accom_num, (avg != null) ? avg : 0.0);
		    }
		}

		request.setAttribute("avgRatings", avgRatings);
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("keyword", keyword);
		request.setAttribute("checkin", check_in_date);
		request.setAttribute("checkout", check_out_date);
		request.setAttribute("peopleCount", people_count);
//		request.setAttribute("page", page.getPage());
		
		return "accom/accomList.jsp";
	}

}




















