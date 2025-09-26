package kr.report.vo;

import java.sql.Date;

public class ReportVO {
	
	private long report_num;
	private String reason;
	private Date report_date;
	private int status;
	
	private long accom_num;
	private long user_num;
	private String accom_name;
	private String address1;
	
	public long getReport_num() {
		return report_num;
	}
	public void setReport_num(long report_num) {
		this.report_num = report_num;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getReport_date() {
		return report_date;
	}
	public void setReport_date(Date report_date) {
		this.report_date = report_date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getAccom_num() {
		return accom_num;
	}
	public void setAccom_num(long accom_num) {
		this.accom_num = accom_num;
	}
	public long getUser_num() {
		return user_num;
	}
	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}
	public String getAccom_name() {
		return accom_name;
	}
	public void setAccom_name(String accom_name) {
		this.accom_name = accom_name;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

}
