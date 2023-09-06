package personal.project.controller;

import java.io.PrintWriter;
import java.util.List;
import personal.project.dao.MemberDao;
import personal.project.util.Component;
import personal.project.util.HttpServletRequest;
import personal.project.util.HttpServletResponse;
import personal.project.util.Servlet;
import personal.project.vo.Member;

@Component("/member/list")
public class MemberListServlet implements Servlet {

  MemberDao memberDao;

  public MemberListServlet(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 목록</h1>");
    out.println("<div style='margin:5px;'>");
    out.println("<a href='/member/form.html'>새 회원</a>");
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println("  <tr><th>번호</th> <th>구분</th> <th>이름</th> <th>이메일</th></tr>");
    out.println("</thead>");

    List<Member> list = memberDao.findAll();
    for (Member m : list) {
      out.printf(
          "<tr>" + " <td>%d</td>" + " <td>%s</td>"
              + " <td><a href='/member/detail?no=%d'>%s</a></td>" + " <td>%s</td>" + "</tr>\n",
          m.getNo(), "S".equals(m.getAuthority()) ? "학생" : "교사", m.getNo(), m.getName(),
          m.getEmail());
    }

    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");
    out.println("</body>");
    out.println("</html>");
  }
}
