package kr.accom.vo;

import java.sql.Date;
import java.util.List;

public class AccomVO {
	
	private long accom_num;
	private long host_num;
	private long region_num;
	private long cate_num;
	private String accom_name;
	private String description;
	private String zipcode;
	private String address1;
	private String address2;
	private long price;
	private int max_people;
	private int accom_status;
	private Date accom_date;
	private int accom_hits;
	
	private String cate_name;
	private String region_name;
	private long image_num;
	private String image_name;
	private String user_name;
	private long user_num;
	private String user_email;
	private String photo;
	private String user_phone;
	private String host_intro;
	private int host_rating;
	private int image_main;

	public String getFullAddress() {
		if (address1 == null) address1 = "";
		if (address2 == null) address2 = "";
		return address1 + " " + address2;
	}
	
    private List<AccomImageVO> imageList;

    // Getters and Setters
    public List<AccomImageVO> getImageList() {
        return imageList;
    }

    public void setImageList(List<AccomImageVO> imageList) {
        this.imageList = imageList;
    }

	// getter/setter
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
	public long getRegion_num() {
		return region_num;
	}
	public void setRegion_num(long region_num) {
		this.region_num = region_num;
	}
	public long getCate_num() {
		return cate_num;
	}
	public void setCate_num(long cate_num) {
		this.cate_num = cate_num;
	}
	public String getAccom_name() {
		return accom_name;
	}
	public void setAccom_name(String accom_name) {
		this.accom_name = accom_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getMax_people() {
		return max_people;
	}
	public void setMax_people(int max_people) {
		this.max_people = max_people;
	}
	public int getAccom_status() {
		return accom_status;
	}
	public void setAccom_status(int accom_status) {
		this.accom_status = accom_status;
	}
	public Date getAccom_date() {
		return accom_date;
	}
	public void setAccom_date(Date accom_date) {
		this.accom_date = accom_date;
	}
	public int getAccom_hits() {
		return accom_hits;
	}
	public void setAccom_hits(int accom_hits) {
		this.accom_hits = accom_hits;
	}
	public String getCate_name() {
		return cate_name;
	}
	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public long getImage_num() {
		return image_num;
	}

	public void setImage_num(long image_num) {
		this.image_num = image_num;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public long getUser_num() {
		return user_num;
	}

	public void setUser_num(long user_num) {
		this.user_num = user_num;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getHost_intro() {
		return host_intro;
	}

	public void setHost_intro(String host_intro) {
		this.host_intro = host_intro;
	}

	public int getHost_rating() {
		return host_rating;
	}

	public void setHost_rating(int host_rating) {
		this.host_rating = host_rating;
	}

	public int getImage_main() {
		return image_main;
	}

	public void setImage_main(int image_main) {
		this.image_main = image_main;
	}

}
