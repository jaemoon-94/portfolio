package kr.host.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import kr.accom.vo.AccomImageVO;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class HostModifyAccomStatusAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String> mapAjax = new HashMap<String, String>();
        HttpSession session = request.getSession();
        Long user_num = (Long) session.getAttribute("user_num");

        if (user_num == null) {
            mapAjax.put("result", "logout");
        } else {
            long accom_num = Long.parseLong(request.getParameter("accom_num"));
            int accom_status = Integer.parseInt(request.getParameter("accom_status"));

            HostDAO dao = HostDAO.getInstance();
            AccomVO accom = dao.getHostAccomDetail(accom_num);

            // 로그인 유저와 숙소 주인이 일치하는지 확인
            if (!user_num.equals(accom.getHost_num())) {
                mapAjax.put("result", "wrongAccess");
            } else {
                accom.setAccom_num(accom_num);
                accom.setAccom_status(accom_status);
                dao.modifyAccomStatus(accom);
                mapAjax.put("result", "success");
            }
        }

        return StringUtil.parseJSON(request, mapAjax);
    }
}
