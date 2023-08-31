<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp"%>
<%@ page import="personal.project.vo.AttachedFile"%>
<%@ page import="personal.project.vo.FreeBoard"%>

<jsp:useBean id="freeBoardDao" type="personal.project.dao.FreeBoardDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="loginUser" class="personal.project.vo.Member" scope="session"/>
<%
    if (loginUser.getMemberNo() == 0) {
        response.sendRedirect("/auth/form.jsp");
        return;
    }

    int fileNo = Integer.parseInt(request.getParameter("fileNo"));

    AttachedFile attachedFile = freeBoardDao.findFileBy(fileNo);
    FreeBoard freeBoard = freeBoardDao.findBy(attachedFile.getFreeBoardNo());

    request.setAttribute("refresh", "2;url=detail.jsp?freeBoardNo=" + freeBoard.getFreeBoardNo());

    if (freeBoard.getFreeWriter().getMemberNo() != loginUser.getMemberNo()) {
        throw new ServletException("게시글 변경 권한이 없습니다!");
    }


    if (freeBoardDao.deleteFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
    } else {
        response.sendRedirect("/freeBoard/detail.jsp?freeBoardNo=" + freeBoard.getFreeBoardNo());
    }
    sqlSessionFactory.openSession(false).commit();

%>











