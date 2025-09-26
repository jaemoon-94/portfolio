<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${accom.accom_name} - CozyTrip</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jm/jm.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/accom.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<section class="accom-detail">
    <div class="detail-header">
        <div>
            <h2 class="detail-title">${accom.accom_name}</h2>
            <p class="detail-location">${accom.address1}</p>
        </div>
        <div>
            <img src="${pageContext.request.contextPath}/images/nowish.png" width="12">
        </div>
    </div>

    <div class="detail-wrap">
        <div class="detail-images">
            <!-- 메인 이미지 -->
            <c:forEach var="image" items="${imageList}">
                <c:if test="${image.main == 1}">
                    <img src="${pageContext.request.contextPath}/images/accomimage/${image.image_name}" alt="대표사진" class="main-image">
                </c:if>
            </c:forEach>
            <!-- 서브 이미지: 메인 이미지 아래 2열 그리드 -->
            <div class="sub-images-grid">
                <c:forEach var="image" items="${imageList}">
                    <c:if test="${image.main == 0}">
                        <img src="${pageContext.request.contextPath}/images/accomimage/${image.image_name}" alt="서브 이미지">
                    </c:if>
                </c:forEach>
            </div>
        </div>

        <div class="detail-info-wrap">
            <!-- 예약 폼 -->
            <form id="reservationForm"  method="get" class="detail-price-box"
                  style="border: 1px solid #ddd; border-radius: 12px; padding: 20px; width: 300px;">
                <input type="hidden" name="accom_num" value="${accom.accom_num}" />
                <input type="hidden" name="total_price" id="calculatedTotalPrice" value="0" />

                <h3 style="font-size: 24px; font-weight: bold; margin-top: -10px;">
                    <fmt:formatNumber value="${accom.price}"/>원
                </h3>

                <div style="border: 1px solid #ccc; border-radius: 10px; padding: 10px; margin-top: 1rem;">
                    <div style="display: flex; justify-content: space-between; margin-bottom: 0.5rem;">
                        <div class="search-item" style="font-size: 12px; color: #888;">
                            <label>체크인</label>
                            <c:if test="${param.check_in_date == null}"><input type="date" id="checkin" name="check_in_date" required></c:if>
                            <c:if test="${param.check_in_date!=null}"><input type="date" id="checkin" name="check_in_date" value="${param.check_in_date}" required></c:if>
                        </div>
                        <div class="search-item" style="font-size: 12px; color: #888;">
                            <label>체크아웃</label>
                            <c:if test="${param.check_out_date == null}"><input type="date" id="checkout" name="check_out_date" required></c:if>
                            <c:if test="${param.check_out_date != null}"><input type="date" id="checkout" name="check_out_date" value="${param.check_out_date}" required></c:if>
                        </div>
                    </div>

                    <div style="display: flex; justify-content: space-between; align-items: center;">
                        <div style="font-size: 14px;">인원</div>
                        <div style="display: flex; align-items: center;">
                            <button type="button" id="minusBtn" style="border: none; background: none; font-size: 20px;">−</button>
                            <span id="guestCount" style="margin: 0 10px;">1</span>
                            <input type="hidden" name="people_count" id="guestInput" value="1">
                            <button type="button" id="plusBtn" style="border: none; background: none; font-size: 20px;">＋</button>
                        </div>
                    </div>
                </div>

                <!-- accomPrice 값을 읽기 위해 data 속성에 저장 -->
                <div style="display:none;">
                    <span id="nightInfoText" data-price="${accom.price}"><fmt:formatNumber value="${accom.price}"/>원 × 박</span>
                </div>

                <button type="button" class="reserve-button" style="width: 100%; margin-top: 1rem; background-color: #ff8c8c; border: none; padding: 12px; border-radius: 8px; color: white; font-size: 16px;">
                    예약하기
                </button>

                <span id="nightInfo" data-price="${accom.price}" style="display: none;"></span>

                <div style="margin-top: 1rem; font-size: 14px;">
                    <div style="display: flex; justify-content: space-between;">
                        <span id="nightInfoText"><fmt:formatNumber value="${accom.price}"/>원 × 박</span>
                        <span id="nightPrice">0원</span>
                    </div>
                    <div style="border-top: 1px solid #ccc; margin-top: 0.5rem; padding-top: 0.5rem; display: flex; justify-content: space-between; font-weight: bold;">
                        <span>총액</span>
                        <span id="totalPrice">0원</span>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="detail-info">
        <div class="detail-category">카테고리: ${accom.cate_name}</div>
        <div class="detail-description">${accom.description}</div>

        <div class="host-info">
            <h4>호스트: ${accom.user_name} 님</h4>
            <div class="host-intro">${accom.host_intro}</div>
        </div>
        
        <div style="margin-top:15px;">
	        <h3>숙소 위치</h3>
	        <div id="map" style="width:100%;height:350px;" data-address="${accom.address1}"></div>
        </div>
        <h3 style="margin:15px 0 15px 0;">숙소 근처 어트랙션</h3>
        	<div class="photo-grid">
	            <c:forEach var="attList" items="${attList}">
	                <div class="photo-box" data-attraction-num="${attList.attraction_num}" data-name="${attList.name}" 
	                data-photo="${attList.image_url}" data-description="${attList.description}"
	                data-address1="${attList.address1}" data-address2="${attList.address2}">
	                    <div class="photo-container">
	                        <img src="${pageContext.request.contextPath}/images/accomimage/${attList.image_url}" alt="${attList.name}">
	                    </div>
	                    <div class="name-container">
	                        <p>${attList.name}</p>
	                    </div>
	                </div>
	            </c:forEach>
    		</div>
    </div>
