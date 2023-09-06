package personal.project.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import personal.project.vo.Member;
import personal.project.vo.Participant;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Member m = new Member();
    m.setMemberId(request.getParameter("memberId"));
    m.setMemberPwd(request.getParameter("memberPwd"));

    Member loginUser = InitServlet.memberDao.findByEmailAndPassword(m);
    List<Participant> particiList = InitServlet.participantDao.findAllParticipant(m);

    if (particiList != null && !particiList.isEmpty()) {
      loginUser.setParticipantList(particiList);
    }

    if (loginUser != null) {
      // 로그인 정보를 다른 요청에서도 사용할 수 있도록 세션 보관소에 담아 둔다.
      request.getSession().setAttribute("loginUser", loginUser);
      response.sendRedirect("/");
      return;
    }

    request.setAttribute("message", "회원 정보가 일치하지 않습니다.");
    request.setAttribute("refresh", "1;url=/auth/form.html");

    request.getRequestDispatcher("/error").forward(request, response);
  }
}
