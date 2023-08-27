package personal.project.handler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

@WebServlet("/freeBoard/update")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class FreeBoardUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/freeBoard/list'>");
    out.println("<title>게시글</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시글 변경</h1>");

    try {
      FreeBoard freeBoard = new FreeBoard();
      freeBoard.setFreeWriter(loginUser);
      freeBoard.setFreeBoardNo(Integer.parseInt(request.getParameter("freeBoardNo")));
      freeBoard.setFreeTitle(request.getParameter("freeTitle"));
      freeBoard.setFreeContent(request.getParameter("freeContent"));

      String uploadDir = request.getServletContext().getRealPath("/upload/freeBoard/");
      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

      // 각각의 파트에서 값을 꺼낸다.
      for (Part part : request.getParts()) {
        if (part.getName().equals("files") && part.getSize() > 0) {
          String originFileName = part.getSubmittedFileName();
          String saveFileName = UUID.randomUUID().toString();

          part.write(uploadDir + saveFileName);

          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(uploadDir);
          attachedFile.setOriginFileName(originFileName);
          attachedFile.setSaveFileName(saveFileName);
          attachedFiles.add(attachedFile);
        }
      }
      freeBoard.setAttachedFiles(attachedFiles);

      if (InitServlet.freeBoardDao.update(freeBoard) == 0) {
        out.println("<p>게시글 변경 권한이 없습니다.</p>");
      } else {
        if (freeBoard.getAttachedFiles().size() > 0) {
          // 게시글을 정상적으로 변경했으면, 그 게시글의 첨부파일을 추가한다.
          int count = InitServlet.freeBoardDao.insertFiles(freeBoard);
        }
        out.println("<p>변경했습니다!</p>");
        response.setHeader("refresh", "1;url=/freeBoard/list");

      }
      InitServlet.sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>게시글 변경 실패입니다!</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }
}
