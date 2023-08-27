package personal.project.handler;

import personal.project.vo.LecBoard;
import personal.project.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/lecBoard/update")
public class LecBoardUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    try {
      LecBoard lecBoard = new LecBoard();
      lecBoard.setLecBoardNo(Integer.parseInt(request.getParameter("no")));
      lecBoard.setLecTitle(request.getParameter("title"));
      lecBoard.setLecContent(request.getParameter("content"));
      lecBoard.setLecWriter(loginUser);

      if (InitServlet.lecBoardDao.update(lecBoard) == 0) {
        throw new Exception("게시글이 없거나 변경 권한이 없습니다.");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list");

      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list?category=" + request.getParameter("category"));

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
