<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CozyTrip - 회원정보</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/FH.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hs/update.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/member_info.js"></script>
</head>
<body>
    <div class="page-main">
        <!-- 헤더 -->
        <jsp:include page="/WEB-INF/views/common/header.jsp" />
        
        <div class="content-main">
            <h2>회원정보</h2>
            
            <div class="mypage-div">
                <div class="profile-container">
                    <!-- 프로필 사진 섹션 (왼쪽) -->
                    <div class="profile-section">
                        <h3 class="profile-title">프로필 사진</h3>
                        
                        <c:if test="${empty user.photo}">
                            <img src="${pageContext.request.contextPath}/images/face.png" class="my-photo">
                        </c:if>
                        <c:if test="${!empty user.photo}">
                            <img src="${pageContext.request.contextPath}/upload/${user.photo}" class="my-photo">
                        </c:if>
                        
                        <div class="align-center">
                            <input type="button" value="수정" id="photo_btn">
                        </div>
                        
                        <div id="photo_choice" style="display:none;">
                            <input type="file" id="photo" accept="image/gif,image/png,image/jpeg"><br>
                            <div class="button-group">
                                <input type="button" value="전송" id="photo_submit">
                                <input type="button" value="취소" id="photo_reset">
                            </div>
                        </div>
                    </div>
                    
                    <!-- 회원정보 섹션 (오른쪽) -->
                    <div class="info-container">
                        <!-- 연락처 섹션 -->
                        <div class="info-section">
                            <div class="section-title">
                                <h3>연락처</h3>
                                <input type="button" value="연락처 수정" onclick="location.href='${pageContext.request.contextPath}/user/modifyUserForm.do'">
                            </div>
                            
                            <div class="info-area">
                                <ul>
                                    <li>아이디 : <span>${user.userId}</span></li>
                                    <li>이름 : <span>${user.userName}</span></li>
                                    <li>전화번호 : <span>${user.userPhone}</span></li>
                                    <li>이메일 : <span>${user.userEmail}</span></li>
                                    <li>가입일 : <span>${user.regDate}</span></li>
                                </ul>
                            </div>
                        </div>
                        
                        <!-- 비밀번호 수정 섹션 -->
                        <div class="info-section">
                            <div class="section-title">
                                <h3>비밀번호 수정</h3>
                                <input type="button" value="비밀번호 수정" onclick="location.href='${pageContext.request.contextPath}/user/modifyPasswordForm.do'">
                            </div>
                            
                        </div>
                        
                        <!-- 회원탈퇴 섹션 -->
                        <div class="info-section">
                            <div class="section-title">
                                <h3>회원탈퇴</h3>
                                <input type="button" value="회원탈퇴" onclick="location.href='${pageContext.request.contextPath}/user/deleteUserForm.do'">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- 푸터 -->
        <jsp:include page="/WEB-INF/views/common/footer.jsp" />
    </div>
</body>
</html>