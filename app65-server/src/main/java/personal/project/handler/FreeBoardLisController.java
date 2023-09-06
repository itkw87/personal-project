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

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form");
      return;
    }


    try {
      String searchType =
              request.getParameter("searchType") == null ? "" : request.getParameter("searchType");
      String searchKeyword =
              request.getParameter("searchKeyword") == null ? "" : request.getParameter("searchKeyword").trim();

      System.out.println("검색 타입: " + searchKeyword);
      System.out.println("검색어: " + searchKeyword);

      FreeBoard freeBoard = new FreeBoard();
      SearchParam sp = new SearchParam();
      sp.setSearchType(searchType);
      sp.setSearchKeyword(searchKeyword);
      freeBoard.setSearchParam(sp);

      FreeBoardDao freeBoardDao = (FreeBoardDao) this.getServletContext().getAttribute("freeBoardDao");


      List<FreeBoard> list = freeBoardDao.findAll(freeBoard);
      System.out.println(list);


      request.setAttribute("searchParam", sp);
      request.setAttribute("list", list);

      response.setContentType("text/html;charset=UTF-8");
      request.getRequestDispatcher("/WEB-INF/jsp/freeBoard/list.jsp").include(request, response);

    } catch (Exception e) {
      request.setAttribute("refresh", "1;url=/");
      throw new ServletException(e);
    }
  }
}
