<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>호스트 프로필</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/yn/hostHeader.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cozykoreaMain.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
    <style>
.profile-container {
    max-width: 800px;
    margin-top: 30px;
    background: #ffffff;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

h2 {
    text-align: center;
    margin-bottom: 30px;
}

.profile-content {
    display: flex;
    align-items: flex-start;
    gap: 30px;
}

.profile-photo {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    background-color: #e0e0e0; 
    display: flex;
    align-items: center;
    justify-content: center;
}

.profile-photo img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 50%;
}

.profile-info {
    flex: 1;
}

.profile-info p {
    margin: 8px 0;
    font-size: 16px;
}

textarea {
    width: 100%;
    padding: 10px;
    font-size: 14px;
    resize: vertical;
    border: 1px solid #ccc;
    border-radius: 6px;
}

button {
    margin-top: 12px;
    padding: 10px 20px;
    font-size: 14px;
    background-color: #fa6767;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
}
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/host/hostHeader.jsp"/>

<div class="container">
<div class="profile-container">
    <h2>호스트 프로필</h2>
    <div class="profile-content">
        <div class="profile-photo">
            <img src="${pageContext.request.contextPath}/images/face.png" alt="프로필 사진">
        </div>
        <div class="profile-info">
            <p><strong>이름 : ${profile.user_name}</strong> </p>
            <p><strong>가입일 : ${profile.host_date}</strong> </p>
            <p><strong>평점 : ${profile.host_rating}</strong> </p>
            <form action="profileModify.do" method="post">
                <input type="hidden" name="user_num" value="">
                <label for="host_intro"><strong>소개글 :</strong></label><br>
                <textarea name="host_intro" id="host_intro" rows="6">${profile.host_intro}</textarea><br>
                <button type="submit">소개글 수정</button>
            </form>
        </div>
    </div>
</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>
