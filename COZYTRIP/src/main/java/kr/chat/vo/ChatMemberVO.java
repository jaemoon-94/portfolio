package kr.chat.vo;

import java.sql.Date;

public class ChatMemberVO {
    private long chatmember_num;   // 채팅 멤버 번호
    private long chatroom_num;     // 채팅방 번호
    private long user_num;         // 사용자 번호
    private Date join_date;       // 참여일
    
    // 기본 생성자
    public ChatMemberVO() {}

    public long getChatmember_num() {
        return chatmember_num;
    }

    public void setChatmember_num(long chatmember_num) {
        this.chatmember_num = chatmember_num;
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

    public Date getJoin_date() {
        return join_date;
    }

    public void setJoin_date(Date join_date) {
        this.join_date = join_date;
    }
}