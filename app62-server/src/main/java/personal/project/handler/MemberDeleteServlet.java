package personal.project.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.Member;

@WebServlet("/member/delete")

public class MemberDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    try {
      if (InitServlet.memberDao.delete(Integer.parseInt(request.getParameter("memberNo"))) == 0) {
        throw new Exception("해당 번호의 회원이 없습니다!");
      } else {
      InitServlet.sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("/auth/logout");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();

      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }

}
