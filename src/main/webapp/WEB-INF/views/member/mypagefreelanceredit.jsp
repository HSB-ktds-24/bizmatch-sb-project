<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>BizMatch | 마이페이지 수정</title>
    <link rel="stylesheet" href="/css/mypagecompanyedit.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
    />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/mypage_freelancer_edit.js"></script>
  </head>
  <body>
    <%@ include file="/WEB-INF/views/common/after_login_header.jsp" %>
    <div class="mainpage-box">
      <%@ include file="/WEB-INF/views/common/freelancer_profile_box_edit.jsp"
      %>
      <main>
        <div class="main-box">
          <section class="sidebar">
            <div class="sidebar-menulist">
              <div class="sidebar-menu" data-target="#introduction">
                내 프로필
              </div>
              <div class="sidebar-menu" data-target="#holding-technology">
                보유 기술
              </div>
              <div class="sidebar-menu" data-target="#attachment">
                포트폴리오
              </div>
              <div class="sidebar-menu">내 프로젝트</div>
            </div>
          </section>
          <section class="myPageList">
            <div class="myPageList-box">
              <div class="introduction" id="introduction">
                소개
                <textarea
                  class="introduction-content"
                  id="mbrintr"
                  name="mbrintr"
                >
${sessionScope._LOGIN_USER_.mbrIntr}</textarea
                >
              </div>
              <div class="holding-technology" id="holding-technology">
                보유 기술
                <div class="holding-technology-list">
                  <c:choose>
                    <c:when test="${not empty mbrPrmStkList}">
                      <c:forEach var="tech" items="${mbrPrmStkList}">
                        <div class="tech">${tech.prmStkVO.prmStk}</div>
                      </c:forEach>
                    </c:when>
                    <c:otherwise>
                      <div class="no-tech">보유 기술 정보가 없습니다</div>
                    </c:otherwise>
                  </c:choose>
                </div>
              </div>
              <div class="attachment" id="attachment">
                첨부자료
                <button class="more-button small" type="button">
                  <a href="/member/mypage/freelancer/portfolio">더 보기</a>
                </button>
                <div class="portfolio-gallery">
                  <div class="result"></div>
                </div>
              </div>
            </div>
          </section>
        </div>
      </main>
    </div>
  </body>
</html>
