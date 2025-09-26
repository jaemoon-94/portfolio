package kr.chat.vo;

import java.sql.Date;

public class ChatRoomVO {
    private long chatroom_num;    // 채팅방 번호
    private long group_num;       // 동행그룹 번호
    private String chatroom_type; // 채팅방 유형
    private Date create_date;    // 생성일
    
    // 기본 생성자
    public ChatRoomVO() {}

    public long getChatroom_num() {
        return chatroom_num;
    }

    public void setChatroom_num(long chatroom_num) {
        this.chatroom_num = chatroom_num;
    }

    public long getGroup_num() {
        return group_num;
    }

    public void setGroup_num(long group_num) {
        this.group_num = group_num;
    }

    public String getChatroom_type() {
        return chatroom_type;
    }

    public void setChatroom_type(String chatroom_type) {
        this.chatroom_type = chatroom_type;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}