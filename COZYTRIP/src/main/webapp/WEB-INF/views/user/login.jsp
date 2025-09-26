<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:choose>
	<c:when test="${auth == 9}">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 정보</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
</head>
<body>
<div class="page-main">
<!-- 상단 네비게이션 바 -->
<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<div class="content-main">
		<h2>회원 정보</h2>
		<div class="result-display">
			<div class="align-center">
				로그인 정지된 회원입니다.<p>
				<input type="button" value="홈으로"
				  onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
		</div>
	</div>
</div>
<!-- 하단 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			alert('아이디 또는 비밀번호가 불일치합니다.');
			history.go(-1);
		</script>
	</c:otherwise>
</c:choose>		
	






