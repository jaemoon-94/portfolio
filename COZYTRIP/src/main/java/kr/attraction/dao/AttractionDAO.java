package kr.attraction.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.attraction.vo.AttractionVO;
import kr.util.DBUtil;

public class AttractionDAO {
    private static AttractionDAO instance = new AttractionDAO();
        
    public static AttractionDAO getInstance() {
        return instance;
    }
    
    private AttractionDAO() {}
    
    // 어트랙션 추가 메서드
    public void insertAttraction(AttractionVO attraction) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            conn = DBUtil.getConnection();
            
            // INSERT 문 실행
            sql = "INSERT INTO attraction (attraction_num, region_num, name, description, " +
                  "zipcode, address1, address2, image_url) " +
                  "VALUES (SEQ_ATTRACTION.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, attraction.getRegion_num());
            pstmt.setString(2, attraction.getName());
            pstmt.setString(3, attraction.getDescription());
            pstmt.setString(4, attraction.getZipcode());
            pstmt.setString(5, attraction.getAddress1());
            pstmt.setString(6, attraction.getAddress2());
            pstmt.setString(7, attraction.getImage_url());
            
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    // 지역별 어트랙션 목록 가져오기 (검색 기능 포함)
    public List<AttractionVO> getAttractionsByRegionNum(long regionNum, String searchKeyword) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AttractionVO> list = null;
        String sql = null;
        