</section>
<!--문의 작성 모달 -->
	<div class="modal-overlay" id="inquiryModal">
        <div class="modal">
            <div class="modal-header">
                <h3 class="modal-title">숙소 문의 작성하기</h3>
                <button class="close-btn" id="closeModalBtn">&times;</button>
            </div>
            <form action="insertInquiry.do" method="post">
            <div class="modal-body">
            		<input type="hidden" name="accom_num" value="${accom.accom_num}" >
                    <div class="form-group">
                        <label for="message">문의 내용</label>
                        <textarea id="message" name="inq_content" class="form-control" required></textarea>
                    </div>
                    비밀글 <input type="checkbox" name="is_secret" value="1">
            </div>
            <div class="modal-footer">
                <button type="button" class="cancel-btn" id="cancelBtn">취소</button>
                <button type="submit" class="submit-btn" id="submitBtn">문의 제출</button>
            </div>
            </form>
        </div>
    </div>
	
	<!--어트랙션 모달 -->
	<div class="modal" id="photoModal">
        <div class="modal-content">
            <span class="close-button">&times;</span>
            <div class="modal-body">
                <div class="modal-photo">
                    <img id="modalImage" src="" alt="사진">
                </div>
                <div class="modal-info">
                    <h2 id="modalName"></h2>
                    <div id="modalDescription" class="description"></div>
                    <div style="height:70px"></div>
                    <div id="modalAttrMap" style="width:100%;height:200px;"></div>
                    <div id="modalAddress" class="description"></div>
                </div>
            </div>
        </div>
    </div>

