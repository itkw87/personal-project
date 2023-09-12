package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import personal.project.dao.FreeBoardDao;
import personal.project.service.FreeBoardService;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component("/freeBoard/delete")
public class FreeBoardDeleteController implements PageController {

  @Autowired
  FreeBoardService freeBoardService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    try {
      FreeBoard fb = freeBoardService.get(Integer.parseInt(request.getParameter("freeBoardNo")));

      if (fb == null || fb.getFreeWriter().getMemberNo() != loginUser.getMemberNo()) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        freeBoardService.delete(fb.getFreeBoardNo());
        return "redirect:list";
      }
    } catch (Exception e) {
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
