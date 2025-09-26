package kr.reserv.vo;

import java.sql.Date;

public class ReservDetailVO {
   // 필드 선언
   private long reserv_num;        // 예약 번호
   private String request;        // 요청사항
   private int payment_status;    // 결제 상태
   private String cancel_reason;  // 취소 사유
   private Date cancel_date;      // 취소 일자
   private Date check_in_date;    // 체크인 날짜
   private Date check_out_date;   // 체크아웃 날짜
   private int reserv_status;     // 예약 상태
   private int people_count;      // 인원 수
   private int total_price;       // 총 가격
   private Date reserv_date;      // 예약 일자
   

   public ReservDetailVO() {
   }
   

   public long getReserv_num() {
       return reserv_num;
   }
   
   public void setReserv_num(long reserv_num) {
       this.reserv_num = reserv_num;
   }
   
   public String getRequest() {
       return request;
   }
   
   public void setRequest(String request) {
       this.request = request;
   }
   
   public int getPayment_status() {
       return payment_status;
   }
   
   public void setPayment_status(int payment_status) {
       this.payment_status = payment_status;
   }
   
   public String getCancel_reason() {
       return cancel_reason;
   }
   
   public void setCancel_reason(String cancel_reason) {
       this.cancel_reason = cancel_reason;
   }
   
   public Date getCancel_date() {
       return cancel_date;
   }
   
   public void setCancel_date(Date cancel_date) {
       this.cancel_date = cancel_date;
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
   
   public int getReserv_status() {
       return reserv_status;
   }
   
   public void setReserv_status(int reserv_status) {
       this.reserv_status = reserv_status;
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
   
   public Date getReserv_date() {
       return reserv_date;
   }
   
   public void setReserv_date(Date reserv_date) {
       this.reserv_date = reserv_date;
   }
}