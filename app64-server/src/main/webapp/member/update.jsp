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

    Member member = new Member();
    member.setMemberNo(Integer.parseInt(request.getParameter("memberNo")));
    member.setMemberCode(request.getParameter("memberCode"));
    member.setMemberName(request.getParameter("memberName"));
    member.setMemberEmail(request.getParameter("memberEmail"));
    member.setMemberPwd(request.getParameter("memberPwd"));
    member.setMemberGender(request.getParameter("memberGender"));
    member.setMemberTel(request.getParameter("memberTel"));
    member.setMemberZipcode(request.getParameter("memberZipcode"));
    member.setMemberAddr(request.getParameter("memberAddr"));
    member.setMemberDetailAddr(request.getParameter("memberDetailAddr"));

    Part photoPart = request.getPart("memberPhoto");
    if (photoPart.getSize() > 0) {
        String uploadFileUrl = ncpObjectStorageService.uploadFile(
                "bitcamp-nc7-bucket-03", "personal/member/", photoPart);
        member.setMemberPhoto(uploadFileUrl);
    }

    if (memberDao.update(member) == 0) {
        throw new Exception("회원이 없습니다.");
    } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list.jsp");
    }

%>
