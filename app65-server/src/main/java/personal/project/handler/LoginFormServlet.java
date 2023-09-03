package personal.project.handler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/form")
public class LoginFormServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    String id = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("memberId")) {
          id = cookie.getValue();
          break;
        }
      }
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>비트캠프</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>로그인</h1>");

    out.println("<form action='/auth/login' method='post'>");
    out.println("<table border='1'>");
    out.println("<tr>");
    out.printf("  <th>아이디</th> <td><input type='text' name='memberId' value='%s'></td>\n", id);
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>암호</th> <td><input type='password' name='memberPwd'></td>");
    out.println("</tr>");
    out.println("</table>");
    out.println("<button>로그인</button>");
    out.println(" <input type='checkbox' name='saveId'> 아이디 저장");
    out.println("</form>");

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");

  }
}











