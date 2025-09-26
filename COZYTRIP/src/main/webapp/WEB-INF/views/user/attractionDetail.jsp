<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>어트랙션 상세</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/FH.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hs/attractions.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header_admin.jsp"/>
    
    <div class="page-main">
        <div class="content-main">
            <div class="attraction-detail">
                <div class="attraction-item">
                    <img src="${pageContext.request.contextPath}/${attraction.image_url.startsWith('/') ? attraction.image_url.substring(1) : attraction.image_url}" alt="${attraction.name}" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/attimage/default_attraction.jpg';">
                    <div class="attraction-name">${attraction.name}</div>
                    <div class="attraction-desc">${attraction.description}</div>
                    <div class="attraction-addr">${attraction.address1} ${attraction.address2}</div>
                    <div class="attraction-zipcode">우편번호: ${attraction.zipcode}</div>
                </div>
            </div>
            
            <div style="margin-top: 20px; text-align: right;">
                <div class="button-group">
                    <button type="button" class="btn-edit" onclick="location.href='attractionEdit.do?attraction_num=${attraction.attraction_num}'">수정</button>
                    <button type="button" class="btn-cancel" onclick="location.href='attractionList.do'">취소</button>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    
    <script>
        function deleteAttraction(attractionNum) {
            if (confirm('정말로 삭제하시겠습니까?')) {
                location.href = 'attractionDelete.do?attraction_num=' + attractionNum;
            }
        }
    </script>
</body>
</html>
