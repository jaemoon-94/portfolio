<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 리뷰 보기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/jm.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/review.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
	
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
<div class="review-container">
	
  <!-- 리뷰 작성 가능한 숙소 목록 -->
 <div class="review-title">작성 가능한 리뷰</div>
<br>
<c:choose>
  <c:when test="${empty reviewableList}">
    <p class="no-review">작성 가능한 리뷰가 없습니다.</p>
  </c:when>
  <c:otherwise>
    <div class="review-list-wrap">
      <c:forEach var="review" items="${reviewableList}">
        <form action="writeReviewForm.do" method="post" class="review-card">
          <div class="review-image-box">
            <c:choose>
              <c:when test="${not empty review.image_name}">
                <img src="${pageContext.request.contextPath}/images/accomimage/${review.image_name}" alt="숙소 이미지">
              </c:when>
              <c:otherwise>
                <div class="no-img"></div>
              </c:otherwise>
            </c:choose>
          </div>
			<div class="review-info-box">
			  <div class="accom-name">${review.accom_name}</div>
			  <div class="review-dates">
			    ${review.check_in_date} ~ ${review.check_out_date}
			  </div>
			  <input type="hidden" name="accom_num" value="${review.accom_num}">
			  <input type="hidden" name="image_name" value="${review.image_name}">
			  <input type="hidden" name="accom_name" value="${review.accom_name}">
			  <input type="hidden" name="reserv_num" value="${review.reserv_num}">
			  <button type="submit" class="write-btn">리뷰 작성</button>
			</div>
        </form>
      </c:forEach>
    </div>
  </c:otherwise>
</c:choose>
  <br>

  <div class="review-title">내가 쓴 리뷰</div>

  <c:choose>
    <c:when test="${count == 0}">
      <div class="review-card">
        <div class="review-image-box">
          <div class="no-img"></div>
        </div>
        <div class="review-info-box">
          <div class="no-review">아직 작성한 리뷰가 없습니다.</div>
        </div>
      </div>
    </c:when>
	<c:otherwise>
	  <div class="review-list-wrap">
	    <c:forEach var="review" items="${list}">
	      <div class="review-card">
	        <div class="review-image-box">
	        	<img src="${pageContext.request.contextPath}/images/accomimage/${review.image_name}" alt="숙소 이미지">
	        </div>
	        <div class="review-info-box">
	          <div class="accom-name">
			    ${review.accom_name}
			    <c:forEach begin="1" end="${review.rating}" var="i">
			        <span class="star">★</span>
			    </c:forEach>
	          </div>
	          <div class="review-snippet">
	            ${fn:substring(review.content, 0, 180)}
	          </div>
				<div class="button-group">
				<form action="updateReviewForm.do" method="post">
				  <button type="submit" class="review-btn update" data-reviewnum="${review.review_num}">수정</button>
				  <input type="hidden" name="image_name" value="${review.image_name}">
				  <input type="hidden" name="accom_name" value="${review.accom_name}">
				  <input type="hidden" name="review_num" value="${review.review_num}">
				  <input type="hidden" name="content" value="${review.content}">
				  <input type="hidden" name="rating" value="${review.rating}">
				</form>
				  <button class="review-btn delete" onclick="deleteReview(${review.review_num})">삭제</button>
				</div>
	        </div>
				<div class="review-date">
				<c:if test="${review.review_modifydate == null}">
					작성 : ${review.review_date}
				</c:if>
				<c:if test="${review.review_modifydate != null}">
					수정 : ${review.review_modifydate}
				</c:if>
				</div>
	      </div>
	    </c:forEach>
	  </div>
	</c:otherwise>
  </c:choose>
</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	
	
	<script type="text/javascript">
  function deleteReview(review_num) {
    if (confirm('리뷰를 삭제하시겠습니까?')) {
      location.href = 'deleteReview.do?review_num=' + review_num;
    }
  }
</script>

</body>
</html>



























