package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.LecBoardDao;
import personal.project.vo.LecBoard;
import personal.project.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/lecBoard/detail")
public class LecBoardDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/LoginForm.html");
      return;
    }

    LecBoard b = new LecBoard();
    b.setLecBoardNo(Integer.parseInt(request.getParameter("no")));

    LecBoardDao lecBoardDao  = (LecBoardDao) this.getServletContext().getAttribute("lecBoardDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");

    LecBoard lecBoard = (LecBoard) lecBoardDao.findBy(b);

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>강의 게시판</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>강의 게시판</h1>");

    if (lecBoard == null) {
      out.println("<p>해당 번호의 게시글이 없습니다!</p>");
      return;
    } else {
      out.println("<form action='/lecBoard/update' method='post'>");
      out.println("<table border='1'>");
      out.printf("<tr><th style='width:120px;'>번호</th>"
          + " <td style='width:300px;'><input type='text' name='no' value='%d' readonly style='border:none'></td></tr>\n",
          lecBoard.getLecBoardNo());
      out.printf("<tr><th>제목</th>" + " <td><input type='text' name='title' value='%s'></td></tr>\n",
          lecBoard.getLecTitle());
      out.printf("<tr><th>내용</th>"
          + " <td><textarea name='content' style='height:200px; width:400px;'>%s</textarea></td></tr>\n",
          lecBoard.getLecContent());
      out.printf("<tr><th>작성자</th> <td>%s</td></tr>\n", lecBoard.getLecWriter().getMemberName());
      out.printf("<tr><th>조회수</th> <td>%s</td></tr>\n", lecBoard.getLecViewCount());
      out.printf("<tr><th>등록일</th> <td>%tY-%1$tm-%1$td</td></tr>\n", lecBoard.getLecRegDate());
      out.println("</table>");

      out.println("<div>");
      if (loginUser.getMemberNo() == lecBoard.getLecWriter().getMemberNo()) {
        out.println("<button>변경</button>");
        out.println("<button type='reset'>초기화</button>");
        out.printf("<a href='/lecBoard/delete?no=%d'>삭제</a>\n", lecBoard.getLecBoardNo());
      }
      out.println("<a href='/lecBoard/list'>목록</a>");
      out.println("</div>");
      out.println("</form>");
      try {
        lecBoard.setLecViewCount(lecBoard.getLecViewCount() + 1);
        lecBoardDao.updateCount(lecBoard);
        sqlSessionFactory.openSession(false).commit();
      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
      }
    }

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }
}
