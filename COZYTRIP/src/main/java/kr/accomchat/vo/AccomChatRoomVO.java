package kr.accomchat.vo;

import java.sql.Date;

public class AccomChatRoomVO {
	private long chatroom_num; //채팅방 번호
	private long accom_num; //부모글 번호
	private long host_num; //호스트 번호
	private long user_num; //읽은 사람(채팅 희망자) 번호
	private Date room_date; //채팅방 생성일
	
	private String accom_name;
	private String user_id;
	private String host_name;
	public long getChatroom_num() {
		return chatroom_num;
	}
	public void setChatroom_num(long chatroom_num) {
		this.chatroom_num = chatroom_num;
	}
	public long getAccom_num() {
		return accom_num;
	}
	public void setAccom_num(long accom_num) {
		this.accom_num = accom_num;
	}
	public long getHost_num() {
		return host_num;
	}
	public void setHost_num(long host_num) {
		this.host_num = host_num;
	}
	public long getUser_num() {
		return user_num;
	}
	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}
	public Date getRoom_date() {
		return room_date;
	}
	public void setRoom_date(Date room_date) {
		this.room_date = room_date;
	}
	public String getAccom_name() {
		return accom_name;
	}
	public void setAccom_name(String accom_name) {
		this.accom_name = accom_name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getHost_name() {
		return host_name;
	}
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}
	
	
}
