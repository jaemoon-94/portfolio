package kr.main.action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.accom.dao.AccomDAO;
import kr.attraction.dao.AttractionDAO;
import kr.attraction.vo.AttractionVO;
import kr.controller.Action;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 숙소 DAO 인스턴스 가져오기
		AccomDAO accomDao = AccomDAO.getInstance();
		
		// 지역별 숙소 개수 조회 - 기존 메서드 활용
		// 서울, 경기도 (region_num = 1)
		String seoulKeyword = "서울";
		int seoulCount = accomDao.getAccomCount(1, 2);
		
		// 강원도 (region_num = 2)
		String gangwonKeyword = "강원";
		int gangwonCount = accomDao.getAccomCount(2, 2);
		
		// 충청도 (region_num = 3)
		String chungcheongKeyword = "충청";
		int chungcheongCount = accomDao.getAccomCount(3, 2);
		
		// 경상도 (region_num = 4)
		String gyeongsangKeyword = "경상";
		int gyeongsangCount = accomDao.getAccomCount(4, 2);
		
		// 전라도 (region_num = 5)
		String jeollaKeyword = "전라";
		int jeollaCount = accomDao.getAccomCount(5, 2);
		
		// 제주도 (region_num = 6)
		String jejuKeyword = "제주";
		int jejuCount = accomDao.getAccomCount(6, 2);
		
		// request 객체에 저장
		request.setAttribute("seoulCount", seoulCount);
		request.setAttribute("gangwonCount", gangwonCount);
		request.setAttribute("chungcheongCount", chungcheongCount);
		request.setAttribute("gyeongsangCount", gyeongsangCount);
		request.setAttribute("jeollaCount", jeollaCount);
		request.setAttribute("jejuCount", jejuCount);
		
		// 어트랙션 데이터 가져오기
		AttractionDAO attractionDao = AttractionDAO.getInstance();
		// 메인 페이지에 표시할 추천 어트랙션 목록 (최신 8개)
		List<AttractionVO> attractionList = attractionDao.getAttractionList();
		// 목록이 너무 길면 8개만 표시
		if(attractionList.size() > 8) {
			attractionList = attractionList.subList(0, 8);
		}
		// request 객체에 어트랙션 목록 저장
		request.setAttribute("attractionList", attractionList);
		
		//JSP 경로 반환
		return "main/main.jsp";
	}
}
