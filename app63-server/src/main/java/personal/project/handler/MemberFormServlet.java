package personal.project.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/member/form")
public class MemberFormServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>TaskTop</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>회원</h1>");
    out.println("<form action='/member/add' method='post' enctype='multipart/form-data'>");
    out.println("<table border='1'>");
    out.println("<tr>");
    out.println("  <th>구분</th> <td style='width:200px;'><select name='memberCode'>" +
            "        <option value=\"S\">학생</option>\n" +
            "        <option value=\"P\">교수</option>\n" +
            "      </select></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>이름</th> <td style='width:200px;'><input type='text' name='memberId' placeholder='아이디'></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>이메일</th> <td><input type='email' name='memberEmail' placeholder='이메일'></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>암호</th> <td><input type='password' name='memberPwd' placeholder='암호'></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>성별</th> ");
    out.println("  <td>");
    out.println("    <select name='memberGender'>");
    out.println("      <option value='M'>남자</option>");
    out.println("      <option value='W'>여자</option>");
    out.println("    </select>  ");
    out.println("  </td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>암호</th> <td><input type='text' name='memberTel' placeholder='전화번호'></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>암호</th> <td><input type='text' name='memberZipCode' placeholder='우편번호'></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>암호</th> <td><input type='text' name='memberAddr' placeholder='기본주소'></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>암호</th> <td><input type='text' name='memberdetailAddr' placeholder='상세주소'></td>");
    out.println("</tr>");
    out.println("<tr>");
    out.println("  <th>사진</th> <td><input type='file' name='memberPhoto'></td>");
    out.println("</tr>");
    out.println("</table>");
    out.println("<button>등록</button>");
    out.println("</form>");

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");

  }
}

