<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>어트랙션 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/FH.css">
    <!-- 어트랙션 전용 CSS 파일 추가 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hs/attractions.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    
    <div class="page-main">
        <c:if test="${not empty param.message}">
            <script>
                alert("${param.message}");
            </script>
        </c:if>
        <div class="content-main">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                <h2>어트랙션 목록</h2>
                
                <div style="display: flex; align-items: center; gap: 10px; margin-bottom: 20px;">
                    <form action="attractionList.do" method="get" style="display: flex; align-items: center; gap: 10px;">
                        <input type="hidden" name="region_num" value="${param.region_num}">
                        <input type="text" name="searchKeyword" value="${searchKeyword}" placeholder="검색어를 입력하세요" 
                               style="padding: 8px 12px; border: 1px solid #ddd; border-radius: 4px; width: 200px; font-size: 14px; transition: all 0.3s ease;">
                        <button type="submit" style="background-color: #4CAF50; color: white; border: none; padding: 8px 15px; border-radius: 4px; cursor: pointer; font-size: 14px; transition: all 0.3s ease;">
                            <i class="fas fa-search"></i> 검색
                        </button>
                    </form>
                    
                    <c:if test="${!empty user_num && user_auth == 2}">
                        <input type="button" value="어트랙션 추가" id="openModal" class="btn-add" 
                               style="background-color: #007bff; color: white; border: none; padding: 8px 15px; border-radius: 4px; cursor: pointer; font-size: 14px; transition: all 0.3s ease;">
                    </c:if>
                </div>
            </div>
            
            <div class="attraction-list">
                <c:choose>
                    <c:when test="${not empty attractionList}">
                        <c:forEach var="attraction" items="${attractionList}">
                            <div class="attraction-item" onclick="location.href='attractionDetail.do?attraction_num=${attraction.attraction_num}'" style="cursor: pointer;">
                                <c:choose>
                                    <c:when test="${not empty attraction.image_url}">
                                        <img src="${pageContext.request.contextPath}/${attraction.image_url.startsWith('/') ? attraction.image_url.substring(1) : attraction.image_url}" alt="${attraction.name}" style="width: 100%; height: 200px; object-fit: cover;" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/attimage/default_attraction.jpg';">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/images/attimage/default_attraction.jpg" alt="${attraction.name}" style="width: 100%; height: 200px; object-fit: cover;">
                                    </c:otherwise>
                                </c:choose>
                                <div class="attraction-name">${attraction.name}</div>
                                <div class="attraction-desc">${attraction.description}</div>
                                <div class="attraction-addr">${attraction.address1} ${attraction.address2}</div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div style="text-align: center; padding: 50px;">
                            <h3>등록된 어트랙션이 없습니다.</h3>
                            <p>어트랙션을 추가해보세요!</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    
    <!-- 어트랙션 추가 모달 -->
    <div id="attractionModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>어트랙션 추가</h2>
            <form id="attraction_form" action="attractionAdd.do" method="post" enctype="multipart/form-data">
                <input type="hidden" name="region_num" id="region_num" value="1">
                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" name="name" id="name" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="description">설명</label>
                    <textarea name="description" id="description" class="form-control" required></textarea>
                </div>
                <div class="form-group">
                    <label for="zipcode">우편번호</label>
                    <div style="display: flex;">
                        <input type="text" name="zipcode" id="zipcode" class="form-control" style="width: 70%;" required>
                        <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기" style="margin-left: 10px;" class="btn-submit">
                    </div>
                </div>
                <div class="form-group">
                    <label for="address1">주소</label>
                    <input type="text" name="address1" id="address1" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="address2">상세주소</label>
                    <input type="text" name="address2" id="address2" class="form-control">
                </div>
                <div class="form-group">
                    <label for="image">이미지</label>
                    <input type="file" name="image" id="image" accept="image/jpeg,image/png,image/gif" required>
                    <img id="preview" class="preview-image">
                </div>
                <div class="form-group" style="text-align: center;">
                    <input type="submit" value="등록" class="btn-submit">
                    <input type="button" value="취소" class="close-modal">
                </div>
            </form>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    
    <!-- 다음 우편번호 API 사용 -->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
        // 모달 관련 스크립트
        var modal = document.getElementById("attractionModal");
        var btn = document.getElementById("openModal");
        var span = document.getElementsByClassName("close")[0];
        var closeBtn = document.getElementsByClassName("close-modal")[0];
        
        btn.onclick = function() {
            modal.style.display = "block";
        }
        
        span.onclick = function() {
            modal.style.display = "none";
        }
        
        closeBtn.onclick = function() {
            modal.style.display = "none";
        }
        
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
        
        // 다음 우편번호 찾기 API
        function execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function(data) {
                    document.getElementById('zipcode').value = data.zonecode;
                    document.getElementById('address1').value = data.address;
                    
                    // 우편번호로 지역 번호 자동 설정
                    var regionNum = getRegionNumByPostcode(data.zonecode);
                    document.getElementById('region_num').value = regionNum;
                    
                    document.getElementById('address2').focus();
                }
            }).open();
        }

        // 우편번호로 지역 번호 반환
        function getRegionNumByPostcode(postcode) {
            var firstTwoDigits = parseInt(postcode.substring(0, 2));
            
            if (firstTwoDigits >= 0 && firstTwoDigits <= 23) {
                return 1; // 수도권
            } else if (firstTwoDigits >= 24 && firstTwoDigits <= 26) {
                return 2; // 강원권
            } else if (firstTwoDigits >= 27 && firstTwoDigits <= 35) {
                return 3; // 충청권
            } else if (firstTwoDigits >= 36 && firstTwoDigits <= 53) {
                return 4; // 영남권
            } else if (firstTwoDigits >= 54 && firstTwoDigits <= 62) {
                return 5; // 호남권
            } else if (firstTwoDigits === 63) {
                return 6; // 제주
            } else {
                return 1; // 기타는 기본값으로 수도권 설정
            }
        }
        
        // 이미지 미리보기
        document.getElementById('image').onchange = function(e) {
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('preview').src = e.target.result;
                document.getElementById('preview').style.display = 'block';
            };
            reader.readAsDataURL(e.target.files[0]);
        };
    </script>
</body>
</html>