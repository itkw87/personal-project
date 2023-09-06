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

import personal.project.vo.AttachedFile;
import personal.project.vo.Member;

@WebServlet("/member/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class MemberAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {


    Member m = new Member();
    m.setMemberCode(request.getParameter("memberCode"));
    m.setMemberId(request.getParameter("memberId"));
    m.setMemberPwd(request.getParameter("memberPwd"));
    m.setMemberName(request.getParameter("memberName"));
    m.setMemberEmail(request.getParameter("memberEmail"));
    m.setMemberGender(request.getParameter("memberGender"));
    m.setMemberTel(request.getParameter("memberTel"));
    m.setMemberZipcode(request.getParameter("memberZipCode"));
    m.setMemberAddr(request.getParameter("memberAddr"));
    m.setMemberDetailAddr(request.getParameter("memberdetailAddr"));

    Part photoPart = request.getPart("memberPhoto");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl = InitServlet.ncpObjectStorageService.uploadFile(
              "bitcamp-nc7-bucket-03", "personal/member/", photoPart);
      m.setMemberPhoto(uploadFileUrl);
    }

    try {
      InitServlet.memberDao.insert(m);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("error", e);
      request.setAttribute("message", "회원 등록 오류!");
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
