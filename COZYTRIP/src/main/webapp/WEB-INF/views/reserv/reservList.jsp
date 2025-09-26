<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>예약 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/payment.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
</head>
<body>
    <!-- 상단 네비게이션 바 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    
    <div class="payment-container">
        <div class="section-header">
            <h2>예약 목록</h2>
        </div>
        
        <c:if test="${count == 0}">
            <div class="no-items">
                <p>예약 내역이 없습니다.</p>
            </div>
        </c:if>
        
        <c:if test="${count > 0}">
            <div class="reserv-list">
                <c:forEach var="reserv" items="${reservList}">
                    <div class="reserv-item">
                        <div class="reserv-info">
                            <div class="accom-image">
                                <img src="${pageContext.request.contextPath}/images/accomimage/${reserv.accomVO.image_name}" 
                                     alt="${reserv.accomVO.accom_name}"
                                     onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/nowish.png';">
                            </div>
                            <div class="accom-info">
                                <h3>${reserv.accomVO.accom_name}</h3>
                                <p class="address">${reserv.accomVO.fullAddress}</p>
                                <div class="reserv-dates">
                                    <p>체크인: <fmt:formatDate value="${reserv.reservDetailVO.check_in_date}" pattern="yyyy년 MM월 dd일"/></p>
                                    <p>체크아웃: <fmt:formatDate value="${reserv.reservDetailVO.check_out_date}" pattern="yyyy년 MM월 dd일"/></p>
                                    <p>인원: ${reserv.reservDetailVO.people_count}명</p>
                                </div>
                                <div class="reserv-number">
                                	<span class="total-price">예약 번호: <fmt:formatNumber value="${reserv.reservDetailVO.reserv_num}" /></span>
                                </div>
                                <div class="price-info">
                                    <span class="total-price">총 결제 금액: <fmt:formatNumber value="${reserv.reservDetailVO.total_price}" pattern="#,###"/>원</span>
                                </div>
                                <div class="reserv-status">
                                    <c:choose>
                                        <c:when test="${reserv.reservDetailVO.reserv_status == 0}">
                                            <span class="status pending">예약 대기</span>
                                            <button type="button" class="btn-cancel" onclick="confirmCancel(${reserv.reservDetailVO.reserv_num})">예약 취소</button>
                                        </c:when>
                                        <c:when test="${reserv.reservDetailVO.reserv_status == 1}">
                                            <span class="status confirmed">예약 확정</span>
                                            <button type="button" class="btn-cancel" onclick="confirmCancel(${reserv.reservDetailVO.reserv_num})">예약 취소</button>
                                        </c:when>
                                        <c:when test="${reserv.reservDetailVO.reserv_status == 2}">
                                            <span class="status completed">이용 완료</span>
                                            <button type="button" class="btn-review" onclick="location.href='${pageContext.request.contextPath}/review/writeReviewForm.do?reserv_num=${reserv.reservDetailVO.reserv_num}&accom_num=${reserv.accomVO.accom_num}&accom_name=${reserv.accomVO.accom_name}&image_name=${reserv.accomVO.image_name}'">리뷰 작성</button>
                                        </c:when>
                                        <c:when test="${reserv.reservDetailVO.reserv_status == 3}">
                                            <span class="status cancelled">예약 취소</span>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                
                <!-- 페이지 네비게이션 -->
                <div class="pagination">
                    <c:if test="${page > 1}">
                        <a href="${pageContext.request.contextPath}/reserv/list.do?pageNum=${page-1}" class="page-link">&lt;</a>
                    </c:if>
                    
                    <span class="current-page">${page} / ${pageCount}</span>
                    
                    <c:if test="${page < pageCount}">
                        <a href="${pageContext.request.contextPath}/reserv/list.do?pageNum=${page+1}" class="page-link">&gt;</a>
                    </c:if>
                </div>
            </div>
        </c:if>
    </div>
    
    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    
    <script>
    function confirmCancel(reservNum) {
        if(confirm('정말로 예약을 취소하시겠습니까? 이 작업은 되돌릴 수 없습니다.')) {
            location.href = '${pageContext.request.contextPath}/reserv/cancel.do?reserv_num=' + reservNum;
        }
    }
    </script>
</body>
</html>