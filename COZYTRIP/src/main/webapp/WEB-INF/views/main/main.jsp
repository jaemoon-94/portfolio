<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CozyTrip - 국내 여행 숙박 예약</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
    <!-- 상단 네비게이션 바 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <!-- 메인 검색 섹션 -->
    <main>
        <div class="container">
            <section class="hero">
                <div class="hero-overlay"></div>
                <div class="hero-content">
                    <h1>특별한 여행을 위한 특별한 공간</h1>
                    <p>코지트립과 함께 잊지 못할 국내 여행을 계획해보세요</p>
                </div>
                <div class="search-container">
					<form class="search-form" action="${pageContext.request.contextPath}/accom/accomList.do" method="get" id="search_Form">
					    <div class="search-item">
					        <label>위치</label>
					        <input type="text" id="locationInput" placeholder="어디로 여행가세요?">
					        <input type="hidden" name="keyword" id="locationHidden">
					    </div>
					    <div class="search-item">
					        <label>체크인</label>
					        <input type="date" id="checkinInput">
					        <input type="hidden" name="check_in_date" id="checkinHidden">
					    </div>
					    <div class="search-item">
					        <label>체크아웃</label>
					        <input type="date" id="checkoutInput">
					        <input type="hidden" name="check_out_date" id="checkoutHidden">
					    </div>
					    <div class="search-item">
					        <label>인원</label>
					        <select id="guestsSelect">
					            <option value="1">성인 1명</option>
					            <option value="2">성인 2명</option>
					            <option value="3">성인 3명</option>
					            <option value="4">성인 4명 이상</option>
					        </select>
					        <input type="hidden" name="people_count" id="guestsHidden">
					    </div>
					    <button class="search-button" type="button" id="searchBtn">검색</button>
					</form>
                </div>
            </section>

            <!-- 숙소 카테고리 탭 -->
            <!-- 
            <section>
                <div class="category-tabs">
                    <a href="${pageContext.request.contextPath}/accom/accomList.do" class="category-tab active">
                        <img src="${pageContext.request.contextPath}/images/accomcategory/all.png" alt="전체">
                        <span>전체</span>
                    </a>

                    <a href="#" class="category-tab">
                        <img src="${pageContext.request.contextPath}/images/accomcategory/pension.png" alt="펜션">
                        <span>펜션</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="${pageContext.request.contextPath}/images/accomcategory/hanok.png" alt="한옥">
                        <span>한옥</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="${pageContext.request.contextPath}/images/accomcategory/guesthouse.png" alt="게스트하우스">
                        <span>게스트하우스</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="${pageContext.request.contextPath}/images/accomcategory/hotel.png" alt="호텔">
                        <span>호텔</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="${pageContext.request.contextPath}/images/accomcategory/etc.png" alt="기타">
                        <span>기타</span>
                    </a>
                    

                </div>
            </section>
             -->

   			    <!-- 지역별 바로가기 -->
				<section class="regions">
				    <div class="section-header">
				        <h2 class="section-title">지역별 숙소 찾기</h2>
				        <a href="${pageContext.request.contextPath}/accom/accomList.do" class="view-all">전체보기</a>
				    </div>
				    <div class="region-grid">
				        <a href="${pageContext.request.contextPath}/accom/accomList.do?region_num=1">
				        <div class="region-card">
				            <img src="${pageContext.request.contextPath}/images/region/seoul.jpg" alt="수도권">
				            <div class="region-info">
				                <h3>서울, 경기도</h3>
				                <p>${seoulCount}개의 숙소</p>
				            </div>
				        </div>
				        </a>
				
						<a href="${pageContext.request.contextPath}/accom/accomList.do?region_num=2">
				        <div class="region-card">
				            <img src="${pageContext.request.contextPath}/images/region/gangwon.jpg" alt="강원도">
				            <div class="region-info">
				                <h3>강원도</h3>
				                <p>${gangwonCount}개의 숙소</p>
				            </div>
				        </div>
						</a>
						
						<a href="${pageContext.request.contextPath}/accom/accomList.do?region_num=3">
				        <div class="region-card">
				            <img src="${pageContext.request.contextPath}/images/region/daejeon.jpg" alt="충청도">
				            <div class="region-info">
				                <h3>충청도</h3>
				                <p>${chungcheongCount}개의 숙소</p>
				            </div>
				        </div>
						</a>
						
						<a href="${pageContext.request.contextPath}/accom/accomList.do?region_num=4">
				        <div class="region-card">
				            <img src="${pageContext.request.contextPath}/images/region/busan.jpg" alt="경상도">
				            <div class="region-info">
				                <h3>경상도</h3>
				                <p>${gyeongsangCount}개의 숙소</p>
				            </div>
				        </div> 
						</a>
				
						<a href="${pageContext.request.contextPath}/accom/accomList.do?region_num=5">
				        <div class="region-card">
				            <img src="${pageContext.request.contextPath}/images/region/gwangju.jpg" alt="전라도">
				            <div class="region-info">
				                <h3>전라도</h3>
				                <p>${jeollaCount}개의 숙소</p>
				            </div>
				        </div>
						</a>
						
						<a href="${pageContext.request.contextPath}/accom/accomList.do?region_num=6">
				        <div class="region-card">
				            <img src="${pageContext.request.contextPath}/images/region/jeju.jpg" alt="제주도">
				            <div class="region-info">
				                <h3>제주도</h3>
				                <p>${jejuCount}개의 숙소</p>
				            </div>
				        </div>
						</a>
					
						<a href="${pageContext.request.contextPath}/accom/accomList.do?region_num=7">
				        <div class="region-card">
				            <img src="${pageContext.request.contextPath}/images/region/guitar.jpg" alt="기타">
				            <div class="region-info">
							     <h3>기타</h3>
				                <!-- <p>0개의 숙소</p> -->
				            </div>
				        </div>
						</a>
				    </div>
				</section>
				
             <!-- 동행 찾기 섹션 -->
            <section class="find-travelgroup">
                <div class="travelgroup">
                    <h2>동행을 찾아보세요</h2>
                    <p>같은 목적지, 더 특별한 여정 – 동행을 만나보세요.</p>
                    <a href="${pageContext.request.contextPath}/travelgroup/list.do" class="travel-group">동행 게시판 바로가기</a>
                </div>
                <div class="host-image"></div>
            </section>
            
            <!-- 어트랙션 섹션 -->
            <section class="attraction-section">
                <div class="attraction-box">
                    <h2>지역 별 관광지를 둘러보세요!</h2>
                    <p>지금, 어디로 떠나볼까요?<br>
                    지역별 인기 관광지를 한눈에!<br>
                    가보고 싶은 여행지를 찾아보세요</p>
                    <a href="${pageContext.request.contextPath}/user/attractionList.do" class="attraction-link">관광지 보러가기</a>
                </div>
            </section>
            
            <!-- 호스트 되기 섹션 -->
            <section class="become-host">
                <div class="host-content">
                    <h2>당신의 공간을 공유해보세요</h2>
                    <p>코지트립의 호스트가 되어 수익을 창출하고 여행자들에게 특별한 경험을 선사하세요. 귀하의 공간이 여행자에게 새로운 추억이 될 수 있습니다.</p>
                    <a href="#" class="host-cta">호스트 되기</a>
                </div>
                <div class="host-image"></div>
            </section>
            
            
           
            
            <!-- 공지사항 섹션 -->
            <section class="notices">
                <div class="section-header">
                    <h2 class="section-title">공지사항</h2>
                    <a href="#" class="view-all">더보기</a>
                </div>
                <div class="notice-list">
                    <div class="notice-item">
                        <div class="notice-title">코지트립 서비스 이용약관 개정 안내</div>
                        <div class="notice-date">2025.04.15</div>
                    </div>
                    <div class="notice-item">
                        <div class="notice-title">2025년 여름 성수기 특별 프로모션 안내</div>
                        <div class="notice-date">2025.04.10</div>
                    </div>
                    <div class="notice-item">
                        <div class="notice-title">호스트 등록 시 주의사항 안내</div>
                        <div class="notice-date">2025.04.01</div>
                    </div>
          
                </div>
            </section>
        </div>
    </main>
    
    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    <script>
