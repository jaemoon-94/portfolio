package kr.attraction.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import kr.attraction.dao.AttractionDAO;
import kr.attraction.vo.AttractionVO;
import kr.controller.Action;

public class AttractionAddAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 웹 애플리케이션의 실제 디렉토리
        String uploadPath = request.getServletContext().getRealPath("/images/attimage");
        System.out.println("[AttractionAddAction] 업로드 경로: " + uploadPath);
        
        try {
            // 디렉토리가 존재하지 않으면 생성
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            System.out.println("[AttractionAddAction] Upload Directory: " + uploadDir.getAbsolutePath());
            System.out.println("[AttractionAddAction] Directory exists: " + uploadDir.exists());
            
            // 파라미터 추출
            long regionNum = Long.parseLong(request.getParameter("region_num"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String zipcode = request.getParameter("zipcode");
            String address1 = request.getParameter("address1");
            String address2 = request.getParameter("address2");
            
            // AttractionVO 객체 생성
            AttractionVO attraction = new AttractionVO();
            attraction.setRegion_num(regionNum);
            attraction.setName(name);
            attraction.setDescription(description);
            attraction.setZipcode(zipcode);
            attraction.setAddress1(address1);
            attraction.setAddress2(address2);
            
            // 이미지 파일 처리
            Part filePart = request.getPart("image");
            System.out.println("[AttractionAddAction] FilePart: " + filePart);
            
            if (filePart != null && filePart.getSize() > 0) {
                try {
                    // 랜덤값 생성
                    String randomStr = Long.toHexString(Double.doubleToLongBits(Math.random()));
                    String originalFileName = filePart.getSubmittedFileName();
                    String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String fileName = randomStr + fileExt;
                    fileName = fileName.toLowerCase();
                    
                    // 파일 저장 경로 생성
                    Path savePath = Paths.get(uploadDir.getAbsolutePath(), fileName);
                    System.out.println("[AttractionAddAction] Save Path: " + savePath);
                    
                    // 파일 저장
                    try (InputStream fileContent = filePart.getInputStream()) {
                        Files.copy(fileContent, savePath, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("[AttractionAddAction] File saved successfully");
                    }
                    
                    // 이미지 URL 설정 - 슬래시로 시작하지 않도록 설정
                    String imagePath = "images/attimage/" + fileName;
                    attraction.setImage_url(imagePath);
                    System.out.println("[AttractionAddAction] Image URL in VO: " + attraction.getImage_url());
                    System.out.println("[AttractionAddAction] 파일 이름: " + fileName);
                    System.out.println("[AttractionAddAction] 원본 파일 이름: " + originalFileName);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("[AttractionAddAction] Error saving file: " + e.getMessage());
                    // 이미지 저장 실패 시 기본 이미지 경로 설정
                    attraction.setImage_url("images/attimage/default_attraction.jpg");
                }
            } else {
                System.out.println("[AttractionAddAction] No file or file size is 0");
                // 이미지가 없을 경우 기본 이미지 경로 설정
                attraction.setImage_url("images/attimage/default_attraction.jpg");
            }
            
            // DAO를 통해 데이터베이스에 저장
            AttractionDAO dao = AttractionDAO.getInstance();
            dao.insertAttraction(attraction);
            
            // 성공 메시지 설정
            request.setAttribute("message", "어트랙션이 성공적으로 추가되었습니다.");
            
            // 리스트 페이지로 리다이렉트
            return "redirect:/user/attractionList.do";
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "어트랙션 추가 중 오류가 발생했습니다.");
            return "redirect:/user/attractionList.do";
        }
    }
}
