<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> 마이페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/FH.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
    <style>
    
    </style>
    
</head>
<body>
    <!-- 상단 네비게이션 바 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <main>
        <div class="container">
            <div class="mypage-header">
                <h1>마이 페이지</h1>
            </div>
            
            <div class="mypage-menu-grid">
                <!-- 회원 정보 수정 -->
                <div class="menu-item">
                    <div class="menu-icon">
                        <img src="${pageContext.request.contextPath}/images/icon/usermodify.jpg" alt="정보수정">
                    </div>
                    <a href="${pageContext.request.contextPath}/user/updateForm.do" class="menu-link">
                        회원 정보 수정
                    </a>
                </div>
                
                <!-- 예약 목록 조회 -->
                <div class="menu-item">
                    <div class="menu-icon">
                        <img src="${pageContext.request.contextPath}/images/icon/booking2.jpg" alt="예약목록">
                    </div>
                    <a href="${pageContext.request.contextPath}/reserv/list.do" class="menu-link">
                        예약 목록 조회
                    </a>
                </div>
                
                <!-- 위시리스트 -->
                <div class="menu-item">
                    <div class="menu-icon">
                        <img src="${pageContext.request.contextPath}/images/icon/wishlist.jpg" alt="위시리스트">
                    </div>
                    <a href="${pageContext.request.contextPath}/wishlist/list.do" class="menu-link">
                        위시리스트
                    </a>
                </div>
                
                <!-- 내 작성 리뷰 -->
                <div class="menu-item">
                    <div class="menu-icon">
                        <img src="${pageContext.request.contextPath}/images/icon/review.jpg" alt="작성리뷰">
                    </div>
                    <a href="${pageContext.request.contextPath}/review/myList.do" class="menu-link">
                        내 작성 리뷰
                    </a>
                </div>
                
                <!-- 메시지/채팅 -->
                <div class="menu-item">
                    <div class="menu-icon">
                        <img src="${pageContext.request.contextPath}/images/icon/inquiry.png" alt="문의">
                    </div>
                    <a href="${pageContext.request.contextPath}/user/myPage/inquiry.do" class="menu-link">
                        QnA
                    </a>
                </div>
                <div class="menu-item">
                    <div class="menu-icon">
                        <img src="${pageContext.request.contextPath}/images/icon/chatting.jpg" alt="채팅">
                    </div>
                    <a href="${pageContext.request.contextPath}/user/myPage/chatting.do" class="menu-link">
                        호스트와 채팅
                    </a>
                </div>
                <!-- 내 동행 그룹 -->
                <div class="menu-item">
                    <div class="menu-icon">
                        <img src="${pageContext.request.contextPath}/images/icon/group.jpg" alt="동행그룹">
                    </div>
                    <a href="${pageContext.request.contextPath}/travelgroup/myList.do" class="menu-link">
                        내 동행 그룹
                    </a>
                </div>
                <!-- 내 신고 목록 -->
                <div class="menu-item">
                    <div class="menu-icon">
                        <img src="${pageContext.request.contextPath}/images/icon/report.png" alt="신고목록">
                    </div>
                    <a href="${pageContext.request.contextPath}/report/reportList.do" class="menu-link">
                        내 신고 목록
                    </a>
                </div>
            </div>
            
            <div class="mypage-divider"></div>
        </div>
    </main>
    <script>
		$(document).ready(function() {
		    // Q&A 항목 클릭 시 내용 토글
		    $('.qa-header').on('click', function() {
		        // 현재 클릭한 요소의 부모(qa-item)를 찾아서 active 클래스 토글
		        const parent = $(this).parent();
		        
		        // 이미 열려있는 항목인지 확인
		        const isActive = parent.hasClass('active');
		        
		        // 모든 항목을 닫음
		        $('.qa-item').removeClass('active');
		        $('.qa-content').slideUp(300);
		        
		        // 닫혀있던 항목이라면 열기
		        if (!isActive) {
		            parent.addClass('active');
		            parent.find('.qa-content').slideDown(300);
		        }
		    });
		    // 드롭다운 메뉴 기능 (실제 구현 시 추가 가능)
		    $('.dropdown-button').on('click', function(e) {
		        e.stopPropagation();
		        // 여기에 드롭다운 메뉴 표시 코드 추가
		    });
		    
		    // 문서 클릭 시 드롭다운 메뉴 닫기
		    $(document).on('click', function() {
		        // 드롭다운 메뉴 닫기 코드 추가
		    });
		});
		</script>
    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>