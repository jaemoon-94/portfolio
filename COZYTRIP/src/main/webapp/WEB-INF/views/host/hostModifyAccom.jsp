<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CozyKorea 호스트 - 숙소 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/modifyAccom.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/hostHeader.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/host/hostHeader.jsp"/>
    <div class="container">
        <div class="edit-container">
            <!-- 수정 페이지 헤더 -->
            <div class="edit-header">
                <h1 class="edit-title">숙소 정보 수정</h1>
                <div class="status-toggle">
				    <span class="status-label">숙소 상태:</span>
				    <label class="toggle-switch">
				        <input type="checkbox" id="status-toggle"
				               ${accom.accom_status == 3 ? 'checked' : ''}
				               ${accom.accom_status == 1 ? 'disabled' : ''}>
				        <span class="toggle-slider"></span>
				    </label>
				    <span class="status-text ${accom.accom_status == 3 ? 'active' : ''}" id="status-text">
				        ${accom.accom_status == 1 ? '승인 전' : (accom.accom_status == 2 ? '비활성화' : '활성화')}
				    </span>
				</div>
            </div>
            
            <form class="edit-form" action="modifyAccom.do" method="post" enctype="multipart/form-data">
            	<input type="hidden" name="accom_num" value="${accom.accom_num}">
                <!-- 기본 정보 섹션 -->
                <div class="form-section">
                    <h2 class="section-title">기본 정보</h2>
                    <div class="form-group">
                        <label for="property-name">숙소 이름</label>
                        <input type="text" id="property-name" value="${accom.accom_name}" name="accom_name" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="property-type">숙소 유형</label>
                        <select id="property-type" name="cate_num" required>
                            <c:forEach var="cate" items="${cateList}">
						    	<option value="${cate.cate_num}" <c:if test="${accom.cate_num == cate.cate_num}">selected</c:if>>${cate.cate_name}</option>
						    </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="max-guests">최대 수용 인원</label>
                        <input type="number" oninput="onlyNumber()" name="max_people" value="${accom.max_people}" id="max-guests" min="1" placeholder="최대 수용 인원" required>
                    </div>
                    <div class="form-group">
                        <label for="property-desc">숙소 설명</label>
                        <textarea id="property-desc" name="description" rows="4" required>${accom.description}</textarea>
                    </div>
                </div>
                <!-- 위치 정보 섹션 -->
                <div class="form-section">
                    <h2 class="section-title">위치 정보</h2>
                    <div class="form-group">
                         <label for="region">지역</label>
                         <select id="region" name="region_num" required style="pointer-events: none; background-color: #eee;" required>
						  <option value="">우편번호를 선택하세요</option>
						  <c:forEach var="region" items="${regionList}">
						    <option value="${region.region_num}" 
						      <c:if test="${accom.region_num == region.region_num}">selected</c:if>>
						      ${region.region_name}
						    </option>
						  </c:forEach>
						</select>
                     </div>
                     
                     <p class="section-desc">정확한 주소 정보를 입력해주세요</p>
                     <div class="form-group">
                         <label for="address-postcode">우편번호</label>
                         <div class="postcode-input">
                             <input type="text" value="${accom.zipcode}" name="zipcode" id="address-postcode" placeholder="우편번호" readonly required>
                             <button type="button" class="find-postcode-btn" onclick="execDaumPostcode()">우편번호 찾기</button>
                         </div>
                     </div>
                     <div class="map-container">
                            <div id="map" style="width:710px;height:300px;margin-top:10px;display:none"></div>
                        </div>
                     <div class="form-group">
                         <label for="address-main">주소</label>
                         <input type="text" value="${accom.address1}" name="address1" id="address-main" placeholder="주소" readonly required>
                     </div>
                     
                     <div class="form-group">
                         <label for="address-detail">상세 주소</label>
                         <input type="text" value="${accom.address2}" name="address2" id="address-detail" placeholder="상세 주소 입력" required>
                     </div>
                </div>
                <!-- 사진 관리 섹션 -->
        		<div class="form-section">
				    <h2 class="section-title">사진 관리</h2>
				    <div id="image-grid" style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; margin-bottom: 15px;">
				
				        <%-- ✅ 대표 사진 칸 (1개 고정) --%>
						<div id="main-photo-wrapper" class="image-wrapper" style="position: relative; border: 1px solid #ddd; border-radius: 8px; overflow: hidden; aspect-ratio: 1;">
						    <div style="position: absolute; top: 10px; left: 10px; background-color: #fa6767; color: white; padding: 4px 8px; border-radius: 4px; font-size: 12px;">대표 사진</div>
						    
						    <c:set var="mainImage" value="${null}" />
						    <c:forEach var="image" items="${imageList}">
						        <c:if test="${image.main == 1}">
						            <c:set var="mainImage" value="${image}" />
						        </c:if>
						    </c:forEach>
						
						    <c:choose>
						        <c:when test="${not empty mainImage}">
						            <img src="${pageContext.request.contextPath}/images/accomimage/${mainImage.image_name}" alt="대표사진" style="width: 100%; height: 100%; object-fit: cover;">
						            <button type="button" class="delete-btn" data-image-num="${mainImage.image_num}" onclick="deleteImage(this)" style="position: absolute; top: 10px; right: 10px; background-color: rgba(0,0,0,0.6); color: white; border: none; padding: 5px 8px; border-radius: 4px; cursor: pointer;">삭제</button>
						        </c:when>
						        <c:otherwise>
						            <label style="width: 100%; height: 100%; display: flex; align-items: center; justify-content: center; cursor: pointer;">
						                <input type="file" name="main_image" id="main-photo" accept="image/*" style="margin-left: 30px;" required>
						            </label>
						        </c:otherwise>
						    </c:choose>
						</div>
				
				
				        <%-- ✅ 2. 서브 이미지 최대 9칸 --%>
				        <c:set var="subImageCount" value="0" />
				        <c:forEach var="image" items="${imageList}">
				            <c:if test="${image.main == 0}">
				                <div class="image-wrapper" style="position: relative; border: 1px solid #ddd; border-radius: 8px; overflow: hidden; aspect-ratio: 1;">
				                    <img src="${pageContext.request.contextPath}/images/accomimage/${image.image_name}" alt="서브사진" style="width: 100%; height: 100%; object-fit: cover;">
				                    <button type="button" class="delete-btn" data-image-num="${image.image_num}" onclick="deleteImage(this)" style="position: absolute; top: 10px; right: 10px; background-color: rgba(0,0,0,0.6); color: white; border: none; padding: 5px 8px; border-radius: 4px; cursor: pointer;">삭제</button>
				                </div>
				                <c:set var="subImageCount" value="${subImageCount + 1}" />
				            </c:if>
				        </c:forEach>
				
				        <%-- ✅ 남은 빈칸 채우기 (최대 9개) --%>
				        <c:set var="startIndex" value="${subImageCount + 1}" />
				        <c:forEach begin="${startIndex}" end="9" var="i" varStatus="status">
				            <div class="image-wrapper" style="position: relative; border: 2px dashed #ddd; border-radius: 8px; aspect-ratio: 1; display: flex; align-items: center; justify-content: center;">
				            	<input type="file" name="sub_image${i}" accept="image/*" style="position: absolute; margin-left:50px; width: 80%;">
				            </div>
				        </c:forEach>
				    </div>
				</div>
                   
                
                <!-- 요금 설정 섹션 -->
                <div class="form-section">
                    <h2 class="section-title">요금 설정</h2>
                    <div class="form-group">
                        <label for="base-price">기본 숙박 요금 (1박 기준)</label>
                        <div style="position: relative;">
                            <span style="position: absolute; left: 12px; top: 10px;">₩</span>
                            <input type="number" oninput="onlyNumber()" name ="price" value="${accom.price}" id="base-price" value="120000" min="0" style="padding-left: 30px;" required>
                        </div>
                    </div>
                </div>
                
                <!-- 제출 버튼 영역 -->
                <div class="form-actions">
                    <button type="button" onclick="location.href='${pageContext.request.contextPath}/host/accomList.do'" class="btn" style="background-color: #f1f1f1; color: #333; border: 1px solid #ddd;">
                        <i class="fas fa-arrow-left"></i> 목록으로 돌아가기
                    </button>
                    <button type="submit" class="btn save-btn">
                        <i class="fas fa-save"></i> 변경사항 저장
                    </button>
                </div>
            </form>
        </div>
    </div>
    <script type="text/javascript">
    function deleteImage(button) {
        const imageNum = $(button).data('image-num');
        const $wrapper = $(button).closest('.image-wrapper');
        const isMain = $wrapper.attr("id") === "main-photo-wrapper";

        if (confirm('정말 삭제하시겠습니까?')) {
            $.ajax({
                url: 'deleteImage.do',
                type: 'post',
                data: { image_num: imageNum },
                dataType: 'json',
                success: function (param) {
                    if (param.result === 'logout') {
                        alert('로그인 후 사용하세요');
                    } else if (param.result === 'success') {
                    	location.reload()
                    } else if (param.result === 'wrongAccess') {
                        alert('잘못된 접근입니다.');
                    } else {
                        alert('삭제 실패');
                    }
                },
                error: function () {
                    alert('네트워크 오류 발생');
                }
            });
        }
    }

	</script>   
    <script>
    
    function onlyNumber() {
        console.log(event.type, event.target.value);
        const regex = /\D/g; // 숫자가 아닌 모든 문자를 찾아라. 
        event.target.value = event.target.value.replace(regex, "");
      }
        document.addEventListener('DOMContentLoaded', function() {
            // 상태 토글 기능
            const statusToggle = document.getElementById('status-toggle');
            const statusText = document.getElementById('status-text');
            
            statusToggle.addEventListener('change', function() {
            	const newStatus = this.checked ? 3 : 2; // 활성화=3, 비활성화=2

                // UI 업데이트
                statusText.textContent = this.checked ? '활성화' : '비활성화';
                statusText.className = this.checked ? 'status-text active' : 'status-text inactive';
                const accomNum = '${accom.accom_num}'; 
                // AJAX 호출
                $.ajax({
                    url: 'modifyAccomStatus.do',
                    type: 'post',
                    data: {
                        accom_num: accomNum,
                        accom_status: newStatus
                    },
                    dataType: 'json',
                    success: function (param) {
                        if (param.result === 'logout') {
                            alert('로그인 후 이용해주세요.');
                            location.href = 'loginForm.do';
                        } else if (param.result === 'success') {
                            console.log('상태 업데이트 성공');
                            alert('상태를 변경하였습니다.')
                        } else if (param.result === 'wrongAccess') {
                            alert('잘못된 접근입니다.');
                        } else {
                            alert('업데이트 실패');
                        }
                    },
                    error: function () {
                        alert('네트워크 오류 발생');
                    }
                });
            });
        });
    </script>
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
	                 //regionSelect.disabled = false;
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
	             height : '100%',
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
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
