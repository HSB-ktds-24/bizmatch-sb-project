<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <!-- <meta charset="UTF-8" /> -->
    <title>프로젝트 지원 기업 리스트</title>
    <link rel="stylesheet" href="/css/projectapplylist.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/project_apply_list.js"></script>
    
  </head>
  <body>
    <%@ include file="/WEB-INF/views/common/after_login_header.jsp" %>
    <div class="project-info">
      <%@ include file="/WEB-INF/views/common/project_card.jsp" %>
    </div>

    <section class="company-list">
<<<<<<< HEAD
      <h2>지원 기업 리스트</h2>
      <div class="company pending">
        <h3>기업명: 동원산업</h3>
        <p>지원일: 2024-10-15</p>
        <p>지원 상태: 심사 중</p>
        <div class="button-group">
        
          <button class="btn view-details">상세 보기</button>
        
          <!-- 주석으로 버튼 태그 바꾸기 
          <c:if test = "${applyProject.memberVO.mbrCtgry =='1'}">
            <button class="btn view-details" 
            		id="detail-btn"
            		data-index="${applyProject.emilAddr}"
            		data-type="${applyProject.memberVO.mbrCtgry}"
            		>상세 보기</button>
          </c:if>
          <c:if test = "${applyProject.memberVO.mbrCtgry =='0'}">
            <button class="btn view-details" 
            		id="detail-btn"
            		data-index="${applyProject.memberVO.cmpId}"
            		data-type="${applyProject.memberVO.mbrCtgry}"
            		>상세 보기</button>
          </c:if>

          
          -->
          <div class="btn-group-right">
            <button class="btn accept">수락</button>
            <button class="btn reject">거절</button>
=======
      <!-- <h2>지원 기업 리스트</h2> -->
      <h1>지원 기업 리스트</h1>

      <c:forEach var="applyProject" items="${applyProjectVOList}">
        <div class="company pending">
          <h3>기업명: ${applyProject.pjApplyTtl}</h3>
          <p>지원일: ${applyProject.pjApplyRgstrDt}</p>
          <p>지원 상태: 심사 중</p>
          <div class="button-group">
            <button class="btn view-details">상세 보기</button>
            <form action="" method="post">
              <div class="btn-group-right">
                <button class="btn accept">수락</button>
                <button class="btn reject">거절</button>
              </div>
            </form>
>>>>>>> 2e5ae75c81d8a0a157856527fa6d4c50f6fa775a
          </div>
        </div>
      </c:forEach>

      <!-- <div class="pagination">
          <a href="#">처음</a>
          <a href="#">&laquo;</a>
          <a href="#" class="active">1</a>
          <a href="#">2</a>
          <a href="#">3</a>
          <a href="#">4</a>
          <a href="#">5</a>
          <a href="#">6</a>
          <a href="#">7</a>
          <a href="#">8</a>
          <a href="#">9</a>
          <a href="#">10</a>
          <a href="#">&raquo;</a>
          <a href="#">끝</a>
        </div> -->
    </section>
  </body>
</html>
