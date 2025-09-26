<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 캐시 관련 메타 태그 제거 -->
    <title>동행 그룹 상세 정보</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/sh.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/chat.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/travelgroup.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
    <!-- 헤더 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <div class="page-container">
        <!-- 왼쪽: 그룹 정보 패널 -->
        <div class="group-info-panel">
            <div class="group-header">
                <h2 class="group-title">${group.tg_title}</h2>
                <div class="group-status">
                    <span class="status-badge ${group.status == 0 ? 'status-recruiting' : 
                                               group.status == 1 ? 'status-closed' : 'status-completed'}">
                        ${group.status == 0 ? '모집중' : 
                          group.status == 1 ? '모집마감' : '동행종료'}
                    </span>
                </div>
            </div>
            
            <div class="group-details">
                <div class="detail-item">
                    <img src="${pageContext.request.contextPath}/images/icon/location.png" alt="지역" class="detail-icon">
                    <span>지역: 
                        <c:choose>
                            <c:when test="${group.group_num == 121}">부산</c:when>
                            <c:when test="${group.region_num == 1}">서울</c:when>
                            <c:when test="${group.region_num == 2}">경기</c:when>
                            <c:when test="${group.region_num == 3}">인천</c:when>
                            <c:when test="${group.region_num == 4}">강원</c:when>
                            <c:when test="${group.region_num == 5}">충북</c:when>
                            <c:when test="${group.region_num == 6}">충남</c:when>
                            <c:when test="${group.region_num == 7}">대전</c:when>
                            <c:when test="${group.region_num == 8}">경북</c:when>
                            <c:when test="${group.region_num == 9}">경남</c:when>
                            <c:when test="${group.region_num == 10}">대구</c:when>
                            <c:when test="${group.region_num == 11}">울산</c:when>
                            <c:when test="${group.region_num == 12}">부산</c:when>
                            <c:when test="${group.region_num == 13}">전북</c:when>
                            <c:when test="${group.region_num == 14}">전남</c:when>
                            <c:when test="${group.region_num == 15}">광주</c:when>
                            <c:when test="${group.region_num == 16}">제주</c:when>
                            <c:otherwise>기타</c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div class="detail-item">
                    <img src="${pageContext.request.contextPath}/images/icon/calender.png" alt="여행 일정" class="detail-icon">
                    <span>여행 일정: 
                        <fmt:formatDate value="${group.travel_date_start}" pattern="yyyy-MM-dd"/>
                        ~ 
                        <fmt:formatDate value="${group.travel_date_end}" pattern="yyyy-MM-dd"/>
                    </span>
                </div>
                <div class="detail-item">
                    <img src="${pageContext.request.contextPath}/images/icon/group.png" alt="모집 인원" class="detail-icon">
                    <span>모집 인원: ${group.member_count}/${group.max_member_count == 0 ? '제한없음' : group.max_member_count}</span>
                </div>
            </div>

            <!-- 동행그룹 내용 -->
            <div class="group-content">
                <h3>동행 내용</h3>
                <div class="content-box">
                    <p>${group.tg_content}</p>
                </div>
            </div>

            <div class="member-list">
                <h3>멤버 목록</h3>
                <div id="memberContainer" class="member-container">
                    <!-- 멤버 목록이 동적으로 추가됨 -->
                </div>
            </div>
        </div>

        <!-- 채팅 컨테이너 -->
        <div class="chat-section">
            <div class="chat-header">
                <div class="chat-header-content">
                    <h3>동행 채팅</h3>
                    <div class="header-buttons">
                        <c:if test="${sessionScope.user_num == group.creator_num}">
                            <c:if test="${group.status == 0}">
                                <button class="group-btn close-group-btn" onclick="confirmClose('${group.group_num}')">
                                    모집 마감
                                </button>
                            </c:if>
                            <button class="group-btn delete-group-btn" onclick="confirmDelete('${group.group_num}')">
                                그룹 삭제
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            
            <!-- 채팅 메시지 -->
            <div id="chatMessages" class="chat-messages"></div>
            
            <!-- 채팅 입력 -->
            <form id="chatting_form">
                <input type="hidden" name="chatroom_num" value="${group.group_num}">
                <div class="chat-input">
                    <textarea id="messageInput" name="message" placeholder="메시지를 입력하세요..." rows="1"></textarea>
                    <button type="submit"><img src="${pageContext.request.contextPath}/images/icon/send.png" alt="전송" class="send-icon"></button>
                </div>
            </form>
        </div>
    </div>

    <!-- 푸터 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>

    <!-- 채팅 스크립트 -->
    <script type="text/javascript">
    // 동행그룹 삭제 확인 함수
    function confirmDelete(group_num) {
        if(confirm('정말 이 동행그룹을 삭제하시겠습니까?\n삭제하면 모든 채팅 내용과 멤버 정보가 함께 삭제됩니다.')) {
            location.href = '${pageContext.request.contextPath}/travelgroup/delete.do?group_num=' + group_num;
        }
    }
    
    // 동행그룹 모집 마감 확인 함수
    function confirmClose(group_num) {
        if(confirm('정말 이 동행그룹의 모집을 마감하시겠습니까?\n모집이 마감되면 새로운 멤버가 참여할 수 없습니다.')) {
            location.href = '${pageContext.request.contextPath}/travelgroup/close.do?group_num=' + group_num;
        }
    }
    $(function() {
        // 변수 선언
        var chatroom_num = ${group.group_num};
        var user_num = ${sessionScope.user_num};
        var user_name = "${sessionScope.user_id}";
        
        // 세션 값 디버깅
        // 세션 정보 초기화
        
        // 웹소켓 연결
        var protocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://';
        var contextPath = '${pageContext.request.contextPath}';
        var wsUrl = protocol + window.location.host + contextPath + "/websocket-chat";
        
        // WebSocket 연결 시도
        
        // 웹소켓 객체 생성
        var socket = new WebSocket(wsUrl);
        
        // 웹소켓 이벤트 핸들러 - 연결 시
        socket.onopen = function() {
            // WebSocket 연결 성공
            alert("그룹채팅에 입장하셨습니다!\n전송을 원하는 메시지를 입력 해 주세요.");
            
            // 채팅방 입장 메시지 전송
            socket.send("join:" + chatroom_num + ":" + user_num);
            
            // 초기 메시지 로드
            selectData();
        };
        
        // 웹소켓 이벤트 핸들러 - 메시지 수신 시
        socket.onmessage = function(event) {
            console.log("메시지 수신: " + event.data);
            
            try {
                // JSON 형태로 수신된 메시지 파싱
                var data = JSON.parse(event.data);
                console.log("파싱된 데이터:", data);
                
                // 대소문자 구분 없이 타입 비교
                var messageType = data.type ? data.type.toLowerCase() : "";
                
                if(messageType === "chat" || messageType === "CHAT") {
                    // 새 메시지 추가 (새로고침 없이 바로 표시)
                    appendMessage(data);
                } else if(messageType === "join" || messageType === "JOIN") {
                    // 입장 알림 (필요한 경우 처리)
                } else {
                    // 알 수 없는 메시지 타입 처리
                }
            } catch(e) {
                // 메시지 파싱 오류 처리
            }
        };
        
        // 웹소켓 이벤트 핸들러 - 연결 종료 시
        socket.onclose = function(event) {
            // WebSocket 연결 종료
        };
        
        // 웹소켓 이벤트 핸들러 - 오류 발생 시
        socket.onerror = function(error) {
            console.error("WebSocket 오류:", error);
            alert("웹소켓 연결 실패");
        };
        
        // 새 메시지 추가 함수
        function appendMessage(item) {
            var output = '';
            var chat_date = $('.date-position:last span').text() || '';
            
            // 날짜 추출 및 날짜가 바뀌면 날짜 구분선 추가
            if(chat_date !== item.message_date) {
                output += '<div class="date-position"><span>' + item.message_date + '</span></div>';
            }
            
            // 메시지 추가 (내 메시지인지 상대방 메시지인지 구분)
            if(item.sender_num == user_num) { // 내 메시지
                output += '<div class="message sent">';
            } else { // 상대방 메시지
                output += '<div class="message received">';
                // 상대방 메시지일 경우에만 발신자 이름 표시 (메시지 위에 표시)
                output += '<div class="sender-name">' + (item.sender_id || item.sender_name || user_name || '익명') + '</div>';
            }
            
            output += '<div class="message-content">' + item.message + '</div>';
            output += '<div class="message-info">';
            output += '<span class="message-time">' + item.message_time + '</span>';
            output += '</div>';
            output += '</div>';
            
            // 메시지 추가
            $("#chatMessages").append(output);
            
            // 스크롤을 가장 아래로 이동
            scrollToBottom();
        }
        
        // 스크롤을 가장 아래로 이동하는 함수
        function scrollToBottom() {
            $("#chatMessages").scrollTop($("#chatMessages")[0].scrollHeight);
        }
        
        // 채팅 메시지 표시 함수 (초기 로드용)
        function selectData() {
            $.ajax({
                url: '${pageContext.request.contextPath}/chat/messageList.do',
                type: 'post',
                data: {"chatroom_num": chatroom_num},
                dataType: 'json',
                success: function(param) {
                    if(param.result == 'logout') {
                        alert('로그인 후 사용하세요');
                        socket.close();
                    } else if(param.result == 'success') {
                        $('#chatMessages').empty();
                        
                        var chat_date = '';
                        $(param.list).each(function(index, item) {
                            var output = '';
                            
                            // 날짜 추출
                            if(chat_date != item.message_date.split(' ')[0]) {
                                chat_date = item.message_date.split(' ')[0];
                                output += '<div class="date-position"><span>' + chat_date + '</span></div>';
                            }
                            
                            // 채팅 메시지 작성자와 로그인한 회원이 같은지 체크
                            if(item.sender_num == user_num) {
                                output += '<div class="message sent">';
                            } else {
                                output += '<div class="message received">';
                                // 상대방 메시지일 경우에만 발신자 이름 표시
                                if(item.sender_name) {
                                    output += '<div class="sender-name">' + item.sender_name + '</div>';
                                }
                            }
                            
                            output += '<div class="message-content">' + item.message + '</div>';
                            output += '<div class="message-info">';
                            output += '<span class="message-time">' + item.message_date.split(' ')[1] + '</span>';
                            output += '</div>';
                            output += '</div>';
                            
                            $('#chatMessages').append(output);
                        });
                        
                        // 스크롤을 하단으로 이동
                        scrollToBottom();
                    } else {
                        alert('채팅 메시지 불러오기 오류 발생');
                        socket.close();
                    }
                },
                error: function() {
                    alert('네트워크 오류 발생');
                    socket.close();
                }
            });
        }
        
        // 채팅 메시지 전송
        $('#chatting_form').submit(function(event) {
            event.preventDefault();
            
            var message = $('#messageInput').val().trim();
            
            if(message === '') {
                alert('내용을 입력하세요');
                $('#messageInput').val('').focus();
                return false;
            }
            
            try {
                // WebSocket으로 메시지 전송 (사용자 이름 포함)
                if(socket.readyState === WebSocket.OPEN) {
                    socket.send("chat:" + chatroom_num + ":" + user_num + ":" + user_name + ":" + message);
                    // 메시지 입력창 초기화
                    $('#messageInput').val('').focus();
                } else {
                    // WebSocket 연결이 닫힘
                    alert("연결이 끊어졌습니다. 페이지를 새로고침하세요.");
                }
            } catch(e) {
                // 메시지 전송 오류 발생
                alert("메시지 전송 중 오류가 발생했습니다.");
            }
        });
        
        // 엔터 키로 폼 제출
        $('#messageInput').keydown(function(event) {
            if(event.keyCode == 13 && !event.shiftKey) {
                event.preventDefault();
                $('#chatting_form').submit();
            }
        });
        
        // 멤버 목록 가져오기
        function getMemberList() {
            $.ajax({
                url: '${pageContext.request.contextPath}/travelgroup/memberList.do',
                type: 'post',
                data: {"group_num": ${group.group_num}},
                dataType: 'json',
                success: function(param) {
                    if(param.result == 'success') {
                        $('#memberContainer').empty();
                        
                        $(param.list).each(function(index, item) {
                            var output = '<div class="member-item">';
                            output += '<span class="member-name">' + item.name + '</span>';
                            if(item.user_num == ${group.creator_num}) {
                                output += ' <span class="member-role">(방장)</span>';
                            }
                            output += '</div>';
                            
                            $('#memberContainer').append(output);
                        });
                    } else {
                        alert('멤버 목록 불러오기 오류 발생');
                    }
                },
                error: function() {
                    alert('네트워크 오류 발생');
                }
            });
        }
        
        // 초기 데이터 로드
        getMemberList();
        
        // 페이지 언로드 시 웹소켓 연결 종료
        $(window).on('beforeunload', function() {
            if(socket && socket.readyState === WebSocket.OPEN) {
                socket.close();
            }
        });
    });
    </script>
</body>
</html>