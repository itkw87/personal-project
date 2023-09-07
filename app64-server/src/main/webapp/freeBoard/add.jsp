<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="personal.project.vo.AttachedFile" %>
<%@ page import="personal.project.vo.FreeBoard" %>

<jsp:useBean id="freeBoardDao" type="personal.project.dao.FreeBoardDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory"
             scope="application"/>
<jsp:useBean id="ncpObjectStorageService" type="personal.project.service.NcpObjectStorageService"
             scope="application"/>
<jsp:useBean id="loginUser" class="personal.project.vo.Member" scope="session"/>

<%
    if (loginUser.getMemberNo() == 0) {
        response.sendRedirect("/auth/form.jsp");
        return;
    }
    request.setAttribute("refresh", "2;url=list.jsp");

    FreeBoard freeBoard = new FreeBoard();
    freeBoard.setFreeWriter(loginUser);
    freeBoard.setFreeTitle(request.getParameter("freeTitle"));
    freeBoard.setFreeContent(request.getParameter("freeContent"));

    ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
    for (Part part : request.getParts()) {
        if (part.getName().equals("files") && part.getSize() > 0) {
            attachedFiles.add(ncpObjectStorageService.uploadFile(new AttachedFile(),
                    "bitcamp-nc7-bucket-03", "personal/freeBoard/", part));
        }
    }
    freeBoard.setAttachedFiles(attachedFiles);

    freeBoardDao.insert(freeBoard);
    if (attachedFiles.size() > 0) {
        freeBoardDao.insertFiles(freeBoard);
    }

    sqlSessionFactory.openSession(false).commit();
    response.sendRedirect("list.jsp");
%>







