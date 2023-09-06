package personal.project.controller;

import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.BoardDao;
import personal.project.util.Component;
import personal.project.util.HttpServletRequest;
import personal.project.util.HttpServletResponse;
import personal.project.util.Servlet;
import personal.project.vo.Board;
import personal.project.vo.Member;

@Component("/board/add")
public class BoardAddServlet implements Servlet {
  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardAddServlet(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }


  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    int category = Integer.parseInt(request.getParameter("category"));

    Board board = new Board();
    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));
    board.setWriter(loginUser);
    board.setCategory(category);

    response.setContentType("text/html;charset=UTF-8"); // 출력 스트림을 사용하기전 contentType 지정
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8>");
    out.printf("<meta http-equiv='refresh' content='1;url=/board/list?category=%d'>\n", category);
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 등록</h1>");
    try {
      boardDao.insert(board);
      sqlSessionFactory.openSession(false).commit();
      out.println("<p>등록 성공입니다!</p>");
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>등록 실패입니다!</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }
}
