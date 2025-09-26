<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내 동행 그룹</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/sh.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/travelgroup.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
/* 내 동행 그룹 목록 스타일 */
.travelgroup-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}
.travelgroup-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}
.travelgroup-header h2 {
    margin: 0;
    color: #333;
}
.create-btn {
    background-color: #2196F3;
    color: #fff;
    border: none;
    padding: 10px 15px;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
    text-decoration: none;
}
.create-btn:hover {
    background-color: #0b7dda;
}
.travelgroup-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
}
.travelgroup-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    overflow: hidden;
    transition: transform 0.3s;
    background-color: #fff;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}
.travelgroup-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}
.travelgroup-image {
    height: 180px;
    overflow: hidden;
    position: relative;
}
.travelgroup-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.travelgroup-status {
    position: absolute;
    top: 10px;
    right: 10px;
    padding: 5px 10px;
    border-radius: 20px;
    font-size: 0.8em;
    font-weight: bold;
    color: white;
}
.status-recruiting {
    background-color: #4CAF50;
}
.status-closed {
    background-color: #F44336;
}
.status-completed {
    background-color: #9E9E9E;
}
.travelgroup-content {
    padding: 15px;
}
.travelgroup-title {
    margin-top: 0;
    margin-bottom: 10px;
    font-size: 1.2em;
    color: #333;
}
.travelgroup-info {
    color: #666;
    font-size: 0.9em;
    margin-bottom: 10px;
}
.travelgroup-info div {
    margin-bottom: 5px;
}
.travelgroup-footer {
    padding: 10px 15px;
    background-color: #f8f9fa;
    border-top: 1px solid #eee;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.member-count {
    font-size: 0.9em;
    color: #666;
}
.view-btn {
    background-color: #2196F3;
    color: white;
    border: none;
    padding: 5px 10px;
    border-radius: 4px;
    text-decoration: none;
    font-size: 0.9em;
}
.view-btn:hover {
    background-color: #0b7dda;
}
.no-groups {
    text-align: center;
    padding: 50px;
    color: #666;
    font-size: 1.1em;
}
</style>
</head>
<body>
    <!-- 헤더 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    
    <div class="travelgroup-container">
        <div class="travelgroup-header">
            <h2>내 동행 그룹</h2>
            <a href="${pageContext.request.contextPath}/travelgroup/form.do" class="create-btn">새 동행 만들기</a>
        </div>
        
        <c:if test="${empty myGroupList}">
            <div class="no-groups">
                <p>참여 중인 동행 그룹이 없습니다.</p>
                <p>새로운 동행을 만들거나 기존 동행에 참여해보세요!</p>
            </div>
        </c:if>
        
        <c:if test="${not empty myGroupList}">
            <div class="travelgroup-grid">
                <c:forEach var="group" items="${myGroupList}">
                    <div class="travelgroup-card">
                        <div class="travelgroup-image">
                            <c:choose>
                                <c:when test="${group.region_num == 1}">
                                    <img src="${pageContext.request.contextPath}/images/region/seoul.jpg" alt="서울, 경기도">
                                </c:when>
                                <c:when test="${group.region_num == 2}">
                                    <img src="${pageContext.request.contextPath}/images/region/gangwon.jpg" alt="강원도">
                                </c:when>
                                <c:when test="${group.region_num == 3}">
                                    <img src="${pageContext.request.contextPath}/images/region/busan.jpg" alt="경상도">
                                </c:when>
                                <c:when test="${group.region_num == 4}">
                                    <img src="${pageContext.request.contextPath}/images/region/gwangju.jpg" alt="전라도">
                                </c:when>
                                <c:when test="${group.region_num == 5}">
                                    <img src="${pageContext.request.contextPath}/images/region/jeju.jpg" alt="제주도">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/images/region/guitar.jpg" alt="기타">
                                </c:otherwise>
                            </c:choose>
                            <div class="travelgroup-status ${group.status == 0 ? 'status-recruiting' : (group.status == 1 ? 'status-closed' : 'status-completed')}">
                                ${group.status == 0 ? '모집중' : (group.status == 1 ? '모집마감' : '동행종료')}
                            </div>
                        </div>
                        <div class="travelgroup-content">
                            <h3 class="travelgroup-title">${group.tg_title}</h3>
                            <div class="travelgroup-info">
                                <div>
                                    <img src="${pageContext.request.contextPath}/images/icon/location.png" alt="지역" class="detail-icon">
                                    <c:choose>
                                        <c:when test="${group.region_num == 1}">서울, 경기도</c:when>
                                        <c:when test="${group.region_num == 2}">강원도</c:when>
                                        <c:when test="${group.region_num == 3}">경상도</c:when>
                                        <c:when test="${group.region_num == 4}">전라도</c:when>
                                        <c:when test="${group.region_num == 5}">제주도</c:when>
                                        <c:otherwise>기타</c:otherwise>
                                    </c:choose>
                                </div>
                                <div>
                                    <img src="${pageContext.request.contextPath}/images/icon/calender.png" alt="여행 일정" class="detail-icon">
                                    <fmt:formatDate value="${group.travel_date_start}" pattern="yyyy-MM-dd"/> ~ 
                                    <fmt:formatDate value="${group.travel_date_end}" pattern="yyyy-MM-dd"/>
                                </div>
                            </div>
                        </div>
                        <div class="travelgroup-footer">
                            <div class="member-count">
                                <img src="${pageContext.request.contextPath}/images/icon/group.png" alt="모집 인원" class="detail-icon">
                                ${group.member_count}/${group.max_member_count == 0 ? '제한없음' : group.max_member_count}
                            </div>
                            <a href="${pageContext.request.contextPath}/travelgroup/detail.do?group_num=${group.group_num}" class="view-btn">상세보기</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
    
    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
