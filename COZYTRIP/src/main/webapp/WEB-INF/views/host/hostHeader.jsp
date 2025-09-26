<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
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

<nav class="host-navbar">
        <div class="container">
            <div class="host-nav-container">
                <a href="${pageContext.request.contextPath}/host/accomList.do" class="host-logo">
                    CozyTrip <span>호스트</span>
                </a>
                
                <div class="host-menu">
                    <a href="${pageContext.request.contextPath}/host/accomList.do" class="host-menu-item active">숙소 목록</a>
                    <a href="${pageContext.request.contextPath}/host/reservList.do" class="host-menu-item">예약 목록</a>
                    <a href="${pageContext.request.contextPath}/host/incomeList.do" class="host-menu-item">수입</a>
                    <a href="${pageContext.request.contextPath}/host/inquiryWaiting.do" class="host-menu-item">QnA</a>
                    <a href="${pageContext.request.contextPath}/host/chatting.do" class="host-menu-item">채팅</a>
                    <a href="${pageContext.request.contextPath}/host/profile.do" class="host-menu-item">호스트 소개</a>
                </div>
                
                <div class="host-profile" id="hostProfile">
				    <img src="${pageContext.request.contextPath}/images/face.png" alt="프로필 이미지" class="host-profile-img">
				    <span class="host-profile-name">${user_id}님</span>
				    <span class="host-dropdown-btn" id="dropdownBtn">
				        <i class="fas fa-chevron-down"></i>
				    </span>
				    <div class="dropdown-menu" id="dropdownMenu">
				        <a href="${pageContext.request.contextPath}/user/myPage.do">마이페이지</a>
				        <a href="${pageContext.request.contextPath}/main/main.do">사용자 모드 전환</a>
				        <a href="${pageContext.request.contextPath}/user/logout.do">로그아웃</a>
				    </div>
				</div>
                
                <button class="mobile-menu-btn">
                    <i class="fas fa-bars"></i>
                </button>
            </div>
        </div>
    </nav>
<script>
document.addEventListener('DOMContentLoaded', function () {
    const menuItems = document.querySelectorAll('.host-menu-item');
    const currentPath = window.location.pathname;

    menuItems.forEach(function(item) {
        // 현재 URL과 메뉴 항목의 href 비교하여 active 클래스 부여
        const itemHref = item.getAttribute('href');
        if (currentPath === itemHref) {
            item.classList.add('active');
        } else {
            item.classList.remove('active');
        }
    });

    // 드롭다운 토글 기능
    const dropdownBtn = document.getElementById('dropdownBtn');
    const dropdownMenu = document.getElementById('dropdownMenu');

    dropdownBtn.addEventListener('click', function (e) {
        e.stopPropagation();
        dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
    });

    document.addEventListener('click', function () {
        dropdownMenu.style.display = 'none';
    });
});
</script>