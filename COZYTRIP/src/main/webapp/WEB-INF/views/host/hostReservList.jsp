<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CozyKorea 호스트 - 예약 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/hostHeader.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
.reservation-table {
    width: 100%;
    border-collapse: collapse;
    background-color: white;
    box-shadow: 0 2px 10px rgba(0,0,0,0.05);
    border-radius: 8px;
    overflow: hidden;
}

.reservation-table thead {
    background-color: #f5f5f5;
}

.reservation-table th, .reservation-table td {
    padding: 12px 15px;
    text-align: center;
    font-size: 14px;
    border-bottom: 1px solid #e0e0e0;
}

.reservation-table th {
    font-weight: 600;
    color: #333;
}

.reservation-table tr:hover {
    background-color: #f9f9f9;
}

.status.confirmed {
    color: #2ecc71;
    font-weight: bold;
}

.status.pending {
    color: #f39c12;
    font-weight: bold;
}

.status.canceled {
    color: #e74c3c;
    font-weight: bold;
}
.status-select {
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 6px;
    font-size: 14px;
    background-color: white;
}
.search-bar {
    margin-bottom: 20px;
    display: flex;
    justify-content: flex-end;
}

.search-form {
    display: flex;
    gap: 8px;
}

.search-form input[type="text"] {
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 6px;
    font-size: 14px;
    width: 250px;
}

.search-form button {
    padding: 8px 16px;
    background-color: #fa6767;
    border: none;
    border-radius: 6px;
    color: white;
    font-weight: bold;
    cursor: pointer;
}

.search-form button:hover {
    background-color: #27ae60;
}
.paging{
	text-align: center;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/host/hostHeader.jsp"/>

<div class="container">
<h2>예약 목록</h2>
    <div class="search-bar">
        <form action="reservList.do" method="get" class="search-form">
            <select name="accom" class="status-select">
                <option value="">전체 숙소</option>
                <c:forEach var="myAccomList" items="${myAccomList}">
                    <option value="${myAccomList.accom_num}" 
                        <c:if test="${param.accom == myAccomList.accom_num}">selected</c:if>>
                        ${myAccomList.accom_name}
                    </option>
                </c:forEach>
            </select>
            <select name="status" class="status-select">
                <option value="">전체 상태</option>
                <option value="0" ${param.status == '0' ? 'selected' : ''}>대기</option>
                <option value="1" ${param.status == '1' ? 'selected' : ''}>확정</option>
                <option value="2" ${param.status == '2' ? 'selected' : ''}>취소</option>
            </select>
            <button type="submit">필터링</button>
        </form>
    </div>

    <table class="reservation-table">
        <thead>
            <tr>
                <th>예약 코드</th>
                <th>숙소명</th>
                <th>예약자</th>
                <th>체크인</th>
                <th>체크아웃</th>
                <th>인원</th>
                <th>총 금액</th>
                <th>상태</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty list}">
                    <c:forEach var="resv" items="${list}">
                        <tr>
                            <td><a href="reservDetail.do?reserv_num=${resv.reserv_num}">${resv.reserv_code}</a></td>
                            <td>${resv.accom_name}</td>
                            <td>${resv.user_name}</td>
                            <td>${resv.check_in_date}</td>
                            <td>${resv.check_out_date}</td>
                            <td>${resv.people_count}</td>
                            <td><fmt:formatNumber value="${resv.total_price}" type="currency"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${resv.reserv_status == 0}">
                                        <span class="status pending">대기</span>
                                    </c:when>
                                    <c:when test="${resv.reserv_status == 1}">
                                        <span class="status confirmed">확정</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status canceled">취소</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="8">예약 내역이 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <div class="paging">
        ${page}
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
