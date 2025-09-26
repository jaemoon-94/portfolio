package kr.accom.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.dao.AccomDAO;
import kr.accom.vo.AccomImageVO;
import kr.accom.vo.AccomVO;
import kr.attraction.dao.AttractionDAO;
import kr.attraction.vo.AttractionVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.review.dao.ReviewDAO;
import kr.review.vo.ReviewVO;
import kr.util.PagingUtil;
import kr.util.StringUtil;

public class AccomDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long accom_num = Long.parseLong(request.getParameter("accom_num"));
		AccomDAO accomDao = AccomDAO.getInstance();
		AccomVO accom = accomDao.getAccom(accom_num);
	    String check_in_date = request.getParameter("check_in_date");
	    String check_out_date = request.getParameter("check_out_date");
	    String people_count = request.getParameter("people_count");
		
		HostDAO host = HostDAO.getInstance(); 
		List<AccomImageVO> imageList = host.getAccomImages(accom_num);
		
		accom.setHost_intro(StringUtil.useBrHtml(accom.getHost_intro()));
		
		InquiryDAO inqDao = InquiryDAO.getInstance();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null || pageNum.isEmpty()) pageNum = "1";
		int currentPage = Integer.parseInt(pageNum);
		int count = inqDao.getInquiryCount(accom_num);
		PagingUtil page = new PagingUtil(currentPage, count, 10,10,"accomDetail.do");
		List<InquiryVO> inqList = null;
		if(count>0) {
			inqList = inqDao.getInqList(accom_num, page.getStartRow(), page.getEndRow());
		}
		
		AttractionDAO attDAO= AttractionDAO.getInstance();
		List<AttractionVO> attList = attDAO.getAttractionsByAccomNum(accom_num);
		request.setAttribute("attList", attList);
		
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		
		List<ReviewVO> reviewList = reviewDAO.getListReviewByAccom_num(accom_num);
		request.setAttribute("reviewList", reviewList);
		Double avgRating = reviewDAO.getAverageRatingByAccomNum(accom_num);
		request.setAttribute("avgRating", avgRating);
		
		
		request.setAttribute("accom", accom);
		request.setAttribute("imageList", imageList);
		request.setAttribute("checkin", check_in_date);
		request.setAttribute("checkout", check_out_date);
		request.setAttribute("peopleCount", people_count);
		
		request.setAttribute("inqList", inqList);
		request.setAttribute("count", count);
		request.setAttribute("page", page.getPage());
		return "accom/accomDetail.jsp";
	}

}
