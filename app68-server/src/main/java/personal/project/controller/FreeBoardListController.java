package personal.project.controller;

import org.springframework.stereotype.Component;
import personal.project.dao.FreeBoardDao;
import personal.project.vo.FreeBoard;
import personal.project.vo.SearchParam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component("/freeBoard/list")
public class FreeBoardListController implements PageController {
  FreeBoardDao freeBoardDao;

  public FreeBoardListController(FreeBoardDao freeBoardDao) {
    this.freeBoardDao = freeBoardDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      String searchType =
              request.getParameter("searchType") == null ? "" : request.getParameter("searchType");
      String searchKeyword =
              request.getParameter("searchKeyword") == null ? "" : request.getParameter("searchKeyword").trim();

      FreeBoard freeBoard = new FreeBoard();
      SearchParam sp = new SearchParam();
      sp.setSearchType(searchType);
      sp.setSearchKeyword(searchKeyword);
      freeBoard.setSearchParam(sp);

      List<FreeBoard> list = freeBoardDao.findAll(freeBoard);

      request.setAttribute("searchParam", sp);
      request.setAttribute("list", list);
      return "/WEB-INF/jsp/freeBoard/list.jsp";

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      throw e;
    }
  }
}
