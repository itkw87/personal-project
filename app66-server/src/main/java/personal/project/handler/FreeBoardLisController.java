package personal.project.controller;

import personal.project.dao.FreeBoardDao;
import personal.project.vo.FreeBoard;
import personal.project.vo.Member;
import personal.project.vo.SearchParam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/freeBoard/list")
public class FreeBoardLisController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

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

      FreeBoardDao freeBoardDao = (FreeBoardDao) this.getServletContext().getAttribute("freeBoardDao");
      List<FreeBoard> list = freeBoardDao.findAll(freeBoard);

      request.setAttribute("searchParam", sp);
      request.setAttribute("list", list);
      request.setAttribute("viewUrl", "/WEB-INF/jsp/freeBoard/list.jsp");
    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      request.setAttribute("exception", e);
    }
  }
}
