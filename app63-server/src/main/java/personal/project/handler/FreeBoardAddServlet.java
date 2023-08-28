package personal.project.handler;

import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.FreeBoardDao;
import personal.project.util.NcpObjectStorageService;
import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/freeBoard/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class FreeBoardAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form");
      return;
    }

    FreeBoardDao freeBoardDao = (FreeBoardDao) this.getServletContext().getAttribute("freeBoardDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");

    try {
      FreeBoard freeBoard = new FreeBoard();
      freeBoard.setFreeWriter(loginUser);
      freeBoard.setFreeTitle(request.getParameter("freeTitle"));
      freeBoard.setFreeContent(request.getParameter("freeContent"));

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      // 각각의 파트에서 값을 꺼낸다.
      for (Part part : request.getParts()) {
        if (part.getName().equals("files") && part.getSize() > 0) {
          AttachedFile attachedFile = ncpObjectStorageService.uploadFile(new AttachedFile(),
                  "bitcamp-nc7-bucket-03", "personal/freeBoard/", part);
          attachedFiles.add(attachedFile);
        }
      }
      freeBoard.setAttachedFiles(attachedFiles);

      freeBoardDao.insert(freeBoard);
      if (!freeBoard.getAttachedFiles().isEmpty()) {
        freeBoardDao.insertFiles(freeBoard);
      }
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();

      // ErrorServlet 으로 포워딩 하기 전에 ErrorServlet이 사용할 데이터를
      // SerlvetRequest 보관소에 저장한다.
      request.setAttribute("error", e);
      request.setAttribute("message", "게시글 등록 오류!");
      request.setAttribute("refresh", "2;url=list");

      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
