<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CozyTrip - 숙소 등록</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/yn.css">
</head>
<body>
    <!-- 상단 네비게이션 바 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <!-- 숙소 등록 메인 섹션 -->
    <main>
        <div class="container">
            <section class="register-header">
                <h1>숙소 등록하기</h1>
                <p>당신의 공간을 코지트립에 등록하고 수익을 창출하세요</p>
            </section>

            <section class="register-form-container">
                <div class="progress-bar">
                    <div class="progress-step active">
                        <div class="step-number">1</div>
                        <span>기본 정보</span>
                    </div>
                    <div class="progress-line"></div>
                    <div class="progress-step">
                        <div class="step-number">2</div>
                        <span>사진 등록</span>
                    </div>
                    <div class="progress-line"></div>
                    <div class="progress-step">
                        <div class="step-number">3</div>
                        <span>위치 정보</span>
                    </div>
                    <div class="progress-line"></div>
                    <div class="progress-step">
                        <div class="step-number">4</div>
                        <span>요금 설정</span>
                    </div>
                </div>

                <form class="register-form" action="registerAccom.do" method="post" enctype="multipart/form-data">
                    <!-- 1단계: 기본 정보 -->
                    <div class="form-section active-section" id="section-1">
                        <h2>기본 정보 입력</h2>
                        <p class="section-desc">숙소에 대한 기본적인 정보를 알려주세요</p>
                        
                        <div class="form-group">
                            <label for="accommodation-name">숙소 이름</label>
                            <input type="text" name="accom_name" id="accommodation-name" placeholder="숙소 이름을 입력하세요" required>
                        </div>
                        
                        <div class="form-group">
						    <label for="accommodation-type">숙소 유형</label>
						    <select id="accommodation-type" name="cate_num" required>
						        <option value="" disabled selected>숙소 유형을 선택하세요</option>
						        <c:forEach var="cate" items="${cateList}">
						            <option value="${cate.cate_num}">${cate.cate_name}</option>
						        </c:forEach>
						    </select>
						</div>
						                        
                        <div class="form-group">
                            <label for="max-guests">최대 수용 인원</label>
                            <input type="number" oninput="onlyNumber()" name="max_people" id="max-guests" min="1" placeholder="최대 수용 인원" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="accommodation-desc">숙소 설명</label>
                            <textarea id="accommodation-desc" name="description" rows="5" placeholder="숙소에 대한 설명을 입력하세요" required></textarea>
                        </div>
                        <div class="nav-buttons">
                            <button type="button" class="next-btn" data-next="section-2">다음 단계</button>
                        </div>
                    </div>
                    
                    <!-- 2단계: 사진 등록 -->
                    <div class="form-section" id="section-2">
                        <h2>숙소 사진 등록</h2>
                        <p class="section-desc">고품질 사진은 예약률을 높이는 데 도움이 됩니다</p>
                        <br>
                        <h3>메인 사진 (1장, 목록에서 보여집니다.)</h3>
                        <div class="photo-upload-container">
                            <div class="main-photo-upload">
							    <div class="upload-placeholder" style="text-align: center;">
						        <input type="file" name="main_image" id="main-photo" accept="image/*" style="margin-left: 50px;" required>
						        <div id="main-preview-container" style="display: flex; justify-content: center; align-items: center; margin-top: 10px;">
						            <img id="main-preview" style="max-width: 200px; display: none; border-radius: 10px;">
						        </div>
						    </div>
							</div>
                            
                            <div class="additional-photos">
                                <h3>추가 사진</h3>
                                <div class="photo-grid" style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 10px;">
								    <c:forEach var="i" begin="1" end="9">
								        <div class="photo-item" style="position: relative; aspect-ratio: 1/1;">
								            <div class="photo-placeholder" style="width: 100%; height: 100%; border: 2px dashed #ccc; border-radius: 10px; display: flex; align-items: center; justify-content: center; flex-direction: column; overflow: hidden;">								             
								                <input type="file" name="sub_image${i}" accept="image/*" style="position: absolute; margin-left:50px; width: 80%;">
								                <img class="sub-preview" style="width: 100%; height: 100%; object-fit: cover; border-radius: 10px; display: none;">
								            </div>
								        </div>
								    </c:forEach>
								</div>

                            </div>
                        </div>
                        
                        <div class="photo-tips">
                            <h3>사진 촬영 팁</h3>
                            <ul>
                                <li>자연광이 있는 낮 시간에 촬영하세요</li>
                                <li>모든 객실과 주요 공간을 포함시키세요</li>
                                <li>공간을 정리한 후 촬영하세요</li>
                                <li>가로 방향으로 촬영하면 더 효과적입니다</li>
                            </ul>
                        </div>
                        
                        <div class="nav-buttons">
                            <button type="button" class="prev-btn" data-prev="section-1">이전 단계</button>
                            <button type="button" class="next-btn" data-next="section-3">다음 단계</button>
                        </div>
                    </div>
                    
                    <!-- 3단계: 위치 정보 -->
                    <div class="form-section" id="section-3">
                        <h2>숙소 위치 정보</h2>
                        <p class="section-desc">정확한 주소 정보를 입력해주세요</p>
                        <div class="form-group">
                            <label for="region">지역</label>
                            <select id="region" name="region_num" disabled required style="pointer-events: none; background-color: #eee;">
                                <option value="" disabled selected>우편번호를 선택하세요</option>
                                <c:forEach var="region" items="${regionList}">
						            <option value="${region.region_num}">${region.region_name}</option>
						        </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="address-postcode">우편번호</label>
                            <div class="postcode-input">
                                <input type="text" name="zipcode" id="address-postcode" placeholder="우편번호" readonly required>
                                <button type="button" class="find-postcode-btn" onclick="execDaumPostcode()">우편번호 찾기</button>
                            </div>
                        </div>
                        <div class="map-container">
                            <div id="map" style="width:710px;height:300px;margin-top:10px;display:none"></div>
                        </div>
                        <div class="form-group">
                            <label for="address-main">주소</label>
                            <input type="text" name="address1" id="address-main" placeholder="주소" readonly required>
                        </div>
                        
                        <div class="form-group">
                            <label for="address-detail">상세 주소</label>
                            <input type="text" name="address2" id="address-detail" placeholder="상세 주소 입력" required>
                        </div>
                        
                        <div class="nav-buttons">
                            <button type="button" class="prev-btn" data-prev="section-2">이전 단계</button>
                            <button type="button" class="next-btn" data-next="section-4">다음 단계</button>
                        </div>
                    </div>
                    
                    <!-- 4단계: 요금 설정 -->
                    <div class="form-section" id="section-4">
                        <h2>요금 및 예약 정책 설정</h2>
                        <p class="section-desc">숙소 요금과 예약 정책을 설정하세요</p>
                        
                        <div class="form-group">
                            <label for="base-price">기본 숙박 요금 (1박 기준)</label>
                            <div class="price-input">
                                <span class="currency-symbol">₩</span>
                                <input type="number" oninput="onlyNumber()" name="price" id="base-price" min="0" placeholder="금액 입력" required>
                                <span class="per-night">/ 박</span>
                            </div>
                        </div>
                        
                        <div class="form-summary">
                            <h3>예상 수입 계산</h3>
                            <div class="income-calculator">
                                <div class="income-row">
                                    <span>기본 요금</span>
                                    <span class="income-value">₩0</span>
                                </div>
                                <div class="income-row">
                                    <span>코지트립 수수료 (10%)</span>
                                    <span class="income-value">-₩0</span>
                                </div>
                                <div class="income-row total">
                                    <span>예상 월 수입 (월 20일 예약 기준)</span>
                                    <span class="income-value">₩0</span>
                                </div>
                            </div>
                            <p class="income-disclaimer">* 실제 수입은 예약 상황에 따라 달라질 수 있습니다.</p>
                        </div>
                        
                        <div class="nav-buttons">
                            <button type="button" class="prev-btn" data-prev="section-3">이전 단계</button>
                            <button type="submit" class="submit-btn">숙소 등록하기</button>
                        </div>
                    </div>
                </form>
            </section>
        </div>
    </main>
    </div>
