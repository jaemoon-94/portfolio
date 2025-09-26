<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/yn.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
	.status-select {
	    padding: 8px 12px;
	    border: 1px solid #ccc;
	    border-radius: 6px;
	    font-size: 14px;
	    background-color: white;
	}
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/host/hostHeader.jsp"/>
<div class="container">
<!-- 상단 수익 카드 -->
<div style="display: flex; gap: 20px; margin:30px 0 30px 0;">
    <!-- 현재 달 전체 수익 -->
    <div style="flex: 1; background-color: #fef2f2; border-radius: 12px; padding: 20px; text-align: center; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
        <i class="fa-solid fa-calendar-days" style="font-size: 24px; color: #e53935;"></i>
        <h4 style="margin: 10px 0;">이번 달 전체 수익</h4>
        <p style="font-size: 20px; font-weight: bold;">
            <fmt:formatNumber value="${currentMonthIncome}" pattern="#,###"/> 원
        </p>
    </div>

    <!-- 올해 전체 수익 -->
    <div style="flex: 1; background-color: #f3e5f5; border-radius: 12px; padding: 20px; text-align: center; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
        <i class="fa-solid fa-calendar" style="font-size: 24px; color: #8e24aa;"></i>
        <h4 style="margin: 10px 0;">올해 전체 수익</h4>
        <p style="font-size: 20px; font-weight: bold;">
            <fmt:formatNumber value="${yearlyIncome}" pattern="#,###"/> 원
        </p>
    </div>

    <!-- 선택된 숙소 수익 -->
    <div style="flex: 1; background-color: #e3f2fd; border-radius: 12px; padding: 20px; text-align: center; box-shadow: 0 2px 5px rgba(0,0,0,0.1);">
        <i class="fa-solid fa-building" style="font-size: 24px; color: #1e88e5;"></i>
        <h4 style="margin: 10px 0;">선택 숙소 수익</h4>
        <p style="font-size: 20px; font-weight: bold;" id="selectedAccomMonthlyIncome"><span id="accomYearlyIncome"></span></p>
    </div>
</div>
<div style="
    background-color: #f9f9f9;
    border-left: 6px solid #fa6767;
    padding: 15px 20px;
    margin: 20px 0;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    color: #333;
    border-radius: 8px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
    font-size: 15px;
">
    <i class="fas fa-info-circle" style="color:#fa6767; margin-right:8px;"></i>
    <strong>안내:</strong> 모든 수입은 <span style="color:#fa6767; font-weight:bold;">CozyTrip 수수료 10%</span>를 제외한 금액입니다.
</div>
<select id="accomSelect" class="status-select" name="accom">
	<option value="0">숙소 선택</option>
    <c:forEach var="myAccomList" items="${myAccomList}">
        <option value="${myAccomList.accom_num}">
            ${myAccomList.accom_name}
        </option>
    </c:forEach>
</select>
<canvas id="incomeChartAccom" width="600" height="400"></canvas>
<div style="height:50px;"></div>
<h2>올해 월별 수익</h2>
<canvas id="incomeChart" width="600" height="400"></canvas>



</div>
<script>
    const labels = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];
    const data = [
        <c:forEach var="income" items="${incomeList}" varStatus="status">
            ${income.totalIncome}<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];

    const ctx = document.getElementById("incomeChart").getContext("2d");
    const chart = new Chart(ctx, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: "월별 수익 (원)",
                backgroundColor: "#fa6767",
                borderColor: "#fa6767",
                data: data,
                fill: false,
                tension: 0.2
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
    
    let chartAccom; // 차트 인스턴스를 전역에 저장

    function loadIncomeChartAccom(accomNum) {
        $.ajax({
            type: 'get',
            url: 'incomeListByAccom.do',
            data: { accom: accomNum },
            dataType: 'json',
            success: function(data) {
                if (data.result === 'success') {
                    const labels = ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"];
                    const incomeData = data.incomeList.map(item => item.totalIncome);

                    if (chartAccom) chartAccom.destroy();

                    const ctx = document.getElementById("incomeChartAccom").getContext("2d");
                    chartAccom = new Chart(ctx, {
                        type: "line",
                        data: {
                            labels: labels,
                            datasets: [{
                                label: "월별 수익 (원)",
                                backgroundColor: "#fa6767",
                                borderColor: "#fa6767",
                                data: incomeData,
                                fill: false,
                                tension: 0.2
                            }]
                        },
                        options: {
                            scales: {
                                y: { beginAtZero: true }
                            }
                        }
                    });
                    document.getElementById("accomYearlyIncome").innerText =
                        data.yearlyIncomebyAccom.toLocaleString() + "원";
                } else {
                    alert("수익 데이터를 불러올 수 없습니다.");
                }
            }
        });
    }


    // 셀렉트박스 변경 시 Ajax 호출
    $('#accomSelect').on('change', function() {
        const selectedAccom = $(this).val();
        loadIncomeChartAccom(selectedAccom);
    });

    // 페이지 로드시 첫 번째 숙소로 로딩
    $(document).ready(function() {
        const defaultAccom = $('#accomSelect').val();
        loadIncomeChartAccom(defaultAccom);
    });

    
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
