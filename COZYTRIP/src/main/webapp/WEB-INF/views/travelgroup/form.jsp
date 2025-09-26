<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>동행 그룹 생성하기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/sh/sh.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<style>
/* 동행 그룹 폼 스타일 */
.form-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
}
.form-header {
    margin-bottom: 20px;
}
.form-header h2 {
    color: #333;
}
.form-group {
    margin-bottom: 20px;
}
.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    color: #555;
}
.form-control {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1em;
}
.form-control:focus {
    border-color: #2196F3;
    outline: none;
}
.form-select {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1em;
}
.form-textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1em;
    min-height: 200px;
    resize: vertical;
}
.date-group {
    display: flex;
    gap: 10px;
    align-items: center;
}
.date-group label {
    margin-bottom: 0;
}
.form-text {
    font-size: 0.9em;
    color: #666;
    margin-top: 5px;
}
.btn-group {
    display: flex;
    gap: 10px;
    margin-top: 30px;
}
.btn {
    padding: 10px 15px;
    border-radius: 4px;
    border: none;
    cursor: pointer;
    font-weight: bold;
    font-size: 1em;
}
.btn-primary {
    background-color: #2196F3;
    color: white;
}
.btn-primary:hover {
    background-color: #0b7dda;
}
.btn-secondary {
    background-color: #6c757d;
    color: white;
}
.btn-secondary:hover {
    background-color: #5a6268;
}
</style>
</head>
<body>
<!-- 상단 네비게이션 바 -->
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="form-container">
    <div class="form-header">
        <h2>동행 그룹 생성하기</h2>
    </div>
    
    <form action="${pageContext.request.contextPath}/travelgroup/create.do" method="post" id="travelGroupForm">
        <c:if test="${not empty accom_num}">
            <input type="hidden" name="accom_num" value="${accom_num}">
        </c:if>
        
        <div class="form-group">
            <label for="tg_title">제목</label>
            <input type="text" class="form-control" id="tg_title" name="tg_title" required maxlength="200">
        </div>
        
        <div class="form-group">
            <label for="region_num">지역</label>
            <select class="form-select" id="region_num" name="region_num" required>
                <option value="">지역을 선택하세요</option>
                <c:forEach var="region" items="${regionList}">
                    <option value="${region.region_num}">${region.region_name}</option>
                </c:forEach>
            </select>
        </div>
        
        <div class="form-group">
            <label>여행 기간</label>
            <div class="date-group">
                <input type="date" class="form-control" id="travel_date_start" name="travel_date_start">
                <span>~</span>
                <input type="date" class="form-control" id="travel_date_end" name="travel_date_end">
            </div>
            <div class="form-text">여행 날짜가 확정되지 않았다면 비워두셔도 됩니다.</div>
        </div>
        
        <div class="form-group">
            <label for="max_member_count">모집 인원</label>
            <select class="form-select" id="max_member_count" name="max_member_count" required>
                <option value="0">제한 없음</option>
                <c:forEach var="i" begin="2" end="10">
                    <option value="${i}">${i}명</option>
                </c:forEach>
            </select>
            <div class="form-text">작성자를 포함한 최대 인원 수입니다.</div>
        </div>
        
        <div class="form-group">
            <label for="tg_content">내용</label>
            <textarea class="form-textarea" id="tg_content" name="tg_content" required maxlength="4000"></textarea>
            <div class="form-text">여행 일정, 만남 장소, 원하는 동행자 유형 등을 자세히 적어주세요.</div>
        </div>
        
        <div class="btn-group">
            <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
            <button type="submit" class="btn btn-primary">등록하기</button>
        </div>
    </form>
</div>

<script>
$(function() {
    // 오늘 날짜 기준으로 날짜 필드 최소값 설정
    var today = new Date().toISOString().split('T')[0];
    $('#travel_date_start').attr('min', today);
    
    // 시작일 변경 시 종료일 최소값 설정
    $('#travel_date_start').change(function() {
        var startDate = $(this).val();
        if(startDate) {
            $('#travel_date_end').attr('min', startDate);
            
            // 종료일이 시작일보다 빠르면 종료일을 시작일로 설정
            if($('#travel_date_end').val() < startDate) {
                $('#travel_date_end').val(startDate);
            }
        }
    });
    
    // 폼 제출 전 유효성 검사
    $('#travelGroupForm').submit(function(e) {
        var title = $('#tg_title').val().trim();
        var content = $('#tg_content').val