package personal.project.controller;

import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.MemberDao;
import personal.project.util.Component;
import personal.project.util.HttpServletRequest;
import personal.project.util.HttpServletResponse;
import personal.project.util.Servlet;
import personal.project.vo.Member;

@Component("/member/update")
public class MemberUpdateServlet implements Servlet {
  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;

  public MemberUpdateServlet(MemberDao memberDao, SqlSessionFactory sqlSessionFactory) {
    this.memberDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }


  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    int no = Integer.parseInt(request.getParameter("no"));
    if (!"A".equals(loginUser.getAuthority()) && no != loginUser.getNo()) {
      throw new Exception("변경 권한이 없습니다!");
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


    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
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
      if (memberDao.update(m) == 0) {
        out.println("<p>회원이 없습니다.</p>");
      } else {
        sqlSessionFactory.openSession(false).commit();
        out.println("<p>변경했습니다!</p>");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>변경 실패입니다!</p>");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");
  }
}
