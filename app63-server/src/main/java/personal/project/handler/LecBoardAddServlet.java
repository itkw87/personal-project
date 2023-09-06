package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.FreeBoardDao;
import personal.project.dao.LecBoardDao;
import personal.project.util.NcpObjectStorageService;
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
      response.sendRedirect("/auth/form");
      return;
    }

    LecBoardDao lecBoardDao  = (LecBoardDao) this.getServletContext().getAttribute("lecBoardDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");

    try {
      LecBoard lecBoard = new LecBoard();
      lecBoard.setLectureNo(Integer.parseInt(request.getParameter("lectureNo")));
      lecBoard.setLecTitle(request.getParameter("lecTitle"));
      lecBoard.setLecContent(request.getParameter("lecContent"));
      lecBoard.setLecWriter(loginUser);
      lecBoard.setLecViewCount(0);
      lecBoard.setLecStatus("Y");

      lecBoardDao.insert(lecBoard);
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();

      // ErrorServlet 으로 포워딩 하기 전에 ErrorServlet이 사용할 데이터를
      // ServletRequest 보관소에 저장한다.
      request.setAttribute("error", e);
      request.setAttribute("message", "게시글 등록 오류!");
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
