package personal.project.handler;

import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.MemberDao;
import personal.project.vo.Member;
import personal.util.NcpObjectStorageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/member/update")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class MemberUpdateController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form");
      return;
    }

    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");

    try {
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
        response.sendRedirect("list");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      throw new ServletException(e);
    }
  }
}
