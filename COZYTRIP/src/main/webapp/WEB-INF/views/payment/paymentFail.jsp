<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>결제 실패</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/sh.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<!-- 상단 네비게이션 바 -->
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<!-- 결제 실패 섹션 -->
<section class="payment-fail">
    <div class="container">
        <div class="fail-box">
            <div class="fail-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="#FF5252" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10"></circle>
                    <line x1="15" y1="9" x2="9" y2="15"></line>
                    <line x1="9" y1="9" x2="15" y2="15"></line>
                </svg>
            </div>
            <h2>결제에 실패했습니다</h2>
            <p>결제 도중 문제가 발생했습니다.</p>
            
            <div class="error-details">
                <c:if test="${not empty error_message}">
                    <div class="error-message">
                        <h3>오류 메시지</h3>
                        <p>${error_message}</p>
                    </div>
                </c:if>
                
                <div class="reservation-info">
                    <h3>예약 정보</h3>
                    <c:if test="${not empty reserv}">
                        <p>예약 번호: ${reserv.reserv_code}</p>
                        <p>숙소: ${accom.accom_name}</p>
                        <p>체크인: <fmt:formatDate value="${reserv.check_in_date}" pattern="yyyy년 MM월 dd일"/></p>
                        <p>체크아웃: <fmt:formatDate value="${reserv.check_out_date}" pattern="yyyy년 MM월 dd일"/></p>
                    </c:if>
                    <c:if test="${empty reserv}">
                        <p>예약 정보를 불러올 수 없습니다.</p>
                    </c:if>
                </div>
            </div>
            
            <div class="fail-guidance">
                <h3>다음과 같은 이유로 결제가 실패할 수 있습니다:</h3>
                <ul>
                    <li>카드 잔액 부족</li>
                    <li>결제 정보 오류</li>
                    <li>결제 시간 초과</li>
                    <li>일시적인 시스템 오류</li>
                </ul>
                <p>문제가 지속될 경우 다른 결제 수단을 이용하시거나 고객센터로 문의해주세요.</p>
            </div>
            
            <div class="btn-group">
                <a href="javascript:history.back();" class="btn-secondary">이전 페이지로</a>
                <a href="${pageContext.request.contextPath}/reserv/form.do?accom_num=${accom.accom_num}" class="btn-primary">다시 시도하기</a>
            </div>
        </div>
    </div>
</section>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>