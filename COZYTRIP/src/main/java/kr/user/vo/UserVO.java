package kr.user.vo;

import java.util.Date;

public class UserVO {
	private long userNum;
	private String userName;
	private String userPw;
	private String userPhone;
	private String userEmail;
	private String photo;
	private Date regDate;
	private int gender;
	private String userId;
	private int auth;

	public UserVO() {
	}

	public UserVO(long userNum, String userName, String userPw, String userPhone, String userEmail, String photo,
			Date regDate, int gender, String userId, int auth) {
		this.userNum = userNum;
		this.userName = userName;
		this.userPw = userPw;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.photo = photo;
		this.regDate = regDate;
		this.gender = gender;
		this.userId = userId;
		this.auth = auth;
	}

	// 비밀번호 일치 여부 체크
	public boolean isCheckedPassword(String userPw) {
		// 회원 등급(auth) -> 0:탈퇴,1:정지,2:일반,9:관리자
		if (auth < 7 && userPw.equals(userPw)) {
			return true;
		}
		return false;
	}

	public long getUserNum() {
		return userNum;
	}

	public void setUserNum(long userNum) {
		this.userNum = userNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "UserDetailVO [userNum=" + userNum + ", userName=" + userName + ", userPw=" + userPw + ", userPhone="
				+ userPhone + ", userEmail=" + userEmail + ", photo=" + photo + ", regDate=" + regDate + ", gender="
				+ gender + ", userId=" + userId + ", auth=" + auth + "]";
	}
}