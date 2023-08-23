package personal.project.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.Member;

@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    if (memberNo != loginUser.getMemberNo()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta http-equiv='refresh' content='1;url=/member/list'>");
      out.println("<title>학교 통합 정보관리 시스템</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>권한 없음</h1>");
      out.println("<p>회원정보 변경 권한이 없습니다.</p>");
      out.println("</body>");
      out.println("</html>");
      return;
    }

    Member m = new Member();
    m.setMemberNo(memberNo);
    m.setMemberCode(request.getParameter("memberCode"));
    m.setMemberName(request.getParameter("memberName"));
    m.setMemberEmail(request.getParameter("memberEmail"));
    m.setMemberPwd(request.getParameter("memberPwd"));
    m.setMemberGender(request.getParameter("memberGender"));
    m.setMemberTel(request.getParameter("memberTel"));
    m.setMemberZipcode(request.getParameter("memberZipcode"));
    m.setMemberAddr(request.getParameter("memberAddr"));
    m.setMemberDetailAddr(request.getParameter("memberDetailAddr"));

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/member/list'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 변경</h1>");

  System.out.println(m.toString());

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
