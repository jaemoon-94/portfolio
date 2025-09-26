package kr.travelgroup.vo;

public class Region {
    private long region_num;
    private String region_name;
    
    public Region(long region_num, String region_name) {
        this.region_num = region_num;
        this.region_name = region_name;
    }
    
    public long getRegion_num() {
        return region_num;
    }
    
    public String getRegion_name() {
        return region_name;
    }
}
