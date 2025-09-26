package kr.host.action;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import kr.accom.vo.AccomImageVO;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.util.FileUtil;

public class HostModifyAccomAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");
        if (user_num == null) {
            return "redirect:/user/loginForm.do";
        }

        long accom_num = Long.parseLong(request.getParameter("accom_num"));
        HostDAO dao = HostDAO.getInstance();
        AccomVO db_accom = dao.getHostAccomDetail(accom_num);

        if (user_num != db_accom.getHost_num()) {
            return "common/accessDenied.jsp";
        }

        AccomVO accom = new AccomVO();
        accom.setAccom_num(accom_num);
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

        // 메인 이미지가 업로드된 경우에만 저장
        Part mainPart = request.getPart("main_image");
        if (mainPart != null && mainPart.getSubmittedFileName() != null && !mainPart.getSubmittedFileName().isEmpty()) {
            String mainImage = FileUtil.uploadFile(request, "main_image");
            AccomImageVO mainImageVO = new AccomImageVO();
            mainImageVO.setImage_name(mainImage);
            mainImageVO.setMain(1); // 메인 이미지
            imageList.add(mainImageVO);
        }

        // 서브 이미지 반복 업로드 처리
        for (int i = 1; i <= 9; i++) {
            String inputName = "sub_image" + i;
            Part part = request.getPart(inputName);
            if (part != null && part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                String subImage = FileUtil.uploadFile(request, inputName);
                AccomImageVO imageVO = new AccomImageVO();
                imageVO.setImage_name(subImage);
                imageVO.setMain(0); // 서브 이미지
                imageList.add(imageVO);
            }
        }

        // 이미지가 있는 경우에만 리스트 세팅
        if (!imageList.isEmpty()) {
            accom.setImageList(imageList);
        }

        dao.modifyAccom(accom);

        request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/host/modifyAccomForm.do?accom_num=" + accom_num);
		
		return "common/alert_view.jsp";
    }
}
