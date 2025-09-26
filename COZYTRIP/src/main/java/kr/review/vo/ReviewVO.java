package kr.review.vo;

import java.sql.Date;
import java.util.List;

import kr.accom.vo.AccomImageVO;

public class ReviewVO {
	
	private long review_num;
	private long reserv_num;
	private int rating;
	private String content;
	private String review_date;
	private String review_modifydate;
	
	private long user_num;
	private String user_name;
	private Date check_in_date;
	private Date check_out_date;
	private long accom_num;
	private String reserv_code;
	private long image_num;
	private String image_name;
	private int image_main;
	private String accom_name;
	
	// getter setter
	public long getReview_num() {
		return review_num;
	}
	public void setReview_num(long review_num) {
		this.review_num = review_num;
	}
	public long getReserv_num() {
		return reserv_num;
	}
	public void setReserv_num(long reserv_num) {
		this.reserv_num = reserv_num;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReview_date() {
		return review_date;
	}
	public void setReview_date(String review_date) {
		this.review_date = review_date;
	}
	public String getReview_modifydate() {
		return review_modifydate;
	}
	public void setReview_modifydate(String review_modifydate) {
		this.review_modifydate = review_modifydate;
	}
	public long getUser_num() {
		return user_num;
	}
	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}
	public Date getCheck_in_date() {
		return check_in_date;
	}
	public void setCheck_in_date(Date check_in_date) {
		this.check_in_date = check_in_date;
	}
	public Date getCheck_out_date() {
		return check_out_date;
	}
	public void setCheck_out_date(Date check_out_date) {
		this.check_out_date = check_out_date;
	}
	public long getAccom_num() {
		return accom_num;
	}
	public void setAccom_num(long accom_num) {
		this.accom_num = accom_num;
	}
	public String getReserv_code() {
		return reserv_code;
	}
	public void setReserv_code(String reserv_code) {
		this.reserv_code = reserv_code;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public long getImage_num() {
		return image_num;
	}
	public void setImage_num(long image_num) {
		this.image_num = image_num;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	public int getImage_main() {
		return image_main;
	}
	public void setImage_main(int image_main) {
		this.image_main = image_main;
	}
	public String getAccom_name() {
		return accom_name;
	}
	public void setAccom_name(String accom_name) {
		this.accom_name = accom_name;
	}

}