</div>
<!-- 다음 우편번호 API 시작 -->
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4b67bd8143aedb26a6bca378a93d93f2&libraries=services"></script>


	<div id="layer" style="display:none;position:fixed;overflow:hidden;z-index:1;-webkit-overflow-scrolling:touch;">
	 <img src="//t1.daumcdn.net/postcode/resource/images/close.png" id="btnCloseLayer" style="cursor:pointer;position:absolute;right:-3px;top:-3px;z-index:1" onclick="closeDaumPostcode()" alt="닫기 버튼">
	 </div>
	 
	 <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	 <script>
	 var mapContainer = document.getElementById('map'), // 지도를 표시할 div
	     mapOption = {
	         center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
	         level: 4 // 지도의 확대 레벨
	     };
	
	 //지도를 미리 생성
	 var map = new daum.maps.Map(mapContainer, mapOption);
	 //주소-좌표 변환 객체를 생성
	 var geocoder = new daum.maps.services.Geocoder();
	 //마커를 미리 생성
	 var marker = new daum.maps.Marker({
	     position: new daum.maps.LatLng(37.537187, 127.005476),
	     map: map
	 });
	 
	 function getRegionByPostcode(postcode) {
		    var firstTwoDigits = parseInt(postcode.substring(0, 2));  // 우편번호 처음 두 자리를 가져옵니다.

		    // 지역 구분
		    if (firstTwoDigits >= 0 && firstTwoDigits <= 23) {
		        return "수도권";
		    } else if (firstTwoDigits >= 24 && firstTwoDigits <= 26) {
		        return "강원권";
		    } else if (firstTwoDigits >= 27 && firstTwoDigits <= 35) {
		        return "충청권";
		    } else if (firstTwoDigits >= 36 && firstTwoDigits <= 53) {
		        return "영남권";
		    } else if (firstTwoDigits >= 54 && firstTwoDigits <= 62) {
		        return "호남권";
		    } else if (firstTwoDigits === 63) {
		        return "제주";
		    } else {
		        return "기타";  // 63 외의 우편번호는 "기타"로 처리
		    }
		}
	     // 우편번호 찾기 화면을 넣을 element
	     var element_layer = document.getElementById('layer');
	 
	     function closeDaumPostcode() {
	         // iframe을 넣은 element를 안보이게 한다.
	         element_layer.style.display = 'none';
	     }
	 
	     function execDaumPostcode() {
	         new daum.Postcode({
	             oncomplete: function(data) {
	                 // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	 
	                 // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                 // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                 var addr = ''; // 주소 변수
	                 var extraAddr = ''; // 참고항목 변수
	 
	                 //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                 if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                     addr = data.roadAddress;
	                 } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                     addr = data.jibunAddress;
	                 }
	 
	                 // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	                 if(data.userSelectedType === 'R'){
	                     // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                     // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                     if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                         extraAddr += data.bname;
	                     }
	                     // 건물명이 있고, 공동주택일 경우 추가한다.
	                     if(data.buildingName !== '' && data.apartment === 'Y'){
	                         extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                     }
	                     // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                     if(extraAddr !== ''){
	                         extraAddr = ' (' + extraAddr + ')';
	                     }
	                     //(주의)address1에 참고항목이 보여지도록 수정
	                     // 조합된 참고항목을 해당 필드에 넣는다.
	                     //(수정) document.getElementById("address2").value = extraAddr;
	                 
	                 } 
	                 //(수정) else {
	                 //(수정)    document.getElementById("address2").value = '';
	                 //(수정) }
	 
	                 // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                 document.getElementById('address-postcode').value = data.zonecode;
	                 //(수정) + extraAddr를 추가해서 address1에 참고항목이 보여지도록 수정
	                 document.getElementById("address-main").value = addr + extraAddr;
	                 // 커서를 상세주소 필드로 이동한다.
	                 document.getElementById("address-detail").focus();
	 
	                 // iframe을 넣은 element를 안보이게 한다.
	                 // (autoClose:false 기능을 이용한다면, 아래 코드를 제거해야 화면에서 사라지지 않는다.)
	                 element_layer.style.display = 'none';
	                 
	                 var region = getRegionByPostcode(data.zonecode);
	                 var regionSelect = document.getElementById("region");
	                 regionSelect.disabled = false;
	                 for (var i = 0; i < regionSelect.options.length; i++) {
	                     if (regionSelect.options[i].text === region) {
	                         regionSelect.selectedIndex = i;
	                         break;
	                     }
	                 }
	                 geocoder.addressSearch(data.address, function(results, status) {
	                     // 정상적으로 검색이 완료됐으면
	                     if (status === daum.maps.services.Status.OK) {

	                         var result = results[0]; //첫번째 결과의 값을 활용

	                         // 해당 주소에 대한 좌표를 받아서
	                         var coords = new daum.maps.LatLng(result.y, result.x);
	                         // 지도를 보여준다.
	                         mapContainer.style.display = "block";
	                         map.relayout();
	                         // 지도 중심을 변경한다.
	                         map.setCenter(coords);
	                         // 마커를 결과값으로 받은 위치로 옮긴다.
	                         marker.setPosition(coords)
	                     }
	                 });
	             },
	             width : '100%',
	             maxSuggestItems : 5
	         }).embed(element_layer);
	 
	         // iframe을 넣은 element를 보이게 한다.
	         element_layer.style.display = 'block';
	 
	         // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
	         initLayerPosition();
	     }
	 
	     // 브라우저의 크기 변경에 따라 레이어를 가운데로 이동시키고자 하실때에는
	     // resize이벤트나, orientationchange이벤트를 이용하여 값이 변경될때마다 아래 함수를 실행 시켜 주시거나,
	     // 직접 element_layer의 top,left값을 수정해 주시면 됩니다.
	     function initLayerPosition(){
	         var width = 300; //우편번호서비스가 들어갈 element의 width
	         var height = 400; //우편번호서비스가 들어갈 element의 height
	         var borderWidth = 5; //샘플에서 사용하는 border의 두께
	 
	         // 위에서 선언한 값들을 실제 element에 넣는다.
	         element_layer.style.width = width + 'px';
	         element_layer.style.height = height + 'px';
	         element_layer.style.border = borderWidth + 'px solid';
	         // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
	         element_layer.style.left = (((window.innerWidth || document.documentElement.clientWidth) - width)/2 - borderWidth) + 'px';
	         element_layer.style.top = (((window.innerHeight || document.documentElement.clientHeight) - height)/2 - borderWidth) + 'px';
	     }
	
