<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 작성 폼</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/jm.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/review.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

	<section class="review-container">
		<h2 style="font-size: 1.8rem; font-weight: bold;">숙소 후기</h2>

		<div class="review-form-wrap">
			<!-- 왼쪽 숙소 정보 -->
			<div class="review-accom-info">
				<img src="${pageContext.request.contextPath}/images/accomimage/${param.image_name}"
					alt="숙소 이미지">
				<div style="font-weight: bold; font-size: 18px;">${param.accom_name}</div>
				<div style="margin-top: 0.5rem; color: #666;">평균 평점 : ★ ${avgRating}</div>
				<!-- 
				<ul class="review-rating-breakdown">
					<li>5점 ⭐⭐⭐⭐⭐ - 8명</li>
					<li>4점 ⭐⭐⭐⭐ - 2명</li>
					<li>3점 ⭐⭐⭐ - 0명</li>
					<li>2점 ⭐⭐ - 0명</li>
					<li>1점 ⭐ - 0명</li>
				</ul>
				 -->
			</div>

			<!-- 오른쪽 리뷰 작성 폼 -->
			<form action="updateReview.do" method="post" class="review-form">
			<input type="hidden" name="review_num" value="${review.review_num}">
				<div class="rating-group">
					<div>
						<label>숙소 평가</label>
						<div class="star-rating">
							<input type="radio" name="rating" value="5" id="accom5" 
								<c:if test="${param.rating == 5}">checked</c:if> />
							<label for="accom5">★</label>
						
							<input type="radio" name="rating" value="4" id="accom4"
								<c:if test="${param.rating == 4}">checked</c:if> />
							<label for="accom4">★</label>
						
							<input type="radio" name="rating" value="3" id="accom3"
								<c:if test="${param.rating == 3}">checked</c:if> />
							<label for="accom3">★</label>
						
							<input type="radio" name="rating" value="2" id="accom2"
								<c:if test="${param.rating == 2}">checked</c:if> />
							<label for="accom2">★</label>
						
							<input type="radio" name="rating" value="1" id="accom1"
								<c:if test="${param.rating == 1}">checked</c:if> />
							<label for="accom1">★</label>
						</div>
					</div>
				</div>
				<div style="margin-top: 1rem;">
					<label for="content">내용</label>
					<textarea name="content" id="content" required>${param.content}</textarea>
				</div>

				<div class="review-form-buttons">
					<button type="button" class="back-btn" onclick="history.back();">취소</button>
					<button type="reset" class="cancel-btn">비우기</button>
					<button type="submit" class="submit-btn">작성하기</button>
				</div>
			</form>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>