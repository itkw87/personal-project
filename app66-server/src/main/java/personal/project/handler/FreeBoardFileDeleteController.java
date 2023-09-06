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
      request.setAttribute("viewUrl", "redirect:../auth/login");
      return;
    }

    FreeBoardDao freeBoardDao = (FreeBoardDao) this.getServletContext().getAttribute("freeBoardDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");


    FreeBoard freeBoard = null;
    try {
      int fileNo = Integer.parseInt(request.getParameter("fileNo"));

      AttachedFile attachedFile = freeBoardDao.findFileBy(fileNo);
      freeBoard = freeBoardDao.findBy(attachedFile.getFreeBoardNo());
      if (freeBoard.getFreeWriter().getMemberNo() != loginUser.getMemberNo()) {
        throw new ServletException("게시글 변경 권한이 없습니다!");
      }

      if (freeBoardDao.deleteFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        request.setAttribute("viewUrl", "redirect:detail?freeBoardNo=" + freeBoard.getFreeBoardNo());
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=detail?freeBoardNo=" + freeBoard.getFreeBoardNo());
      request.setAttribute("exception", e);
    }
  }
}
