package personal.project.controller;

import personal.project.vo.Member;
import personal.project.vo.Participant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet("/lecBoard/form")
public class LecBoardFormServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    List<Participant> participantsList = loginUser.getParticipantList();

    if (loginUser == null) {
      response.sendRedirect("/auth/form");
      return;
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>학교 통합정보 관리 시스템</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>강의게시글</h1>");

    if (participantsList.size() > 0) {
      out.println("<form action='/lecBoard/add' method='post'>");
      out.println("강의번호 선택 <select name='lectureNo'>");
      for (Participant p : participantsList) {
        out.printf("<option value='%d'>%1$d</option>\n", p.getLectureNo());
      }
      out.println("</select><br>");
      out.println("제목 <input type='text' name='lecTitle'><br>");
      out.println("내용 <textarea name='lecContent'></textarea><br>");
      out.println("<button>등록</button>");
      out.println("</form>");
    }

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }
}

