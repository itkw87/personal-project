package personal.project.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.Member;

@WebServlet("/freeBoard/form")
public class FreeBoardFormServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    if (loginUser == null) {
      System.out.println("로그인안됨");
      response.sendRedirect("/auth/form.html");
      return;
    }

    System.out.println("자유게시글 폼 ");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>학교 통합정보 관리 시스템</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>자유게시글</h1>");
    out.println("<form action='/freeBoard/add' method='post' enctype='multipart/form-data'>");
    out.println("제목 <input type='text' name='freeTitle'><br>");
    out.println("내용 <textarea name='freeContent'></textarea><br>");
    out.println("파일 <input type='file' name='files' multiple><br>");
    out.println("<button>등록</button>");
    out.println("</form>");

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }
}

