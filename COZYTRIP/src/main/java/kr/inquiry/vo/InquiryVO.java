package kr.inquiry.vo;

import java.sql.Date;

public class InquiryVO {
	private String user_id;
	private long inquiry_num;
	private long user_num;
	private long accom_num;
	private String accom_name;
	private String inq_content;
	private int inq_status;
	private Date inq_date;
	private int is_secret;

	// 답변 관련
	private long answer_num;
	private String answer_content;
	private Date answer_date;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public long getInquiry_num() {
		return inquiry_num;
	}
	public void setInquiry_num(long inquiry_num) {
		this.inquiry_num = inquiry_num;
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
	public String getInq_content() {
		return inq_content;
	}
	public void setInq_content(String inq_content) {
		this.inq_content = inq_content;
	}
	public int getInq_status() {
		return inq_status;
	}
	public void setInq_status(int inq_status) {
		this.inq_status = inq_status;
	}
	public Date getInq_date() {
		return inq_date;
	}
	public void setInq_date(Date inq_date) {
		this.inq_date = inq_date;
	}
	public long getAnswer_num() {
		return answer_num;
	}
	public void setAnswer_num(long answer_num) {
		this.answer_num = answer_num;
	}
	public String getAnswer_content() {
		return answer_content;
	}
	public void setAnswer_content(String answer_content) {
		this.answer_content = answer_content;
	}
	public Date getAnswer_date() {
		return answer_date;
	}
	public void setAnswer_date(Date answer_date) {
		this.answer_date = answer_date;
	}
	public int getIs_secret() {
		return is_secret;
	}
	public void setIs_secret(int is_secret) {
		this.is_secret = is_secret;
	}
	public String getAccom_name() {
		return accom_name;
	}
	public void setAccom_name(String accom_name) {
		this.accom_name = accom_name;
	}
	
	
	
}
