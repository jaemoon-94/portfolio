<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CozyTrip - 국내 여행 숙박 예약</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/jm.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
    <!-- 상단 네비게이션 바 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
            
    <!-- 숙소 목록 섹션 -->
    <section class="accomlist">
	<div class="container">
        <div class="property-grid" id="propertyGrid">
			<c:forEach var="wishList" items="${list}" varStatus="status">
			<div class="property-card ${status.index >= 4 ? 'hidden' : ''}"
			     onclick="location.href='${pageContext.request.contextPath}/accom/accomDetail.do?accom_num=${wishList.accom_num}'"
			     style="cursor: pointer;">
                    <div class="property-image">
                        <img class="accom_img" src="${pageContext.request.contextPath}/images/accomimage/${wishList.image_name}">
                        <div class="wishlist-button">
                            <img class="output_wish" data-num="${wishList.accom_num}" 
     								src="${pageContext.request.contextPath}/images/yeswish.png" width="12">
                        </div>
                    </div>
                    <div class="property-info">
                        <div class="property-type">${wishList.cate_name}</div>
                        <h3 class="property-name">${wishList.accom_name}</h3>
                        <div class="property-location">
                            <span>${wishList.address1}</span>
                        </div>
                        <div class="property-rating">
                            <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" style="display: block; height: 14px; width: 14px; fill: #FA6767;">
                                <path d="M15.094 1.579l-4.124 8.885-9.86 1.27a1 1 0 0 0-.542 1.736l7.293 6.565-1.965 9.852a1 1 0 0 0 1.483 1.061L16 25.951l8.625 4.997a1 1 0 0 0 1.482-1.06l-1.965-9.853 7.293-6.565a1 1 0 0 0-.541-1.735l-9.86-1.271-4.127-8.885a1 1 0 0 0-1.814 0z" fill-rule="evenodd"></path>
                            </svg>
                            <div class="property-price"><fmt:formatNumber value="${wishList.price}"/>원 / 1박</div>
                            <span style="margin-left: auto; padding-right: 8px;">4.9 (145)</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        </div>
   
    </section>

    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    
</body>
</html>
