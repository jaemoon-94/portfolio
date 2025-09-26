<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>사용자 관리 - CozyTrip Admin</title>
    <!-- 기존 CSS 파일 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/FH.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hs/admin_list.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header_admin.jsp"/>

<div class="admin-user-container">
    <h1 class="admin-user-title">사용자 관리</h1>

    <!-- 검색 폼 -->
    <form action="adminUserList.do" method="get" class="user-search-form">
        <select name="keyfield">
            <option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>ID</option>
            <option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>이름</option>
            <option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>이메일</option>
        </select>
        <input type="text" name="keyword" value="${param.keyword}" placeholder="검색어를 입력하세요">
        <button type="submit">검색</button>
        <button type="button" onclick="location.href='adminUserList.do'">전체 목록</button>
    </form>

    <c:if test="${count == 0}">
        <div class="empty-list-message">
            <p>등록된 사용자가 없습니다.</p>
        </div>
    </c:if>

    <c:if test="${count > 0}">
        <div style="overflow-x:auto;">
            <table class="admin-user-list">
                <thead>
                    <tr>
                        <th class="col-num">회원번호</th>
                        <th class="col-id">ID</th>
                        <th class="col-name">이름</th>
                        <th class="col-email">이메일</th>
                        <th class="col-phone">전화번호</th>
                        <th class="col-date">가입일</th>
                        <th class="col-auth">권한</th>
                        <th class="col-manage">관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${list}">
                        <tr>
                            <td>${user.userNum}</td>
                            <td>${user.userId}</td>
                            <td>${user.userName}</td>
                            <td>${user.userEmail}</td>
                            <td>${user.userPhone}</td>
                            <td><fmt:formatDate value="${user.regDate}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <form action="adminUserAuth.do" method="post">
                                    <input type="hidden" name="user_num" value="${user.userNum}">
                                    <select name="auth" class="auth-select">
                                        <option value="0" <c:if test="${user.auth == 0}">selected</c:if>>일반회원</option>
                                        <option value="1" <c:if test="${user.auth == 1}">selected</c:if>>숙소관리자</option>
                                        <option value="2" <c:if test="${user.auth == 2}">selected</c:if>>관리자</option>
                                        <option value="8" <c:if test="${user.auth == 8}">selected</c:if>>정지</option>
                                        <option value="9" <c:if test="${user.auth == 9}">selected</c:if>>탈퇴</option>
                                    </select>
                                    <button type="submit" class="update-auth-btn">권한변경</button>
                                </form>
                            </td>
                            <td>
                                <form action="adminUserDelete.do" method="post" onsubmit="return confirm('정말로 이 사용자를 삭제하시겠습니까?');">
                                    <input type="hidden" name="user_num" value="${user.userNum}">
                                    <button type="submit" class="delete-user-btn">삭제</button>
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
    // 사용자 관리 페이지 스크립트
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