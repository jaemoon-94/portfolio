package kr.host.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.host.vo.HostIncomeVO;
import kr.util.StringUtil;

public class HostIncomeByAccomAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
        Long user_num = (Long)session.getAttribute("user_num");

        Map<String, Object> mapAjax = new HashMap<>();
        
        if (user_num == null) {
            mapAjax.put("result", "logout");
            return StringUtil.parseJSON(request, mapAjax);
        }

        long accom_num = Long.parseLong(request.getParameter("accom"));

        HostDAO dao = HostDAO.getInstance();
        List<HostIncomeVO> incomeList = dao.getMonthlyIncomeByAccom(accom_num); // accom 기준 수익 조회
        
        int yearlyIncomebyAccom = 0;
        for (HostIncomeVO vo : incomeList) {
            int income = (int) (vo.getTotalIncome() * 0.9); // 원래 수익
            vo.setTotalIncome(income); // 수수료 제외 수익 저장
            yearlyIncomebyAccom += income;
        }
        mapAjax.put("result", "success");
        mapAjax.put("incomeList", incomeList);
        mapAjax.put("yearlyIncomebyAccom", yearlyIncomebyAccom);
        return StringUtil.parseJSON(request, mapAjax);
    }

}
