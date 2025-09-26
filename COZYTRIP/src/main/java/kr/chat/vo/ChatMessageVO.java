package kr.chat.vo;

import java.sql.Date;

public class ChatMessageVO {
    private long message_num;      // 메시지 번호
    private long chatroom_num;     // 채팅방 번호
    private long sender_num;       // 발신자 번호
    private String message;       // 메시지
    private Date message_date;    // 발신 일시
    
    // 기본 생성자
    public ChatMessageVO() {}

    public long getMessage_num() {
        return message_num;
    }

    public void setMessage_num(long message_num) {
        this.message_num = message_num;
    }

    public long getChatroom_num() {
        return chatroom_num;
    }

    public void setChatroom_num(long chatroom_num) {
        this.chatroom_num = chatroom_num;
    }

    public long getSender_num() {
        return sender_num;
    }

    public void setSender_num(long sender_num) {
        this.sender_num = sender_num;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMessage_date() {
        return message_date;
    }

    public void setMessage_date(Date message_date) {
        this.message_date = message_date;
    }
}