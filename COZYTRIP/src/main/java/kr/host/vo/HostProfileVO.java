package kr.host.vo;

import java.sql.Date;

public class HostProfileVO {
	private long user_num;
	private String user_name;
	private String photo;
	private String host_intro;
	private Date host_date;
	private int host_rating;
	public long getUser_num() {
		return user_num;
	}
	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getHost_intro() {
		return host_intro;
	}
	public void setHost_intro(String host_intro) {
		this.host_intro = host_intro;
	}
	public Date getHost_date() {
		return host_date;
	}
	public void setHost_date(Date host_date) {
		this.host_date = host_date;
	}
	public int getHost_rating() {
		return host_rating;
	}
	public void setHost_rating(int host_rating) {
		this.host_rating = host_rating;
	}
	
	
}
