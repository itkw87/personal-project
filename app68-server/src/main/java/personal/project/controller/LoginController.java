package personal.project.controller;

import org.springframework.stereotype.Component;
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

@Component("/auth/login")
public class LoginController implements PageController {
  MemberDao memberDao;
  ParticipantDao participantDao;

  public LoginController(MemberDao memberDao, ParticipantDao participantDao) {
    this.memberDao = memberDao;
    this.participantDao = participantDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/auth/form.jsp";
    }

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

    if (loginUser == null) {
      request.setAttribute("refresh", "2;url=/app/auth/login");
      throw new Exception("회원 정보가 일치하지 않습니다.");
    }

    request.getSession().setAttribute("loginUser", loginUser);
    return "redirect:/";
  }
}