<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=4b67bd8143aedb26a6bca378a93d93f2&libraries=services"></script>
<script>
function initMapInModal(address) {
    var mapContainer = document.getElementById('modalAttrMap');
    mapContainer.innerHTML = "";

    var mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
    };

    var map = new kakao.maps.Map(mapContainer, mapOption);
    var geocoder = new kakao.maps.services.Geocoder();

    geocoder.addressSearch(address, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

            var infowindow = new kakao.maps.InfoWindow({
                content: '<div style="width:150px;text-align:center;padding:6px 0;">어트랙션</div>'
            });
            infowindow.open(map, marker);
            setTimeout(function() {
                map.relayout(); // 지도 영역을 다시 계산
                map.setCenter(coords); // 주소 좌표로 중심 이동
            }, 350); // 모달 fadeIn(300) 이후 약간의 여유 시간
        } else {
            mapContainer.innerHTML = "<p>지도를 불러올 수 없습니다.</p>";
        }
    });
}
$(document).ready(function() {
    // 모달 요소
    const modal = $("#photoModal");
    const modalImage = $("#modalImage");
    const imagePath= '${pageContext.request.contextPath}/images/accomimage/';
    const modalName = $("#modalName");
    const modalDescription = $("#modalDescription");
    const modalAddress = $("#modalAddress");
    const closeButton = $(".close-button");
    
    // 각 사진 박스를 클릭했을 때 모달 열기
    $(".photo-box").on("click", function() {
        // 데이터 속성에서 정보 가져오기
        const id = $(this).data("id");
        const name = $(this).data("name");
        const photo = imagePath + $(this).data("photo");
        const description = $(this).data("description");
        const address = $(this).data("address1")+$(this).data("address2");
        // 모달에 정보 설정
        modalImage.attr("src", photo);
        modalImage.attr("alt", name);
        modalName.text(name);
        modalDescription.text(description);
        modalAddress.text(address);
        initMapInModal(address);
        // 모달 표시
        modal.fadeIn(300);
        
        // 스크롤 방지
        $("body").css("overflow", "hidden");
    });
    
    // 닫기 버튼 클릭 시 모달 닫기
    closeButton.on("click", function() {
        closeModal();
    });
    
    // 모달 외부 클릭 시 모달 닫기
    $(window).on("click", function(event) {
        if (event.target === modal[0]) {
            closeModal();
        }
    });
    
    // ESC 키 누를 때 모달 닫기
    $(document).on("keydown", function(event) {
        if (event.key === "Escape") {
            closeModal();
        }
    });
    
    // 모달 닫기 함수
    function closeModal() {
        modal.fadeOut(300);
        $("body").css("overflow", "auto");
    }
    
    // 데이터 속성이 없는 경우 에러 방지
    $(".photo-box").each(function() {
        if (!$(this).data("description")) {
            $(this).attr("data-description", "");
        }
    });
});

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
var mapContainer = document.getElementById('map');
var address = mapContainer.getAttribute('data-address').replace(/'/g, "\\'");

var mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
};

var map = new kakao.maps.Map(mapContainer, mapOption);
var geocoder = new kakao.maps.services.Geocoder();

geocoder.addressSearch(address, function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });

        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width:150px;text-align:center;padding:6px 0;">숙소 위치</div>'
        });
        infowindow.open(map, marker);
        map.setCenter(coords);
    }
});   
$(function () {
// data-price 속성에서 가격을 가져옴
const accomPrice = parseInt($('#nightInfo').data('price'));

function formatNumber(num) {
    return num.toLocaleString() + '원';
}

function calculateNights() {
    const checkin = $('#checkin').val();
    const checkout = $('#checkout').val();

    if (checkin && checkout) {
        const checkinDate = new Date(checkin);
        const checkoutDate = new Date(checkout);

        // 날짜 차이를 밀리초로 계산 후, 날짜 수로 변환
        const timeDiff = checkoutDate.getTime() - checkinDate.getTime();
        const nights = timeDiff / (1000 * 3600 * 24);

        // 최소 1박 이상 예약해야 함
        if (nights < 1) {
            alert('최소 1박 이상 예약하셔야 합니다.');
            $('#checkout').val('');
            $('#nightInfoText').text(`${accomPrice.toLocaleString()}원 × 박`);
            $('#nightPrice').text('0원');
            $('#totalPrice').text('0원');
            $('#calculatedTotalPrice').val(0);
            return;
        }

        const totalPrice = accomPrice * nights;
        
        // 디버깅: calculateNights 함수에서 nights와 accomPrice 값 확인
        console.log('accomPrice:', accomPrice);
        console.log('nights:', nights);

        // 박 수 업데이트
        $('#nightInfoText').text(`${accomPrice.toLocaleString()}원 × ${nights}박`);
        $('#nightPrice').text(formatNumber(totalPrice));
        $('#totalPrice').text(formatNumber(totalPrice));
        $('#calculatedTotalPrice').val(totalPrice);
    }
}

function setMinDate() {
    const today = new Date();
    today.setDate(today.getDate() + 1);
    const minCheckin = today.toISOString().split('T')[0];
    $('#checkin').attr('min', minCheckin);

    $('#checkin').on('change', function () {
        const checkinDate = new Date(this.value);
        checkinDate.setDate(checkinDate.getDate() + 1);
        const minCheckout = checkinDate.toISOString().split('T')[0];
        $('#checkout').attr('min', minCheckout);
        $('#checkout').val('');
        calculateNights();
    });
}

let guestCount = 1;

$('#plusBtn').click(function () {
    if (guestCount < 10) {
        guestCount++;
        $('#guestCount').text(guestCount);
        $('#guestInput').val(guestCount);
    }
});

$('#minusBtn').click(function () {
    if (guestCount > 1) {
        guestCount--;
        $('#guestCount').text(guestCount);
        $('#guestInput').val(guestCount);
    }
});

$('#checkout').on('change', calculateNights);

// 실행
setMinDate();
});

