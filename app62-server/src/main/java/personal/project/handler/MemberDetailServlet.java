package personal.project.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import personal.project.vo.Member;

@WebServlet(value = "/member/detail")
public class MemberDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Member loginUser = (Member) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    Member member = InitServlet.memberDao.findBy(Integer.parseInt(request.getParameter("memberNo")));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>회원</h1>");

    if (member == null) {
      out.println("<p>해당 번호의 회원이 없습니다!</p>");
    } else {
      out.println("<form action='/member/update' method='post' enctype='multipart/form-data'>");
      out.println("<table border='1'>");
      out.printf("<tr><th style='width:120px;'>사진</th>"
              + " <td style='width:300px;'>"
              + (member.getMemberPhoto() == null ? "<img style='height:80px' src='/images/avatar.png'>" :
              "<a href='https://kr.object.ncloudstorage.com/bitcamp-nc7-bucket-03/personal/member/%s'>"
                      + "<img src='http://tzoswbbmvlov19010725.cdn.ntruss.com/personal/member/%1$s?type=f&w=60&h=80&faceopt=true&ttype=jpg'>"
                      + "</a>")
              + " <input type='file' name='memberPhoto'>"
              + "</td></tr>\n", member.getMemberPhoto());
      out.printf("<tr><th style='width:120px;'>번호</th>"
          + " <td style='width:300px;'><input type='text' name='memberNo' value='%d' readonly style='border:none'></td></tr>\n",
          member.getMemberNo());
      out.printf("<tr><th>구분</th>\n" +
                      "<td><select name='memberCode'>\n" +
                      "  <option value='S' %s>학생</option>\n" +
                      "  <option value='P' %s>교수</option>\n" +
                      "</select></td></tr>",
              ("S".equals(member.getMemberCode()) ? "selected" : ""),
              ("P".equals(member.getMemberCode()) ? "selected" : ""));
      System.out.println("------------------------------------------------------");
      System.out.println(member.getMemberCode());
      System.out.println(member.getMemberCode().length());
      out.printf("<tr><th>이름</th>" + " <td><input type='text' name='memberName' value='%s'></td></tr>\n",
          member.getMemberName());
      out.printf(
          "<tr><th>이메일</th>" + " <td><input type='email' name='memberEmail' value='%s'></td></tr>\n",
          member.getMemberEmail());

      if (loginUser.getMemberNo() == member.getMemberNo()) {
        out.println("<tr><th>암호</th>" + " <td><input type='password' name='memberPwd'></td></tr>");
      }
      out.printf(
          "<tr><th>성별</th>\n" + " <td><select name='memberGender'>\n"
              + " <option value='M' %s>남자</option>\n"
              + " <option value='W' %s>여자</option></select></td></tr>\n",
          ("M".equals(member.getMemberGender()) ? "selected" : ""),
          ("W".equals(member.getMemberGender()) ? "selected" : ""));
        out.printf(
            "<tr><th>휴대폰</th>"
                + " <td><input type='text' name='memberTel' value='%s'></td></tr>\n",
            member.getMemberTel());
        out.printf(
            "<tr><th>우편번호</th>"
                + " <td><input type='text' name='memberZipcode' value='%s'></td></tr>\n",
            member.getMemberZipcode());
        out.printf(
            "<tr><th>주소</th>"
                + " <td><input type='text' name='memberAddr' value='%s'></td></tr>\n",
            member.getMemberAddr());
        out.printf(
            "<tr><th>상세주소</th>"
                + " <td><input type='text' name='memberDetailAddr' value='%s'></td></tr>\n",
            member.getMemberDetailAddr());
      out.printf(
          "<tr><th>등록일</th>"
              + " <td><input type='text' name='memberDate' value='%s' readonly></td></tr>\n",
          member.getMemberDate().toString());
      out.println("</table>");
    }

    out.println("<div>");
    if (loginUser.getMemberNo() == member.getMemberNo()) {
      out.println("<button>변경</button>");
      out.println("<button type='reset'>초기화</button>");
      out.printf("<a href='/member/delete?memberNo=%d'>탈퇴</a>\n", member.getMemberNo());
    }
    out.println("<a href='/member/list'>목록</a>\n");
    out.println("</div>");
    out.println("</form>");

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }
}

