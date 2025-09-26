<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/FH.css">
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/hs/delete.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#delete_form').submit(function(){
			const items = 
				document.querySelectorAll(
					'input[type="text"],input[type="email"],input[type="password"]');
			for(let i=0;i<items.length;i++){
				if(items[i].value.trim()==''){
					let label = document.querySelector(
							    'label[for="'+items[i].id+'"]');
					alert(label.textContent + ' 입력 필수');
					items[i].value = '';
					items[i].focus();
					return false;
				}//end of if
			}//end of for
			
			if($('#passwd').val()!=$('#cpasswd').val()){
				alert('비밀번호와 비밀번호 확인이 불일치');
				$('#passwd').val('').focus();
				$('#cpasswd').val('');
				return false;
			}//end of if
		});//end of sumbit
		
		//비밀번호 확인까지 한 후 다시 비밀번호를 수정하려고
		//하면 비밀번호 확인을 초기화
		$('#passwd').keyup(function(){
			$('#cpasswd').val('');
			$('#message_cpasswd').text('');
		});
		
		//비밀번호와 비밀번호 확인 일치 여부 체크
		$('#cpasswd').keyup(function(){
			if($('#cpasswd').val()!='' && 
				$('#passwd').val()==$('#cpasswd').val()){
				$('#message_cpasswd').text('비밀번호 일치');
			}else{
				$('#message_cpasswd').text('');
			}
		});
		
	});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>회원탈퇴</h2>
		<form action="deleteUser.do" method="post"
		                             id="delete_form">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id"
				 maxlength="12" autocomplete="off">
			</li>
			<li>
				<label for="email">이메일</label>
				<input type="email" name="email" id="email"
				 maxlength="50" autocomplete="off">
			</li>
			<li>
				<label for="passwd">비밀번호</label>
				<input type="password" name="passwd" 
				        id="passwd" maxlength="12">
			</li>
			<li>
				<label for="cpasswd">비밀번호 확인</label>
				<input type="password"  
				        id="cpasswd" maxlength="12">
				<span id="message_cpasswd"></span>        
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="회원탈퇴">
			<input type="button" value="취소"
			     onclick="location.href='myPage.do'">
		</div>
		</form>
	</div>
</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>