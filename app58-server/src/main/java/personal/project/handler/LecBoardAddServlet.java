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

@WebServlet("/lecBoard/add")
public class LecBoardAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    LecBoard lecBoard = new LecBoard();
    lecBoard.setLectureNo(Integer.parseInt(request.getParameter("lectureNo")));
    lecBoard.setLecTitle(request.getParameter("lecTitle"));
    lecBoard.setLecContent(request.getParameter("lecContent"));
    lecBoard.setLecWriter(loginUser);
    lecBoard.setLecViewCount(0);
    lecBoard.setLecStatus("Y");

    System.out.println("추가할 강의 게시글 정보 : " + lecBoard.toString());

    response.setContentType("text/html;charset=UTF-8"); // 출력 스트림을 사용하기전 contentType 지정
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/lecBoard/list'>");
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 등록</h1>");
    try {
      InitServlet.lecBoardDao.insert(lecBoard);
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
