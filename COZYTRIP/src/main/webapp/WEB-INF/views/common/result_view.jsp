<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${result_title}</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/FH.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/hs/result.css">
</head>
<body>
	<!-- 헤더 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<div class="page-main">

		<div class="content-main">
			<h2>${result_title}</h2>
			<div class="result-display">
				<div class="align-center">
					${result_msg}
					<p>
						<button onclick="location.href='${result_url}'">확인</button>
				</div>
			</div>
		</div>

	</div>
	<!-- 푸터 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>

</html>