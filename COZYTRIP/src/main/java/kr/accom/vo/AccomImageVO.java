package kr.accom.vo;

import java.sql.Date;

public class AccomImageVO {
    private long image_num;
    private long accom_num;
    private String image_name;
    private int main;
    private Date upload_date;
	public long getImage_num() {
		return image_num;
	}
	public void setImage_num(long image_num) {
		this.image_num = image_num;
	}
	public long getAccom_num() {
		return accom_num;
	}
	public void setAccom_num(long accom_num) {
		this.accom_num = accom_num;
	}
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	public int getMain() {
		return main;
	}
	public void setMain(int main) {
		this.main = main;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	@Override
	public String toString() {
		return "AccomImageVO [image_num=" + image_num + ", accom_num=" + accom_num + ", image_name=" + image_name
				+ ", main=" + main + ", upload_date=" + upload_date + "]";
	}
}
