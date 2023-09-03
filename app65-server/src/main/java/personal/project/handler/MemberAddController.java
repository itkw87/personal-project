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

@WebServlet("/member/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class MemberAddController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher("/WEB-INF/jsp/member/form.jsp").include(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");

    try {
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
      m.setMemberDetailAddr(request.getParameter("memberDetailAddr"));

      Part photoPart = request.getPart("memberPhoto");
      if (photoPart.getSize() > 0) {
        String uploadFileUrl = ncpObjectStorageService.uploadFile(
                "bitcamp-nc7-bucket-03", "personal/member/", photoPart);
        m.setMemberPhoto(uploadFileUrl);
      }

      System.out.println(m.toString());
      memberDao.insert(m);
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("message", "회원 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw new ServletException(e);
    }
  }
}
