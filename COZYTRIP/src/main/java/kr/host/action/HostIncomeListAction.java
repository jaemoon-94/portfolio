package kr.host.action;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.vo.AccomVO;
import kr.controller.Action;
import kr.host.dao.HostDAO;
import kr.host.vo.HostIncomeVO;

public class HostIncomeListAction implements Action {
   

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 HttpSession session = request.getSession();
		 Long host_num = (Long)session.getAttribute("user_num");
		 HostDAO dao = HostDAO.getInstance();
	     List<HostIncomeVO> incomeList = dao.getMonthlyIncomeByHost(host_num);

	     for (HostIncomeVO vo : incomeList) {
	         int income = vo.getTotalIncome(); // 원래 수익
	         vo.setTotalIncome((int)(income * 0.9)); // 수수료 제외 수익 저장
	     }
	     
	     int thisYearIncome = 0;
	     int thisMonthIncome = 0;
	     String currentMonth = String.format("%02d", LocalDate.now().getMonthValue());

	     for (HostIncomeVO vo : incomeList) {
	         thisYearIncome += vo.getTotalIncome();
	         if (vo.getMonth().equals(currentMonth)) {
	             thisMonthIncome = vo.getTotalIncome();
	         }
	     }

	     // 수수료 제외
	     thisYearIncome *= 0.9;
	     thisMonthIncome *= 0.9;

	     request.setAttribute("yearlyIncome", thisYearIncome);
	     request.setAttribute("currentMonthIncome", thisMonthIncome);
	     
	     
	     //셀렉트박스 목록
		 List<AccomVO> vo =dao.getListAccomByHost(host_num);
		 request.setAttribute("myAccomList", vo);	     
	     request.setAttribute("incomeList", incomeList);
	     return "host/incomeList.jsp";
	}
}
