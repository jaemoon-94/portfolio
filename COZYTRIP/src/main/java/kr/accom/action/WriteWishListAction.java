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

public class WriteWishListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if (user_num == null) {
			mapAjax.put("result", "logout");
		} else {
			long accom_num = Long.parseLong(request.getParameter("accom_num"));
			WishListVO wishVO = new WishListVO();
			wishVO.setAccom_num(accom_num);
			wishVO.setUser_num(user_num);
			
			WishListDAO dao = WishListDAO.getInstance();
			
			WishListVO db_wish = dao.selectWish(wishVO);
			if (db_wish != null) {
				dao.deleteWish(db_wish);
				mapAjax.put("status", "noWish");
			} else {
				dao.insertWish(wishVO);
				mapAjax.put("status", "yesWish");
			} // if
			mapAjax.put("result", "success");
		} // if
		
		return StringUtil.parseJSON(request, mapAjax);
	}

}



























