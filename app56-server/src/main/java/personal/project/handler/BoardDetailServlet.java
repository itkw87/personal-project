package personal.project.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.Board;
import personal.project.vo.Member;

@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    int category = Integer.parseInt(request.getParameter("category"));
    Board board =
        InitServlet.boardDao.findBy(category, Integer.parseInt(request.getParameter("no")));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.printf("<title>%s</title>\n", category == 1 ? "게시글" : "자유게시글");
    out.println("</head>");
    out.println("<body>");
    out.printf("<h1>%s</h1>\n", category == 1 ? "게시글" : "자유게시글");

    if (board == null) {
      out.println("<p>해당 번호의 게시글이 없습니다!</p>");
      return;
    } else {
      out.println("<form action='/board/update' method='post'>");
      out.printf("<input type='hidden' name='category' value='%d'>\n", board.getCategory());
      out.println("<table border='1'>");
      out.printf("<tr><th style='width:120px;'>번호</th>"
          + " <td style='width:300px;'><input type='text' name='no' value='%d' readonly style='border:none'></td></tr>\n",
          board.getNo());
      out.printf("<tr><th>제목</th>" + " <td><input type='text' name='title' value='%s'></td></tr>\n",
          board.getTitle());
      out.printf("<tr><th>내용</th>"
          + " <td><textarea name='content' style='height:200px; width:400px;'>%s</textarea></td></tr>\n",
          board.getContent());
      out.printf("<tr><th>작성자</th> <td>%s</td></tr>\n", board.getWriter().getName());
      out.printf("<tr><th>조회수</th> <td>%s</td></tr>\n", board.getViewCount());
      out.printf("<tr><th>등록일</th> <td>%tY-%1$tm-%1$td</td></tr>\n", board.getCreatedDate());
      out.println("</table>");

      out.println("<div>");
      if ("A".equals(loginUser.getAuthority()) || loginUser.getNo() == board.getWriter().getNo()) {
        out.println("<button>변경</button>");
        out.println("<button type='reset'>초기화</button>");
        out.printf("<a href='/board/delete?category=%d&no=%d'>삭제</a>\n", board.getCategory(),
            board.getNo());
      }
      out.printf("<a href='/board/list?category=%d'>목록</a>\n", board.getCategory());
      out.println("</div>");
      out.println("</form>");
      try {
        board.setViewCount(board.getViewCount() + 1);
        InitServlet.boardDao.updateCount(board);
        InitServlet.sqlSessionFactory.openSession(false).commit();
      } catch (Exception e) {
        InitServlet.sqlSessionFactory.openSession(false).rollback();
        throw new RuntimeException(e);
      }
    }

    out.println("</body>");
    out.println("</html>");
  }
}
