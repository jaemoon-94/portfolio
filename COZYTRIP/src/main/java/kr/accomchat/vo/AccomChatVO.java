package kr.accomchat.vo;

public class AccomChatVO {
	private long chat_num; //채팅번호
	private long chatroom_num; //채팅방번호
	private long user_num; //채팅 메시지 작성자 번호
	private String message; //채팅날짜
	private String chat_date; //채팅 날짜
	private int read_check; //읽음0 읽지않음1
	
	private String user_id;
	private String host_name;
	private String accom_name;
	private long accom_num;
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
	public String getHost_name() {
		return host_name;
	}
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}
	private int unread_cnt;//읽지않은 채팅 메시지 수
	public long getChat_num() {
		return chat_num;
	}
	public void setChat_num(long chat_num) {
		this.chat_num = chat_num;
	}
	public long getChatroom_num() {
		return chatroom_num;
	}
	public void setChatroom_num(long chatroom_num) {
		this.chatroom_num = chatroom_num;
	}
	public long getUser_num() {
		return user_num;
	}
	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getChat_date() {
		return chat_date;
	}
	public void setChat_date(String chat_date) {
		this.chat_date = chat_date;
	}
	public int getRead_check() {
		return read_check;
	}
	public void setRead_check(int read_check) {
		this.read_check = read_check;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getUnread_cnt() {
		return unread_cnt;
	}
	public void setUnread_cnt(int unread_cnt) {
		this.unread_cnt = unread_cnt;
	}
	
	
}
