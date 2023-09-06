package personal.project.controller;

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
public class FreeBoardListServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    String searchType =
            request.getParameter("searchType") == null ? "" : request.getParameter("searchType");
    String searchKeyword =
            request.getParameter("searchKeyword") == null ? "" : request.getParameter("searchKeyword").trim();

    System.out.println("검색어: " + searchKeyword);

    FreeBoard freeBoard = new FreeBoard();
    SearchParam sp = new SearchParam();
    sp.setSearchType(searchType);
    sp.setSearchKeyword(searchKeyword);
    freeBoard.setSearchParam(sp);

    System.out.println(freeBoard.toString());

    List<FreeBoard> list  = InitServlet.freeBoardDao.findAll(freeBoard);

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>자유게시판</title>");
    out.println("</head>");
    out.println("<body>");

    // HaderServlet의 출력 결과를 합친다.
    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>자유게시글 목록</h1>");
    out.println("<div style='margin:5px;'>");
    out.println("<button onclick=\"location.href='http://localhost:8888/freeBoard/form'\">새 글</button>");
    out.println("</div>");
    out.println("<hr>");

    System.out.println(list);

    if (list != null && list.size() > 0) {
      out.printf("<table border='1'>");
      out.println("<thead>");
      out.println("<tr><th>번호</th>  <th>제목</th> <th>작성자</th> <th>조회수</th> <th>등록일</th></tr>");
      out.println("</thead>");
      out.println("<tbody>");
      for (FreeBoard fb : list) {
          out.printf(
                  "<tr>" + " <td>%d</td>" + " <td><a href='/freeBoard/detail?freeBoardNo=%d'>%s</a></td>"
                          + " <td>%s</td>" + " <td>%d</td>" + " <td>%s</td></tr>\n",
                  fb.getFreeBoardNo(), fb.getFreeBoardNo(),
                  (fb.getFreeTitle().length() > 0 ? fb.getFreeTitle() : "<제목없음>"),
                  fb.getFreeWriter().getMemberName(), fb.getFreeViewCount(),
                  dateFormatter.format(fb.getFreeRegDate()));
        }
      } else {
      out.print("<br>");
      out.print("<br>");
      out.print("<h2>존재하는 게시글이 없습니다!</h2>");
      out.print("<br>");
      out.print("<br>");
    }
      out.println("</tbody>");
      out.println("</table>");

    out.println("<hr>");
    out.print(
            "<button type='button' onclick=\"location.href='http://localhost:8888/'\">메인</button>");
    out.println("<form action='/freeBoard/list'>");
    out.printf(
            "<select name='searchType'>" + "<option value='free_title' %s>제목</option>"
                    + "<option value='free_content' %s>내용</option>" + "</select>",
            searchType.contains("title") ? "selected" : "",
            searchType.contains("content") ? "selected" : "");
    out.printf(
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' name='searchKeyword' placeholder='검색어를 입력하세요.' value='%s' style='width:220px'/>",
            searchKeyword);
    out.print("<button>검색</button>");
    out.print("</form>");

    // FooterServlet의 출력 결과를 합친다.
    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }
}
