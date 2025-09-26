<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>숙소 예약하기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/yn.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
.reserv-container {
    max-width: 900px;
    margin: 0 auto;
    padding: 20px;
}
.reserv-header {
    margin-bottom: 20px;
}
.reserv-header h2 {
    color: #333;
    margin-bottom: 10px;
}
.accom-info {
    display: flex;
    margin-bottom: 30px;
    border: 1px solid #ddd;
    border-radius: 5px;
    overflow: hidden;
}
.accom-image {
    flex: 0 0 300px;
    height: 200px;
    overflow: hidden;
}
.accom-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
.accom-details {
    flex: 1;
    padding: 20px;
    background-color: #f8f9fa;
}
.accom-details h3 {
    margin-top: 0;
    margin-bottom: 10px;
    color: #333;
}
.accom-location {
    color: #666;
    margin-bottom: 10px;
}
.accom-rating {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}
.accom-rating svg {
    margin-right: 5px;
}
.accom-price {
    font-weight: bold;
    color: #2196F3;
    font-size: 1.2em;
}
.form-section {
    margin-bottom: 30px;
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 20px;
}
.form-section h3 {
    margin-top: 0;
    margin-bottom: 20px;
    color: #333;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}
.form-group {
    margin-bottom: 20px;
}
.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    color: #555;
}
.form-control {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1em;
}
.date-group {
    display: flex;
    gap: 15px;
}
.form-select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1em;
}
.form-textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1em;
    min-height: 100px;
    resize: vertical;
}
.price-summary {
    background-color: #f8f9fa;
    padding: 20px;
    border-radius: 5px;
    margin-bottom: 30px;
}
.price-summary h3 {
    margin-top: 0;
    margin-bottom: 15px;
    color: #333;
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

<div class="reserv-container">
    <div class="reserv-header">
        <h2>숙소 예약</h2>
    </div>
    
    <div class="accom-info">
    <%-- <div class="accom-image">
            <img src="${pageContext.request.contextPath}/images/accomimage/${accom.image_name}" alt="${accom.accom_name}" onerror="this.src='${pageContext.request.contextPath}/images/no_image.jpg'">
        </div> --%>
        
        <div class="accom-details">
            <h3>${accom.accom_name}</h3>
            <p class="accom-location">${accom.fullAddress}</p>
            <div class="accom-rating">
                <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" role="presentation" focusable="false" style="display: block; height: 14px; width: 14px; fill: #FF385C;">
                    <path d="M15.094 1.579l-4.124 8.885-9.86 1.27a1 1 0 0 0-.542 1.736l7.293 6.565-1.965 9.852a1 1 0 0 0 1.483 1.061L16 25.951l8.625 4.997a1 1 0 0 0 1.482-1.06l-1.965-9.853 7.293-6.565a1 1 0 0 0-.541-1.735l-9.86-1.271-4.127-8.885a1 1 0 0 0-1.814 0z" fill-rule="evenodd"></path>
                </svg>
                <span>${accom.host_rating} (${reviewCount})</span>
            </div>
            <p class="accom-price">₩<fmt:formatNumber value="${accom.price}" pattern="#,###"/> / 1박</p>
        </div>
    </div>
    
    <form action="${pageContext.request.contextPath}/reserv/create.do" method="post" id="reservForm">
        <input type="hidden" name="accom_num" value="${accom.accom_num}">
        <input type="hidden" name="price" value="${accom.price}">
        
        <div class="form-section">
            <h3>날짜 및 인원 선택</h3>
            
            <div class="form-group date-group">
                <div style="flex: 1;">
                    <label for="check_in_date">체크인 날짜</label>
                    <input type="date" class="form-control" id="check_in_date" name="check_in_date" value="${check_in_date}" required>
                </div>
                <div style="flex: 1;">
                    <label for="check_out_date">체크아웃 날짜</label>
                    <input type="date" class="form-control" id="check_out_date" name="check_out_date" value="${check_out_date}" required>
                </div>
            </div>
            
            <div class="form-group">
                <label for="people_count">인원 수</label>
                <select class="form-select" id="people_count" name="people_count" required>
                    <c:forEach var="i" begin="1" end="${accom.max_people}">
                        <option value="${i}" ${i eq people_count ? 'selected' : ''}>${i}명</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        
        <div class="form-section">
            <h3>요청사항</h3>
            <div class="form-group">
                <textarea class="form-textarea" id="request" name="request" placeholder="호스트에게 전달할 요청사항이 있으면 입력해주세요." maxlength="1000"></textarea>
            </div>
        </div>
        
        <div class="price-summary">
            <h3>요금 세부 정보</h3>
            <div class="price-row">
                <span>객실 요금</span>
                <span>₩<fmt:formatNumber value="${accom.price}" pattern="#,###"/> x <span id="nights">0</span>박</span>
            </div>
            <div class="price-total">
                <span>총 금액</span>
                <span id="total_price">₩0</span>
                <input type="hidden" name="total_price" id="total_price_input" value="${total_price}">
            </div>
        </div>
        
        <div class="btn-group">
            <a href="javascript:history.back();" class="btn btn-secondary">뒤로 가기</a>
            <button type="submit" class="btn btn-primary">예약하기</button>
        </div>
    </form>
</div>

<script>
$(function() {
    // 오늘 날짜 기준으로 체크인 날짜 제한
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var yyyy = today.getFullYear();
    
    today = yyyy + '-' + mm + '-' + dd;
    $('#check_in_date').attr('min', today);
    
    // 체크인 날짜 선택 시 체크아웃 날짜 제한
    $('#check_in_date').change(function() {
        var checkInDate = $(this).val();
        $('#check_out_date').attr('min', checkInDate);
        
        // 체크인 이후 날짜여야 함
        if($('#check_out_date').val() <= checkInDate) {
            var nextDay = new Date(checkInDate);
            nextDay.setDate(nextDay.getDate() + 1);
            
            var nextDd = String(nextDay.getDate()).padStart(2, '0');
            var nextMm = String(nextDay.getMonth() + 1).padStart(2, '0');
            var nextYyyy = nextDay.getFullYear();
            
            $('#check_out_date').val(nextYyyy + '-' + nextMm + '-' + nextDd);
        }
        
        calculatePrice();
    });
    
    // 체크아웃 날짜 변경 시 가격 계산
    $('#check_out_date').change(function() {
        calculatePrice();
    });
    
    // 요금 계산 함수
    function calculatePrice() {
        var checkIn = new Date($('#check_in_date').val());
        var checkOut = new Date($('#check_out_date').val());
        
        if(checkIn && checkOut && checkOut > checkIn) {
            var diffTime = Math.abs(checkOut - checkIn);
            var diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
            
            $('#nights').text(diffDays);
            
            var pricePerNight = ${accom.price};
            var totalPrice = pricePerNight * diffDays;
            
            $('#total_price').text('₩' + totalPrice.toLocaleString());
            $('#total_price_input').val(totalPrice);
        } else {
            $('#nights').text('0');
            $('#total_price').text('₩0');
            $('#total_price_input').val('0');
        }
    }

    // 폼 제출 전 유효성 검사
    $('#reservForm').submit(function(e) {
        var checkIn = new Date($('#check_in_date').val());
        var checkOut = new Date($('#check_out_date').val());
        
        if(!checkIn || !checkOut) {
            alert('체크인/체크아웃 날짜를 선택해주세요.');
            e.preventDefault();
            return false;
        }
        
        if(checkOut <= checkIn) {
            alert('체크아웃 날짜는 체크인 날짜 이후여야 합니다.');
            e.preventDefault();
            return false;
        }
        
        if($('#total_price_input').val() == '0') {
            alert('예약 날짜를 확인해주세요.');
            e.preventDefault();
            return false;
        }
        
        return true;
    });
    
    // 페이지 로드 시 가격 계산 실행
    calculatePrice();
});
</script>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>