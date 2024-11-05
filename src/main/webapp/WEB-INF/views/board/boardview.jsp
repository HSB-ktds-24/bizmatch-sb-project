<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <link rel="stylesheet" href="/css/boardview.css" />
  </head>
  <body>
    <%@ include file="/WEB-INF/views/common/after_login_header.jsp" %>
    
    <div class="main-box">
    <div class="content-box">
      <div class="title">${boardVO.pstNm}</div>

      <div class="post-info">
        <div class="post-type">
     
          	<c:if test = "${boardVO.pstCtgry eq '0'}">
	              	<div class="type-deco">공지</div>
	              </c:if>
		          <c:if test = "${boardVO.pstCtgry eq '1'}">
	              	<div class= "blue-box">문의</div>
	              </c:if>
	     
          <div class="author">작성자: ${boardVO.mbrNm}</div>
        </div>
        <div class="times">

          <div>마지막 수정일: ${boardVO.lstModDt }</div>
        </div>
      </div>
      <div class="main-content">${boardVO.pstCntnt}</div>
      <c:choose>
      	<c:when test="${boardVO.athrId eq loginInfo.emilAddr}">
      	      <div class="function-line">
        <a class="modify-btn" href="/board/modify/${boardVO.pstId}"/>수정</a>
      </div>
      	</c:when>
      </c:choose>

    </div>
    </div>
  </body>
</html>
