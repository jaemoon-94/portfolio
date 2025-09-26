package kr.host.vo;

import java.sql.Date;

public class HostReservVO {
	private long reserv_num;
    private String reserv_code;
    private long accom_num;
    private String accom_name;
    private long user_num;
    private String user_name;
    private Date check_in_date;
    private Date check_out_date;
    private int people_count;
    private int total_price;
    private int reserv_status;
	public long getReserv_num() {
		return reserv_num;
	}
	public void setReserv_num(long reserv_num) {
		this.reserv_num = reserv_num;
	}
	public String getReserv_code() {
		return reserv_code;
	}
	public void setReserv_code(String reserv_code) {
		this.reserv_code = reserv_code;
	}
	public long getAccom_num() {
		return accom_num;
	}
	public void setAccom_num(long accom_num) {
		this.accom_num = accom_num;
	}
	public String getAccom_name() {
		return accom_name;
	}
	public void setAccom_name(String accom_name) {
		this.accom_name = accom_name;
	}
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
	public int getPeople_count() {
		return people_count;
	}
	public void setPeople_count(int people_count) {
		this.people_count = people_count;
	}
	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public int getReserv_status() {
		return reserv_status;
	}
	public void setReserv_status(int reserv_status) {
		this.reserv_status = reserv_status;
	}
	
    
    
}
