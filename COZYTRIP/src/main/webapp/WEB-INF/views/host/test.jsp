<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>COZYTRIP 채팅</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
.chat-wrapper {
  /* 채팅용 변수들 (범위 제한) */
  --chat-primary-color: #f68181;
  --chat-secondary-color: #5d9cec;
  --chat-light-gray: #f5f7fa;
  --chat-dark-gray: #656d78;
  --chat-success-color: #000000;
  --chat-border-radius: 8px;
  --chat-box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  
  /* 채팅 영역 폰트 설정 */
  font-family: 'Noto Sans KR', sans-serif;
  color: #333;
  line-height: 1.6;
}

.content-main {
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
}

.chat-wrapper h2 {
  color: #333;
  margin-bottom: 20px;
  font-weight: 500;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.chat-wrapper h2 small {
  color: var(--chat-primary-color);
  font-weight: 600;
}

.chat-layout {
  display: flex;
  height: 80vh;
  gap: 20px;
}

.chat-rooms-list {
  width: 300px;
  background-color: white;
  border-radius: var(--chat-border-radius);
  box-shadow: var(--chat-box-shadow);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.rooms-header {
  background-color: var(--chat-primary-color);
  color: white;
  padding: 15px;
  text-align: center;
}

.rooms-header h3 {
  margin: 0;
  font-weight: 500;
}

.search-container {
  padding: 10px;
  border-bottom: 1px solid #e6e9ed;
}

.search-input {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #e6e9ed;
  border-radius: var(--chat-border-radius);
  font-size: 14px;
}

.search-input:focus {
  outline: none;
  border-color: var(--chat-secondary-color);
}

.rooms-list {
  overflow-y: auto;
  flex: 1;
}

.room-item {
  padding: 15px;
  border-bottom: 1px solid #e6e9ed;
  cursor: pointer;
  transition: background-color 0.2s;
}

.room-item:hover {
  background-color: var(--chat-light-gray);
}

.room-item.active {
  background-color: #ffeaea;
  border-left: 3px solid var(--chat-primary-color);
}

.room-title {
  font-weight: 500;
  margin-bottom: 5px;
  font-size: 15px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.room-accom {
  color: var(--chat-dark-gray);
  font-size: 13px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.room-preview {
  color: var(--chat-dark-gray);
  font-size: 12px;
  margin-top: 5px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.room-time {
  text-align: right;
  color: var(--chat-dark-gray);
  font-size: 11px;
  margin-top: 5px;
}

.unread-badge {
  background-color: var(--chat-primary-color);
  color: white;
  font-size: 11px;
  padding: 2px 6px;
  border-radius: 10px;
  margin-left: 5px;
}

.chat-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #fff;
  border-radius: var(--chat-border-radius);
  box-shadow: var(--chat-box-shadow);
  overflow: hidden;
}

.chat-header {
  background-color: var(--chat-primary-color);
  color: white;
  padding: 15px 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.chat-header h3 {
  margin: 0;
  font-weight: 500;
}

.chat-status {
  font-size: 12px;
  color: #e6e9ed;
}

#chatting_message {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background-color: var(--chat-light-gray);
}

.date-position {
  text-align: center;
  margin: 15px 0;
  position: relative;
}

.date-position span {
  background-color: #e6e9ed;
  color: var(--chat-dark-gray);
  padding: 5px 15px;
  border-radius: 15px;
  font-size: 12px;
}

.from-position, .to-position {
  margin-bottom: 20px;
  max-width: 80%;
}

.from-position {
  align-self: flex-end;
  margin-left: auto;
  text-align: right;
  color: var(--chat-dark-gray);
  font-size: 13px;
}

.to-position {
  align-self: flex-start;
  margin-right: auto;
  color: var(--chat-dark-gray);
  font-size: 13px;
}

.item {
  margin-top: 5px;
  border-radius: var(--chat-border-radius);
  padding: 10px 15px;
  position: relative;
  display: inline-block;
  max-width: 100%;
}

.from-position .item {
  background-color: var(--chat-primary-color);
  color: white;
  border-top-right-radius: 0;
}

.to-position .item {
  background-color: white;
  color: #333;
  border-top-left-radius: 0;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.item span {
  word-break: break-word;
}

.item b {
  color: var(--chat-success-color);
  margin-right: 5px;
}

.align-right {
  text-align: right;
  font-size: 11px;
  margin-top: 5px;
  opacity: 0.7;
}

#chatting_form {
  padding: 15px;
  border-top: 1px solid #e6e9ed;
  background-color: white;
}

.message-input-container {
  display: flex;
  align-items: flex-end;
}

#message {
  flex: 1;
  border: 1px solid #e6e9ed;
  border-radius: var(--chat-border-radius);
  padding: 12px;
  resize: none;
  height: 60px;
  font-family: inherit;
  font-size: 14px;
  transition: border-color 0.3s;
}

#message:focus {
  outline: none;
  border-color: var(--chat-secondary-color);
}

.send-button {
  background-color: var(--chat-primary-color);
  color: white;
  border: none;
  border-radius: var(--chat-border-radius);
  padding: 12px 20px;
  margin-left: 10px;
  cursor: pointer;
  height: 60px;
  transition: background-color 0.3s;
}

.send-button:hover {
  background-color: var(--chat-secondary-color);
}

.send-button i {
  margin-right: 5px;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
  .chat-layout {
    flex-direction: column;
    height: auto;
  }
  
  .chat-rooms-list {
    width: 100%;
    height: 40vh;
  }
  
  .chat-container {
    height: 60vh;
  }
}
</style>
<script type="text/javascript">
$(function(){
   //웹소켓 시작
   const message_socket = new WebSocket("ws://localhost:8080/COZYTRIP/webSocket2");
   message_socket.onopen = function(evt){
      message_socket.send("board:");
   }
   //서버로부터 메시지를 받으면 호출되는 함수 지정
   message_socket.onmessage = function(evt){
      //메시지 알림
      let data = evt.data;
      if(data.substring(0,6) == 'board:'){
         console.log('데이터 처리');
         selectData();
         // 채팅방 목록도 갱신
         loadChatRooms();
      }
   }
   message_socket.onclose = function(evt){
      //소켓이 종료된 후 부과적인 작없이 있을 경우 명시
      console.log('chat close');
   }
   
   //웹소켓 끝
   
   // 채팅방 목록 로드
   function loadChatRooms() {
      $.ajax({
         url: '${pageContext.request.contextPath}/accom/chatRoomList.do',
         type: 'post',
         dataType: 'json',
         success: function(param) {
            if(param.result == 'logout') {
               alert('로그인 후 사용하세요!');
               message_socket.close();
            } else if(param.result == 'success') {
               $('.rooms-list').empty();
               
               $(param.list).each(function(index, item) {
            	   let output = '<div class="room-item" data-room-num="' + item.chatroom_num + '" data-accom-num="' + item.accom_num + '">';
                  
                  // 상대방 이름 표시 (로그인한 유저가 누구인지에 따라)
                  if(${user_num} == item.user_num) {
                     output += '<div class="room-title">' + item.host_name;
                  } else {
                     output += '<div class="room-title">' + item.user_id;
                  }
                  
                  // 읽지 않은 메시지 표시
                  if(item.unread_cnt > 0) {
                     output += '<span class="unread-badge">' + item.unread_cnt + '</span>';
                  }
                  
                  output += '</div>';
                  
                  // 숙소 정보
                  output += '<div class="room-accom">[' + item.accom_name + ']</div>';
                  
                  // 마지막 메시지 미리보기
                  if(item.message) {
                     output += '<div class="room-preview">' + item.message + '</div>';
                  }
                  
                  // 마지막 메시지 시간
                  if(item.chat_date) {
                     // 날짜 형식에서 시간만 추출
                     const timeOnly = item.chat_date.split(' ')[1];
                     output += '<div class="room-time">' + timeOnly + '</div>';
                  }
                  
                  output += '</div>';
                  
                  $('.rooms-list').append(output);
                  
                  // 현재 선택된 채팅방 표시
                  if(item.chatroom_num == $('#chatroom_num').val()) {
                     $('.room-item[data-room-num="' + item.chatroom_num + '"]').addClass('active');
                  }
                  
               });
               $('.rooms-list').off('click', '.room-item').on('click', '.room-item', function() {
                   const accomNum = $(this).data('accom-num');
                   location.href = '${pageContext.request.contextPath}/accom/chatDetail.do?accom_num=' + accomNum;
                });
               
            } else {
               alert('채팅방 목록 호출 오류');
            }
         },
         error: function() {
            alert('네트워크 오류 발생');
         }
      });
   }
   
   
   // 페이지 로드 시 채팅방 목록 로드
   loadChatRooms();
   
   
   // 페이지 로드 시 채팅창으로 포커스
   $('#message').focus();
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
    
    <div class="content-main chat-wrapper">
        <h2>
            [${AccomChatRoom.accom_name}]의 호스트 <small>${AccomChatRoom.host_name}</small>님과 채팅
        </h2>
        
        <div class="chat-layout">
            <!-- 채팅방 목록 -->
            <div class="chat-rooms-list">
                <div class="rooms-header">
                    <h3>채팅방 목록</h3>
                </div>
                <div class="rooms-list">
                    <!-- 채팅방 목록은 JavaScript에서 동적으로 로드됩니다 -->
                </div>
            </div>
            
            <!-- 채팅 영역 -->
            <div class="chat-container">
                <div class="chat-header">
                    <div class="chat-status">온라인</div>
                </div>
                
                <div id="chatting_message"></div>
                
                <form id="chatting_form">
                    <input type="hidden" name="chatroom_num" value="${AccomChatRoom.chatroom_num}" id="chatroom_num">
                    <div class="message-input-container">
                        <textarea name="message" id="message" placeholder="메시지를 입력하세요..."></textarea>
                        <button type="submit" class="send-button">
                            <i class="fas fa-paper-plane"></i>
                            <span>전송</span>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>