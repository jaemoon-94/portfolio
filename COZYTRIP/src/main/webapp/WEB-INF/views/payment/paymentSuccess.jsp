<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>결제 완료</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/yn.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<!-- 상단 네비게이션 바 -->
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<!-- 결제 완료 섹션 -->
<section class="payment-success">
    <div class="container">
        <div class="success-box">
            <div class="success-icon">
                <div class="success-icon">✅</div>
            </div>
            <h2>결제가 완료되었습니다!</h2>
            <p>예약이 성공적으로 확정되었습니다.</p>
            
            <div class="reservation-details">
                <h3>예약 정보</h3>
                <table>
                    <tr>
                        <th>예약 번호</th>
                        <td>${reserv.reserv_num}</td>
                    </tr>
                    <tr>
                        <th>숙소명</th>
                        <td>${accom.accom_name}</td>
                    </tr>
                    <tr>
                        <th>체크인</th>
                        <td><fmt:formatDate value="${reservDetail.check_in_date}" pattern="yyyy년 MM월 dd일"/></td>
                    </tr>
                    <tr>
                        <th>체크아웃</th>
                        <td><fmt:formatDate value="${reservDetail.check_out_date}" pattern="yyyy년 MM월 dd일"/></td>
                    </tr>
                    <tr>
                        <th>인원 수</th>
                        <td>${reservDetail.people_count}명</td>
                    </tr>
                    <tr>
                        <th>결제 금액</th>
                        <td><fmt:formatNumber value="${payment.amount}" pattern="#,###"/>원</td>
                    </tr>
                    <tr>
                        <th>결제 수단</th>
                        <td>${payment.payment_method}</td>
                    </tr>
                    <tr>
                        <th>결제일</th>
                        <td><fmt:formatDate value="${payment.payment_date}" pattern="yyyy년 MM월 dd일 HH:mm:ss"/></td>
                    </tr>
                </table>
            </div>
            
            <div class="success-notice">

                <p>예약 상세 정보는 마이페이지 > 예약 내역에서 확인하실 수 있습니다.</p>
            </div>
            
            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/reserv/detail.do?reserv_num=${reserv.reserv_num}" class="btn-secondary">예약 상세 보기</a>
                <a href="${pageContext.request.contextPath}/main.do" class="btn-primary">홈으로 돌아가기</a>
            </div>
        </div>
    </div>
</section>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>