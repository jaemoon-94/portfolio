package kr.payment.vo;

import java.sql.Date;

public class PaymentVO {
	private long payment_num;	//결제 번호
	private long reserv_num;		//예약 번호
	private int amount;			//결제 금액
	private String payment_status;	// 결제 상태
	private String payment_method;	// 결제 수단
	private String tid;	// 결제 거래 고유ID
	private Date payment_date;		// 결제일
	
	public PaymentVO() {}

	public long getPayment_num() {
		return payment_num;
	}

	public void setPayment_num(long payment_num) {
		this.payment_num = payment_num;
	}

	public long getReserv_num() {
		return reserv_num;
	}

	public void setReserv_num(long reserv_num) {
		this.reserv_num = reserv_num;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public Date getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Date payment_date) {
		this.payment_date = payment_date;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	};
	
}
