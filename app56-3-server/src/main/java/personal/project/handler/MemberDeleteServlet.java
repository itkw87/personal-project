package personal.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.Member;

@WebServlet("/member/delete")

public class MemberDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    int memberNo = Integer.parseInt(request.getParameter("memberNo"));
    if (memberNo != loginUser.getMemberNo()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta http-equiv='refresh' content='1;url=/member/list'>");
      out.println("<title>학교 통합 정보관리 시스템</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>권한 없음</h1>");
      out.println("<p>회원정보 삭제 권한이 없습니다.</p>");
      out.println("</body>");
      out.println("</html>");
      return;
    }

    try {
      if (InitServlet.memberDao.delete(memberNo) == 0) {
        throw new Exception("해당 번호의 회원이 없습니다!");
      } else {
        response.sendRedirect("/member/list");
      }
      InitServlet.sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

}
