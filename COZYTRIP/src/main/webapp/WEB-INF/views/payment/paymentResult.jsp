<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>결제 결과</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/payment.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="payment-container">
        <div class="payment-header">
            <h2>결제 결과</h2>
        </div>
        
        <c:if test="${payment_result == 'success'}">
            <div class="success-box">
                <div class="success-icon">
                    <img src="${pageContext.request.contextPath}/images/icon/checkicon.png" alt="체크 아이콘">
                </div>
                <h2>결제가 성공적으로 완료되었습니다</h2>
                
                <div class="reservation-details">
                    <table>
                        <tr>
                            <th>결제 금액</th>
                            <td>${payment_info.amount}원</td>
                        </tr>
                        <tr>
                            <th>결제 방법</th>
                            <td>${payment_info.payment_method}</td>
                        </tr>
                        <tr>
                            <th>결제 시간</th>
                            <td>${payment_info.created_at}</td>
                        </tr>
                        <tr>
                            <th>주문번호</th>
                            <td>${payment_info.partner_order_id}</td>
                        </tr>
                    </table>
                </div>
                
                <div class="success-notice">
                    <p>예약이 완료되었습니다. 예약 내역은 마이페이지에서 확인하실 수 있습니다.</p>
                </div>
            </div>
        </c:if>
        
        <c:if test="${payment_result == 'fail'}">
            <div class="fail-box">
                <div class="fail-icon">
                    <i class="fas fa-times-circle"></i>
                </div>
                <h2>결제에 실패했습니다</h2>
                
                <div class="error-details">
                    <div class="error-message">
                        ${error_msg}
                    </div>
                    
                    <div class="fail-guidance">
                        <h3>다음 사항을 확인해 주세요:</h3>
                        <ul>
                            <li>결제 정보가 올바른지 확인해 주세요.</li>
                            <li>카드 한도가 초과되지 않았는지 확인해 주세요.</li>
                            <li>결제 수단이 정상적인지 확인해 주세요.</li>
                        </ul>
                    </div>
                </div>
            </div>
        </c:if>
        
        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/main/main.do" class="btn btn-primary">메인으로 돌아가기</a>
            <c:if test="${payment_result == 'fail'}">
                <a href="javascript:history.back()" class="btn btn-secondary">다시 시도하기</a>
            </c:if>
        </div>
    </div>
</body>
</html>
