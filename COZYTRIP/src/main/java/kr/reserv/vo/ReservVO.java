package kr.reserv.vo;

import kr.accom.vo.AccomVO;

public class ReservVO {
   // 필드 선언
   private int reserv_num;      // 예약 번호
   private long user_num;        // 사용자 번호
   private long accom_num;       // 숙소 번호
   private String reserv_code;  // 예약 코드
   
   private ReservDetailVO reservDetailVO;
   private AccomVO accomVO;
   

   public ReservVO() {
   }
   

   public int getReserv_num() {
       return reserv_num;
   }
   
   public void setReserv_num(int reserv_num) {
       this.reserv_num = reserv_num;
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
   
   public String getReserv_code() {
       return reserv_code;
   }
   
   public void setReserv_code(String reserv_code) {
       this.reserv_code = reserv_code;
   }
   
   public ReservDetailVO getReservDetailVO() {
       return reservDetailVO;
   }
   
   public void setReservDetailVO(ReservDetailVO reservDetailVO) {
       this.reservDetailVO = reservDetailVO;
   }
   
   public AccomVO getAccomVO() {
       return accomVO;
   }
   
   public void setAccomVO(AccomVO accomVO) {
       this.accomVO = accomVO;
   }
}