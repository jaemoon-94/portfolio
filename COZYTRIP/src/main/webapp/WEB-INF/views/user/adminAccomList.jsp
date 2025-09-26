<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>숙소 관리 - CozyTrip Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/FH.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hs/admin_accom_list.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header_admin.jsp"/>

<div class="admin-user-container">
    <h1 class="admin-user-title">숙소 관리</h1>

    <!-- 검색 폼 -->
    <form action="adminAccomList.do" method="get" class="user-search-form">
        <select name="keyfield">
            <option value="accom_name" <c:if test="${param.keyfield == 'accom_name'}">selected</c:if>>숙소명</option>
            <option value="region_name" <c:if test="${param.keyfield == 'region_name'}">selected</c:if>>지역명</option>
            <option value="address1" <c:if test="${param.keyfield == 'address1'}">selected</c:if>>주소</option>
        </select>
        <input type="text" name="keyword" value="${param.keyword}" placeholder="검색어를 입력하세요">
        <button type="submit">검색</button>
        <button type="button" onclick="location.href='adminAccomList.do'">전체 목록</button>
    </form>

    <c:if test="${count == 0}">
        <div class="empty-list-message">
            <p>등록된 숙소가 없습니다.</p>
        </div>
        <script>
            $(document).ready(function(){
                // 알림 메시지 표시
                const alertMsg = '${requestScope.alertMsg}';
                if (alertMsg) {
                    alert(alertMsg);
                }
            });
        </script>
    </c:if>

    <c:if test="${count > 0}">
        <div style="overflow-x:auto;">
            <table class="admin-user-list">
                <thead>
                    <tr>
                        <th class="col-num">번호</th>
                        <th class="col-accom-name">숙소명</th>
                        <th class="col-address">주소</th>
                        <th class="col-region">지역</th>
                        <th class="col-price">가격</th>
                        <th class="col-type">유형</th>
                        <th class="col-date">등록일</th>
                        <th class="col-status">상태</th>
                        <th class="col-manage">관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="accom" items="${list}">
                        <tr>
                            <td>${accom.accom_num}</td>
                            <td>${accom.accom_name}</td>
                            <td>${accom.address1} ${accom.address2}</td>
                            <td>${accom.region_name}</td>
                            <td><fmt:formatNumber value="${accom.price}" pattern="#,###"/>원</td>
                            <td>${accom.cate_name}</td>
                            <td><fmt:formatDate value="${accom.accom_date}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <form action="/COZYTRIP/user/adminAccomStatusUpdate.do" method="post" class="status-update-form">
                                    <input type="hidden" name="accom_num" value="${accom.accom_num}">
                                    <select name="accom_status" class="status-select">
                                        <option value="1" ${accom.accom_status == 1 ? 'selected' : ''}>승인전</option>
                                        <option value="2" ${accom.accom_status == 2 ? 'selected' : ''}>비활성화</option>
                                        <option value="3" ${accom.accom_status == 3 ? 'selected' : ''}>활성화</option>
                                    </select>
                                    <button type="submit" class="update-auth-btn" style="width: 100%; max-width: 80px;">상태변경</button>
                                </form>
                            </td>
                            <td>
                                <form action="adminAccomDelete.do" method="post" onsubmit="return confirm('정말로 이 숙소를 삭제하시겠습니까?');">
                                    <input type="hidden" name="accom_num" value="${accom.accom_num}">
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
        document.addEventListener('DOMContentLoaded', function() {
            const pageLinks = document.querySelectorAll('.user-paging a');
            pageLinks.forEach(link => {
                if(link.classList.contains('current-page')) {
                    link.classList.remove('current-page');
                    link.classList.add('user-current-page');
                }
            });

            // URL에서 메시지 파라미터 확인
            const urlParams = new URLSearchParams(window.location.search);
            const message = urlParams.get('message');
            if (message) {
                alert(decodeURIComponent(message));
            }
        });
    </script>

    <style>
        .custom-alert {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            z-index: 1000;
        }
        
        .alert-content {
            text-align: center;
        }
        
        .alert-content button {
            margin-top: 10px;
            padding: 5px 15px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        
        .alert-content button:hover {
            background: #45a049;
        }
    </style>
</body>
</html>
