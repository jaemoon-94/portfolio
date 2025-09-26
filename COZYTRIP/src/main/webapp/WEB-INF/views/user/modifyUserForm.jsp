<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/FH.css">
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/hs/updatename.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		//회원정보 수정 유효성 체크
		$('#modify_form').submit(function(){
			const items = 
				document.querySelectorAll('.input-check');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = 
						document.querySelector(
								'label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 입력 필수');
					items[i].value='';
					items[i].focus();
					return false;
				}//end of if
				
				if(items[i].id == 'zipcode' 
					 && !/^[0-9]{5}$/.test($('#zipcode').val())){
					alert('우편번호를 입력하세요.(숫자5자리)');
					$('#zipcode').val('').focus();
					return false;
				}				
			}			
		});
		
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>회원정보 수정</h2>
		<form id="modify_form" action="modifyUser.do"
		                                     method="post">
			<ul>
				<li>
					<label for="name">이름</label>
					<input type="text" name="name" id="name"
					             value="${member.name}"
					             maxlength="10" class="input-check">
				</li>
				<li>
					<label for="phone">전화번호</label>
					<input type="text" name="phone" id="phone"
					             value="${member.phone}"
					             maxlength="15" class="input-check">
				</li>
				<li>
					<label for="email">이메일</label>
					<input type="text" name="email" id="email"
					             value="${member.email}"
					             maxlength="15" class="input-check">
				</li>
		
			</ul> 
			<div class="align-center">
				<input type="submit" value="수정">
				<input type="button" value="취소"
			     onclick="location.href='updateForm.do'">
			</div>                                    
		</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>