package personal.project.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.Member;

@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    int no = Integer.parseInt(request.getParameter("no"));
    if (!"A".equals(loginUser.getAuthority()) && no != loginUser.getNo()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta http-equiv='refresh' content='1;url=/member/list'>");
      out.println("<title>학교 통합 정보관리 시스템</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>권한 없음</h1>");
      out.println("<p>회원정보 변경 권한이 없습니다.</p>");
      out.println("</body>");
      out.println("</html>");
      return;
    }

    Member m = new Member();
    m.setNo(no);
    m.setAuthority(request.getParameter("authority"));
    m.setBirth(request.getParameter("birth"));
    m.setName(request.getParameter("name"));
    m.setGender(request.getParameter("gender").charAt(0));
    m.setStatus("1".equals(request.getParameter("status")) ? true : false);
    m.setEmail(request.getParameter("email"));
    m.setPassword(request.getParameter("password"));
    if ("S".equals(m.getAuthority())) {
      m.setGrade(Integer.parseInt(request.getParameter("grade")));
      m.setKoreanScore(Integer.parseInt(request.getParameter("koreanScore")));
      m.setEnglishScore(Integer.parseInt(request.getParameter("englishScore")));
      m.setMathScore(Integer.parseInt(request.getParameter("mathScore")));
      m.setScoreAvg((float) (m.getKoreanScore() + m.getEnglishScore() + m.getMathScore()) / 3);
    }

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/member/list'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 변경</h1>");
    try {
      if (InitServlet.memberDao.update(m) == 0) {
        out.println("<p>회원이 없습니다.</p>");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        out.println("<p>변경했습니다!</p>");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>변경 실패입니다!</p>");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");
  }
}