</script>
    <!-- 모달 오버레이 -->
    
    
    <script>
        // 필요한 DOM 요소들 선택
        const openModalBtn = document.getElementById('openModalBtn');
        const closeModalBtn = document.getElementById('closeModalBtn');
        const cancelBtn = document.getElementById('cancelBtn');
        const submitBtn = document.getElementById('submitBtn');
        const modal = document.getElementById('inquiryModal');
        const inquiryForm = document.getElementById('inquiryForm');
        
        // 모달 열기
        openModalBtn.addEventListener('click', () => {
            modal.style.display = 'flex';
            document.body.style.overflow = 'hidden'; // 배경 스크롤 방지
        });
        
        // 모달 닫기 함수
        const closeModal = () => {
            modal.style.display = 'none';
            document.body.style.overflow = ''; // 배경 스크롤 복원
            inquiryForm.reset(); // 폼 초기화
        };
        
        // 닫기 버튼으로 모달 닫기
        closeModalBtn.addEventListener('click', closeModal);
        
        // 취소 버튼으로 모달 닫기
        cancelBtn.addEventListener('click', closeModal);
        
        // 배경 클릭으로 모달 닫기
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                closeModal();
            }
        });
        
        // ESC 키로 모달 닫기
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape' && modal.style.display === 'flex') {
                closeModal();
            }
        });
// =====================================================
	
	
$(function () {
    // data-price 속성에서 가격을 가져옴
    const accomPrice = parseInt($('#nightInfo').data('price'));

    function formatNumber(num) {
        return num.toLocaleString() + '원';
    }

    function calculateNights() {
        const checkin = $('#checkin').val();
        const checkout = $('#checkout').val();

        if (checkin && checkout) {
            const checkinDate = new Date(checkin);
            const checkoutDate = new Date(checkout);

            // 날짜 차이를 밀리초로 계산 후, 날짜 수로 변환
            const timeDiff = checkoutDate.getTime() - checkinDate.getTime();
            const nights = timeDiff / (1000 * 3600 * 24);

            // 최소 1박 이상 예약해야 함
            if (nights < 1) {
                alert('최소 1박 이상 예약하셔야 합니다.');
                $('#checkout').val('');
                $('#nightInfoText').text(`${accomPrice.toLocaleString()}원 × 박`);
                $('#nightPrice').text('0원');
                $('#totalPrice').text('0원');
                $('#calculatedTotalPrice').val(0);
                return;
            }

            const totalPrice = accomPrice * nights;
            
            // 디버깅: calculateNights 함수에서 nights와 accomPrice 값 확인
            console.log('accomPrice:', accomPrice);
            console.log('nights:', nights);

            // 박 수 업데이트
            $('#nightInfoText').text(`${accomPrice.toLocaleString()}원 × ${nights}박`);
            $('#nightPrice').text(formatNumber(totalPrice));
            $('#totalPrice').text(formatNumber(totalPrice));
            $('#calculatedTotalPrice').val(totalPrice);
        }
    }

    function setMinDate() {
        const today = new Date();
        today.setDate(today.getDate() + 1);
        const minCheckin = today.toISOString().split('T')[0];
        $('#checkin').attr('min', minCheckin);

        $('#checkin').on('change', function () {
            const checkinDate = new Date(this.value);
            checkinDate.setDate(checkinDate.getDate() + 1);
            const minCheckout = checkinDate.toISOString().split('T')[0];
            $('#checkout').attr('min', minCheckout);
            $('#checkout').val('');
            calculateNights();
        });
    }

    let guestCount = 1;

    $('#plusBtn').click(function () {
        if (guestCount < 10) {
            guestCount++;
            $('#guestCount').text(guestCount);
            $('#guestInput').val(guestCount);
        }
    });

    $('#minusBtn').click(function () {
        if (guestCount > 1) {
            guestCount--;
            $('#guestCount').text(guestCount);
            $('#guestInput').val(guestCount);
        }
    });

    $('#checkout').on('change', calculateNights);

    // 실행
    setMinDate();
});

</script>
</body>
</html>
