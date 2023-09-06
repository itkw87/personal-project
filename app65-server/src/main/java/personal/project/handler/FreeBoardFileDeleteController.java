package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.FreeBoardDao;
import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/freeBoard/fileDelete")
public class FreeBoardFileDeleteController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auto/form");
      return;
    }

    FreeBoardDao freeBoardDao = (FreeBoardDao) this.getServletContext().getAttribute("freeBoardDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");


    FreeBoard freeBoard = null;
    try {
      int fileNo = Integer.parseInt(request.getParameter("fileNo"));

      //첨부파일 데이터에 있는 게시글 번호로 게시글 데이터를 가져온다.
      AttachedFile attachedFile = freeBoardDao.findFileBy(fileNo);

      // 첨부팡일 데이터에 있는 게시글 번호로 데이터를 가져온다.
      freeBoard = freeBoardDao.findBy(attachedFile.getFreeBoardNo());

      // 게시글 데이터의 작성자와 로그인 한 작성자가 일치하는지 검사한다.
      if (freeBoard.getFreeWriter().getMemberNo() != loginUser.getMemberNo()) {
        throw new ServletException("게시글 변경 권한이 없습니다!");
      }

      // 일치하면 첨부파일을 삭제한다.
      if (freeBoardDao.deleteFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("/freeBoard/detail?freeBoardNo=" + freeBoard.getFreeBoardNo());
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=detail?freeBoardNo=" + freeBoard.getFreeBoardNo());
      throw new RuntimeException(e);
    }
  }
}
