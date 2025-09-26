<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CozyKorea - 로그인</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/FH.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/hs/login.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		// 로그인 폼 유효성 검사
		$('#login_form').submit(function() {
			if ($('#user_id').val().trim() == '') {
				alert('아이디를 입력하세요.');
				$('#user_id').focus();
				return false;
			}
			if ($('#passwd').val().trim() == '') {
				alert('비밀번호를 입력하세요.');
				$('#passwd').focus();
				return false;
			}
		});
	});
</script>

<!-- 메시지 표시 -->
<jsp:include page="/WEB-INF/views/common/alert_singleView.jsp" />
</head>
<body>
	<div class="wrapper">

		<!-- 헤더 -->
		<jsp:include page="/WEB-INF/views/common/header.jsp" />

		<!-- 로그인 메인 -->
		<main class="page-background">
			<div class="login-container">

				<h2 class="login-title">로그인</h2>

				<form class="login-form"
					action="${pageContext.request.contextPath}/user/loginUser.do"
					method="post">
					<div class="form-field">
						<label for="user_id">아이디</label> <input type="text" name="user_id"
							id="user_id" maxlength="12" autocomplete="off" required>
					</div>
					<div class="form-field">
						<label for="passwd">비밀번호</label> <input type="password"
							name="passwd" id="passwd" maxlength="12" required>
					</div>
					<div class="button-group">
						<input type="submit" value="로그인" class="btn btn-primary">
						<input type="button" value="회원가입" class="btn btn-secondary"
							onclick="location.href='${pageContext.request.contextPath}/user/signup.do'">
						<input type="button" value="홈으로" class="btn btn-secondary"
							onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
					</div>
				</form>
			</div>
		</main>

		<!-- 푸터 -->
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />

	</div>
</body>


</html>
