package kr.host.vo;

import java.sql.Date;

public class HostReservDetailVO {
    private long reserv_num;            // 예약 번호 (PK)
    private long user_num;              // 회원 번호 (ctuser PK)
    private long payment_num;           // 결제 번호 (payment PK)
    private long host_num;
    
    private String accom_name;
	private String user_name;          // 예약자 이름
    private String user_phone;         // 예약자 연락처
    private Date reserv_date;          // 예약 일자
    private String reserv_code;        // 예약 코드
    private String payment_status;     // 결제 상태
    private int reserv_status;         // 예약 상태
    private int amount;                // 결제 금액
    private String payment_method;     // 결제 수단
    private Date check_in_date;        // 체크인 날짜
    private Date check_out_date;       // 체크아웃 날짜
    private Date cancel_date;     

	private String cancel_reason;      
    private int people_count;          // 투숙 인원 수
	public long getReserv_num() {
		return reserv_num;
	}
	public String getReservStatusName() {
		String rs_name;
		switch(reserv_status) {
		case 0: rs_name = "대기"; break;
		case 1: rs_name = "확정"; break;
		case 2: rs_name = "취소"; break;
		default : rs_name = "상태오류";
		}
		return rs_name;
	}
	public String getPaymentStatusName() {
		String pay_name;
		switch(payment_status) {
		case "0": pay_name = "미결제"; break;
		case "1": pay_name = "결제완료"; break;
		case "2": pay_name = "결제취소"; break;
		default : pay_name = "상태오류";
		}
		return pay_name;
	}
    public Date getCancel_date() {
		return cancel_date;
	}
	public void setCancel_date(Date cancel_date) {
		this.cancel_date = cancel_date;
	}
	public String getCancel_reason() {
		return cancel_reason;
	}
	public void setCancel_reason(String cancel_reason) {
		this.cancel_reason = cancel_reason;
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
	public void setReserv_num(long reserv_num) {
		this.reserv_num = reserv_num;
	}
	public long getUser_num() {
		return user_num;
	}
	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}
	public long getPayment_num() {
		return payment_num;
	}
	public void setPayment_num(long payment_num) {
		this.payment_num = payment_num;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public Date getReserv_date() {
		return reserv_date;
	}
	public void setReserv_date(Date reserv_date) {
		this.reserv_date = reserv_date;
	}
	public String getReserv_code() {
		return reserv_code;
	}
	public void setReserv_code(String reserv_code) {
		this.reserv_code = reserv_code;
	}
	public String getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}
	public int getReserv_status() {
		return reserv_status;
	}
	public void setReserv_status(int reserv_status) {
		this.reserv_status = reserv_status;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
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


}