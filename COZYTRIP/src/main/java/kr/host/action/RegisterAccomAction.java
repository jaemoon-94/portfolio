package kr.host.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.catalina.Host;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import kr.accom.vo.AccomImageVO;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.util.FileUtil;

public class RegisterAccomAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/user/loginForm.do";
		}
		//로그인 된경우
		AccomVO accom =  new AccomVO();
		accom.setHost_num(user_num);
		accom.setRegion_num(Long.parseLong(request.getParameter("region_num")));
		accom.setCate_num(Long.parseLong(request.getParameter("cate_num")));
		accom.setAccom_name(request.getParameter("accom_name"));
		accom.setDescription(request.getParameter("description"));
		accom.setZipcode(request.getParameter("zipcode"));
		accom.setAddress1(request.getParameter("address1"));
		accom.setAddress2(request.getParameter("address2"));
		accom.setPrice(Long.parseLong(request.getParameter("price")));
		accom.setMax_people(Integer.parseInt(request.getParameter("max_people")));
		
		List<AccomImageVO> imageList = new ArrayList<>();
		
		String mainImage = FileUtil.uploadFile(request, "main_image");
		AccomImageVO mainImageVO = new AccomImageVO();
		mainImageVO.setImage_name(mainImage);
		mainImageVO.setMain(1); // 메인 이미지
		imageList.add(mainImageVO);

		for (int i = 1; i <= 9; i++) {
		    String inputName = "sub_image" + i;
		    Part part = request.getPart(inputName);
		    if (part != null && part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
		        String subImage = FileUtil.uploadFile(request, inputName); // 이름으로 호출
		        AccomImageVO imageVO = new AccomImageVO();
		        imageVO.setImage_name(subImage);
		        imageVO.setMain(0); // 서브 이미지
		        imageList.add(imageVO);
		    }
		}

		// 이미지 리스트 숙소 VO에 세팅
		accom.setImageList(imageList);
		
		HostDAO dao = HostDAO.getInstance();
		dao.insertAccom(accom);
		
		
		request.setAttribute("notice_msg", "숙소 등록 완료! 관리자가 승인해야 예약을 받을 수 있습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/host/accomList.do");
		return "common/alert_view.jsp";
		
		
	}

}
