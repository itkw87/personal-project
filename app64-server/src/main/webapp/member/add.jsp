<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp"%>
<%@ page import="personal.project.vo.Member"%>

<jsp:useBean id="memberDao" type="personal.project.dao.MemberDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="ncpObjectStorageService" type="personal.project.service.NcpObjectStorageService" scope="application"/>
<%
    request.setAttribute("refresh", "2;url=list.jsp");

    Member m = new Member();
    m.setMemberCode(request.getParameter("memberCode"));
    m.setMemberId(request.getParameter("memberId"));
    m.setMemberPwd(request.getParameter("memberPwd"));
    m.setMemberName(request.getParameter("memberName"));
    m.setMemberEmail(request.getParameter("memberEmail"));
    m.setMemberGender(request.getParameter("memberGender"));
    m.setMemberTel(request.getParameter("memberTel"));
    m.setMemberZipcode(request.getParameter("memberZipcode"));
    m.setMemberAddr(request.getParameter("memberAddr"));
    m.setMemberDetailAddr(request.getParameter("memberDetailAddr"));

    System.out.println(m.toString());

    Part photoPart = request.getPart("memberPhoto");
    if (photoPart.getSize() > 0) {
        String uploadFileUrl = ncpObjectStorageService.uploadFile(
                "bitcamp-nc7-bucket-03", "personal/member/", photoPart);
        m.setMemberPhoto(uploadFileUrl);
    }

    memberDao.insert(m);
    sqlSessionFactory.openSession(false).commit();
    response.sendRedirect("list.jsp");

%>