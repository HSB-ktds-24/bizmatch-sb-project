<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!-- JSTL을 위한 Directive 선언 -->
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <link rel="stylesheet" href="/css/myproject_card.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/project_card.js"></script>
  </head>
  <body>
    
       <div class="project-card-container">
        <div class="project-card">
          <div class="project-box">
            <div class="project-head">
              <div class="project-head-front">
                <div class="status-recruiting" data-pjid = ${projectVO.pjId }>모집중</div>
                <!-- <div class="status-additional-recruiting">추가모집중</div> -->
                <!-- <div class="status-ing">진행중</div> -->
                <!-- <div class="status-done">완료</div> -->
                <h2 id="pjTtl">${projectVO.pjTtl}</h2>
              </div>
              <div class="post-date">등록일자 ${projectVO.rgstrDt}</div>
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
                <div class="project-body-content">${projectVO.pjRcrutEndDt}</div>
              </div>
              <div class="sidebar"></div>
              <div class="project-body-box">
                <div class="project-body-title">프로젝트 일정</div>
                <div class="project-body-content">
                  ${projectVO.strtDt} ~ ${projectVO.endDt}
                </div>
              </div>
            </div>
            <div class="project-footer">
              <div class="button-box">
                <input
                  class="apply"
                  id="apply"
                  data-id="${projectVO.pjId}"
                  type="button"
                  value="신청하기"
                />
              </div>
              <div class="estimated-amount">
                <div>예상 금액</div>
                <div class="half-sidebar"></div>
                <div class="bold">${projectVO.cntrctAccnt} 원</div>
              </div>
            </div>
          </div>
        </div>
      </div>
     
  </body>
</html>
