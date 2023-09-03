<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style='height:50px;background-color:orange;'>
  <img src='https://www.ncloud.com/public/img/logo-m.png' style='height:40px'>
  <a href='/member/list'>회원</a>
  <a href='/freeBoard/list'>자유게시글</a>
  <a href='/lecBoard/list'>강의게시판</a>

  <c:choose>
    <c:when test="${empty sessionScope.loginUser}">
      <a href='/auth/form'>로그인</a>
    </c:when>
    <c:otherwise>
      <c:if test="${empty sessionScope.loginUser.memberPhoto}">
        <img style='height:40px' src='/images/avatar.png'>
      </c:if>
      <c:if test="${not empty sessionScope.loginUser.memberPhoto}">
        <img src='http://tzoswbbmvlov19010725.cdn.ntruss.com/personal/member/${loginUser.memberPhoto}?type=f&w=30&h=40&faceopt=true&ttype=jpg'>
      </c:if>
      ${loginUser.memberName} <a href='/auth/logout'>로그아웃</a>
    </c:otherwise>
  </c:choose>
</div>





