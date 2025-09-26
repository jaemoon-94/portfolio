$(function(){
    /*=================================
    * 각 숙소의 위시리스트 상태 가져오기
    *=================================*/
    function loadAllWishes(){
        // 각 숙소의 위시리스트 상태 가져오기
        $('.output_wish').each(function(){
            let $wishIcon = $(this);
            let accom_num = $wishIcon.attr('data-num');
            // 위시리스트 상태 확인

            $.ajax({
                url: 'getWish.do',
                type: 'post',
                data: {accom_num: accom_num},
                dataType: 'json',
                success: function(param){
                    // 위시리스트 상태 응답 처리
                    if(param.status == 'yesWish'){
                        $wishIcon.attr('src', '../images/yeswish.png');
                    } else {
                        $wishIcon.attr('src', '../images/nowish.png');
                    }
                },
                error: function(xhr, status, error){
                    // 네트워크 오류 처리
                }
            });
        });
    }

    /*=================================
    * 위시리스트 등록 및 삭제 - div에 이벤트 연결
    *=================================*/
    // .wishlist-button div에 이벤트 리스너 연결
    $(document).on('click', '.wishlist-button', function(event){
        event.stopPropagation(); // 부모 카드 클릭 방지
        // 위시리스트 버튼 클릭 처리
        
        // 이미지 요소 찾기
        let $wishIcon = $(this).find('.output_wish');
        let accom_num = $wishIcon.attr('data-num');
        // 클릭한 숙소 번호 처리

        $.ajax({
            url: 'writeWish.do',
            type: 'post',
            data: {accom_num: accom_num},
            dataType: 'json',
            success: function(param){
                // writeWish 응답 처리
                if(param.result == 'logout'){
                    alert('로그인 후 눌러주세요!');
                } else if(param.result == 'success'){
                    if(param.status == 'yesWish'){
                        $wishIcon.attr('src', '../images/yeswish.png');
                    } else {
                        $wishIcon.attr('src', '../images/nowish.png');
                    }
                } else {
                    alert('위시리스트 등록/삭제 오류 발생');
                }
            },
            error: function(xhr, status, error){
                // 네트워크 오류 처리
            }
        });
    });
    
    // 기존 .output_wish 이벤트도 유지 (두 가지 방식 모두 시도)
    $(document).on('click', '.output_wish', function(event){
        event.stopPropagation(); // 부모 카드 클릭 방지
        // 이미지 직접 클릭 처리
        
        let $wishIcon = $(this);
        let accom_num = $wishIcon.attr('data-num');
        // 클릭한 이미지 숙소번호 처리

        $.ajax({
            url: 'writeWish.do',
            type: 'post',
            data: {accom_num: accom_num},
            dataType: 'json',
            success: function(param){
                // 이미지 클릭 writeWish 응답 처리
                if(param.result == 'logout'){
                    alert('로그인 후 눌러주세요!');
                } else if(param.result == 'success'){
                    if(param.status == 'yesWish'){
                        $wishIcon.attr('src', '../images/yeswish.png');
                    } else {
                        $wishIcon.attr('src', '../images/nowish.png');
                    }
                } else {
                    alert('위시리스트 등록/삭제 오류 발생');
                }
            },
            error: function(xhr, status, error){
                // 네트워크 오류 처리
            }
        });
    });

    /*=================================
    * 초기화 함수 호출과 디버깅
    *=================================*/
    // DOM이 완전히 로드된 후 실행
    $(document).ready(function() {
        // DOM 로드 완료 후 초기화
        
        
        // 초기 상태 로드
        loadAllWishes();
    });
});