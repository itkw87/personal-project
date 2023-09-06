package personal.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import personal.project.vo.Member;

@WebServlet("/member/update")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class MemberUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    Member m = new Member();
    m.setMemberNo(Integer.parseInt(request.getParameter("memberNo")));
    m.setMemberCode(request.getParameter("memberCode"));
    m.setMemberName(request.getParameter("memberName"));
    m.setMemberEmail(request.getParameter("memberEmail"));
    m.setMemberPwd(request.getParameter("memberPwd"));
    m.setMemberGender(request.getParameter("memberGender"));
    m.setMemberTel(request.getParameter("memberTel"));
    m.setMemberZipcode(request.getParameter("memberZipcode"));
    m.setMemberAddr(request.getParameter("memberAddr"));
    m.setMemberDetailAddr(request.getParameter("memberDetailAddr"));

    Part photoPart = request.getPart("memberPhoto");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl = InitServlet.ncpObjectStorageService.uploadFile(
              "bitcamp-nc7-bucket-03", "personal/member/", photoPart);
      m.setMemberPhoto(uploadFileUrl);
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/member/list'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 변경</h1>");

    try {
      if (InitServlet.memberDao.update(m) == 0) {
        out.println("<p>회원이 없습니다.</p>");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        out.println("<p>변경했습니다!</p>");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>변경 실패입니다!</p>");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");
  }
}