$(function() {
	
    // 오늘 날짜 가져오기 (yyyy-mm-dd)
    function getToday() {
        let today = new Date();
        return today.toISOString().split("T")[0];
    }

    // 체크인 초기 설정 (오늘부터 선택 가능)
    let today = getToday();
    $('#checkinInput').attr('min', today);

    // 체크인 날짜 바뀌면 체크아웃 min도 그 다음날로 설정
    $('#checkinInput').on('change', function() {
        let checkinDate = new Date($(this).val());
        checkinDate.setDate(checkinDate.getDate() + 1);

        let nextDay = checkinDate.toISOString().split("T")[0];
        $('#checkoutInput').val(""); // 이전 체크아웃 값 초기화
        $('#checkoutInput').attr('min', nextDay);
    });
	
    $('#search_Form').on('submit', function(e) {
        e.preventDefault(); // 기본 제출 막기
    });

    $('#searchBtn').click(function() {
        let location = $('#locationInput').val();
        let checkin = $('#checkinInput').val();
        let checkout = $('#checkoutInput').val();
        let guests = $('#guestsSelect').val();

        $('#locationHidden').val(location);
        $('#checkinHidden').val(checkin);
        $('#checkoutHidden').val(checkout);
        $('#guestsHidden').val(guests);

        this.form.submit();  // JS로 submit 처리
    });
    
    $('#locationInput').on('keydown', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault(); // 엔터 누르면 submit 안 되게
            $('#searchBtn').click(); // 대신 버튼 클릭과 동일하게 처리
        }
    });
});
</script>
</body>
</html>