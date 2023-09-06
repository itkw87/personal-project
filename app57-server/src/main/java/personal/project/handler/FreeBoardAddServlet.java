package personal.project.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


    try {
      // 멀티파트의 각 파트 데이터를 저장할 객체를 만드는 공장
      DiskFileItemFactory factory = new DiskFileItemFactory();

      // 멀티파트 형식으로 넘어 온 요청 파라미터를 분석하여 처리하는 객체
      ServletFileUpload upload = new ServletFileUpload(factory);

      // 멀티파트 요청 파라미터를 분석
      List<FileItem> parts = upload.parseRequest(request);

      // 웹 애플리케이션 환경정보를 알고 있는 객체 꺼내기
      ServletContext sc = request.getServletContext();

      // 웹 애플리케이션 환경정보에서 파일을 업로드할 디렉토리의 실제 경로를 계산하여 추출한다.
      String uploadDir = sc.getRealPath("/upload/freeBoard/");

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      FreeBoard freeBoard = new FreeBoard();
      freeBoard.setFreeWriter(loginUser);

      // 각각의 파트에서 값을 꺼낸다.
      for (FileItem part : parts) {
        if (part.getSize() == 0) {
          continue;
        }
        if (part.isFormField()) { // 일반 데이터인 경우
          if (part.getFieldName().equals("freeTitle")) {
            freeBoard.setFreeTitle(part.getString("UTF-8"));
          } else if (part.getFieldName().equals("freeContent")) {
            freeBoard.setFreeContent(part.getString("UTF-8"));
          }
        } else { // 파일 데이터인 경우
          // 업로드 파일은 배포 폴더에 저장한다.
          // 1) 원래 파일의 이름과 저장할 때 사용할 파일 이름을 준비한다.
          String originFileName = part.getName();
          String saveFileName = UUID.randomUUID().toString();

          // 2) upload 배포 폴더에서 파일을 저장한다.
          part.write(new File(uploadDir, saveFileName));

          // 3) 파일 이름을 객체에 보관하여 목록에 추가한다.
          AttachedFile attachedFile = new AttachedFile();
          attachedFile.setFilePath(uploadDir);
          attachedFile.setOriginFileName(originFileName);
          attachedFile.setSaveFileName(saveFileName);

          attachedFiles.add(attachedFile);
        }
      }
      freeBoard.setAttachedFiles(attachedFiles);

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
      out.println("<h1>게시글 등록</h1>");
      try {
        InitServlet.freeBoardDao.insert(freeBoard);

        if (!freeBoard.getAttachedFiles().isEmpty()) {
          InitServlet.freeBoardDao.insertFiles(freeBoard);
        }
        InitServlet.sqlSessionFactory.openSession(false).commit();

        out.println("<p>등록 성공입니다!</p>");
      } catch (Exception e) {
        InitServlet.sqlSessionFactory.openSession(false).rollback();
        out.println("<p>등록 실패입니다!</p>");
        e.printStackTrace();
      }
      out.println("</body>");
      out.println("</html>");

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
