<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CozyTrip 호스트 - 숙소 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/yn.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <!-- 호스트 네비게이션 바 -->
    <jsp:include page="/WEB-INF/views/host/hostHeader.jsp"/>
	
    <!-- 메인 콘텐츠 -->
    <main style="margin-top:60px;">
		
        <div class="container">
        	<div style="width: 100%; margin: 20px 0 10px 0; text-align: right; padding-right: 15px;">
			    <button type="button" class="edit-btn"
			            onclick="location.href='${pageContext.request.contextPath}/host/registerAccomForm.do'"
			            style="padding: 8px 16px;">
			        새 숙소 등록
			    </button>
			</div>
        	
        	<c:if test="${empty myAccomList}">
            <div class="empty-state" style="display: none;">
                <div class="empty-state-icon">
                    <i class="fas fa-home"></i>
                </div>
                <h2 class="empty-state-title">등록된 숙소가 없습니다</h2>
                <p class="empty-state-desc">지금 바로 첫 번째 숙소를 등록하고 호스트 여정을 시작해보세요!</p>
            </div> 
			</c:if>
            <!-- 숙소 목록 -->
            <c:if test="${!empty myAccomList}">
            <div class="accommodation-list">
                <!-- 숙소 1 -->
                <c:forEach var="myAccomList" items="${myAccomList}">
                <div class="accommodation-card">
                    <div class="card-image">
                        <img src="${pageContext.request.contextPath}/images/accomimage/${myAccomList.imageList[0].image_name}" alt="숙소사진">
                        <c:choose>
						    <c:when test="${myAccomList.accom_status == 1}">
						        <div class="card-status status-pending">승인 전</div>
						    </c:when>
						    <c:when test="${myAccomList.accom_status == 2}">
						        <div class="card-status status-inactive">비활성</div>
						    </c:when>
						    <c:when test="${myAccomList.accom_status == 3}">
						        <div class="card-status status-active">활성</div>
						    </c:when>
						    <c:otherwise>
						        <div class="card-status status-unknown">알 수 없음</div>
						    </c:otherwise>
						</c:choose>
                    </div>
                    <div class="card-info">
                        <h3 class="card-title">${myAccomList.accom_name}</h3>
                        <div class="card-location">
                            <svg viewBox="0 0 24 24" width="16" height="16" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M21 10c0 7-9 13-9 13s-9-6-9-13a9 9 0 0 1 18 0z"></path>
                                <circle cx="12" cy="10" r="3"></circle>
                            </svg>
                            ${myAccomList.address1}
                        </div>
                        <div class="card-meta">
                            <div class="meta-item">
                                <svg viewBox="0 0 24 24" width="16" height="16" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                                    <circle cx="9" cy="7" r="4"></circle>
                                    <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                                    <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                                </svg>
                                최대 ${myAccomList.max_people} 명
                            </div>
                        </div>
                        <div class="card-stats">
                        	<div class="stat-item">
                                <div class="stat-value">${myAccomList.accom_date}</div>
                                <div class="stat-label">등록일</div>
                            </div>
                            <div class="stat-item">
                                <div class="stat-value card-price">₩ ${myAccomList.price}</div>
                                <div class="stat-label">1박 가격</div>
                            </div>
                        </div>
                        <div class="card-buttons">
                            <button class="edit-btn" onclick="location.href='${pageContext.request.contextPath}/host/modifyAccomForm.do?accom_num=${myAccomList.accom_num}'">편집하기</button>
                            <button class="preview-btn" onclick="openPopup(${myAccomList.accom_num})">미리보기</button>
                        </div>
                    </div>
                </div>
                </c:forEach>
            </div>
            </c:if>            
        </div>
    </main>
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // 새 숙소 등록 버튼
            const addBtn = document.querySelector('.add-btn');
            
            addBtn.addEventListener('click', function() {
                alert('새 숙소 등록 페이지로 이동합니다.');
            });
            
            // 카드 액션 버튼 이벤트
            const cardActionBtns = document.querySelectorAll('.card-action-btn');
            
            cardActionBtns.forEach(btn => {
                btn.addEventListener('click', function() {
                    const card = this.closest('.accommodation-card');
                    const title = card.querySelector('.card-title').textContent;
                    const action = this.getAttribute('title');
                    
                    if (action === '미리보기') {
                        alert(`'${title}' 숙소 미리보기 페이지로 이동합니다.`);
                    } else if (action === '편집') {
                        alert(`'${title}' 숙소 편집 페이지로 이동합니다.`);
                    }
                });
            });
            
            // 페이지네이션 이벤트
            const pageItems = document.querySelectorAll('.page-item');
            
            pageItems.forEach(item => {
                item.addEventListener('click', function() {
                    pageItems.forEach(p => p.classList.remove('active'));
                    this.classList.add('active');
                });
            });
        });
        function openPopup(accom_num) {
            window.open(
                '${pageContext.request.contextPath}/host/accomDetail.do?accom_num='+accom_num, // 팝업에 띄울 JSP 주소
                'popupWindow',                                      // 팝업 이름 (중복 방지용)
                'width=800,height=600,scrollbars=yes'              // 옵션: 크기, 스크롤 여부 등
            );
        }
    </script>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>