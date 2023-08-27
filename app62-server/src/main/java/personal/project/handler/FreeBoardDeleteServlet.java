package personal.project.handler;

import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/freeBoard/delete")
public class FreeBoardDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    FreeBoard freeBoard = new FreeBoard();
    freeBoard.setFreeBoardNo(Integer.parseInt(request.getParameter("freeBoardNo")));
    freeBoard.setFreeWriter(loginUser);

    try {
      if (InitServlet.freeBoardDao.delete(freeBoard) == 0) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
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
