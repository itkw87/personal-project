package personal.project.handler;

import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.MemberDao;
import personal.project.util.Component;
import personal.project.util.HttpServletRequest;
import personal.project.util.HttpServletResponse;
import personal.project.util.Servlet;
import personal.project.vo.Member;

@Component("/member/delete")
public class MemberDeleteServlet implements Servlet {
  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;

  public MemberDeleteServlet(MemberDao memberDao, SqlSessionFactory sqlSessionFactory) {
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
      throw new Exception("삭제 권한이 없습니다!");
    }

    try {
      if (memberDao.delete(no) == 0) {
        throw new Exception("해당 번호의 회원이 없습니다!");
      } else {
        response.sendRedirect("/member/list");
      }
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

}
