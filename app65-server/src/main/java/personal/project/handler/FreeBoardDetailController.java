package personal.project.handler;

import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.FreeBoardDao;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/freeBoard/detail")
public class FreeBoardDetailController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {


    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form");
      return;
    }
    FreeBoardDao freeBoardDao = (FreeBoardDao) this.getServletContext().getAttribute("freeBoardDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");

    try {

      FreeBoard freeBoard = freeBoardDao.findBy(Integer.parseInt(request.getParameter("freeBoardNo")));

      if (freeBoard != null) {
        freeBoard.setFreeViewCount(freeBoard.getFreeViewCount() + 1);
        freeBoardDao.updateCount(freeBoard);
        sqlSessionFactory.openSession(false).commit();
        request.setAttribute("freeBoard", freeBoard);
      }
      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/WEB-INF/jsp/freeBoard/detail.jsp").include(request, response);
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "5;url=/freeBoard/list");
      throw new RuntimeException(e);
    }
  }
}

