package personal.project.handler;

import personal.project.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

 @WebServlet("/index.html")
public class HomeServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>학교 통합 정보 관리시스템</title>");
    out.println("</head>");
    out.println("<body>");
    out.print("<h1 style='display: inline;'>학교 통합 정보 관리시스템</h1>");
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println(
          "<button type='button' onclick=\"location.href='http://localhost:8888/auth/form.html'\" style='margin-left: 1200px;'>로그인</button>");
    } else {
      out.printf(
          "<label style='font-style: italic; font-weight: bold; margin-left: 800px;'>%s 회원님</label> 환영합니다!  <button type='button' onclick=\"location.href='http://localhost:8888/auth/logout'\">로그아웃</button>\n",
          loginUser.getMemberName());
    }
    out.println("<ul>");
    out.println("  <li><a href='/member/list'>회원관리</a></li>");
    out.println("  <li><a href='/lecBoard/list'>강의게시판</a></li>");
    out.println("  <li><a href='/freeBoard/list'>자유게시판</a></li>");
    out.println("</ul>");
    out.println("</body>");
    out.println("</html>");

  }
}
