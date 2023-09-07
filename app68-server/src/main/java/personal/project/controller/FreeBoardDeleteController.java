package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import personal.project.dao.FreeBoardDao;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component("/freeBoard/delete")
public class FreeBoardDeleteController implements PageController {

  FreeBoardDao freeBoardDao;
  SqlSessionFactory sqlSessionFactory;

  public FreeBoardDeleteController(FreeBoardDao freeBoardDao, SqlSessionFactory sqlSessionFactory) {
    this.freeBoardDao = freeBoardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:../auth/login";
    }

    try {
    FreeBoard freeBoard = new FreeBoard();
    freeBoard.setFreeBoardNo(Integer.parseInt(request.getParameter("freeBoardNo")));
    freeBoard.setFreeWriter(loginUser);

    freeBoardDao.deleteFiles(freeBoard.getFreeBoardNo());

      if (freeBoardDao.delete(freeBoard) == 0) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        return "redirect:list";
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
