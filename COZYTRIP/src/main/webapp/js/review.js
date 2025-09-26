$(function(){
	let rowCount = 10;
	let currentPage;
	let count;

	/*
	// 리뷰 목록
	function selectList(pageNum) {
		currentPage = pageNum;
		$('#loading').show();

		$.ajax({
			url:'myList.do',
			type:'post',
			data:{pageNum:pageNum,rowCount:rowCount,user_num:$('#user_num').val()},
			dataType:'json',
			success:function(param){
				$('#loading').hide();
				count = param.count;

				if (pageNum == 1){
					$('#output').empty();
				}

				$(param.list).each(function(index, item) {
					let output = '<div class="review-card">';

					// 이미지 영역
					output += '<div class="review-img-box">';
					output += '숙소 이미지'; // 실제 이미지 URL 있으면 <img src="'+item.image_url+'">로
					output += '</div>';

					// 텍스트 영역
					output += '<div class="review-content">';
					output += '<div class="review-title">' + item.accom_name;
					if (item.review_star) {
						output += ' <span class="review-rating">★ ' + item.review_star.toFixed(2) + '</span>';
					}
					output += '</div>';

					output += '<div class="review-text">' + item.content + '</div>';

					if (item.review_modifydate){
						output += '<div class="review-date">최근 수정일 : ' + item.review_modifydate + '</div>';
					} else {
						output += '<div class="review-date">등록일 : ' + item.review_date + '</div>';
					}

					if(param.user_num == item.user_num){
						output += '<div class="review-btn-box">';
						output += '<input type="button" data-reviewnum="'+item.review_num+'" value="수정" class="modify-btn">';
						output += '<input type="button" data-reviewnum="'+item.review_num+'" value="삭제" class="delete-btn">';
						output += '</div>';
					}

					output += '</div>'; // review-content
					output += '</div>'; // review-card

					$('#output').append(output);
				});

				if (currentPage >= Math.ceil(count / rowCount)){
					$('.paging-button').hide();
				} else {
					$('.paging-button').show();
				}
			},
			error:function(){
				$('#loading').hide();
				alert('네트워크 오류 발생');
			}
		});
	}

	// 다음 댓글 보기 버튼
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	*/

	// 수정 버튼 클릭
	$(document).on('click','.review-btn.update',function(){
		let review_num = $(this).attr('data-reviewnum');

		let $card = $(this).closest('.review-card');
		let imageSrc = $card.find('.review-image-box img').attr('src');
		let accomName = $card.find('.accom-name').text();
		let accomDesc = $card.find('.review-snippet').text();
		let content = accomDesc.replace(/<br>/gi, '\n'); // br -> 줄바꿈

		let modifyUI = '<form id="mre_form" class="review-edit-form">';
		modifyUI += '<div class="review-image-box">';
		modifyUI += '  <img src="'+ imageSrc +'" alt="숙소 이미지">';
		modifyUI += '</div>';

		modifyUI += '<div class="review-info-box">';
		modifyUI += '  <div class="accom-name">' + accomName + '</div>';
		modifyUI += '  <textarea name="content" id="mcontent" rows="2" required>' + content + '</textarea>';
		modifyUI += '  <input type="hidden" name="review_num" value="'+ review_num +'">';
		modifyUI += '  <div class="button-group">';
		modifyUI += '    <button type="submit" class="r-btn update">수정</button>';
		modifyUI += '    <button type="button" class="r-btn delete re-reset">취소</button>';
		modifyUI += '  </div>';
		modifyUI += '</div>';
		modifyUI += '</form>';

		// 기존 카드 내부 콘텐츠 숨기고 폼 추가
		initModifyForm(); // 기존 폼 제거
		$(this).parents('.review-card').append(modifyUI);
		$(this).parent().hide();
		/*
		$card.find('.review-info-box').hide();
		$card.append(modifyUI);
		*/
	});

	// 수정 폼 초기화 함수
	function initModifyForm(){
		$('#mre_form').remove();
		$('.review-info-box').show();
	}

	// 수정 폼 취소
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	});

	// 수정 전송
	$(document).on('submit','#mre_form',function(event){
		if($('#mcontent').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#mcontent').val('').focus();
			return false;
		}

		let form_data = $(this).serialize();

		$.ajax({
			url:'updateReview.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 수정할 수 있습니다.');
				}else if(param.result == 'success'){
					$('#mre_form').parent()
					              .find('.review-snippet')
								  .html(useBrNoHtml(
									$('#m_content').val()));
					$('#mre_form').parent()
					              .find('.modify-date')
								  .text('최근 수정일 : 방금');
					initModifyForm();
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글을 수정할 수 없습니다.');
				}else{
					alert('리뷰 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		event.preventDefault();
	});

	});
	
	selectList(1);
