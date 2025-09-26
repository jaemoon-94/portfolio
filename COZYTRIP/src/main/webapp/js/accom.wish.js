$(function(){
	/*=================================
	 * 좋아요 선택 여부와 선택한 총개수 읽기
	 *=================================*/
	function selectWish(){
		//서버와 통신
		$.ajax({
			url:'getWish.do',
			type:'post',
			data:{accom_num:$('#output_wish').attr('data-num')},
			dataType:'json',
			success:function(param){
				console.log(param);  // 응답값 확인
				displayWish(param);
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	/*=================================
	 * 좋아요 등록(및 삭제) 이벤트 연결
	 *=================================*/
	$('#output_wish').click(function(){
		//서버와 통신
		$.ajax({
			url:'writeWish.do',
			type:'post',
			data:{accom_num:$(this).attr('data-num')},
			dataType:'json',
			success:function(param){
				console.log(param);  // 응답값 확인
				if(param.result == 'logout'){
					alert('로그인 후 눌러주세요!');
				}else if(param.result == 'success'){
					displayWish(param);
				}else{
					alert('위시리스트 등록/삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	/*=================================
	 * 좋아요 표시 함수
	 *=================================*/
	function displayWish(param){
		let output;
		if(param.status == 'yesWish'){//좋아요 선택
			output = '../images/yeswish.png';
		}else{//좋아요 미선택
			output = '../images/nowish.png';
		}
		//문서 객체에 설정
		$('#output_wish').attr('src',output);
		$('#output_fcount').text(param.count);
	}
	/*=================================
	 * 초기 데이터 호출
	 *=================================*/	
	selectWish();
});















