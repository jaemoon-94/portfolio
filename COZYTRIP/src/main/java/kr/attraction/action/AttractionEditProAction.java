package kr.attraction.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import kr.attraction.dao.AttractionDAO;
import kr.attraction.vo.AttractionVO;
import kr.controller.Action;

public class AttractionEditProAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String view = null;
        
        try {
            // 파라미터 추출
            long attractionNum = Long.parseLong(request.getParameter("attraction_num"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String zipcode = request.getParameter("zipcode");
            String address1 = request.getParameter("address1");
            String address2 = request.getParameter("address2");
            
            // 이미지 업로드 처리
            String uploadPath = request.getServletContext().getRealPath("/images/attimage");
            System.out.println("[AttractionEditProAction] 업로드 경로: " + uploadPath);
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
                System.out.println("[AttractionEditProAction] 디렉토리 생성: " + uploadDir.getAbsolutePath());
            }
            System.out.println("[AttractionEditProAction] 디렉토리 존재 여부: " + uploadDir.exists());
            
            String imageUrl = null;
            Part imagePart = request.getPart("image");
            if (imagePart != null && imagePart.getSize() > 0) {
                // 랜덤값 생성
                String randomStr = Long.toHexString(Double.doubleToLongBits(Math.random()));
                String originalFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
                String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
                String fileName = randomStr + fileExt;
                fileName = fileName.toLowerCase();
                
                Path targetLocation = Paths.get(uploadPath, fileName);
                try (InputStream fileContent = imagePart.getInputStream()) {
                    Files.copy(fileContent, targetLocation, StandardCopyOption.REPLACE_EXISTING);
                    imageUrl = "images/attimage/" + fileName;
                } catch (Exception e) {
                    e.printStackTrace();
                    imageUrl = "images/attimage/default_attraction.jpg";
                }
            } else {
                // 이미지가 없을 경우 기존 이미지 유지
                AttractionDAO dao = AttractionDAO.getInstance();
                imageUrl = dao.getAttractionImage(attractionNum);
                if (imageUrl == null || imageUrl.isEmpty()) {
                    imageUrl = "images/attimage/default_attraction.jpg";
                }
            }
            
            // VO 생성
            AttractionVO attraction = new AttractionVO();
            attraction.setAttraction_num(attractionNum);
            attraction.setName(name);
            attraction.setDescription(description);
            attraction.setZipcode(zipcode);
            attraction.setAddress1(address1);
            attraction.setAddress2(address2);
            if (imageUrl != null) {
                attraction.setImage_url(imageUrl);
            }
            
            // DAO를 통해 데이터베이스에서 데이터 수정
            AttractionDAO dao = AttractionDAO.getInstance();
            dao.updateAttraction(attraction);
            
            // 수정 완료 메시지 설정
            String message = "수정이 완료되었습니다.";
            
            // 수정 후 리스트 페이지로 리다이렉트
            view = "redirect:/user/attractionList.do?message=" + URLEncoder.encode(message, "UTF-8");
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        
        return view;
    }
}
