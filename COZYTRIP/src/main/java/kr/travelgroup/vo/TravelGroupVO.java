package kr.travelgroup.vo;

import java.sql.Date;
import java.util.List;

public class TravelGroupVO {
    private long group_num;         // 그룹번호
    private long creator_num;       // 생성자 번호
    private String creator_id;		// 생성자 id
	private long accom_num;         // 숙소 번호
    private String tg_title;       // 동행그룹 제목
    private String tg_content;     // 동행 내용
    private Date create_date;      // 생성일
    private Date travel_date_start; // 여행 시작일
    private Date travel_date_end;   // 여행 종료일
    private int max_member_count;   // 모집인원
    private int region_num;         // 여행지역번호
    private int status;             // 모집상태
    private int member_count;		// 멤버수


	private List<GroupMemberVO> memberList;
    
    // 기본 생성자
    public TravelGroupVO() {}

    public long getGroup_num() {
        return group_num;
    }

    public void setGroup_num(long group_num) {
        this.group_num = group_num;
    }

    public long getCreator_num() {
        return creator_num;
    }

    public void setCreator_num(long creator_num) {
        this.creator_num = creator_num;
    }

    public long getAccom_num() {
        return accom_num;
    }

    public void setAccom_num(long accom_num) {
        this.accom_num = accom_num;
    }

    public String getTg_title() {
        return tg_title;
    }

    public void setTg_title(String tg_title) {
        this.tg_title = tg_title;
    }

    public String getTg_content() {
        return tg_content;
    }

    public void setTg_content(String tg_content) {
        this.tg_content = tg_content;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getTravel_date_start() {
        return travel_date_start;
    }

    public void setTravel_date_start(Date travel_date_start) {
        this.travel_date_start = travel_date_start;
    }

    public Date getTravel_date_end() {
        return travel_date_end;
    }

    public void setTravel_date_end(Date travel_date_end) {
        this.travel_date_end = travel_date_end;
    }

    public int getMax_member_count() {
        return max_member_count;
    }

    public void setMax_member_count(int max_member_count) {
        this.max_member_count = max_member_count;
    }

    public int getRegion_num() {
        return region_num;
    }

    public void setRegion_num(int region_num) {
        this.region_num = region_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public List<GroupMemberVO> getMemberList() {
        return memberList;
    }
    
    public void setMemberList(List<GroupMemberVO> memberList) {
        this.memberList = memberList;
    }
    
    public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}
	
    
    public int getMember_count() {
		return member_count;
	}

	public void setMember_count(int member_count) {
		this.member_count = member_count;
	}

}