<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!-- header 시작 -->
<style>
.host-profile {
    display: flex;
    align-items: center;
    position: relative; /* 드롭다운 위치 기준 */
}

.host-profile-img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
    object-fit: cover;
}

.host-profile-name {
    font-weight: 500;
}

.host-dropdown-btn {
    margin-left: 5px;
    cursor: pointer;
    color: #666;
}

/* 드롭다운 메뉴 스타일 */
.dropdown-menu {
    display: none;
    position: absolute;
    top: 100%; /* 바로 아래 */
    right: 0;
    background-color: white;
    border: 1px solid #ddd;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    border-radius: 6px;
    min-width: 140px;
    z-index: 1000;
}

.dropdown-menu a {
    display: block;
    padding: 10px 15px;
    color: #333;
    text-decoration: none;
    font-size: 14px;
}

.dropdown-menu a:hover {
    background-color: #f5f5f5;
}

</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<header>
    <div class="container">
        <nav class="navbar">
            <a href="${pageContext.request.contextPath}/user/adminForm.do" class="logo">CozyTrip_Admin</a>
              <div class="nav-links">
                <c:if test="${!empty user_num}">
                    <div class="host-profile" id="hostProfile">
                        <img src="${pageContext.request.contextPath}/images/face.png" alt="프로필 이미지" class="host-profile-img">
                        <span class="host-profile-name">${user_id}님</span>
                        <span class="host-dropdown-btn" id="dropdownBtn">
                            <i class="fas fa-chevron-down"></i>
                        </span>
                        <div class="dropdown-menu" id="dropdownMenu">
                            <a href="${pageContext.request.contextPath}/user/logout.do">로그아웃</a>
                        </div>
                    </div>
                </c:if>
                <c:if test="${empty user_num}">
                    <a href="${pageContext.request.contextPath}/user/loginForm.do" class="login-btn">로그인</a>
                    <a href="${pageContext.request.contextPath}/user/signup.do" class="signup-btn">회원가입</a>
                </c:if>
            </div>
        </nav>
    </div>
</header>


<!--  
<div id="main_logo">
	<h1 class="align-center">
		<a href="${pageContext.request.contextPath}/main/main.do">회원제 게시판</a>
	</h1>
</div>
<div id="main_nav">
	<ul>
		<li>
			<a href="${pageContext.request.contextPath}/board/list.do">게시판</a>
		</li>
		<c:if test="${!empty user_num && user_auth == 9}">
		<li>
			<a href="${pageContext.request.contextPath}/member/adminList.do">회원관리</a>
		</li>	
		</c:if>
		<c:if test="${!empty user_num}">
		<li>
			<a href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a>
		</li>
		</c:if>
		
		<c:if test="${!empty user_num && !empty user_photo}">
		<li class="menu-profile">
			<img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo">
		</li>
		</c:if>
		<c:if test="${!empty user_num && empty user_photo}">
		<li class="menu-profile">
			<img src="${pageContext.request.contextPath}/images/face.png" width="25" height="25" class="my-photo">
		</li>
		</c:if>
		
		<c:if test="${!empty user_num}">
		<li class="menu-logout">
			[<span>${user_id}</span>]
			<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
		</li>
		</c:if>
		<c:if test="${empty user_num}">
		<li>
			<a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a>
		</li>
		<li>
			<a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
		</li>	
		</c:if>
	</ul>
</div>
-->
<!-- header 끝 -->
<script>
document.addEventListener('DOMContentLoaded', function () {
    const dropdownBtn = document.getElementById('dropdownBtn');
    const dropdownMenu = document.getElementById('dropdownMenu');

    dropdownBtn.addEventListener('click', function (e) {
        e.stopPropagation();
        // Toggle 보이기/숨기기
        dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
    });

    // 바깥 클릭 시 드롭다운 닫기
    document.addEventListener('click', function () {
        dropdownMenu.style.display = 'none';
    });
});
</script>






