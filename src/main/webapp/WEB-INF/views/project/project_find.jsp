<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!-- JSTL을 위한 Directive 선언 -->
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="/css/projectfind.css" />
    <link rel="stylesheet" href="/css/project_card.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
	<script type="text/javascript" src="/js/projectfind.js"></script>
  <title>BizMatch | 프로젝트 찾기</title>
  </head>
  <body>
    
      <%@ include file="/WEB-INF/views/common/after_login_header.jsp" %>

      <div class="projectfind-page">
        <div class="container">
          <div class="container-box">
          <h1  class="container-title">프로젝트 찾기</h1>
          <!-- <div class="border-line"></div> -->
          <div class="project-find">
            <div class="search-box">
              <select class="search-type" name="searchType">
                <option value="entire"  ${searchBoardVO.searchType == "entire" ? "selected" : ""}>전체</option>
                <option value="pjTtl" ${searchBoardVO.searchType == "pjTtl" ? "selected" : ""}>제목</option>
                <option value="pjDesc" ${searchBoardVO.searchType == "pjDesc" ? "selected" : ""}>내용</option>
              </select>
                <input
                  type="text"
                  name="searchKeyword"
                  class="search-keyword"
                  placeholder="어떤 프로젝트를 찾으시나요?"
                />
                <input type="hidden" name="orderBy" id="orderBy" value="latest" />
                <button type="submit" class="search-btn">검색</button>
            </div>
          </div>
        </div>
        
          <div style="display: flex; justify-content: space-between;" >
          <div class="menu">
            <ul class="categories">
              <li><a href="#">전체</a></li>
              <li><a href="#">IT·프로그래밍</a></li>
              <li><a href="#">디자인</a></li>
              <li><a href="#">마케팅</a></li>
              <li><a href="#">영상·사진·음향</a></li>
              <li><a href="#">기획</a></li>
            </ul>
          </div>
          
          <div class="filters">
            <span data-order="latest" class="latest active">최신순</span>
            <span data-order="deadline" class="deadline">마감임박순</span>
            <span data-order="amount" class="amount">금액높은순</span>
          </div>
       

        </div>
      </div>
        <div id="result">
          <!--projectPaginationListVO 에서 하나씩꺼내서 반복 출력-->
          
                <%@ include file="/WEB-INF/views/common/project_card.jsp" %>
        </div>
        <div class="pagenation pagenation-ajax">

          
          <!--searchProjectVO -->
          <div class="pre-page-btn">
		
            <c:if test="${searchProjectVO.hasPrevGroup}">
              <div>
                <a data-url="/project/findPage" 
                   data-page-no="0" 
                   data-exposure-list-size="${searchProjectVO.exposureListSize}" 
                   href="javascript:void(-1)">처음</a>
              </div>
              <div>
                <a data-url="/project/findPage" 
                  data-page-no="${searchProjectVO.prevGroupStartPageNo}" 
                  data-exposure-list-size="${searchProjectVO.exposureListSize}" 
                  href="javascript:void(-1)">이전</a>
              </div>
            </c:if>
          </div>
          <div class="page-number-btn">
          
            <c:forEach begin="${searchProjectVO.groupStartPageNo}"
                      end= "${searchProjectVO.groupEndPageNo}"
                      step = "1"
                      var = "p" >
              <div class = "number-box ${searchProjectVO.currPageNo eq p ? 'active':'' }">
                <a data-url="/project/findPage" 
                  data-page-no="${p}" 
                  data-exposure-list-size="${searchProjectVO.exposureListSize}" 
                  href="javascript:void(-1)">
                  ${p+1}
                </a>
              </div>
            </c:forEach>
            
            
            
          </div>
          <div class="next-page-btn">
             <c:if test="${searchProjectVO.hasNextGroup}">
              <div>
                <a data-url="/project/findPage" 
                data-page-no="${searchProjectVO.nextGroupStartPageNo}" 
                data-exposure-list-size="${searchProjectVO.exposureListSize}" 
                href="javascript:void(-1)">다음</a>
              </div>
              <div>
                <a data-url="/project/findPage" 
                  data-page-no="${searchProjectVO.pageCount-1}" 
                  data-exposure-list-size="${searchProjectVO.exposureListSize}" 
                  href="javascript:void(-1)">마지막</a>
              </div>
            </c:if>
          </div>
        </div>
        <%@ include file="/WEB-INF/views/common/footer.jsp" %>
      </div>  
  </body>
</html>
