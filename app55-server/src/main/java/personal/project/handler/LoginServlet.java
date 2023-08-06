package personal.project.handler;

import java.io.PrintWriter;
import personal.project.dao.MemberDao;
import personal.project.util.Component;
import personal.project.util.HttpServletRequest;
import personal.project.util.HttpServletResponse;
import personal.project.util.Servlet;
import personal.project.vo.Member;

@Component("/auth/login")
public class LoginServlet implements Servlet {

  MemberDao memberDao;

  public LoginServlet(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
    while (true) {
      Member m = new Member();
      m.setEmail(request.getParameter("email"));
      m.setPassword(request.getParameter("password"));

      Member loginUser = memberDao.findByEmailAndPassword(m);
      if (loginUser != null) {
        // 로그인 정보를 다른 요청에서도 사용할 수 있도록 세션 보관소에 담아 둔다.
        request.getSession().setAttribute("loginUser", loginUser);
        response.sendRedirect("/");
        return;
      }

      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta charset='UTF-8'>");
      out.println("<meta http-equiv='refresh' content='1;url=/auth/form.html'>");
      out.println("<title>로그인</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>로그인</h1>");
      out.println("<p>회원 정보가 일치하지 않습니다.</p>");
      out.println("</body>");
      out.println("</html>");
    }
  }
}
