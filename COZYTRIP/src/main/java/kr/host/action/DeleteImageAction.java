package kr.host.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.vo.AccomImageVO;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class DeleteImageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> mapAjax = new HashMap<>();
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            mapAjax.put("result", "logout");
        } else {
            long image_num = Long.parseLong(request.getParameter("image_num"));
            HostDAO dao = HostDAO.getInstance();
            AccomImageVO image = dao.getAccomImage(image_num);
            AccomVO accom = dao.getHostAccomDetail(image.getAccom_num());

            if (!user_num.equals(accom.getHost_num())) {
                mapAjax.put("result", "wrongAccess");
            } else {
                dao.deleteAccomImage(image_num);
                FileUtil.removeFile(request, image.getImage_name());
                mapAjax.put("result", "success");
            }
        }

        return StringUtil.parseJSON(request, mapAjax);
    }

}





