package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import personal.project.dao.FreeBoardDao;
import personal.project.service.FreeBoardService;
import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/freeBoard/fileDelete")
public class FreeBoardFileDeleteController implements PageController {

  @Autowired
  FreeBoardService freeBoardService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    FreeBoard freeBoard = null;
    try {
      int fileNo = Integer.parseInt(request.getParameter("fileNo"));

      AttachedFile attachedFile = freeBoardService.getAttachedFile(fileNo);
      freeBoard = freeBoardService.get(attachedFile.getFreeBoardNo());
      if (freeBoard.getFreeWriter().getMemberNo() != loginUser.getMemberNo()) {
        throw new ServletException("게시글 변경 권한이 없습니다!");
      }

      if (freeBoardService.deleteAttachedFile(fileNo) == 0) {
        throw new Exception("해당 번호의 첨부파일이 없다.");
      } else {
        return "redirect:detail?freeBoardNo=" + freeBoard.getFreeBoardNo();
      }

    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=detail?freeBoardNo=" + freeBoard.getFreeBoardNo());
      throw e;
    }
  }
}
