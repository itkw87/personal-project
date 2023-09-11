package personal.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import personal.project.dao.MemberDao;
import personal.project.dao.ParticipantDao;
import personal.project.service.MemberService;
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
  @Autowired
  MemberService memberService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/auth/form.jsp";
    }

    String memberId = request.getParameter("memberId");
    String memberPwd = request.getParameter("memberPwd");

    if (request.getParameter("saveId") != null) {
      Cookie cookie = new Cookie("memberId", memberId);
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("memberId", "no"); // number가 아니라 No임!
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    Member loginUser = memberService.get(memberId, memberPwd);
    if (loginUser == null) {
      request.setAttribute("refresh", "2;url=/app/auth/login");
      throw new Exception("회원 정보가 일치하지 않습니다.");
    }

    request.getSession().setAttribute("loginUser", loginUser);
    return "redirect:/";
  }
}
