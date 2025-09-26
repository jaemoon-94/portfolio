package kr.user.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.util.StringUtil;

public class CheckUniqueInfoAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 요청 파라미터 받기 (수정)
		String userId = request.getParameter("user_id");
		String userEmail = request.getParameter("user_email");
		String userPhone = request.getParameter("user_phone");

		// UserDAO 인스턴스 생성
		UserDAO dao = UserDAO.getInstance();

		// ID, Email, Phone 중복 체크
		boolean flag = dao.checkUniqueInfo(userId, userEmail, userPhone);

		Map<String, String> mapAjax = new HashMap<String, String>();

		// 중복 여부 판단
		if (flag) {
			// 중복된 경우
			if (userId != null && userEmail == null && userPhone == null) {
				mapAjax.put("result", "idDuplicated"); // ID 중복
			} else if (userId == null && userEmail != null && userPhone == null) {
				mapAjax.put("result", "emailDuplicated"); // 이메일 중복
			} else if (userId == null && userEmail == null && userPhone != null) {
				mapAjax.put("result", "phoneDuplicated"); // 전화번호 중복
			}
		} else {
			// 미중복인 경우
			if (userId != null && userEmail == null && userPhone == null) {
				mapAjax.put("result", "idNotFound"); // ID 미중복
			} else if (userId == null && userEmail != null && userPhone == null) {
				mapAjax.put("result", "emailNotFound"); // 이메일 미중복
			} else if (userId == null && userEmail == null && userPhone != null) {
				mapAjax.put("result", "phoneNotFound"); // 전화번호 미중복
			}
		}

		// JSON 형식으로 변환하여 클라이언트로 전송
		return StringUtil.parseJSON(request, mapAjax);
	}
}
