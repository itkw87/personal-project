<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8" %> <%-- directive element --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>회원</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>회원 목록</h1>
<div style='margin:5px;'>
    <a href='/member/add'>새 회원</a>
</div>
<table border='1'>
    <thead>
    <tr>
        <th>번호</th>
        <th>구분</th>
        <th>사진</th>
        <th>이름</th>
        <th>이메일</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="member">
        <tr>
            <td>${member.memberNo}</td>
            <td>${member.memberCode == "S" ? "학생" : "교수"}</td>
            <td>
                <c:if test="${empty member.memberPhoto}">
                    <img src='http://tzoswbbmvlov19010725.cdn.ntruss.com/personal/member/avatar?type=f&w=30&h=40&faceopt=true&ttype=jpg'>
                </c:if>
                <c:if test="${not empty member.memberPhoto}">
                    <img src='http://tzoswbbmvlov19010725.cdn.ntruss.com/personal/member/${member.memberPhoto}?type=f&w=30&h=40&faceopt=true&ttype=jpg'>
                </c:if>
            <td><a href='/member/detail?memberNo=${member.memberNo}'>${member.memberName}</a>
            </td>
            </td>
            <td>${member.memberEmail}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href='/'>메인</a>

<jsp:include page="../footer.jsp"/>
`
</body>
</html>
