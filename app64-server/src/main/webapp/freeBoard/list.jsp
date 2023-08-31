<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="refresh" value="2;url=list.jsp" scope="request"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset='UTF-8'>
  <title>자유게시글</title>
</head>
<body>

<jsp:include page="../header.jsp"/>
<jsp:useBean id="freeBoard" class="personal.project.vo.FreeBoard" scope="page"/>
<jsp:useBean id="searchParam" class="personal.project.vo.SearchParam" scope="page"/>

<c:set target="${pageScope.searchParam}" property="searchType" value="${param.searchType == null ? '' :param.searchType}"/>
<c:set target="${pageScope.searchParam}" property="searchKeyword" value="${param.searchKeyword == null ? '' : param.searchKeyword}"/>
<c:set target="${pageScope.freeBoard}" property="searchParam" value="${pageScope.searchParam}"/>

<jsp:useBean id="freeBoardDao" type="personal.project.dao.FreeBoardDao" scope="application"/>
<c:set var="list" value="${freeBoardDao.findAll(freeBoard)}" scope="page"/>


<h1>자유게시글 목록</h1>
<a href='/freeBoard/form.jsp'>새 글</a>
<br>
<hr>
<br>
  <tbody>
  <c:choose>
    <c:when test="${not empty list}">
    <table border="1">
      <thead>
        <tr><th>번호</th> <th>제목</th> <th>작성자</th> <th>조회수</th> <th>등록일</th></tr>
      </thead>
      <c:forEach items="${list}" var="freeBoard">
        <tr>
          <td>${freeBoard.freeBoardNo}</td>
          <td><a href='/freeBoard/detail.jsp?freeBoardNo=${freeBoard.freeBoardNo}'>
              ${freeBoard.freeTitle.length() > 0 ? freeBoard.freeTitle : "제목없음"}
          </a>
          </td>
          <td>${freeBoard.freeWriter.memberName}</td>
          <td>${freeBoard.freeViewCount}</td>
          <td><fmt:formatDate value="${freeBoard.freeRegDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
      </c:forEach>
    </c:when>
    <c:when test="${empty list}">
      <h2>존재하는 게시글이 없습니다!</h2>
    </c:when>
  </c:choose>
  </tbody>
</table>
<br>
<hr>
<a href='/'>메인</a>
<br>
<br>
<form action='/freeBoard/list.jsp'>
  <select name='searchType'>
        <option value='free_title' ${pageScope.searchParam.searchType ==  'free_title' ? 'selected' : ''}>제목</option>
        <option value='free_content' ${pageScope.searchParam.searchType == 'free_content' ? 'selected' : ''}>내용</option>
  </select>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' name='searchKeyword' placeholder='검색어를 입력하세요.' value='${pageScope.searchParam.searchKeyword}' style='width:220px'>
  <button>검색</button>
</form>
<br>
<br>

<jsp:include page="../footer.jsp"/>

</body>
</html>