<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Insert title here</title>
<!-- <link rel="stylesheet" href="/css/common.css" /> -->
<link rel="stylesheet" href="/css/project_info.css" />
</head>
<body>
	<%@ include file="/WEB-INF/views/common/after_login_header.jsp"%>
	<div class="card-include">
		<%@ include file="/WEB-INF/views/common/myproject_card.jsp"%>
	</div>
	<!-- <div class="project-card-container">
      <div class="project-card">
        <div class="project-box">
          <div class="project-head">
            <div class="project-head-front">
              <div class="status">모집중</div>
              <h2>HTML/Java 매뉴얼 관련 작업</h2>
            </div>
            <div class="post-date">등록일자 2024.08.25</div>
          </div>
          <div class="project-body">
            <div class="project-body-box">
              <div class="project-body-title">프로젝트 분야</div>
              <div class="project-body-content bold">
                IT·프로그래밍 / 웹사이트·모바일앱 개발
              </div>
            </div>
            <div class="sidebar"></div>
            <div class="project-body-box">
              <div class="project-body-title">관련기술</div>
              <div class="project-body-content">
                <div class="circle-box">
                  <div class="circle"></div>
                </div>
                <div class="language">Java, HTML5</div>
              </div>
            </div>
            <div class="sidebar"></div>
            <div class="project-body-box">
              <div class="project-body-title">모집 마감일</div>
              <div class="project-body-content">2024년 8월 27일</div>
            </div>
            <div class="sidebar"></div>
            <div class="project-body-box">
              <div class="project-body-title">프로젝트 일정</div>
              <div class="project-body-content">
                2024년 9월 1일 ~ 2024년 9월 31일
              </div>
            </div>
          </div>
          <div class="project-footer">
            <div class="button-box">
              <button class="apply">신청하기</button>
            </div>
            <div class="estimated-amount">
              <div>예상 금액</div>
              <div class="half-sidebar"></div>
              <div class="bold">10,000,000 원</div>
            </div>
          </div>
        </div>
      </div>
    </div> -->
	<div class="main-content-container">
		<div class="main-content">
			<div>
				<h1 class="main-content-title">업무내용</h1>
			</div>
			<div class="main-content-detail">${projectVO.pjDesc }</div>
		</div>
	</div>
	<div class="main-content-container">
		<div class="main-content">
			<div>
				<h1 class="main-content-title">모집요건</h1>
			</div>
			<div class="main-content-detail"></div>
		</div>
	</div>

	<div class="main-content-container">
		<div class="main-content">
			<div>
				<h1 class="main-content-title">근무환경</h1>
			</div>
			<div class="main-content-detail"></div>
		</div>
	</div>
	<div class="main-content-container">
		<div class="main-content">
			<div>
				<h1 class="main-content-title">프로젝트 문의</h1>
			</div>
			<div class="main-content-detail1"></div>
			<div class="main-content-detail1"></div>
			<div class="main-content-detail1"></div>
			<div class="main-content-detail1"></div>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>
