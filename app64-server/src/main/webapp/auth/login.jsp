<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="refresh" value="2;url=/auth/form.jsp" scope="request"/>

<jsp:useBean id="m" class="personal.project.vo.Member" scope="page"/>
<c:set target="${pageScope.m}" property="memberId" value="${param.memberId}"/>
<c:set target="${pageScope.m}" property="memberPwd" value="${param.memberPwd}"/>

<c:if test="${not empty param.saveId}">
  <%
    Cookie cookie = new Cookie("memberId", m.getMemberId());
    response.addCookie(cookie);
  %>
</c:if>

<c:if test="${empty param.saveId}">
  <%
    Cookie cookie = new Cookie("memberId", "no");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  %>
</c:if>

<jsp:useBean id="memberDao" type="personal.project.dao.MemberDao" scope="application"/>
<c:set var="loginUser" value="${memberDao.findByEmailAndPassword(m)}" scope="session"/>

<jsp:useBean id="loginUser" type="personal.project.vo.Member" scope="session"/>
<c:redirect url="/"/>
