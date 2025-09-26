<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>호스트 예약 상세 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/yn.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        .card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
            margin: 30px;
        }
        
        .reservation-header {
            padding: 20px;
            border-bottom: 1px solid #eee;
        }
        
        .property-name {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
        }
        
        .property-address {
            color: #666;
            margin-bottom: 15px;
        }
        
        .status-badge {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 4px;
            font-weight: 500;
            font-size: 14px;
            margin-bottom: 10px;
        }
        
        .status-pending {
            background-color: #FFF8E1;
            color: #F9A825;
        }
        
        .status-confirmed {
            background-color: #E8F5E9;
            color: #388E3C;
        }
        
        .status-canceled {
            background-color: #FFEBEE;
            color: #D32F2F;
        }
        
        .card-section {
            padding: 20px;
            border-bottom: 1px solid #eee;
        }
        
        .section-title {
            font-size: 18px;
            font-weight: 500;
            margin-bottom: 15px;
        }
        
        .info-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        
        .info-item {
            margin-bottom: 15px;
        }
        
        .info-label {
            font-size: 14px;
            color: #666;
            margin-bottom: 5px;
        }
        
        .info-value {
            font-weight: 500;
        }
        
        .map-container {
            height: 200px;
            background-color: #eee;
            border-radius: 4px;
            margin-top: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #999;
        }
        
        .status-selector {
            margin-top: 20px;
        }
        
        .status-selector select {
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #ddd;
            width: 100%;
            font-size: 16px;
        }
        
        .card-footer {
            padding: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .btn {
            padding: 10px 20px;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            border: none;
        }
        
        .btn-secondary {
            background-color: #f0f0f0;
            color: #333;
        }
        
        .btn-primary {
            background-color: #FF5A5F;
            color: white;
        }
        
        .canceled-notice {
            padding: 15px;
            background-color: #FFEBEE;
            border-radius: 4px;
            margin-bottom: 20px;
            border-left: 4px solid #D32F2F;
        }
        
        .canceled-notice h3 {
            color: #D32F2F;
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/host/hostHeader.jsp"/>
  <div class="container">
        <div class="card">
            <!-- 예약 헤더 정보 -->
            <div class="reservation-header">
                <!-- 예약 상태에 따라 클래스를 변경할 수 있습니다. 현재는 '취소됨' 상태로 설정해두었습니다 -->
                <c:if test="${reserv.reserv_status==2 }">
                <span class="status-badge status-canceled">
                    취소됨
                </span>
                </c:if>
                <h1 class="property-name">${reserv.accom_name}</h1>
                
                <!-- 취소된 경우 표시 - 확인을 위해 표시해두었습니다 -->
                <c:if test="${reserv.reserv_status == 2 and not empty reserv.cancel_date}">
                <div class="canceled-notice">
                    <h3>예약이 취소되었습니다</h3>
                    <p>취소 일시: ${reserv.cancel_date}</p>
                    <p>취소 사유: ${reserv.cancel_reason}</p>
                </div>
                </c:if>
            </div>
            
            <!-- 예약 상세 정보 -->
            <div class="card-section">
                <h2 class="section-title">예약 정보</h2>
                <div class="info-grid">
                	<div class="info-item">
                        <div class="info-label">예약자 </div>
                        <div class="info-value">${reserv.user_name}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">예약 번호</div>
                        <div class="info-value">${reserv.reserv_code}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">예약일</div>
                        <div class="info-value">${reserv.reserv_date}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">체크인</div>
                        <div class="info-value">${reserv.check_in_date}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">체크아웃</div>
                        <div class="info-value">${reserv.check_out_date}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">인원</div>
                        <div class="info-value">${reserv.people_count}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">인원</div>
                        <div class="info-value">${reserv.people_count}</div>
                    </div>
                </div>
            </div>
            
            <!-- 결제 정보 -->
            <div class="card-section">
                <h2 class="section-title">결제 정보</h2>
                <div class="info-grid">
                    <div class="info-item">
                        <div class="info-label">결제 수단</div>
                        <div class="info-value">${reserv.payment_method}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">결제 금액</div>
                        <div class="info-value">${reserv.amount}원</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">결제 상태</div>
                        <div class="info-value">
                        <c:if test="${reserv.payment_status=='ready'}">결제준비중</c:if>
                        <c:if test="${reserv.payment_status=='completed'}">결제완료</c:if>
                        <c:if test="${reserv.payment_status=='2'}">결제취소</c:if>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- 상태 변경 -->
            <form action="modifyReservStatus.do" method="post">
	            <div class="card-section">
	                <h2 class="section-title">예약 상태 관리</h2>
	                <div class="status-selector">
	                	<input type="hidden" name="reserv_num" value="${reserv.reserv_num }">
	                    <select id="reservationStatus" name="reserv_status">
	                        <option value="0" <c:if test="${reserv.reserv_status==0}">selected</c:if>>대기</option>
	                        <option value="1" <c:if test="${reserv.reserv_status==1}">selected</c:if>>확정</option>
	                        <option value="2" <c:if test="${reserv.reserv_status==2}">selected</c:if>>취소</option>
	                    </select>
	                </div>
	            </div>
	            
	            <!-- 하단 버튼 -->
	            <div class="card-footer">
	                <button type="button" class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/host/reservList.do'">목록으로 돌아가기</button>
	                <button type="submit" class="btn btn-primary" onclick="updateReservationStatus()">상태 변경하기</button>
	            </div>
            </form>
        </div>
    </div>
</body>
</html>