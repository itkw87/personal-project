package personal.project.controller;

import java.io.PrintWriter;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.MemberDao;
import personal.project.util.Component;
import personal.project.util.HttpServletRequest;
import personal.project.util.HttpServletResponse;
import personal.project.util.Servlet;
import personal.project.vo.Member;

@Component("/member/add")
public class MemberAddServlet implements Servlet {
  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;

  public MemberAddServlet(MemberDao memberDao, SqlSessionFactory sqlSessionFactory) {
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

    if (!"A".equals(loginUser.getAuthority())) {
      throw new Exception("관리자 권한이 없는 계정입니다.");
    }

    Member m = new Member();
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
    out.println("<h1>회원 등록</h1>");

    System.out.println(m.toString());
    try {
      memberDao.insert(m);
      sqlSessionFactory.openSession(false).commit();
      out.println("<p>등록 성공입니다!</p>");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      out.println("<p>등록 실패입니다!</p>");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
  }
}
