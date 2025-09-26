<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/jm.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/report.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container">
<br>
<form action="writeReport.do" method="post" class="report-form">
			<input type="hidden" name="report_num" value="${param.report_num}">
			<input type="hidden" name="accom_num" value="${accom_num}">
				<div class="rating-group">
					<div>
						<label>숙소 이름 : </label>
						<div class="accom-name">
							${accom_name}
						</div>
					</div>
				</div>
				<div style="margin-top: 1rem;">
					<label for="content">신고 내용</label>
					<textarea name="reason" id="reason" required></textarea>
				</div>

				<div class="report-form-buttons">
					<button type="button" class="back-btn" onclick="history.back();">취소</button>
					<button type="reset" class="cancel-btn">비우기</button>
					<button type="submit" class="submit-btn">신고하기</button>
				</div>
			</form>
</div>


<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

</body>
</html>



































