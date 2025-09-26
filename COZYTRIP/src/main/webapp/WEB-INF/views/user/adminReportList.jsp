<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>신고 관리 - CozyTrip Admin</title>
    <!-- 기존 CSS 파일 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/FH.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hs/admin_list.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header_admin.jsp"/>

<div class="admin-user-container">
    <h1 class="admin-user-title">신고 관리</h1>

    <!-- 검색 폼 -->
    <form action="adminReportList.do" method="get" class="user-search-form">
        <select name="keyfield">
            <option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>신고번호</option>
            <option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>숙소명</option>
            <option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>신고사유</option>
        </select>
        <input type="text" name="keyword" value="${param.keyword}" placeholder="검색어를 입력하세요">
        <button type="submit">검색</button>
        <button type="button" onclick="location.href='adminReportList.do'">전체 목록</button>
    </form>

    <c:if test="${count == 0}">
        <div class="empty-list-message">
            <p>등록된 신고가 없습니다.</p>
        </div>
    </c:if>

    <c:if test="${count > 0}">
        <div style="overflow-x:auto;">
            <table class="admin-user-list">
                <thead>
                    <tr>
                        <th class="col-num">신고번호</th>
                        <th class="col-accom">숙소명</th>
                        <th class="col-address">주소</th>
                        <th class="col-reason">신고사유</th>
                        <th class="col-date">신고일</th>
                        <th class="col-status" style="width: 20%">상태</th>
                        <th class="col-manage" style="width: 8%">관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="report" items="${list}">
                        <tr>
                            <td>${report.report_num}</td>
                            <td>${report.accom_name}</td>
                            <td>${report.address1}</td>
                            <td>${report.reason}</td>
                            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <form action="adminReportStatus.do" method="post">
                                    <input type="hidden" name="report_num" value="${report.report_num}">
                                    <select name="status" class="auth-select">
                                        <option value="0" <c:if test="${report.status == 0}">selected</c:if>>처리 대기</option>
                                        <option value="1" <c:if test="${report.status == 1}">selected</c:if>>처리 중</option>
                                        <option value="2" <c:if test="${report.status == 2}">selected</c:if>>처리 완료</option>
                                        <option value="3" <c:if test="${report.status == 3}">selected</c:if>>거부</option>
                                    </select>
                                    <button type="submit" class="update-auth-btn" style="width: 100%; max-width: 80px;">상태변경</button>
                                </form>
                            </td>
                            <td>
                                <form action="adminReportDelete.do" method="post" onsubmit="return confirm('정말로 이 신고를 삭제하시겠습니까?');">
                                    <input type="hidden" name="report_num" value="${report.report_num}">
                                    <button type="submit" class="delete-user-btn" style="width: 100%; max-width: 80px;">삭제</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- 페이징 처리 -->
        <div class="user-paging">
            ${pagingHtml}
        </div>
    </c:if>
</div>

<div class="user-separator"></div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script>
    // 신고 관리 페이지 스크립트
    document.addEventListener('DOMContentLoaded', function() {
        // 현재 페이지 하이라이트를 위한 클래스 변경
        const pageLinks = document.querySelectorAll('.user-paging a');
        pageLinks.forEach(link => {
            if(link.classList.contains('current-page')) {
                link.classList.remove('current-page');
                link.classList.add('user-current-page');
            }
        });
    });
</script>
</body>
</html>
