<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>어트랙션 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/FH.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hs/attractions.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
    <jsp:include page="/WEB-INF/views/common/header_admin.jsp"/>
    
    <div class="page-main">
        <div class="content-main">
            <h2>어트랙션 수정</h2>
            
            <form id="attraction_form" action="attractionEditPro.do" method="post" enctype="multipart/form-data">
                <input type="hidden" name="attraction_num" value="${attraction.attraction_num}">
                
                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" name="name" id="name" class="form-control" value="${attraction.name}" required>
                </div>
                
                <div class="form-group">
                    <label for="description">설명</label>
                    <textarea name="description" id="description" class="form-control" required>${attraction.description}</textarea>
                </div>
                
                <div class="form-group">
                    <label for="zipcode">우편번호</label>
                    <div style="display: flex;">
                        <input type="text" name="zipcode" id="zipcode" class="form-control" value="${attraction.zipcode}" style="width: 70%;" required>
                        <input type="button" onclick="execDaumPostcode()" value="우편번호 찾기" style="margin-left: 10px;" class="btn-submit">
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="address1">주소</label>
                    <input type="text" name="address1" id="address1" class="form-control" value="${attraction.address1}" required>
                </div>
                
                <div class="form-group">
                    <label for="address2">상세주소</label>
                    <input type="text" name="address2" id="address2" class="form-control" value="${attraction.address2}">
                </div>
                
                <div class="form-group">
                    <label for="image">이미지</label>
                    <input type="file" name="image" id="image" accept="image/jpeg,image/png,image/gif">
                    <img id="preview" class="preview-image" src="${pageContext.request.contextPath}/${attraction.image_url.startsWith('/') ? attraction.image_url.substring(1) : attraction.image_url}" style="max-width: 200px;" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/attimage/default_attraction.jpg';">
                </div>
                
                <div class="form-group" style="text-align: center;">
                    <input type="submit" value="수정" class="btn-submit" onclick="return confirm('정말로 수정하시겠습니까?')">
                    <button type="button" class="btn-cancel" onclick="if(confirm('정말로 삭제하시겠습니까?')) location.href='attractionDelete.do?attraction_num=${attraction.attraction_num}'">삭제</button>
                    <button type="button" class="btn-cancel" onclick="location.href='attractionList.do'">취소</button>
                </div>
            </form>
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    
    <!-- 다음 우편번호 API 사용 -->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
        // 다음 우편번호 찾기 API
        function execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function(data) {
                    document.getElementById('zipcode').value = data.zonecode;
                    document.getElementById('address1').value = data.address;
                    document.getElementById('address2').focus();
                    
                    // 우편번호로 지역 번호 자동 설정
                    var regionNum = getRegionNumByPostcode(data.zonecode);
                    document.getElementById('region_num').value = regionNum;
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
            };
            reader.readAsDataURL(e.target.files[0]);
        };
    </script>
</body>
</html>