</script>



<!-- 다음 우편번호 API 끝 -->	
    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>

    <script>
	    function onlyNumber() {
	        console.log(event.type, event.target.value);
	        const regex = /\D/g; // 숫자가 아닌 모든 문자를 찾아라. 
	        event.target.value = event.target.value.replace(regex, "");
	      }
        // 단계별 폼 전환 스크립트
        document.addEventListener('DOMContentLoaded', function() {
            const nextButtons = document.querySelectorAll('.next-btn');
            const prevButtons = document.querySelectorAll('.prev-btn');
            const formSections = document.querySelectorAll('.form-section');
            const progressSteps = document.querySelectorAll('.progress-step');
            
            // 다음 단계 버튼 이벤트
            nextButtons.forEach(button => {
                button.addEventListener('click', function() {
                	const currentSection = this.closest('.form-section');
                    const inputs = currentSection.querySelectorAll('input[required], textarea[required], select[required]');
                    let isValid = true;
                    inputs.forEach(input => {
                        if (!input.value.trim()) {
                            isValid = false;
                        }
                    });

                    if (!isValid) {
                        alert('모든 필수 입력 항목을 작성해주세요.');
                        return; // 다음 섹션으로 넘어가지 않음
                    }             	
                	
                	
                	
                    const nextSectionId = this.getAttribute('data-next');
                    const nextSection = document.getElementById(nextSectionId);
                    const nextStepIndex = Array.from(formSections).indexOf(nextSection);
                    
                    // 현재 섹션 숨기기
                    currentSection.classList.remove('active-section');
                    // 다음 섹션 보이기
                    nextSection.classList.add('active-section');
                    // 진행 상태 업데이트
                    updateProgressBar(nextStepIndex);
                    
                    // 스크롤 맨 위로
                    window.scrollTo(0, 0);
                });
            });
            
            // 이전 단계 버튼 이벤트
            prevButtons.forEach(button => {
                button.addEventListener('click', function() {
                    const prevSectionId = this.getAttribute('data-prev');
                    const prevSection = document.getElementById(prevSectionId);
                    const currentSection = this.closest('.form-section');
                    const prevStepIndex = Array.from(formSections).indexOf(prevSection);
                    
                    // 현재 섹션 숨기기
                    currentSection.classList.remove('active-section');
                    
                    // 이전 섹션 보이기
                    prevSection.classList.add('active-section');
                    
                    // 진행 상태 업데이트
                    updateProgressBar(prevStepIndex);
                    
                    // 스크롤 맨 위로
                    window.scrollTo(0, 0);
                });
            });
            
            // 진행 상태 업데이트 함수
            function updateProgressBar(activeIndex) {
                progressSteps.forEach((step, index) => {
                    if (index <= activeIndex) {
                        step.classList.add('active');
                    } else {
                        step.classList.remove('active');
                    }
                });
            }
            
           

            
            // 요금 계산 이벤트
            const basePrice = document.getElementById('base-price');
            const cleaningFee = document.getElementById('cleaning-fee');
            const serviceFee = document.getElementById('service-fee');
            const incomeValues = document.querySelectorAll('.income-value');
            
            function calculateIncome() {
                const basePriceValue = parseFloat(basePrice.value) || 0;
                const cleaningFeeValue = parseFloat(cleaningFee.value) || 0;
                const serviceFeeValue = parseFloat(serviceFee.value) || 0;
                
                const monthlyBase = basePriceValue * 20;
                const fees = cleaningFeeValue + serviceFeeValue;
                const cozyFee = (monthlyBase + fees) * 0.03;
                const totalIncome = monthlyBase + fees - cozyFee;
                
                // 값 업데이트
                incomeValues[0].textContent = `₩${monthlyBase.toLocaleString()}`;
                incomeValues[1].textContent = `₩${fees.toLocaleString()}`;
                incomeValues[2].textContent = `-₩${cozyFee.toLocaleString()}`;
                incomeValues[3].textContent = `₩${totalIncome.toLocaleString()}`;
            }
            
            // 가격 입력 필드에 이벤트 리스너 추가
            if (basePrice) {
                basePrice.addEventListener('input', calculateIncome);
            }
            if (cleaningFee) {
                cleaningFee.addEventListener('input', calculateIncome);
            }
            if (serviceFee) {
                serviceFee.addEventListener('input', calculateIncome);
            }
        });
        
    </script>
    <script>
		const basePriceInput = document.getElementById('base-price');
		const incomeValues = document.querySelectorAll('.income-value');
		
		basePriceInput.addEventListener('input', function(e) {
		    let value = e.target.value;
		    if (value === '') value = 0;
		    value = Number(value);
		
		    const commission = Math.floor(value * 0.10); // 수수료 10%
		    const expectedIncome = value * 20 - commission * 20; // 20박 기준 예상 수입
		
		    // income-value에 적용
		    incomeValues[0].textContent = '₩' + value;         // 기본 요금
		    incomeValues[1].textContent = '-₩' + commission;    // 수수료
		    incomeValues[2].textContent = '₩' + expectedIncome; // 예상 수입
		});
</script>
    
</body>
</html>