<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>동행 찾기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/sh.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
/* 동행 그룹 목록 스타일 */
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
.filter-section {
    background-color: #f8f9fa;
    padding: 15px;
    border-radius: 5px;
    margin-bottom: 20px;
}
.filter-row {
    display: flex;
    gap: 15px;
    margin-bottom: 10px;
}
.filter-item {
    display: flex;
    align-items: center;
}
.filter-item label {
    margin-right: 8px;
    font-weight: bold;
}
.filter-item select {
    padding: 8px;
    border-radius: 4px;
    border: 1px solid #ddd;
}
.filter-btn {
    background-color: #4CAF50;
    color: white;
    border: none;
    padding: 8px 15px;
    border-radius: 4px;
    cursor: pointer;
}
.filter-btn:hover {
    background-color: #45a049;
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
.detail-link {
    text-decoration: none;
    color: #2196F3;
    font-weight: bold;
    font-size: 0.9em;
}
.detail-link:hover {
    text-decoration: underline;
}
.pagination {
    display: flex;
    justify-content: center;
    margin-top: 30px;
    gap: 5px;
}
.pagination a, .pagination span {
    display: inline-block;
    padding: 8px 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    text-decoration: none;
    color: #333;
}
.pagination .current {
    background-color: #2196F3;
    color: white;
    border-color: #2196F3;
}
.pagination a:hover {
    background-color: #f5f5f5;
}
.no-results {
    text-align: center;
    padding: 30px;
    background-color: #f8f9fa;
    border-radius: 5px;
    color: #666;
}
</style>
</head>
<body>
<!-- 상단 네비게이션 바 -->
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="travelgroup-container">
    <div class="travelgroup-header">
        <h2>동행 찾기</h2>
        <a href="${pageContext.request.contextPath}/travelgroup/form.do" class="create-btn">동행 그룹 만들기</a>
    </div>
    
    <div class="filter-section">
        <form action="${pageContext.request.contextPath}/travelgroup/list.do" method="get">
            <div class="filter-row">
                <div class="filter-item">
                    <label for="region_num">지역</label>
                    <select name="region_num" id="region_num">
                        <option value="0">전체</option>
                        <c:forEach var="region" items="${regionList}">
                            <option value="${region.region_num}" ${region_num eq region.region_num ? 'selected' : ''}>${region.region_name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="filter-item">
                    <label for="status">상태</label>
                    <select name="status" id="status">
                        <option value="">전체</option>
                        <option value="0" ${status eq '0' ? 'selected' : ''}>모집중</option>
                        <option value="1" ${status eq '1' ? 'selected' : ''}>모집마감</option>
                        <option value="2" ${status eq '2' ? 'selected' : ''}>동행종료</option>
                    </select>
                </div>
                <button type="submit" class="filter-btn">필터 적용</button>
            </div>
        </form>
    </div>
    
    <c:if test="${count == 0}">
        <div class="no-results">
            <p>등록된 동행 그룹이 없습니다.</p>
        </div>
    </c:if>
    
    <c:if test="${count > 0}">
        <div class="travelgroup-grid">
            <c:forEach var="group" items="${list}">
                <div class="travelgroup-card">
                    <div class="travelgroup-image">
                        <c:set var="regionImage" value="default_region.jpg" />
                        <c:choose>
                            <c:when test="${group.region_num == 1}"><c:set var="regionImage" value="seoul.jpg" /></c:when>
                            <c:when test="${group.region_num == 2}"><c:set var="regionImage" value="gangwon.jpg" /></c:when>
                            <c:when test="${group.region_num == 3}"><c:set var="regionImage" value="busan.jpg" /></c:when>
                            <c:when test="${group.region_num == 4}"><c:set var="regionImage" value="gwangju.jpg" /></c:when>
                            <c:when test="${group.region_num == 5}"><c:set var="regionImage" value="jeju.jpg" /></c:when>
                        </c:choose>
                        <img src="${pageContext.request.contextPath}/images/region/${regionImage}" alt="지역 이미지" onerror="this.src='${pageContext.request.contextPath}/images/default_region.jpg'">
                        <div class="travelgroup-status 
                            ${group.status == 0 ? 'status-recruiting' : 
                              group.status == 1 ? 'status-closed' : 'status-completed'}">
                            ${group.status == 0 ? '모집중' : 
                              group.status == 1 ? '모집마감' : '동행종료'}
                        </div>
                    </div>
                    <div class="travelgroup-content">
                        <h3 class="travelgroup-title">${group.tg_title}</h3>
                        <div class="travelgroup-info">
                            <div>지역: 
<c:choose>
    <c:when test="${group.region_num == 1}">서울, 경기도</c:when>
    <c:when test="${group.region_num == 2}">강원도</c:when>
    <c:when test="${group.region_num == 3}">경상도</c:when>
    <c:when test="${group.region_num == 4}">전라도</c:when>
    <c:when test="${group.region_num == 5}">제주도</c:when>
    <c:when test="${group.region_num == 6}">기타</c:when>
    <c:otherwise>기타</c:otherwise>
</c:choose>
</div>
                            <div>일정: 
                                <fmt:formatDate value="${group.travel_date_start}" pattern="yyyy-MM-dd"/>
                                ~ 
                                <fmt:formatDate value="${group.travel_date_end}" pattern="yyyy-MM-dd"/>
                            </div>
                            <div>작성자: ${group.creator_id}</div>
                            <div>작성일: <fmt:formatDate value="${group.create_date}" pattern="yyyy-MM-dd"/></div>
                        </div>
                    </div>
                    <div class="travelgroup-footer">
                        <div class="member-count">멤버: ${group.member_count}/${group.max_member_count == 0 ? '제한없음' : group.max_member_count}</div>
                        <a href="${pageContext.request.contextPath}/travelgroup/detail.do?group_num=${group.group_num}" class="detail-link">자세히 보기</a>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <div class="pagination">
            <c:if test="${startPage > 1}">
                <a href="${pageContext.request.contextPath}/travelgroup/list.do?pageNum=${startPage-1}&region_num=${region_num}&status=${status}">이전</a>
            </c:if>
            
            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                <c:choose>
                    <c:when test="${currentPage == i}">
                        <span class="current">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/travelgroup/list.do?pageNum=${i}&region_num=${region_num}&status=${status}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            
            <c:if test="${endPage < pageCount}">
                <a href="${pageContext.request.contextPath}/travelgroup/list.do?pageNum=${endPage+1}&region_num=${region_num}&status=${status}">다음</a>
            </c:if>
        </div>
    </c:if>
</div>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>