package personal.project.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

@WebServlet("/freeBoard/detail")
public class FreeBoardDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/LoginForm.html");
      return;
    }

    FreeBoard b = new FreeBoard();
    b.setFreeBoardNo(Integer.parseInt(request.getParameter("no")));

    System.out.println("매개변수b: " + b.toString());

    FreeBoard freeBoard = (FreeBoard) InitServlet.freeBoardDao.findBy(b);

    System.out.println("조회후Board: " + freeBoard.toString());

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>자유 게시판</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>자유 게시판</h1>");

    if (freeBoard == null) {
      out.println("<p>해당 번호의 게시글이 없습니다!</p>");
      return;
    } else {
      out.println("<form action='/freeBoard/update' method='post'>");
      out.println("<table border='1'>");
      out.printf("<tr><th style='width:120px;'>번호</th>"
          + " <td style='width:300px;'><input type='text' name='no' value='%d' readonly style='border:none'></td></tr>\n",
          freeBoard.getFreeBoardNo());
      out.printf("<tr><th>제목</th>" + " <td><input type='text' name='title' value='%s'></td></tr>\n",
          freeBoard.getFreeTitle());
      out.printf("<tr><th>내용</th>"
          + " <td><textarea name='content' style='height:200px; width:400px;'>%s</textarea></td></tr>\n",
          freeBoard.getFreeContent());
      out.printf("<tr><th>작성자</th> <td>%s</td></tr>\n", freeBoard.getFreeWriter().getMemberName());
      out.printf("<tr><th>조회수</th> <td>%s</td></tr>\n", freeBoard.getFreeViewCount());
      out.printf("<tr><th>등록일</th> <td>%tY-%1$tm-%1$td</td></tr>\n", freeBoard.getFreeRegDate());
      out.println("</table>");

      out.println("<div>");
      if (loginUser.getMemberNo() == freeBoard.getFreeWriter().getMemberNo()) {
        out.println("<button>변경</button>");
        out.println("<button type='reset'>초기화</button>");
        out.printf("<a href='/freeBoard/delete?no=%d'>삭제</a>\n", freeBoard.getFreeBoardNo());
      }
      out.println("<a href='/freeBoard/list'>목록</a>");
      out.println("</div>");
      out.println("</form>");
      try {
        freeBoard.setFreeViewCount(freeBoard.getFreeViewCount() + 1);
        InitServlet.freeBoardDao.updateCount(freeBoard);
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
