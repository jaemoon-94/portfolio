package kr.travelgroup.vo;

import java.sql.Date;

public class GroupMemberVO {
    private long member_num;   // 멤버번호
    private long group_num;    // 그룹번호
    private long user_num;     // 회원번호
    private Date join_date;   // 참여일
    

    public GroupMemberVO() {}

    public long getMember_num() {
        return member_num;
    }

    public void setMember_num(long member_num) {
        this.member_num = member_num;
    }

    public long getGroup_num() {
        return group_num;
    }

    public void setGroup_num(long group_num) {
        this.group_num = group_num;
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