package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.LecBoardDao;
import personal.project.dao.MemberDao;
import personal.project.dao.ParticipantDao;
import personal.project.vo.Member;
import personal.project.vo.Participant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    ParticipantDao participantDao = (ParticipantDao) this.getServletContext().getAttribute("participantDao");

    Member m = new Member();
    m.setMemberId(request.getParameter("memberId"));
    m.setMemberPwd(request.getParameter("memberPwd"));


    Member loginUser = memberDao.findByEmailAndPassword(m);
    List<Participant> particiList = participantDao.findAllParticipant(m);

    if (particiList != null && !particiList.isEmpty()) {
      loginUser.setParticipantList(particiList);
    }

    if (request.getParameter("saveId") != null) {
      Cookie cookie = new Cookie("memberId", m.getMemberId());
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("memberId", "no");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    if (loginUser != null) {
      // 로그인 정보를 다른 요청에서도 사용할 수 있도록 세션 보관소에 담아 둔다.
      request.getSession().setAttribute("loginUser", loginUser);
      response.sendRedirect("/");
      return;
    }

    request.setAttribute("message", "회원 정보가 일치하지 않습니다.");
    request.setAttribute("refresh", "1;url=/auth/form");

    request.getRequestDispatcher("/error").forward(request, response);
  }
}
