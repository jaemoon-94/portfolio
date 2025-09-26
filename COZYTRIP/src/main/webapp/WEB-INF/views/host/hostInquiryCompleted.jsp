<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답변 대기 문의</title>
<style>
.tabs { display: flex; border-bottom: 2px solid #ccc; margin: 20px 0 20px 0; }
.tab {
    padding: 10px 20px;
    text-decoration: none;
    color: black;
    border: 1px solid #ccc;
    border-bottom: none;
    background: #f4f4f4;
}
.tab.active {
    background: white;
    font-weight: bold;
    border-top: 2px solid #fa6767;
    border-left: 2px solid #fa6767;
    border-right: 2px solid #fa6767;
}
.inquiry {
    background: white;
    border: 1px solid #ccc;
    border-radius: 6px;
    padding: 15px;
    margin-bottom: 20px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
}
.inquiry-header {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}
.inquiry-accom {
    font-weight: bold;
    font-size: 16px;
    color: #fa6767;
}
.inquiry-date {
    color: #777;
    font-size: 14px;
}
.inquiry-user {
    font-weight: bold;
    margin-bottom: 5px;
}
.inquiry-content {
    background: #f9f9f9;
    padding: 12px;
    border-radius: 4px;
    margin-bottom: 15px;
    border-left: 3px solid #007bff;
}
.inquiry-content {
    background: #f9f9f9;
    padding: 12px;
    border-radius: 4px;
    margin-bottom: 15px;
    border-left: 3px solid #fa6767;
}
.answer-box {
    margin-top: 10px;
    width: 100%;
}
.answer-box textarea {
    width: 100%;
    height: 80px;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    resize: vertical;
    box-sizing: border-box;
}
.answer-box button {
    margin-top: 10px;
    padding: 8px 16px;
    background-color: #fa6767;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    float: right;
}
.answer-box:after {
    content: "";
    display: table;
    clear: both;
}
.answer-box button:hover {
    background-color: #fa6767;
}
.status-select {
    padding: 8px 12px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 6px;
    font-size: 14px;
    background-color: white;
    width: 200px;
}
.no-inquiries {
    text-align: center;
    padding: 30px;
    color: #777;
}
.paging{
	text-align: center;
}

.search-form button {
    padding: 8px 16px;
    background-color: #fa6767;
    border: none;
    border-radius: 6px;
    color: white;
    font-weight: bold;
    cursor: pointer;
    height:40px;
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/yn.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/hostHeader.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

</head>
<body>
<jsp:include page="/WEB-INF/views/host/hostHeader.jsp"/>
<div class="container">
    <h2>문의 관리</h2>

    <div class="tabs">
        <a href="inquiryWaiting.do" class="tab">답변 대기</a>
        <a href="inquiryCompleted.do" class="tab active">답변 완료</a>
    </div>
    <form action="inquiryCompleted.do" method="get" class="search-form">
	    <select id="accomSelect" name="accom" class="status-select">
	        <option value="">숙소 선택</option>
	        <c:forEach var="myAccomList" items="${myAccomList}">
	            <option value="${myAccomList.accom_num}">
	                ${myAccomList.accom_name}
	            </option>
	        </c:forEach>
	    </select>
	    <button type="submit">필터링</button>
    </form>
    <c:if test="${empty inqList}">
        <div class="no-inquiries">
            <p>답변 완료된 문의가 없습니다.</p>
        </div>
    </c:if>
    
    <c:forEach var="inqList" items="${inqList}">
        <div class="inquiry" data-inq-num="${inqList.inquiry_num}" data-accom-num="${inqList.accom_num}">
            <div class="inquiry-header">
                <div class="inquiry-accom">${inqList.accom_name}</div>
                <div class="inquiry-date">
                    <fmt:formatDate value="${inqList.inq_date}" pattern="yyyy-MM-dd HH:mm"/>
                </div>
            </div>
            <div class="inquiry-user">
                <span>문의자: ${inqList.user_name}</span>
            </div>
            <div class="inquiry-content">
                ${inqList.inq_content}
            </div>
            <form action="${pageContext.request.contextPath}/host/inquiryAnswerModify.do" method="post" class="answer-box">
                <input type="hidden" name="answer_num" value="${inqList.answer_num}">
                <textarea placeholder="답변을 입력하세요" name="answer_content" required>${inqList.answer_content}</textarea><br>
                <button type="submit">답변 수정</button>
            </form>
        </div>
    </c:forEach>
    <div class="paging">
        ${page}
    </div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>