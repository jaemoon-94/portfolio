package kr.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class FileUtil {
    // 업로드 상대 경로 - webapp 내부의 upload 폴더
	// 업로드 상대 경로 - webapp 내부의 images/accomimage 폴더
public static final String UPLOAD_PATH = "/images/accomimage";

    // 파일 업로드
    public static String uploadFile(HttpServletRequest request, String param) throws IOException, ServletException {
        // 컨텍스트 경로상의 업로드 절대 경로
        String contextPath = request.getServletContext().getRealPath("/");
        // Tomcat 작업 디렉토리에 저장
        String upload = contextPath + "images/accomimage";
        
        // 두 경로에 모두 저장 (프로젝트 폴더와 Tomcat 작업 디렉토리)
        String projectRoot = new File("c:/Users/SIST/git/COZYTRIP/COZYTRIP").getAbsolutePath();
        String projectUpload = projectRoot + "/src/main/webapp/images/accomimage";
        
        System.out.println("==== 파일 업로드 경로 정보 =====");
        System.out.println("UPLOAD_PATH: " + UPLOAD_PATH);
        System.out.println("Tomcat 작업 디렉토리 경로: " + upload);
        System.out.println("프로젝트 디렉토리 경로: " + projectUpload);
        System.out.println("=============================");
        
        File dir = new File(upload);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        Part part = request.getPart(param);
        String filename = part.getSubmittedFileName();
        
        if (!filename.isEmpty()) {
            // 파일명이 중복되지 않도록 파일명 변경
            UUID uuid = UUID.randomUUID();
            // 원래 파일명을 보존하지 않을 경우
            filename = uuid.toString() + filename.substring(filename.lastIndexOf("."));
            
            System.out.println("파일 저장 경로: " + upload);
            System.out.println("저장될 파일명: " + filename);
            
            // 디렉토리가 있는지 다시 한번 확인
            if (!dir.exists()) {
                System.out.println("디렉토리가 존재하지 않아 생성합니다: " + upload);
                boolean created = dir.mkdirs();
                System.out.println("디렉토리 생성 결과: " + created);
            }
            
            // 파일 저장 전 경로 확인
            File destFile = new File(upload + "/" + filename);
            System.out.println("파일이 저장될 전체 경로: " + destFile.getAbsolutePath());
            
            // Tomcat 작업 디렉토리에 파일 저장
            part.write(upload + "/" + filename);
            
            // 파일이 실제로 저장되었는지 확인
            if (destFile.exists()) {
                System.out.println("파일이 Tomcat 작업 디렉토리에 성공적으로 저장되었습니다.");
                
                // 프로젝트 디렉토리에도 파일 복사
                try {
                    File projectDir = new File(projectUpload);
                    if (!projectDir.exists()) {
                        projectDir.mkdirs();
                    }
                    
                    File sourceFile = new File(upload + "/" + filename);
                    File targetFile = new File(projectUpload + "/" + filename);
                    
                    // 파일 복사
                    java.nio.file.Files.copy(
                        sourceFile.toPath(),
                        targetFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                    );
                    
                    System.out.println("파일이 프로젝트 디렉토리에도 성공적으로 복사되었습니다.");
                } catch (Exception e) {
                    System.out.println("프로젝트 디렉토리에 파일 복사 실패: " + e.getMessage());
                }
            } else {
                System.out.println("파일 저장에 실패했습니다!");
            }
        }
        
        return filename;
    }

    // 파일 삭제
    public static void removeFile(HttpServletRequest request, String filename) {
        if (filename != null) {
            // Tomcat 작업 디렉토리 경로
            String contextPath = request.getServletContext().getRealPath("/");
            String tomcatUpload = contextPath + "images/accomimage";
            
            // 프로젝트 디렉토리 경로
            String projectRoot = new File("c:/Users/SIST/git/COZYTRIP/COZYTRIP").getAbsolutePath();
            String projectUpload = projectRoot + "/src/main/webapp/images/accomimage";
            
            // Tomcat 작업 디렉토리에서 파일 삭제
            File tomcatFile = new File(tomcatUpload + "/" + filename);
            if (tomcatFile.exists()) {
                boolean tomcatDeleted = tomcatFile.delete();
                System.out.println("Tomcat 작업 디렉토리 파일 삭제 결과: " + tomcatDeleted);
            } else {
                System.out.println("Tomcat 작업 디렉토리에 삭제할 파일이 존재하지 않습니다: " + tomcatFile.getAbsolutePath());
            }
            
            // 프로젝트 디렉토리에서 파일 삭제
            File projectFile = new File(projectUpload + "/" + filename);
            if (projectFile.exists()) {
                boolean projectDeleted = projectFile.delete();
                System.out.println("프로젝트 디렉토리 파일 삭제 결과: " + projectDeleted);
            } else {
                System.out.println("프로젝트 디렉토리에 삭제할 파일이 존재하지 않습니다: " + projectFile.getAbsolutePath());
            }
        }
    }

    public static String getFilename(String headerName) {
        for (String name : headerName.split(";")) {
            if (name.trim().startsWith("filename")) {
                String filename = name.substring(name.indexOf("=") + 1).trim().replace("\"", "");
                int index = filename.lastIndexOf(File.separator);
                return filename.substring(index + 1);
            }
        }
        return null;
    }
}