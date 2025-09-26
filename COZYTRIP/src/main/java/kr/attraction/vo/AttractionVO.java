package kr.attraction.vo;

public class AttractionVO {
	private long attraction_num;
    private long region_num;
    private String name;
    private String description;
    private String zipcode;
    private String address1;
    private String address2;
    private String image_url;
	public long getAttraction_num() {
		return attraction_num;
	}
	public void setAttraction_num(long attraction_num) {
		this.attraction_num = attraction_num;
	}
	public long getRegion_num() {
		return region_num;
	}
	public void setRegion_num(long region_num) {
		this.region_num = region_num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
    
    
}
