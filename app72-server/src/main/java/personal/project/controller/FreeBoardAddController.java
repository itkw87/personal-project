package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import personal.project.dao.FreeBoardDao;
import personal.project.service.FreeBoardService;
import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;
import personal.project.service.NcpObjectStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.ArrayList;

@Component("/freeBoard/add")
public class FreeBoardAddController implements PageController {

  // 생성자 대신 @Autowired 사용하여 인스턴스 필드 초기화
  @Autowired
  FreeBoardService freeBoardService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/freeBoard/form.jsp";
    }

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      request.getParts(); // 일단 클라이언트가 보낸 파일을 읽는다. 그래야 응답 가능!
      return "redirect:../auth/login";
    }

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

      freeBoardService.add(freeBoard);
      return "redirect:list";

    } catch (Exception e) {
      request.setAttribute("message", "게시글 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw  e;
    }
  }
}
