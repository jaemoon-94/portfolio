package kr.host.vo;

import java.sql.Date;

public class HostInquiryVO {
    // inquiry 테이블
    private long inquiry_num;
    private long user_num;
    private long accom_num;
    private String inq_content;
    private int inq_status;
    private Date inq_date;

    // user_detail 테이블
    private String user_name;

    // accom_detail 테이블
    private long host_num;
    private String accom_name;

    // answer 테이블 (LEFT JOIN으로 들어올 수 있음)
    private long answer_num;
    private String answer_content;
    private Date answer_date;
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
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public long getHost_num() {
		return host_num;
	}
	public void setHost_num(long host_num) {
		this.host_num = host_num;
	}
	public String getAccom_name() {
		return accom_name;
	}
	public void setAccom_name(String accom_name) {
		this.accom_name = accom_name;
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
}
