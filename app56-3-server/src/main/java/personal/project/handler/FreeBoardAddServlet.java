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

@WebServlet("/freeBoard/add")
public class FreeBoardAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    FreeBoard freeBoard = new FreeBoard();
    freeBoard.setFreeTitle(request.getParameter("freeTitle"));
    freeBoard.setFreeContent(request.getParameter("freeContent"));
    freeBoard.setFreeWriter(loginUser);
    freeBoard.setFreeViewCount(0);
    freeBoard.setFreeStatus("Y");

    response.setContentType("text/html;charset=UTF-8"); // 출력 스트림을 사용하기전 contentType 지정
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/freeBoard/list'>");
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 등록</h1>");
    try {
      InitServlet.freeBoardDao.insert(freeBoard);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      out.println("<p>등록 성공입니다!</p>");
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>등록 실패입니다!</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }
}
