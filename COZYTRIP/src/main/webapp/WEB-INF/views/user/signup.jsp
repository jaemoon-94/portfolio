<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CozyKorea - 회원가입</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/FH.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/hs/signup.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		let idChecked = 0;
		let emailChecked = 0;
		let phoneChecked = 0;

		// 아이디 중복 체크
		$('#id_check').click(function() {
			if (!/^[A-Za-z0-9]{4,12}$/.test($('#user_id').val())) {
				alert('영문 또는 숫자 4자~12자를 사용하세요');
				$('#user_id').val('').focus();
				return;
			}

			$.ajax({
				url : 'checkUniqueInfo.do',
				type : 'post',
				data : {
					user_id : $('#user_id').val()
				}, // 여기 수정
				dataType : 'json',
				success : function(param) {
					if (param.result == 'idNotFound') {
						idChecked = 1;
						$('#message_id').css('color', '#000000').text('등록 가능');
					} else if (param.result == 'idDuplicated') {
						idChecked = 0;
						$('#message_id').css('color', 'red').text('중복된 ID');
						$('#user_id').val('').focus();
					} else {
						idChecked = 0;
						alert('아이디 중복 체크 오류 발생');
					}
				},
				error : function() {
					idChecked = 0;
					alert('네트워크 오류 발생');
				}
			});
		});

		// 아이디 입력 시 초기화
		$('#insert_form #user_id').keydown(function() {
			idChecked = 0;
			$('#message_id').text('');
		});

		// 이메일 중복 체크
		$('#email_check').click(
				function() {
					if (!/^\w+@\w+\.\w+$/.test($('#user_email').val())) {
						alert('이메일 형식에 맞게 입력하세요!');
						$('#user_email').val('').focus();
						return;
					}

					$.ajax({
						url : 'checkUniqueInfo.do',
						type : 'post',
						data : {
							user_email : $('#user_email').val()
						}, // 여기 수정
						dataType : 'json',
						success : function(param) {
							if (param.result == 'emailNotFound') {
								emailChecked = 1;
								$('#message_email').css('color', '#000000')
										.text('등록 가능');
							} else if (param.result == 'emailDuplicated') {
								emailChecked = 0;
								$('#message_email').css('color', 'red').text(
										'중복된 이메일');
								$('#user_email').val('').focus();
							} else {
								emailChecked = 0;
								alert('이메일 중복 체크 오류 발생');
							}
						},
						error : function() {
							emailChecked = 0;
							alert('네트워크 오류 발생');
						}
					});
				});

		$('#insert_form #user_email').keydown(function() {
			emailChecked = 0;
			$('#message_email').text('');
		});

		// 폰번호 중복 체크
		$('#phone_check').click(
				function() {
					if (!/^[0-9]{10,11}$/.test($('#user_phone').val())) {
						alert('전화번호는 숫자 10자 또는 11자만 입력 가능합니다');
						$('#user_phone').val('').focus();
						return;
					}

					$.ajax({
						url : 'checkUniqueInfo.do',
						type : 'post',
						data : {
							user_phone : $('#user_phone').val()
						}, // 여기 수정
						dataType : 'json',
						success : function(param) {
							if (param.result == 'phoneNotFound') {
								phoneChecked = 1;
								$('#message_phone').css('color', '#000000')
										.text('등록 가능');
							} else if (param.result == 'phoneDuplicated') {
								phoneChecked = 0;
								$('#message_phone').css('color', 'red').text(
										'중복된 전화번호');
								$('#user_phone').val('').focus();
							} else {
								phoneChecked = 0;
								alert('전화번호 중복 체크 오류 발생');
							}
						},
						error : function() {
							phoneChecked = 0;
							alert('네트워크 오류 발생');
						}
					});
				});

		$('#insert_form #user_phone').keydown(function() {
			phoneChecked = 0;
			$('#message_phone').text('');
		});

		// 최종 유효성 체크
		$('#insert_form')
				.submit(
						function() {
							const items = document
									.querySelectorAll('.input-check');
							for (let i = 0; i < items.length; i++) {
								if (items[i].value.trim() == '') {
									const label = document
											.querySelector('label[for="'
													+ items[i].id + '"]');
									alert(label.textContent + ' 입력 필수');
									items[i].value = '';
									items[i].focus();
									return false;
								}

								if (items[i].id == 'user_id'
										&& !/^[A-Za-z0-9]{4,12}$/.test($(
												'#user_id').val())) {
									alert('영문 또는 숫자 4자~12자를 사용하세요');
									$('#user_id').val('').focus();
									return false;
								}

								if (items[i].id == 'user_id' && idChecked == 0) {
									alert('아이디 중복체크 필수');
									return false;
								}

								if (items[i].id == 'user_email'
										&& !/^\w+@\w+\.\w+$/.test($(
												'#user_email').val())) {
									alert('이메일 형식에 맞게 입력하세요!');
									$('#user_email').val('').focus();
									return false;
								}

								if (items[i].id == 'user_email'
										&& emailChecked == 0) {
									alert('이메일 중복체크 필수');
									return false;
								}

								if (items[i].id == 'user_phone'
										&& !/^[0-9]{10,11}$/.test($(
												'#user_phone').val())) {
									alert('전화번호는 숫자 10자 또는 11자만 입력 가능합니다');
									$('#user_phone').val('').focus();
									return false;
								}

								if (items[i].id == 'user_phone'
										&& phoneChecked == 0) {
									alert('전화번호 중복체크 필수');
									return false;
								}

								if (items[i].id == 'gender'
										&& $('#gender').val() == '') {
									alert('성별을 선택해주세요');
									return false;
								}
							}
						});
	});
</script>
</head>
<body>
	<!-- 상단 네비게이션 바 -->
	<jsp:include page="/WEB-INF/views/common/header.jsp" />

	<!-- 숙소 등록 메인 섹션 -->
	<main>
		<div class="page-main">
			<div class="content-main">
				<h2>회원가입</h2>

				<form id="insert_form" action="insertUser.do" method="post">
					<ul>
						<li><label for="user_id">아이디</label> <input type="text"
							name="user_id" id="user_id" maxlength="12" autocomplete="off"
							class="input-check"> <input type="button" value="ID중복체크"
							id="id_check"> <span id="message_id"></span>
						<li><label for="user_name">이름</label> <input type="text"
							name="user_name" id="user_name" maxlength="10"
							class="input-check"></li>
						<li><label for="passwd">비밀번호</label> <input type="password"
							name="passwd" id="passwd" maxlength="12" class="input-check">
						</li>
						<li><label for="user_phone">전화번호</label> <input type="text"
							name="user_phone" id="user_phone" maxlength="15"
							class="input-check"> <input type="button"
							value="전화번호 중복체크" id="phone_check"> <span
							id="message_phone"></span></li>
						<li><label for="user_email">이메일</label> <input type="email"
							name="user_email" id="user_email" maxlength="50"
							class="input-check" autocomplete="off"> <input
							type="button" value="이메일 중복체크" id="email_check"> <span
							id="message_email"></span></li>
						<li><label for="gender">성별</label> <select name="gender"
							id="gender" class="input-check">
								<option value="">선택</option>
								<option value="M">남성</option>
								<option value="F">여성</option>
						</select></li>
					</ul>
					<div class="align-center">
						<input type="submit" value="회원가입"> <input type="button"
							value="홈으로"
							onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
					</div>
				</form>


			</div>
		</div>
	</main>
	<!-- 푸터 -->
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>