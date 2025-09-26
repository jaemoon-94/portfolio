<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 신고 목록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/jm.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/report.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="report-container">
  <div class="report-title">내 신고 목록</div>

  <c:if test="${count == 0}">
    <p>등록된 신고가 없습니다.</p>
  </c:if>

  <c:if test="${count > 0}">
    <table class="report-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>숙소명</th>
          <th>주소</th>
          <th>신고사유</th>
          <th>신고일자</th>
          <th>처리상태</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="report" items="${list}" varStatus="status">
          <tr>
            <td>신고 ${status.index + 1}</td>
            <td><a href="${pageContext.request.contextPath}/accom/accomDetail.do?accom_num=${report.accom_num}">${report.accom_name}</a></td>
            <td>${report.address1}</td>
            <td>${report.reason}</td>
            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd"/></td>
            <td>
              <c:choose>
                <c:when test="${report.status == 0}">대기중</c:when>
                <c:when test="${report.status == 1}">조치완료</c:when>
                <c:otherwise>기타</c:otherwise>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:if>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>









































