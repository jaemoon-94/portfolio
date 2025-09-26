<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>QnA</title>
<style>
/* 테이블 스타일 */
.board-table {
    width: 100%;
    border-collapse: collapse;
}

.table-header {
    display: flex;
    background-color: #f9f9f9;
    border-bottom: 1px solid #ddd;
    padding: 12px 20px;
    font-weight: 500;
    font-size: 14px;
    color: #666;
}

.col-status {
    flex: 0 0 100px;
    text-align: center;
}

.col-title {
    flex: 1;
    padding-left: 10px;
}

.col-writer {
    flex: 0 0 120px;
    text-align: center;
}

.col-date {
    flex: 0 0 100px;
    text-align: center;
}

/* Q&A 아이템 스타일 */
.qa-item {
    border-bottom: 1px solid #eee;
}

.qa-header {
    display: flex;
    padding: 15px 20px;
    align-items: center;
    cursor: pointer;
    transition: background-color 0.2s;
}

.qa-header:hover {
    background-color: #f8f8f8;
}

.qa-header .col-status {
    font-weight: 500;
}

.qa-header .col-title {
    font-size: 14px;
}

.qa-header .col-writer {
    font-size: 13px;
    color: #777;
}

.qa-header .col-date {
    font-size: 13px;
    color: #777;
}

/* Q&A 내용 부분 */
.qa-content {
    background-color: #f9f9f9;
    padding: 20px 20px 20px 120px;
    display: none; /* 기본적으로 숨김 */
}

.question {
    margin-bottom: 20px;
    font-size: 14px;
    line-height: 1.6;
}

.answer {
    position: relative;
    background-color: #fff;
    border: 1px solid #eee;
    border-radius: 5px;
    padding: 20px;
}

.answer-label {
    background-color: #333;
    color: white;
    font-size: 12px;
    padding: 2px 8px;
    border-radius: 3px;
    position: absolute;
    left: -40px;
    top: 0;
}

.answer-content {
    font-size: 14px;
    line-height: 1.6;
    margin-bottom: 10px;
}

.answer-info {
    display: flex;
    justify-content: space-between;
    color: #777;
    font-size: 13px;
    margin-top: 15px;
    padding-top: 10px;
    border-top: 1px solid #eee;
}

/* 활성화된 QA 아이템 */
.qa-item.active .qa-content {
    display: block;
}

/* 반응형 스타일 */
@media (max-width: 768px) {
    .board-header {
        flex-direction: column;
        align-items: flex-start;
    }
    
    .board-options {
        margin-top: 15px;
    }
    
    .col-status {
        flex: 0 0 70px;
    }
    
    .col-writer, .col-date {
        flex: 0 0 100px;
    }
    .col-delete {
        flex: 0 0 70px;
    }
    .qa-content {
        padding: 20px;
    }
    
    .answer-label {
        position: relative;
        left: 0;
        display: inline-block;
        margin-bottom: 10px;
    }
}

@media (max-width: 576px) {
    .table-header .col-writer,
    .table-header .col-date,
    .qa-header .col-writer,
    .qa-header .col-date {
        display: none;
    }
    
    .col-title {
        flex: 1;
    }
}
/* 삭제 버튼 스타일 */
.delete-btn {
    background-color: #ff5a5a;
    color: white;
    border: none;
    border-radius: 4px;
    padding: 5px 10px;
    font-size: 12px;
    cursor: pointer;
    transition: background-color 0.2s;
}
.delete-btn:disabled {
    background-color: #d3d3d3; 
    color: #a1a1a1;             
    cursor: not-allowed;      
}

.delete-btn:hover {
    background-color: #e04040;
}
.delete-btn:disabled:hover {
    background-color: #d3d3d3; 
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/yn.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/hostHeader.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="container">
    <div style="margin-top:20px;">
	        <h3 style="margin:15px 0px 15px 0px;">나의 QnA</h3>
	        <div class="board-table">
	            <div class="table-header">
	                <div class="col-status">숙소명</div>
	                <div class="col-status">답변상태</div>
	                <div class="col-title">제목</div>
	                <div class="col-date">작성일</div>
	                <div class="col-delete">삭제</div>
	            </div>
	            <!-- 게시글 항목들 -->
	            <c:forEach var="inqList" items="${inqList}">
	            <div class="qa-item">
				    <div class="qa-header">
	                    <div class="col-status">${inqList.accom_name}</div>
	                    <div class="col-status">${inqList.inq_status == 0 ? '답변 전' : '답변 완료'}</div>
	                    <div class="col-title">${inqList.inq_content}</div>
	                    <div class="col-date">${inqList.inq_date}</div>
	                    <div class="col-delete">
		                    <form method="post" action="${pageContext.request.contextPath}/user/myPage/deleteInquiry.do" style="display:inline;">
							    <input type="hidden" name="inquiry_num" value="${inqList.inquiry_num}" />
							    <button class="delete-btn" type="submit" 
							            onclick="return confirm('정말 삭제하시겠습니까?');"
							            <c:if test="${not empty inqList.answer_content}"> disabled</c:if>>
							        삭제
							    </button>
							</form>
                        </div>
	                </div>
	                <div class="qa-content">
	                    <div class="question">
	                        <p>${inqList.inq_content}</p>
	                    </div>
	                    <c:if test="${not empty inqList.answer_content }">
	                    <div class="answer">
	                        <div class="answer-label">답변</div>
	                        <div class="answer-content">
	                            <p>${inqList.answer_content}</p>
	                        </div>
	                        <div class="answer-info">
	                            <span class="answerer">판매자</span>
	                            <span class="answer-date">${inqList.answer_date}</span>
	                        </div>
	                    </div>
	                    </c:if>
	                </div>
	            </div>
	            </c:forEach>
	            <div style="text-align: center;">
	            	${page}
	            </div>
	        </div>
        </div>
</div>
<script>
$(document).ready(function() {
    // Q&A 항목 클릭 시 내용 토글
    $('.qa-header').on('click', function() {
        // 현재 클릭한 요소의 부모(qa-item)를 찾아서 active 클래스 토글
        const parent = $(this).parent();
        
        // 이미 열려있는 항목인지 확인
        const isActive = parent.hasClass('active');
        
        // 모든 항목을 닫음
        $('.qa-item').removeClass('active');
        $('.qa-content').slideUp(300);
        
        // 닫혀있던 항목이라면 열기
        if (!isActive) {
            parent.addClass('active');
            parent.find('.qa-content').slideDown(300);
        }
    });
    // 드롭다운 메뉴 기능 (실제 구현 시 추가 가능)
    $('.dropdown-button').on('click', function(e) {
        e.stopPropagation();
        // 여기에 드롭다운 메뉴 표시 코드 추가
    });
    
    // 문서 클릭 시 드롭다운 메뉴 닫기
    $(document).on('click', function() {
        // 드롭다운 메뉴 닫기 코드 추가
    });
});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>