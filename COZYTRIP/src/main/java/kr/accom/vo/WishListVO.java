package kr.accom.vo;

public class WishListVO {
	
	private long wish_num;
	private long user_num;
	private long accom_num;
	
	public WishListVO() {}
	
	public WishListVO(long user_num, long accom_num) {
		this.user_num = user_num;
		this.accom_num = accom_num;
	}
	
	public WishListVO(long wish_num, long user_num, long accom_num) {
		this.wish_num = wish_num;
		this.user_num = user_num;
		this.accom_num = accom_num;
	}

	// getter/setter
	public long getWish_num() {
		return wish_num;
	}

	public void setWish_num(long wish_num) {
		this.wish_num = wish_num;
	}

	public long getUser_num() {
		return user_num;
	}

	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}

	public long getAccom_num() {
		return accom_num;
	}

	public void setAccom_num(long accom_num) {
		this.accom_num = accom_num;
	}

}
