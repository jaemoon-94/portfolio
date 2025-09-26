package kr.user.action;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.controller.Action;
import kr.user.dao.UserDAO;
import kr.util.FileUtil;
import kr.util.StringUtil;

public class UpdateMyPhotoAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,String> mapAjax = 
				              new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//파일 업로드 처리
			String photo = 
					 FileUtil.uploadFile(request, "photo");
			
			UserDAO dao = UserDAO.getInstance();
			//프로필 사진 수정
			dao.updateMyPhoto(photo, user_num);
			
			//이전 파일 삭제
			String user_photo = 
				(String)session.getAttribute("user_photo");
			FileUtil.removeFile(request, user_photo);
			
			//현재 파일로 세션 정보 갱신
			session.setAttribute("user_photo", photo);
			
			mapAjax.put("result", "success");
		}		
		//JSON 문자열로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}




