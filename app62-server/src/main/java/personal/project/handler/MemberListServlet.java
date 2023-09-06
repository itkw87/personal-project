package personal.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import personal.project.vo.Member;

@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>회원 목록</h1>");
    out.println("<div style='margin:5px;'>");
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println("  <tr><th>번호</th> <th>구분</th> <th>사진</th> <th>이름</th> <th>이메일</th></tr>");
    out.println("</thead>");

    List<Member> list = InitServlet.memberDao.findAll();
    for (Member m : list) {
      if (m.getMemberPhoto() == null || m.getMemberPhoto().isEmpty()) {
        m.setMemberPhoto("avatar");
      }
      out.printf(
              "<tr>" +
                      " <td>%d</td>" +
                      " <td>%s</td>" +
                      " <td><img src='http://tzoswbbmvlov19010725.cdn.ntruss.com/personal/member/%s?type=f&w=30&h=40&faceopt=true&ttype=jpg'></td>" +
                      " <td><a href='/member/detail?memberNo=%d'>%s</a></td>" +
                      " <td>%s</td>" +
              "</tr>\n",
              m.getMemberNo(),
              "S".equals(m.getMemberCode()) ? "학생" : "교수",
              m.getMemberPhoto(),
              m.getMemberNo(),
              m.getMemberName(),
              m.getMemberEmail());
    }

    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }
}
