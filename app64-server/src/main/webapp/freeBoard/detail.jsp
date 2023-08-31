<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="refresh" value="2;url=list.jsp" scope="request"/>
<jsp:useBean id="freeBoardDao" type="personal.project.dao.FreeBoardDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<c:set var="freeBoard" value="${freeBoardDao.findBy(param.freeBoardNo)}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>자유 게시글</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>자유 게시글</h1>

<c:if test="${empty freeBoard}">
    <p>해당 번호의 자유 게시글이 없습니다!</p>
</c:if>

<c:if test="${not empty freeBoard}">
    <form action='/freeBoard/update.jsp' method='post' enctype='multipart/form-data'>
        <table border='1'>
            <tr><th style='width:120px;'>번호</th>
                <td style='width:300px;'><input type='text' name='freeBoardNo' value='${freeBoard.freeBoardNo}' readonly></td></tr>
            <tr><th>제목</th>
                <td><input type='text' name='freeTitle' value='${freeBoard.freeTitle}'></td></tr>
            <tr><th>내용</th>
                <td><textarea name='freeContent' style='height:200px; width:400px;'>${freeBoard.freeContent}</textarea></td></tr>
            <tr><th>작성자</th> <td>${freeBoard.freeWriter.memberName}</td></tr>
            <tr><th>조회수</th> <td>${freeBoard.freeViewCount}</td></tr>
            <tr><th>등록일</th> <td>${simpleDateFormatter.format(freeBoard.freeRegDate)}</td></tr>
            <tr><th>첨부파일</th><td>
                <c:forEach items="${freeBoard.attachedFiles}" var="file">
                    <c:if test="${file.fileNo > 0}">
                        <a href='https://kr.object.ncloudstorage.com/bitcamp-nc7-bucket-03/personal/freeBoard/${file.saveFileName}'>${file.originFileName}</a>
                        [<a href='/freeBoard/fileDelete.jsp?fileNo=${file.fileNo}'>삭제</a>]<br>
                    </c:if>
                </c:forEach>
                <input type='file' name='files' multiple>
            </td></tr>
        </table>

        <div>
            <button>변경</button>
            <button type='reset'>초기화</button>
            <a href='/freeBoard/delete.jsp?freeBoardNo=${param.freeBoardNo}'>삭제</a>
            <a href='/freeBoard/list.jsp'>목록</a>
        </div>
    </form>
    <c:set target="${pageScope.freeBoard}" property="freeViewCount" value="${freeBoard.freeViewCount + 1}"/>
    <c:set var="updateCount" value="${freeBoardDao.updateCount(freeBoard)}"/>
    <% sqlSessionFactory.openSession(false).commit(); %>
</c:if>

<jsp:include page="../footer.jsp"/>

</body>
</html>













