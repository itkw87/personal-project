package personal.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.Board;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    int category = Integer.parseInt(request.getParameter("category"));
    String searchType =
        request.getParameter("searchType") == null ? "" : request.getParameter("searchType");
    String searchKeyword =
        request.getParameter("searchKeyword") == null ? "" : request.getParameter("searchKeyword");

    Board b = new Board();
    b.setCategory(category);
    b.setSearchType(searchType);
    b.setSearchKeyword(searchKeyword);

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.printf("<title>%s</title>\n", category == 1 ? "게시글" : "자유게시글");
    out.println("</head>");
    out.println("<body>");
    out.printf("<h1>%s 목록</h1>\n", category == 1 ? "게시글" : "자유게시글");
    out.println("<div style='margin:5px;'>");
    out.printf(
        "<button onclick=\"location.href='http://localhost:8888/board/form?category=%d'\">새 글</button>",
        category);
    out.println("</div>");
    out.println("<hr>");



    List<Board> list = InitServlet.boardDao.findAll(b);
    if (list != null && list.size() > 0) {
      out.printf("<table border='1'>");
      out.println("<thead>");
      out.println("  <tr><th>번호</th> <th>제목</th> <th>작성자</th> <th>조회수</th> <th>등록일</th></tr>");
      out.println("</thead>");
      for (Board board : list) {
        out.printf(
            "<tr>" + " <td>%d</td>" + " <td><a href='/board/detail?category=%d&no=%d'>%s</a></td>"
                + " <td>%s</td>" + " <td>%d</td>" + " <td>%s</td></tr>\n",
            board.getNo(), board.getCategory(), board.getNo(),
            (board.getTitle().length() > 0 ? board.getTitle() : "<제목없음>"),
            board.getWriter().getName(), board.getViewCount(),
            dateFormatter.format(board.getCreatedDate()));
      }
      out.println("</tbody>");
      out.println("</table>");
    } else {
      out.print("<br>");
      out.print("<br>");
      out.print("<h2>해당 게시물은 존재하지 않습니다!</h2>");
      out.print("<br>");
      out.print("<br>");
    }


    out.println("<hr>");
    out.print(
        "<button type='button' onclick=\"location.href='http://localhost:8888/'\">메인</button>");
    out.println("<form action='/board/list'>");
    out.printf(
        "<select name='searchType'>" + "<option value='title' %s>제목</option>"
            + "<option value='content' %s>내용</option>" + "</select>",
        "title".equals(searchType) ? "selected" : "",
        "content".equals(searchType) ? "selected" : "");
    out.printf(
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' name='searchKeyword' placeholder='검색어를 입력하세요.' value='%s' style='width:220px'/>",
        searchKeyword);
    out.printf("<input type='hidden' name='category' value='%d'/>", category);
    out.print("<button>검색</button>");
    out.print("</form>");
    out.println("</body>");
    out.println("</html>");
  }
}
