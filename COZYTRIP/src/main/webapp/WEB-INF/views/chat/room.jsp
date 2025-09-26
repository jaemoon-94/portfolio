<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>CozyTrip - 채팅방</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/sh.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
/* 채팅방 스타일 */
.container {
    max-width: 1200px;
    margin: 20px auto;
    padding: 0 15px;
}

.chat-container {
    border: 1px solid #e1e1e1;
    border-radius: 10px;
    overflow: hidden;
    background-color: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.chat-header {
    background-color: #f8f9fa;
    border-bottom: 1px solid #e1e1e1;
    padding: 15px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.chat-header .group-info {
    display: flex;
    align-items: center;
}

.chat-header .group-info img {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 12px;
    object-fit: cover;
    background-color: #f0f0f0;
    border: 1px solid #e1e1e1;
}

.chat-header h3 {
    margin: 0;
    font-size: 18px;
    color: #333;
    font-weight: 600;
}

.layout {
    display: flex;
    height: 600px;
}

.main-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    border-right: 1px solid #e1e1e1;
}

.group-description {
    padding: 15px 20px;
    background-color: #f9f9f9;
    border-bottom: 1px solid #e1e1e1;
    font-size: 14px;
    color: #555;
}

.group-description p {
    margin: 10px 0 0 0;
    font-size: 13px;
    color: #666;
    line-height: 1.5;
}

.chat-messages {
    flex: 1;
    overflow-y: auto;
    padding: 20px;
    background-color: #fff;
}

.date-divider {
    text-align: center;
    margin: 15px 0;
    position: relative;
}

.date-divider span {
    background-color: #f5f5f5;
    padding: 0 10px;
    color: #888;
    font-size: 12px;
    position: relative;
    z-index: 1;
}

.date-divider:before {
    content: '';
    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    height: 1px;
    background-color: #ddd;
    z-index: 0;
}

.message {
    margin-bottom: 20px;
    max-width: 70%;
    display: flex;
    flex-direction: column;
    position: relative;
}

.my-message {
    margin-left: auto;
    align-items: flex-end;
}

.other-message {
    margin-right: auto;
    align-items: flex-start;
}

.message-info {
    font-size: 13px;
    color: #666;
    margin-bottom: 5px;
    font-weight: 500;
}

.message-content {
    padding: 12px 16px;
    border-radius: 12px;
    position: relative;
    max-width: 100%;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.message-content span {
    word-break: break-word;
    line-height: 1.4;
}

.message-time {
    font-size: 11px;
    color: #888;
    margin-top: 5px;
    display: inline-block;
}

.my-message .message-content {
    background-color: #FFF200; /* 노란색 말풍선 */
    color: #333;
    border-top-right-radius: 4px;
}

.my-message .message-time {
    color: #888;
    text-align: right;
}

.other-message .message-content {
    background-color: #FF9999; /* 분홍색 말풍선 */
    color: #333;
    border-top-left-radius: 4px;
}

.chat-form {
    display: flex;
    padding: 15px;
    background-color: white;
    border-top: 1px solid #e1e1e1;
    align-items: center;
}

.chat-form textarea {
    flex: 1;
    border: 1px solid #e1e1e1;
    border-radius: 20px;
    padding: 12px 15px;
    resize: none;
    outline: none;
    font-family: inherit;
    font-size: 14px;
    max-height: 80px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05) inset;
}

.chat-form button {
    background-color: #FF9999; /* 분홍색 버튼 */
    color: #333;
    border: none;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    margin-left: 10px;
    cursor: pointer;
    transition: background-color 0.2s;
    font-weight: 500;
    display: flex;
    align-items: center;
    justify-content: center;
}

.chat-form button:hover {
    background-color: #FF7777;
}

.chat-members {
    width: 250px;
    padding: 15px;
    background-color: #f9f9f9;
    overflow-y: auto;
    display: flex;
    flex-direction: column;
}

.chat-members h4, .chat-members h5 {
    margin-top: 0;
    padding-bottom: 10px;
    border-bottom: 1px solid #ddd;
    font-size: 16px;
    color: #333;
}

.chat-members h5 {
    margin-top: 20px;
    font-size: 15px;
}

.chat-members ul {
    list-style: none;
    padding: 0;
    margin: 0;
}

.chat-members li {
    padding: 8px 0;
    border-bottom: 1px solid #eee;
    display: flex;
    align-items: center;
}

.member-avatar {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    background-color: #4a89dc;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 10px;
    font-weight: bold;
}

.group-details {
    margin-top: auto;
    padding-top: 15px;
    border-top: 1px solid #ddd;
}

.group-details p {
    margin: 8px 0;
    font-size: 13px;
    color: #666;
}
</style>
</head>
<body>
<!-- 상단 네비게이션 바 -->
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="container">
    <div class="chat-container">
        <div class="chat-header">
            <div class="group-info">
                <c:if test="${not empty group}">
                    <img src="${pageContext.request.contextPath}/images/travel-group-icon.png" alt="동행그룹" onerror="this.src='${pageContext.request.contextPath}/images/default-group.png'">
                    <h3>동행 채팅 - ${group.tg_title}</h3>
                </c:if>
                <c:if test="${empty group}">
                    <img src="${pageContext.request.contextPath}/images/chat-icon.png" alt="채팅" onerror="this.src='${pageContext.request.contextPath}/images/default-chat.png'">
                    <h3>채팅방 ${chatRoom.chatroom_num}</h3>
                </c:if>
            </div>
            <div>
                <c:if test="${not empty group}">
                    <a href="${pageContext.request.contextPath}/travelgroup/detail.do?group_num=${group.group_num}" class="btn btn-sm btn-light">동행그룹 보기</a>
                </c:if>
            </div>
        </div>
        
        <div class="layout">
            <div class="main-content">
                <c:if test="${not empty group}">
                    <div class="group-description">
                        <strong>여행 일정:</strong> ${group.tg_start_date} ~ ${group.tg_end_date} · 
                        <strong>여행지:</strong> ${group.tg_region}
                        <p>${group.tg_content}</p>
                    </div>
                </c:if>
                
                <div class="chat-messages" id="chat-messages">
                    <!-- 메시지 목록이 여기에 동적으로 추가됨 -->
                    <c:if test="${not empty messageList}">
                        <c:set var="prevDate" value="" />
                        <c:forEach var="message" items="${messageList}">
                            <c:set var="currentDate" value="${fn:substring(message.message_date, 0, 10)}" />
                            
                            <!-- 날짜가 바뀌면 날짜 구분선 추가 -->
                            <c:if test="${prevDate ne currentDate}">
                                <div class="date-divider">
                                    <span>${currentDate}</span>
                                </div>
                                <c:set var="prevDate" value="${currentDate}" />
                            </c:if>
                            
                            <!-- 메시지 표시 -->
                            <div class="message ${message.sender_num eq user_num ? 'my-message' : 'other-message'}">
                                <c:if test="${message.sender_num ne user_num}">
                                    <div class="message-info">
                                        ${message.sender_name}
                                    </div>
                                </c:if>
                                <div class="message-content">
                                    <span>${message.message}</span>
                                    <div class="message-time">${fn:substring(message.message_date, 11, 16)}</div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
                
                <form class="chat-form" id="chat-form">
                    <input type="hidden" id="chatroom_num" name="chatroom_num" value="${chatRoom.chatroom_num}">
                    <input type="hidden" id="user_num" name="user_num" value="${user_num}">
                    <textarea id="message" name="message" placeholder="메시지를 입력하세요..." rows="1" required></textarea>
                    <button type="submit"><i class="fas fa-paper-plane"></i></button>
                </form>
            </div>
            
            <div class="chat-members">
                <h4>참여자 목록</h4>
                <ul>
                    <c:forEach var="member" items="${memberList}">
                        <li>
                            <div class="member-avatar">${fn:substring(member.user_name, 0, 1)}</div>
                            ${member.user_name} ${member.user_num eq user_num ? '<span style="color:#4a89dc;">(나)</span>' : ''}
                        </li>
                    </c:forEach>
                </ul>
                
                <c:if test="${not empty group}">
                    <div class="group-details">
                        <h5>동행 정보</h5>
                        <p><strong>인원:</strong> ${group.tg_current_cnt}/${group.tg_max_cnt}명</p>
                        <p><strong>상태:</strong> 
                            <c:choose>
                                <c:when test="${group.tg_status == 0}">모집중</c:when>
                                <c:when test="${group.tg_status == 1}">모집완료</c:when>
                                <c:when test="${group.tg_status == 2}">여행중</c:when>
                                <c:when test="${group.tg_status == 3}">여행완료</c:when>
                                <c:otherwise>미정</c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$(function() {
    // 웹소켓 연결
    // 웹소켓 연결 - 프로토콜 자동 감지
    const protocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://';
    // 컨텍스트 경로 추출 및 포함
    const contextPath = '${pageContext.request.contextPath}';
    const socket = new WebSocket(protocol + window.location.host + contextPath + "/websocket-chat");
    console.log("WebSocket 연결 시도: " + protocol + window.location.host + contextPath + "/websocket-chat");
    // 컨텍스트 경로 포함
    console.log("엔드포인트 통일 - /websocket-chat 사용");
    
    // 웹소켓 이벤트 핸들러
    socket.onopen = function() {
        console.log("WebSocket 연결됨");
        // 채팅방 입장 메시지 전송
        socket.send("join:${chatRoom.chatroom_num}:${user_num}");
        // 초기 메시지 로드 (기존 메시지 불러오기)
        selectData();
    };
    
    socket.onmessage = function(event) {
        console.log("메시지 수신: " + event.data);
        
        try {
            // JSON 형태로 수신된 메시지 파싱
            const data = JSON.parse(event.data);
            
            if(data.type === "chat") {
                // 새 메시지 추가 (새로고침 없이 바로 표시)
                appendMessage(data);
            } else if(data.type === "join") {
                // 입장 알림 (필요한 경우 처리)
                console.log("사용자 입장: " + data.user_num);
            }
        } catch(e) {
            console.error("메시지 파싱 오류:", e);
        }
    };
    
    socket.onclose = function() {
        console.log("WebSocket 연결 종료");
    };
    
    socket.onerror = function(error) {
        console.error("WebSocket 오류:", error);
    };
    
    // 새 메시지 추가 함수
    function appendMessage(item) {
        let output = '';
        let chat_date = $('.date-divider:last span').text() || '';
        
        // 날짜 추출 및 날짜가 바뀌면 날짜 구분선 추가
        if(chat_date !== item.message_date) {
            output += '<div class="date-divider"><span>' + item.message_date + '</span></div>';
        }
        
        // 메시지 추가 (내 메시지인지 상대방 메시지인지 구분)
        if(item.sender_num == "${user_num}") { // 내 메시지
            output += '<div class="message my-message">';
        } else { // 상대방 메시지
            output += '<div class="message other-message">';
            output += '<div class="message-info">' + item.sender_name + '</div>';
        }
        
        output += '<div class="message-content">';
        output += '<span>' + item.message + '</span>';
        output += '<div class="message-time">' + item.message_time + '</div>';
        output += '</div>';
        output += '</div>';
        
        // 메시지 추가
        $("#chat-messages").append(output);
        
        // 스크롤을 가장 아래로 이동
        scrollToBottom();
    }
    
    // 채팅 메시지 표시 함수
    function selectData() {
        $.ajax({
            url: "${pageContext.request.contextPath}/chat/messageList.do",
            type: "post",
            data: {
                chatroom_num: $("#chatroom_num").val()
            },
            dataType: "json",
            success: function(param) {
                if(param.result == "logout") {
                    alert("로그인 후 사용하세요!");
                    socket.close();
                } else if(param.result == "success") {
                    // 기존 메시지 목록 비우기
                    $("#chat-messages").empty();
                    
                    let chat_date = '';
                    $(param.list).each(function(index, item) {
                        let output = '';
                        
                        // 날짜 추출 및 날짜가 바뀌면 날짜 구분선 추가
                        if(item.new_date || chat_date != item.message_date) {
                            chat_date = item.message_date;
                            output += '<div class="date-divider"><span>' + chat_date + '</span></div>';
                        }
                        
                        // 메시지 추가 (내 메시지인지 상대방 메시지인지 구분)
                        if(item.sender_num == "${user_num}") { // 내 메시지
                            output += '<div class="message my-message">';
                        } else { // 상대방 메시지
                            output += '<div class="message other-message">';
                            output += '<div class="message-info">' + item.sender_name + '</div>';
                        }
                        
                        output += '<div class="message-content">';
                        output += '<span>' + item.message + '</span>';
                        output += '<div class="message-time">' + item.message_time + '</div>';
                        output += '</div>';
                        output += '</div>';
                        
                        // 메시지 추가
                        $("#chat-messages").append(output);
                    });
                    
                    // 스크롤을 가장 아래로 이동
                    scrollToBottom();
                } else {
                    alert("채팅 메시지 호출 오류");
                    socket.close();
                }
            },
            error: function() {
                alert("네트워크 오류 발생");
                socket.close();
            }
        });
    }
    
    // 채팅 폼 제출 이벤트 처리
    $("#chat-form").submit(function(e) {
        e.preventDefault(); // 기본 제출 동작 방지
        
        // 폼 데이터 가져오기
        const chatroom_num = $("#chatroom_num").val();
        const user_num = $("#user_num").val();
        const message = $("#message").val().trim();
        
        if(message === "") return; // 메시지가 비어있으면 무시
        
        // WebSocket으로 메시지 전송 (사용자 이름 포함)
        socket.send("chat:" + chatroom_num + ":" + user_num + ":${user_name}:" + message);
        
        // 메시지 입력창 초기화
        $("#message").val("").focus();
    });
    
    // 엔터 키로 폼 제출
    $("#message").keydown(function(event) {
        if(event.keyCode == 13 && !event.shiftKey) {
            event.preventDefault();
            $("#chat-form").submit();
        }
    });
    
    // 스크롤을 가장 아래로 이동하는 함수
    function scrollToBottom() {
        let chatMessages = document.getElementById("chat-messages");
        chatMessages.scrollTop = chatMessages.scrollHeight;
    }
    
    // 초기 스크롤 이동
    setTimeout(function() {
        scrollToBottom();
    }, 100);
});
</script>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>