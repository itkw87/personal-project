package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import personal.project.dao.FreeBoardDao;
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

  FreeBoardDao freeBoardDao;
  SqlSessionFactory sqlSessionFactory;
  NcpObjectStorageService ncpObjectStorageService;

  public FreeBoardUpdateController(FreeBoardDao freeBoardDao, SqlSessionFactory sqlSessionFactory, NcpObjectStorageService ncpObjectStorageService) {
    this.freeBoardDao = freeBoardDao;
    this.sqlSessionFactory = sqlSessionFactory;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      request.getParts(); // 일단 클라이언트가 보낸 파일을 읽는다. 그래야 응답 가능!
      return "redirect:../auth/login";
    }


    try {
      FreeBoard freeBoard = new FreeBoard();
      freeBoard.setFreeWriter(loginUser);
      freeBoard.setFreeBoardNo(Integer.parseInt(request.getParameter("freeBoardNo")));
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

      if (freeBoardDao.update(freeBoard) == 0) {
        throw new Exception("게시글이 없거나 변경 권한이 없습니다.!");
      } else {
        if (attachedFiles.size() > 0) {
          freeBoardDao.insertFiles(freeBoard);
        }
        sqlSessionFactory.openSession(false).commit();
        return "redirect:list";
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=detail?freeBoardNo=" + request.getParameter("freeBoardNo"));
      throw e;
    }
  }
}
