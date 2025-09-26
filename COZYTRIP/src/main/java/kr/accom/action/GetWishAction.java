package kr.accom.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.accom.dao.WishListDAO;
import kr.accom.vo.WishListVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class GetWishAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("accom_num param: " + request.getParameter("accom_num"));
		long accom_num = Long.parseLong(request.getParameter("accom_num"));
		
		Map<String,Object> mapAjax = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		System.out.println("세션에서 가져온 user_num: " + user_num);  // 세션 값 확인
		WishListDAO dao = WishListDAO.getInstance();
		if (user_num == null) {
			mapAjax.put("status", "noWish");
		} else {
			WishListVO wishVO = dao.selectWish(new WishListVO(user_num,accom_num));
			System.out.println(wishVO);
			if (wishVO != null) {
				mapAjax.put("status", "yesWish");
			} else {
				mapAjax.put("status", "noWish");
			} // if
			
		} // if
		
		return StringUtil.parseJSON(request, mapAjax);
	}

}


































