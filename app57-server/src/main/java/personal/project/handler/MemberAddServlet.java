package personal.project.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.Member;

@WebServlet("/member/add")
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

    System.out.println(m.toString());

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 등록</h1>");

    try {
      InitServlet.memberDao.insert(m);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      out.println("<p>회원가입에 성공하셨습니다!</p>");

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>회원가입에 실패하셨습니다!</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }
}
