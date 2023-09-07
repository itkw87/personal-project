package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import personal.project.dao.FreeBoardDao;
import personal.project.vo.FreeBoard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component("/freeBoard/detail")
public class FreeBoardDetailController implements PageController {

  FreeBoardDao freeBoardDao;
  SqlSessionFactory sqlSessionFactory;

  public FreeBoardDetailController(FreeBoardDao freeBoardDao, SqlSessionFactory sqlSessionFactory) {
    this.freeBoardDao = freeBoardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      FreeBoard freeBoard = freeBoardDao.findBy(Integer.parseInt(request.getParameter("freeBoardNo")));

      if (freeBoard != null) {
        freeBoard.setFreeViewCount(freeBoard.getFreeViewCount() + 1);
        freeBoardDao.updateCount(freeBoard);
        sqlSessionFactory.openSession(false).commit();
        request.setAttribute("freeBoard", freeBoard);
      }
      return "/WEB-INF/jsp/freeBoard/detail.jsp";

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "5;url=/freeBoard/list");
      throw e;
    }
  }
}

