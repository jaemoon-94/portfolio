package kr.host.action;

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
import kr.util.PagingUtil;
import kr.util.StringUtil;

public class AccomDetailAction  implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long accom_num = Long.parseLong(request.getParameter("accom_num"));
		AccomDAO accomDao = AccomDAO.getInstance();
		AccomVO accom = accomDao.getAccom(accom_num);
		
		HostDAO host = HostDAO.getInstance(); 
		List<AccomImageVO> imageList = host.getAccomImages(accom_num);
		
		accom.setHost_intro(StringUtil.useBrHtml(accom.getHost_intro()));
		
		InquiryDAO inqDao = InquiryDAO.getInstance();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		
		
		AttractionDAO attDAO= AttractionDAO.getInstance();
		System.out.println("accom_num: " + accom_num);
		List<AttractionVO> attList = attDAO.getAttractionsByAccomNum(accom_num);
		request.setAttribute("attList", attList);
		
		
		request.setAttribute("accom", accom);
		request.setAttribute("imageList", imageList);
		return "host/accomDetail.jsp";
	}
}