        try {
            conn = DBUtil.getConnection();
            
            // region_num과 검색어 조건을 모두 고려
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                sql = "SELECT * FROM attraction WHERE region_num = ? AND (name LIKE ? OR description LIKE ?) ORDER BY attraction_num DESC";
            } else {
                sql = "SELECT * FROM attraction WHERE region_num = ? ORDER BY attraction_num DESC";
            }
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, regionNum);
            
            if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
                String keyword = "%" + searchKeyword.trim() + "%";
                pstmt.setString(2, keyword);
                pstmt.setString(3, keyword);
            }
            
            rs = pstmt.executeQuery();
            
            list = new ArrayList<AttractionVO>();
            while (rs.next()) {
                AttractionVO vo = new AttractionVO();
                vo.setAttraction_num(rs.getLong("attraction_num"));
                vo.setRegion_num(rs.getLong("region_num"));
                vo.setName(rs.getString("name"));
                vo.setDescription(rs.getString("description"));
                vo.setZipcode(rs.getString("zipcode"));
                vo.setAddress1(rs.getString("address1"));
                vo.setAddress2(rs.getString("address2"));
                vo.setImage_url(rs.getString("image_url"));
                
                list.add(vo);
            }
            
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }
    
    // 숙소 번호로 어트랙션 목록 가져오기 (기존 메서드)
    public List<AttractionVO> getAttractionsByAccomNum(long accomNum) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AttractionVO> list = null;
        String sql = null; 
        try {
            conn = DBUtil.getConnection();
            sql = "SELECT a.attraction_num, a.region_num, a.name, a.description, a.zipcode, " +
                     "a.address1, a.address2, a.image_url " +
                     "FROM attraction a " +
                     "JOIN accom_detail ad ON a.region_num = ad.region_num " +
                     "WHERE ad.accom_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, accomNum);
            rs = pstmt.executeQuery();
            list = new ArrayList<AttractionVO>();
            while (rs.next()) {
                AttractionVO vo = new AttractionVO();
                vo.setAttraction_num(rs.getLong("attraction_num"));
                vo.setRegion_num(rs.getLong("region_num"));
                vo.setName(rs.getString("name"));
                vo.setDescription(rs.getString("description"));
                vo.setZipcode(rs.getString("zipcode"));
                vo.setAddress1(rs.getString("address1"));
                vo.setAddress2(rs.getString("address2"));
                String imageUrl = rs.getString("image_url");
                if (imageUrl == null || imageUrl.isEmpty()) {
                    imageUrl = "images/attimage/default_attraction.jpg";
                } else if (!imageUrl.startsWith("images/attimage/")) {
                    imageUrl = "images/attimage/" + imageUrl;
                }
                vo.setImage_url(imageUrl);
                list.add(vo);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return list;
    }
    
    // 전체 어트랙션 목록 가져오기
    public List<AttractionVO> getAttractionList() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AttractionVO> list = null;
        String sql = null;
        
        try {
            conn = DBUtil.getConnection();
            sql = "SELECT * FROM attraction ORDER BY attraction_num DESC";
            
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            list = new ArrayList<AttractionVO>();
            while (rs.next()) {
                AttractionVO vo = new AttractionVO();
                vo.setAttraction_num(rs.getLong("attraction_num"));
                vo.setRegion_num(rs.getLong("region_num"));
                vo.setName(rs.getString("name"));
                vo.setDescription(rs.getString("description"));
                vo.setZipcode(rs.getString("zipcode"));
                vo.setAddress1(rs.getString("address1"));
                vo.setAddress2(rs.getString("address2"));
                vo.setImage_url(rs.getString("image_url"));
                
                list.add(vo);
            }
            
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return list;
    }

    // 특정 어트랙션 정보 가져오기
    public AttractionVO getAttractionByNum(long attractionNum) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AttractionVO attraction = null;
        String sql = null;
        
        try {
            conn = DBUtil.getConnection();
            sql = "SELECT * FROM attraction WHERE attraction_num = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, attractionNum);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                attraction = new AttractionVO();
                attraction.setAttraction_num(rs.getLong("attraction_num"));
                attraction.setRegion_num(rs.getLong("region_num"));
                attraction.setName(rs.getString("name"));
                attraction.setDescription(rs.getString("description"));
                attraction.setZipcode(rs.getString("zipcode"));
                attraction.setAddress1(rs.getString("address1"));
                attraction.setAddress2(rs.getString("address2"));
                attraction.setImage_url(rs.getString("image_url"));
            }
            
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return attraction;
    }
    
    // 특정 어트랙션의 이미지 경로 조회
    public String getAttractionImage(long attractionNum) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String imageUrl = null;
        String sql = null;
        
        try {
            conn = DBUtil.getConnection();
            sql = "SELECT image_url FROM attraction WHERE attraction_num = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, attractionNum);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                imageUrl = rs.getString("image_url");
            }
            
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        
        return imageUrl;
    }

    // 어트랙션 삭제 메서드
    public void deleteAttraction(long attractionNum) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            conn = DBUtil.getConnection();
            sql = "DELETE FROM attraction WHERE attraction_num = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, attractionNum);
            
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

    // 어트랙션 수정 메서드
    public void updateAttraction(AttractionVO attraction) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        
        try {
            conn = DBUtil.getConnection();
            
            if (attraction.getImage_url() != null) {
                sql = "UPDATE attraction SET name = ?, description = ?, " +
                      "zipcode = ?, address1 = ?, address2 = ?, image_url = ? " +
                      "WHERE attraction_num = ?";
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, attraction.getName());
                pstmt.setString(2, attraction.getDescription());
                pstmt.setString(3, attraction.getZipcode());
                pstmt.setString(4, attraction.getAddress1());
                pstmt.setString(5, attraction.getAddress2());
                pstmt.setString(6, attraction.getImage_url());
                pstmt.setLong(7, attraction.getAttraction_num());
            } else {
                sql = "UPDATE attraction SET name = ?, description = ?, " +
                      "zipcode = ?, address1 = ?, address2 = ? " +
                      "WHERE attraction_num = ?";
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, attraction.getName());
                pstmt.setString(2, attraction.getDescription());
                pstmt.setString(3, attraction.getZipcode());
                pstmt.setString(4, attraction.getAddress1());
                pstmt.setString(5, attraction.getAddress2());
                pstmt.setLong(6, attraction.getAttraction_num());
            }
            
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
}