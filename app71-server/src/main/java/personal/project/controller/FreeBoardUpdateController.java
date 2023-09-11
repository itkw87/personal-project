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


@Component("/freeBoard/update")
public class FreeBoardUpdateController implements PageController {

  @Autowired
  FreeBoardService freeBoardService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      request.getParts(); // 일단 클라이언트가 보낸 파일을 읽는다. 그래야 응답 가능!
      return "redirect:../auth/login";
    }

    try {
      FreeBoard freeBoard = freeBoardService.get(Integer.parseInt(request.getParameter("freeBoardNo")));
      if (freeBoard == null || freeBoard.getFreeWriter().getMemberNo() != loginUser.getMemberNo()) {
        throw  new Exception("게시글이 존재하지 않거나 변경 권한이 없습니다.");
      }
      freeBoard.setFreeTitle(request.getParameter("freeTitle"));
      freeBoard.setFreeContent(request.getParameter("freeContent"));

      ArrayList<AttachedFile> attachedFiles = new ArrayList<>();
      for (Part part : request.getParts()) {
        if (part.getName().equals("files") && part.getSize() > 0) {
          AttachedFile attachedFile = ncpObjectStorageService.uploadFile(new AttachedFile(),
                  "bitcamp-nc7-bucket-03", "personal/freeBoard/", part);
          attachedFiles.add(attachedFile);
        }
      }
      freeBoard.setAttachedFiles(attachedFiles);

      freeBoardService.update(freeBoard);
      return "redirect:list";

    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=detail?freeBoardNo=" + request.getParameter("freeBoardNo"));
      throw e;
    }
  }
}
