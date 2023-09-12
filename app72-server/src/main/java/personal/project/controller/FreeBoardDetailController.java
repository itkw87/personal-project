package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import personal.project.dao.FreeBoardDao;
import personal.project.service.FreeBoardService;
import personal.project.vo.FreeBoard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component("/freeBoard/detail")
public class FreeBoardDetailController implements PageController {

  @Autowired
  FreeBoardService freeBoardService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      int freeBoardNo = Integer.parseInt(request.getParameter("freeBoardNo"));

      FreeBoard freeBoard = freeBoardService.get(freeBoardNo);
      if (freeBoard != null) {
        freeBoardService.increaseViewCount(freeBoardNo);
        request.setAttribute("freeBoard", freeBoard);
      }
      return "/WEB-INF/jsp/freeBoard/detail.jsp";
    } catch (Exception e) {
      request.setAttribute("refresh", "5;url=/freeBoard/list");
      throw e;
    }
  }
}

