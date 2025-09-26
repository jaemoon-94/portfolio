<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CozyTrip - 예약 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/yn.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
.reserv-container {
    max-width: 900px;
    margin: 0 auto;
    padding: 20px;
}
.reserv-header {
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.reserv-header h2 {
    color: #333;
    margin: 0;
}
.reserv-status {
    display: inline-block;
    padding: 5px 15px;
    border-radius: 20px;
    font-weight: bold;
    color: white;
}
.status-waiting {
    background-color: #FFC107;
}
.status-confirmed {
    background-color: #4CAF50;
}
.status-canceled {
    background-color: #F44336;
}
.reserv-section {
    margin-bottom: 30px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: white;
}
.reserv-section h3 {
    margin-top: 0;
    margin-bottom: 15px;
    color: #333;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}
.accom-info {
    display: flex;
    margin-bottom: 15px;
}
.accom-image {
    flex: 0 0 200px;
    height: 150px;
    overflow: hidden;
    margin-right: 20px;
    border-radius: 5px;
}
.accom-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.accom-details {
    flex: 1;
}
.accom-details h4 {
    margin-top: 0;
    margin-bottom: 10px;
    color: #333;
}
.accom-location {
    color: #666;
    margin-bottom: 10px;
}
.stay-info {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 10px;
}
.stay-info-item {
    flex: 1 0 50%;
    margin-bottom: 5px;
}
.stay-info-item strong {
    display: inline-block;
    width: 100px;
    color: #555;
}
.request-box {
    background-color: #f8f9fa;
    padding: 15px;
    border-radius: 5px;
    margin-top: 15px;
}
.request-box h5 {
    margin-top: 0;
    margin-bottom: 10px;
    color: #333;
}
.price-box {
    background-color: #f8f9fa;
    padding: 15px;
    border-radius: 5px;
}
.price-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}
.price-total {
    display: flex;
    justify-content: space-between;
    font-weight: bold;
    font-size: 1.2em;
    margin-top: 15px;
    color: #2196F3;
}
.payment-info {
    margin-top: 15px;
}
.payment-info p {
    margin-bottom: 5px;
}
.btn-group {
    display: flex;
    gap: 10px;
    justify-content: flex-end;
    margin-top: 20px;
}
.btn {
    padding: 10px 15px;
    border-radius: 4px;
    border: none;
    cursor: pointer;
    font-weight: bold;
    text-decoration: none;
    text-align: center;
}
.btn-primary {
    background-color: #2196F3;
    color: white;
}
.btn-primary:hover {
    background-color: #0b7dda;
}
.btn-danger {
    background-color: #F44336;
    color: white;
}
.btn-danger:hover {
    background-color: #d32f2f;
}
.btn-secondary {
    background-color: #6c757d;
    color: white;
}
.btn-secondary:hover {
    background-color: #5a6268;
}
</style>
</head>
<body>
<!-- 상단 네비게이션 바 -->
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="reserv-container">
    <div class="reserv-header">
        <h2>예약 상세 정보</h2>
        <div class="reserv-status 
            ${reserv.reservDetailVO.reserv_status eq 0 ? 'status-waiting' : 
              reserv.reservDetailVO.reserv_status eq 1 ? 'status-confirmed' : 'status-canceled'}">
            ${reserv.reservDetailVO.reserv_status eq 0 ? '예약 대기' : 
              reserv.reservDetailVO.reserv_status eq 1 ? '예약 확정' : '예약 취소'}
        </div>
    </div>
    
    <div class="reserv-section">
        <h3>예약 정보</h3>
        
        <div class="accom-info">
        <%--
        <div class="accom-image">
                <img src="${pageContext.request.contextPath}/images/accomimage/${accom.image_name}" alt="${accom.accom_name}" onerror="this.src='${pageContext.request.contextPath}/images/no_image.jpg'">
            </div> 
            
            --%>
            
            <div class="accom-details">
                <h4>${accom.accom_name}</h4>
                <p class="accom-location">${accom.address1} ${accom.address2}</p>
                <div class="stay-info">
                    <div class="stay-info-item">
                        <strong>체크인:</strong> <fmt:formatDate value="${reserv.reservDetailVO.check_in_date}" pattern="yyyy-MM-dd (E)"/>
                    </div>
                    <div class="stay-info-item">
                        <strong>체크아웃:</strong> <fmt:formatDate value="${reserv.reservDetailVO.check_out_date}" pattern="yyyy-MM-dd (E)"/>
                    </div>
                    <div class="stay-info-item">
                        <strong>인원:</strong> ${reserv.reservDetailVO.people_count}명
                    </div>
                    <div class="stay-info-item">
                        <strong>예약번호:</strong> ${reserv.reserv_code}
                    </div>
                    <div class="stay-info-item">
                        <strong>예약일:</strong> <fmt:formatDate value="${reserv.reservDetailVO.reserv_date}" pattern="yyyy-MM-dd HH:mm"/>
                    </div>
                </div>
                
                <c:if test="${not empty reserv.reservDetailVO.request}">
                    <div class="request-box">
                        <h5>요청사항</h5>
                        <p>${reserv.reservDetailVO.request}</p>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    
    <div class="reserv-section">
        <h3>결제 정보</h3>
        
        <div class="price-box">
            <div class="price-row">
                <span>객실 요금</span>
                <span>₩<fmt:formatNumber value="${accom.price}" pattern="#,###"/> x ${nights}박</span>
            </div>
            <div class="price-total">
                <span>총 금액</span>
                <span>₩<fmt:formatNumber value="${reserv.reservDetailVO.total_price}" pattern="#,###"/></span>
            </div>
        </div>
        
        <div class="payment-info">
            <p><strong>결제 상태:</strong> 
                <c:choose>
                    <c:when test="${reserv.reservDetailVO.payment_status eq 0}">결제 대기</c:when>
                    <c:when test="${reserv.reservDetailVO.payment_status eq 1}">결제 완료</c:when>
                    <c:otherwise>결제 취소</c:otherwise>
                </c:choose>
            </p>
            
            <c:if test="${not empty payment}">
                <p><strong>결제 방법:</strong> ${payment.payment_method}</p>
                <p><strong>결제일:</strong> <fmt:formatDate value="${payment.payment_date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
            </c:if>
        </div>
    </div>
    
    <div class="btn-group">
        <a href="${pageContext.request.contextPath}/reserv/list.do" class="btn btn-secondary">목록으로</a>
        
        <c:if test="${reserv.reservDetailVO.reserv_status eq 0 && reserv.reservDetailVO.payment_status eq 0}">
            <!-- 예약 대기 상태이고 결제 전인 경우에만 결제하기 버튼 표시 -->
            <a href="${pageContext.request.contextPath}/payment/form.do?reserv_num=${reserv.reserv_num}" class="btn btn-primary">결제하기</a>
        </c:if>
        
        <c:if test="${reserv.reservDetailVO.reserv_status ne 2 && reserv.reservDetailVO.check_in_date > today}">
            <!-- 아직 취소되지 않고 체크인 전인 경우에만 취소 버튼 표시 -->
            <a href="javascript:confirmCancel();" class="btn btn-danger">예약 취소</a>
        </c:if>
    </div>
</div>

<script>
function confirmCancel() {
    if(confirm('예약을 취소하시겠습니까?')) {
        location.href = '${pageContext.request.contextPath}/reserv/cancel.do?reserv_num=${reserv.reserv_num}';
    }
}

// 오늘 날짜 설정 (JSP에서 사용할 수 있도록)
var today = '<fmt:formatDate value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd"/>';
</script>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>