<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/FH.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/hs/admin.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header_admin.jsp"/>

<div class="admin-container">
    <h1 class="admin-title">관리자 페이지</h1>

    <div class="admin-functions">
        <!-- First row of admin functions -->
        <div class="admin-row">
            <div class="admin-function-item">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/icon/profile.png" alt="사용자 관리">
                </div>
                <button class="admin-button"
                        onclick="location.href='${pageContext.request.contextPath}/user/adminUserList.do'">
                    사용자 관리
                </button>
            </div>

            <div class="admin-function-item">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/icon/mag.png" alt="예약 관리">
                </div>
                <button class="admin-button" disabled>
                    예약 관리
                </button>
            </div>

            <div class="admin-function-item">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/icon/dashboard.png" alt="대시보드">
                </div>
                <button class="admin-button" disabled>
                    대시보드
                </button>
            </div>
        </div>

        <!-- Second row of admin functions -->
        <div class="admin-row">
            <div class="admin-function-item">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/icon/mag2.png" alt="숙소 관리">
                </div>
                <button class="admin-button"
                        onclick="location.href='${pageContext.request.contextPath}/user/adminAccomList.do'">
                    숙소 관리
                </button>
            </div>

            <div class="admin-function-item">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/icon/report.png" alt="신고 관리">
                </div>
                <button class="admin-button"
                        onclick="location.href='${pageContext.request.contextPath}/user/adminReportList.do'">
                    신고 관리
                </button>
            </div>

            <div class="admin-function-item">
                <div class="icon-circle">
                    <img src="${pageContext.request.contextPath}/images/icon/destination.png" alt="어트랙션 관리">
                </div>
                <button class="admin-button"
                        onclick="location.href='${pageContext.request.contextPath}/user/attractionList.do'">
                    어트랙션 관리
                </button>
            </div>
        </div>
    </div>
</div>

<div class="separator"></div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
