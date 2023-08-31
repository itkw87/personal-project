<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="personal.project.vo.AttachedFile"%>
<%@ page import="personal.project.vo.FreeBoard"%>

<jsp:useBean id="freeBoardDao" type="personal.project.dao.FreeBoardDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="ncpObjectStorageService" type="personal.util.NcpObjectStorageService" scope="application"/>
<jsp:useBean id="loginUser" class="personal.project.vo.Member" scope="session"/>
<%
    if (loginUser.getMemberNo() == 0) {
        response.sendRedirect("/auth/form.jsp");
        return;
    }

    request.setAttribute("refresh", "2;url=list.jsp");

    FreeBoard freeBoard = new FreeBoard();
    freeBoard.setFreeWriter(loginUser);
    freeBoard.setFreeBoardNo(Integer.parseInt(request.getParameter("freeBoardNo")));
    freeBoard.setFreeTitle(request.getParameter("freeTitle"));
    freeBoard.setFreeContent(request.getParameter("freeContent"));

    ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
    // 각각의 파트에서 값을 꺼낸다.
    for (Part part : request.getParts()) {
        if (part.getName().equals("files") && part.getSize() > 0) {
            AttachedFile attachedFile = ncpObjectStorageService.uploadFile(new AttachedFile(),
                    "bitcamp-nc7-bucket-03", "personal/freeBoard/", part);
            attachedFiles.add(attachedFile);
        }
    }
    freeBoard.setAttachedFiles(attachedFiles);


    if (freeBoardDao.update(freeBoard) == 0) {
        throw new Exception("게시글이 없거나 변경 권한이 없습니다.!");
    } else {
        if (freeBoard.getAttachedFiles().size() > 0) {
          freeBoardDao.insertFiles(freeBoard);
        }
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list.jsp");
    }
%>











