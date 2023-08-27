package personal.project.handler;

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
      String uploadFileUrl = InitServlet.ncpObjectStorageService.uploadFile(
              "bitcamp-nc7-bucket-03", "personal/member/", photoPart);
      member.setMemberPhoto(uploadFileUrl);
    }

    try {
      if (InitServlet.memberDao.update(member) == 0) {
        throw new Exception("회원이 없습니다.");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
