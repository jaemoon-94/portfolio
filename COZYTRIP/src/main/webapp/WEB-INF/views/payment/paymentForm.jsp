<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>결제하기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/payment.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
.btn-group {
    display: flex;
    gap: 10px;
}
.btn {
    padding: 12px 20px;
    border-radius: 4px;
    border: none;
    cursor: pointer;
    font-weight: bold;
    font-size: 1em;
    text-align: center;
    text-decoration: none;
}
.btn-primary {
    background-color: #2196F3;
    color: white;
}
.btn-primary:hover {
    background-color: #0b7dda;
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

<!-- 결제 섹션 -->
<section class="payment-form">
    <div class="container">
        <div class="section-header">
            <h2 class="section-title">결제하기</h2>
        </div>
        
        <div class="reserv-summary">
            <h3>예약 정보</h3>
            <div class="reserv-info">
                <div class="accom-image">
                    <img src="${pageContext.request.contextPath}/images/accomimage/${reserv.accomVO.image_name}" 
                         alt="${reserv.accomVO.accom_name}"
                         onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/nowish.png';">
                </div>
                <div class="accom-info">
                    <p class="reserv-num">예약번호: ${reserv.reserv_code}</p>
                    <h4>${reserv.accomVO.accom_name}</h4>
                    <p class="accom-address">${reserv.accomVO.address1} ${reserv.accomVO.address2}</p>
                    <div class="reserv-dates">
                        <p>체크인: <fmt:formatDate value="${reserv.reservDetailVO.check_in_date}" pattern="yyyy년 MM월 dd일"/></p> 
                        <p>체크아웃: <fmt:formatDate value="${reserv.reservDetailVO.check_out_date}" pattern="yyyy년 MM월 dd일"/></p> 
                        <p>인원: ${reserv.reservDetailVO.people_count}명</p>
                    </div>
                </div>
            </div>
            
            <div class="price-details">
                <h3>요금 세부 정보</h3>
                <div class="price-total">
                    <span>총 결제 금액</span>
                    <span><fmt:formatNumber value="${reserv.reservDetailVO.total_price}" pattern="#,###"/>원</span>
                </div>
            </div>
        </div>
        
        <div class="payment-methods">
            <h3>결제 수단 선택</h3>
            <div class="payment-options">
                <div class="payment-option selected" data-method="kakaopay">
                    <img src="${pageContext.request.contextPath}/images/icon/kakaopay.png" alt="카카오페이">
                    <span>카카오페이</span>
                </div>
                
            </div>
        </div>
        
        <div class="agreement">
            <label>
                <input type="checkbox" id="agreement" required>
                <span>예약 조건 및 개인정보 처리방침에 동의합니다.</span>
            </label>
        </div>
        
        <div class="btn-group">
            <button type="button" onclick="history.back()" class="btn btn-secondary">뒤로 가기</button>
            <button type="button" id="paymentBtn" class="btn btn-primary">결제하기</button>
        </div>
        
        <form id="paymentForm" action="${pageContext.request.contextPath}/payment/ready.do" method="post">
            <input type="hidden" name="reserv_num" value="${reserv.reserv_num}">
            <input type="hidden" name="payment_method" id="payment_method" value="kakaopay">
            <input type="hidden" name="amount" value="${reservDetail.total_price}">
        </form>
    </div>
</section>

<script>
$(document).ready(function() {
    // 결제 수단 선택
    $('.payment-option').click(function() {
        $('.payment-option').removeClass('selected');
        $(this).addClass('selected');
        $('#payment_method').val($(this).data('method'));
    });
    
    // 결제하기 버튼 클릭
    $('#paymentBtn').click(function() {
        if(!$('#agreement').prop('checked')) {
            alert('예약 조건 및 개인정보 처리방침에 동의해주세요.');
            return;
        }
        
        $('#paymentForm').submit();
    });
});
</script>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>