<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp"%>
<%@ page import="personal.project.vo.FreeBoard"%>
<%@ page import="personal.project.vo.FreeBoard" %>

<jsp:useBean id="freeBoardDao" type="personal.project.dao.FreeBoardDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="loginUser" class="personal.project.vo.Member" scope="session"/>
<%
    if (loginUser.getMemberNo() == 0) {
        response.sendRedirect("/auth/form.jsp");
        return;
    }

    request.setAttribute("refresh", "2;url=list.jsp");

    FreeBoard freeBoard = new FreeBoard();
    freeBoard.setFreeBoardNo(Integer.parseInt(request.getParameter("freeBoardNo")));
    freeBoard.setFreeWriter(loginUser);

    freeBoardDao.deleteFile(freeBoard.getFreeBoardNo());

    if (freeBoardDao.delete(freeBoard) == 0) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
    } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list.jsp");
    }
%>











