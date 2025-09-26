<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CozyKorea - 국내 여행 숙박 예약</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Noto Sans KR', sans-serif;
        }
        
        body {
            color: #333;
            background-color: #fff;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }
        
        /* 상단 네비게이션 바 */
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 0;
            border-bottom: 1px solid #eee;
        }
        
        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #3D5A80;
            text-decoration: none;
        }
        
        .nav-search {
            display: flex;
            align-items: center;
            padding: 10px 20px;
            border-radius: 25px;
            border: 1px solid #ddd;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .nav-search input {
            border: none;
            outline: none;
            margin-left: 10px;
            width: 250px;
        }
        
        .nav-links {
            display: flex;
            gap: 20px;
            align-items: center;
        }
        
        .nav-links a {
            text-decoration: none;
            color: #333;
            font-weight: 500;
        }
        
        .login-btn, .signup-btn, .host-btn {
            padding: 8px 16px;
            border-radius: 20px;
            font-weight: 500;
        }
        
        .login-btn {
            background-color: transparent;
            border: 1px solid #3D5A80;
            color: #3D5A80;
        }
        
        .signup-btn {
            background-color: #3D5A80;
            border: none;
            color: white;
        }
        
        .host-btn {
            background-color: #E07A5F;
            border: none;
            color: white;
        }
        
        .nav-icon {
            position: relative;
            margin-left: 10px;
        }
        
        .notification-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background-color: #FF5A5F;
            color: white;
            border-radius: 50%;
            width: 18px;
            height: 18px;
            font-size: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        /* 메인 검색 섹션 */
        .hero {
            background-image: url('/api/placeholder/1200/400');
            background-size: cover;
            background-position: center;
            padding: 100px 0;
            margin: 30px 0;
            border-radius: 15px;
            position: relative;
        }
        
        .hero-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.3);
            border-radius: 15px;
        }
        
        .hero-content {
            position: relative;
            z-index: 2;
            color: white;
            text-align: center;
            margin-bottom: 30px;
        }
        
        .hero-content h1 {
            font-size: 2.5rem;
            margin-bottom: 15px;
        }
        
        .hero-content p {
            font-size: 1.1rem;
            margin-bottom: 30px;
        }
        
        .search-container {
            background-color: white;
            border-radius: 15px;
            padding: 20px;
            max-width: 800px;
            margin: 0 auto;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            position: relative;
            z-index: 2;
        }
        
        .search-form {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }
        
        .search-item {
            flex: 1;
            min-width: 200px;
        }
        
        .search-item label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            font-size: 14px;
        }
        
        .search-item input, .search-item select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 8px;
            outline: none;
        }
        
        .search-button {
            background-color: #3D5A80;
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
            margin-top: 20px;
            width: 100%;
            transition: background-color 0.3s;
        }
        
        .search-button:hover {
            background-color: #2C4B70;
        }
        
        /* 숙소 유형 탭 */
        .category-tabs {
            display: flex;
            gap: 15px;
            margin: 40px 0 20px;
            overflow-x: auto;
            padding-bottom: 10px;
        }
        
        .category-tab {
            display: flex;
            flex-direction: column;
            align-items: center;
            text-decoration: none;
            color: #717171;
            transition: all 0.3s;
        }
        
        .category-tab:hover {
            transform: translateY(-3px);
        }
        
        .category-tab img {
            width: 32px;
            height: 32px;
            margin-bottom: 8px;
            opacity: 0.7;
            transition: opacity 0.3s;
        }
        
        .category-tab.active {
            color: #3D5A80;
            border-bottom: 2px solid #3D5A80;
            padding-bottom: 5px;
        }
        
        .category-tab.active img {
            opacity: 1;
        }
        
        /* 지역별 바로가기 */
        .regions {
            margin: 50px 0;
        }
        
        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        
        .section-title {
            font-size: 24px;
            color: #2B2D42;
        }
        
        .view-all {
            color: #3D5A80;
            text-decoration: none;
            font-weight: 500;
        }
        
        .region-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }
        
        .region-card {
            position: relative;
            border-radius: 15px;
            overflow: hidden;
            height: 250px;
            transition: transform 0.3s ease;
        }
        
        .region-card:hover {
            transform: scale(1.03);
        }
        
        .region-card img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        
        .region-info {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            padding: 20px;
            background: linear-gradient(to top, rgba(0,0,0,0.7), transparent);
            color: white;
        }
        
        .region-info h3 {
            margin-bottom: 5px;
        }
        
        /* 추천 숙소 섹션 */
        .recommended {
            margin: 50px 0;
        }
        
        .property-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
        }
        
        .property-card {
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        
        .property-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.15);
        }
        
        .property-image {
            height: 200px;
            position: relative;
        }
        
        .property-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        
        .wishlist-button {
            position: absolute;
            top: 15px;
            right: 15px;
            background-color: rgba(255,255,255,0.7);
            border-radius: 50%;
            width: 32px;
            height: 32px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        
        .wishlist-button:hover {
            background-color: rgba(255,255,255,0.9);
        }
        
        .property-badges {
            position: absolute;
            bottom: 15px;
            left: 15px;
            display: flex;
            gap: 5px;
        }
        
        .property-badge {
            background-color: rgba(61, 90, 128, 0.8);
            color: white;
            font-size: 12px;
            padding: 4px 8px;
            border-radius: 4px;
        }
        
        .property-badge.special {
            background-color: rgba(224, 122, 95, 0.8);
        }
        
        .property-info {
            padding: 15px;
        }
        
        .property-type {
            font-size: 12px;
            color: #666;
            margin-bottom: 5px;
        }
        
        .property-name {
            font-weight: bold;
            margin-bottom: 8px;
            color: #2B2D42;
        }
        
        .property-location {
            display: flex;
            align-items: center;
            font-size: 14px;
            color: #666;
            margin-bottom: 8px;
        }
        
        .property-rating {
            display: flex;
            align-items: center;
            margin-bottom: 8px;
        }
        
        .property-rating span {
            margin-left: 5px;
            font-size: 14px;
        }
        
        .property-price {
            font-weight: bold;
            color: #3D5A80;
        }
        
        /* 호스트 되기 섹션 */
        .become-host {
            margin: 70px 0;
            background-color: #F4F1DE;
            border-radius: 15px;
            padding: 50px;
            position: relative;
            overflow: hidden;
        }
        
        .host-content {
            max-width: 50%;
            position: relative;
            z-index: 2;
        }
        
        .host-content h2 {
            font-size: 2rem;
            margin-bottom: 20px;
            color: #2B2D42;
        }
        
        .host-content p {
            margin-bottom: 30px;
            color: #555;
            line-height: 1.6;
        }
        
        .host-cta {
            display: inline-block;
            background-color: #E07A5F;
            color: white;
            padding: 12px 25px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        
        .host-cta:hover {
            background-color: #D0694F;
        }
        
        .host-image {
            position: absolute;
            top: 0;
            right: 0;
            width: 45%;
            height: 100%;
            background-image: url('/api/placeholder/500/300');
            background-size: cover;
            background-position: center;
            border-top-right-radius: 15px;
            border-bottom-right-radius: 15px;
        }
        
        /* 공지사항 섹션 */
        .notices {
            margin: 50px 0;
        }
        
        .notice-list {
            border-top: 2px solid #3D5A80;
        }
        
        .notice-item {
            display: flex;
            justify-content: space-between;
            padding: 15px 10px;
            border-bottom: 1px solid #eee;
            transition: background-color 0.3s;
        }
        
        .notice-item:hover {
            background-color: #f8f8f8;
        }
        
        .notice-title {
            font-weight: 500;
        }
        
        .notice-date {
            color: #888;
            font-size: 14px;
        }
        
        /* 푸터 */
        footer {
            background-color: #f7f7f7;
            padding: 40px 0;
            margin-top: 50px;
        }
        
        .footer-links {
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
        }
        
        .footer-column {
            flex: 1;
            min-width: 200px;
            margin-bottom: 20px;
        }
        
        .footer-column h4 {
            margin-bottom: 15px;
            font-size: 16px;
            color: #2B2D42;
        }
        
        .footer-column a {
            display: block;
            text-decoration: none;
            color: #717171;
            margin-bottom: 10px;
            font-size: 14px;
        }
        
        .footer-column a:hover {
            color: #3D5A80;
        }
        
        .footer-info {
            margin-top: 20px;
            border-top: 1px solid #ddd;
            padding-top: 20px;
        }
        
        .footer-social {
            display: flex;
            gap: 15px;
            margin-bottom: 15px;
        }
        
        .social-icon {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background-color: #3D5A80;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .copyright {
            text-align: center;
            font-size: 14px;
            color: #717171;
            margin-top: 15px;
        }
    </style>
</head>
<body>
    <!-- 상단 네비게이션 바 -->
    <header>
        <div class="container">
            <nav class="navbar">
                <a href="#" class="logo">CozyKorea</a>
                <div class="nav-search">
                    <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" style="display:block;fill:none;height:16px;width:16px;stroke:#717171;stroke-width:4;overflow:visible" aria-hidden="true" role="presentation" focusable="false">
                        <circle cx="13" cy="13" r="8"></circle>
                        <line x1="21" y1="21" x2="26" y2="26"></line>
                    </svg>
                    <input type="text" placeholder="어디로 여행가세요?">
                </div>
                <div class="nav-links">
                    <a href="#" class="host-btn">호스트 되기</a>
                    <a href="#" class="login-btn">로그인</a>
                    <a href="#" class="signup-btn">회원가입</a>
                    <a href="#" class="nav-icon">
                        <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" style="display:block;height:20px;width:20px;fill:#717171" aria-hidden="true" role="presentation" focusable="false">
                            <path d="m16 28c7-4.733 14-10 14-17 0-1.792-.683-3.583-2.05-4.95-1.367-1.366-3.158-2.05-4.95-2.05-1.791 0-3.583.684-4.949 2.05l-2.051 2.051-2.05-2.051c-1.367-1.366-3.158-2.05-4.95-2.05-1.791 0-3.583.684-4.949 2.05-1.367 1.367-2.051 3.158-2.051 4.95 0 7 7 12.267 14 17z"></path>
                        </svg>
                        <div class="notification-badge">2</div>
                    </a>
                    <a href="#" class="nav-icon">
                        <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" style="display:block;height:20px;width:20px;fill:#717171" aria-hidden="true" role="presentation" focusable="false">
                            <path d="M16 1C8.28 1 2 7.28 2 15c0 1.813.344 3.594 1.031 5.25.063.219.031.5-.125.75l-1.438 2.375c-.281.438-.25 1.125.094 1.531.344.375.938.625 1.438.625h1.344c.656 0 1.156-.313 1.562-.563.563-.375 1.156-.75 1.719-.75h4.719c.563 0 1.156.375 1.719.75.406.25.906.563 1.562.563H21c.5 0 1.094-.25 1.438-.625.344-.406.375-1.094.094-1.531l-1.438-2.375c-.156-.25-.188-.531-.125-.75.688-1.656 1.031-3.438 1.031-5.25 0-7.72-6.28-14-14-14zm0 2c6.617 0 12 5.383 12 12 0 1.557-.281 3.082-.875 4.531-.344.875-.25 1.813.25 2.594l1.438 2.375H25.5c-.094 0-.188-.031-.25-.125 0 0-.375-.438-.594-.656-.75-.5-1.719-1.156-3.031-1.156h-4.719c-1.312 0-2.281.656-3.031 1.156-.219.219-.594.656-.594.656-.063.094-.156.125-.25.125h-1.313l1.438-2.375c.5-.781.594-1.719.25-2.594-.594-1.45-.875-2.975-.875-4.531 0-6.617 5.383-12 12-12zM16 4.5c-5.79 0-10.5 4.71-10.5 10.5 0 .828.672 1.5 1.5 1.5s1.5-.672 1.5-1.5c0-4.136 3.364-7.5 7.5-7.5.828 0 1.5-.672 1.5-1.5s-.672-1.5-1.5-1.5z"></path>
                        </svg>
                        <div class="notification-badge">3</div>
                    </a>
                </div>
            </nav>
        </div>
    </header>

    <!-- 메인 검색 섹션 -->
    <main>
        <div class="container">
            <section class="hero">
                <div class="hero-overlay"></div>
                <div class="hero-content">
                    <h1>특별한 여행을 위한 특별한 공간</h1>
                    <p>코지코리아와 함께 잊지 못할 국내 여행을 계획해보세요</p>
                </div>
                <div class="search-container">
                    <form class="search-form">
                        <div class="search-item">
                            <label>위치</label>
                            <input type="text" placeholder="어디로 여행가세요?">
                        </div>
                        <div class="search-item">
                            <label>체크인</label>
                            <input type="date">
                        </div>
                        <div class="search-item">
                            <label>체크아웃</label>
                            <input type="date">
                        </div>
                        <div class="search-item">
                            <label>인원</label>
                            <select>
                                <option>성인 1명</option>
                                <option>성인 2명</option>
                                <option>성인 3명</option>
                                <option>성인 4명 이상</option>
                            </select>
                        </div>
                        <button class="search-button">검색</button>
                    </form>
                </div>
            </section>

            <!-- 숙소 카테고리 탭 -->
            <section>
                <div class="category-tabs">
                    <a href="#" class="category-tab active">
                        <img src="/files/dog.png" alt="전체">
                        <span>전체</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="/api/placeholder/32/32" alt="아파트/콘도">
                        <span>아파트/콘도</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="/api/placeholder/32/32" alt="주택/빌라">
                        <span>주택/빌라</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="/api/placeholder/32/32" alt="펜션">
                        <span>펜션</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="/api/placeholder/32/32" alt="한옥">
                        <span>한옥</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="/api/placeholder/32/32" alt="게스트하우스">
                        <span>게스트하우스</span>
                    </a>
                    <a href="#" class="category-tab">
                        <img src="/api/placeholder/32/32" alt="호텔">
                        <span>호텔</span>
                    </a>
                </div>
            </section>

            <!-- 지역별 바로가기 -->
            <section class="regions">
                <div class="section-header">
                    <h2 class="section-title">지역별 숙소 찾기</h2>
                    <a href="#" class="view-all">전체보기</a>
                </div>
                <div class="region-grid">
                    <div class="region-card">
                        <img src="/api/placeholder/300/250" alt="서울">
                        <div class="region-info">
                            <h3>서울</h3>
                            <p>3,245개의 숙소</p>
                        </div>
                    </div>
                    <div class="region-card">
                        <img src="/api/placeholder/300/250" alt="부산">
                        <div class="region-info">
                            <h3>부산</h3>
                            <p>1,876개의 숙소</p>
                        </div>
                    </div>
                    <div class="region-card">
                        <img src="/api/placeholder/300/250" alt="제주">
                        <div class="region-info">
                            <h3>제주</h3>
                            <p>2,543개의 숙소</p>
                        </div>
                    </div>
                    <div class="region-card">
                        <img src="/api/placeholder/300/250" alt="강원도">
                        <div class="region-info">
                            <h3>강원도</h3>
                            <p>1,432개의 숙소</p>
                        </div>
                    </div>
                </div>
            </section>
            
            <!-- 추천 숙소 섹션 -->
            <section class="recommended">
                <div class="section-header">
                    <h2 class="section-title">추천 숙소</h2>
                    <a href="#" class="view-all">전체보기</a>
                </div>
                <div class="property-grid">
                    <div class="property-card">
                        <div class="property-image">
                            <img src="/api/placeholder/280/200" alt="추천 숙소 1">
                            <div class="wishlist-button">
                                <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" role="presentation" focusable="false" style="display: block; fill: rgba(0, 0, 0, 0.5); height: 24px; width: 24px; stroke: white; stroke-width: 2; overflow: visible;">
                                    <path d="m16 28c7-4.733 14-10 14-17 0-1.792-.683-3.583-2.05-4.95-1.367-1.366-3.158-2.05-4.95-2.05-1.791 0-3.583.684-4.949 2.05l-2.051 2.051-2.05-2.051c-1.367-1.366-3.158-2.05-4.95-2.05-1.791 0-3.583.684-4.949 2.05-1.367 1.367-2.051 3.158-2.051 4.95 0 7 7 12.267 14 17z"></path>
                                </svg>
                            </div>
                            <div class="property-badges">
                                <div class="property-badge">인기</div>
                                <div class="property-badge special">특가</div>
                            </div>
                        </div>
                        <div class="property-info">
                            <div class="property-type">아파트</div>
                            <h3 class="property-name">강남 스카이뷰 아파트</h3>
                            <div class="property-location">
                                <span>서울 강남구</span>
                            </div>
                            <div class="property-rating">
                                <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" role="presentation" focusable="false" style="display: block; height: 14px; width: 14px; fill: #FF385C;">
                                    <path d="M15.094 1.579l-4.124 8.885-9.86 1.27a1 1 0 0 0-.542 1.736l7.293 6.565-1.965 9.852a1 1 0 0 0 1.483 1.061L16 25.951l8.625 4.997a1 1 0 0 0 1.482-1.06l-1.965-9.853 7.293-6.565a1 1 0 0 0-.541-1.735l-9.86-1.271-4.127-8.885a1 1 0 0 0-1.814 0z" fill-rule="evenodd"></path>
                                </svg>
                                <span>4.9 (145)</span>
                            </div>
                            <div class="property-price">₩120,000 / 박</div>
                        </div>
                    </div>
                    
                    <div class="property-card">
                        <div class="property-image">
                            <img src="/api/placeholder/280/200" alt="추천 숙소 2">
                            <div class="wishlist-button">
                                <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" role="presentation" focusable="false" style="display: block; fill: rgba(0, 0, 0, 0.5); height: 24px; width: 24px; stroke: white; stroke-width: 2; overflow: visible;">
                                </svg>
                                </div>
                                </div>
                                </div>
                                </div>
                                </section>
                                </div>
                                </main>
                                </body>
                                </html>	
