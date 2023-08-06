package personal.project.handler;

import java.io.PrintWriter;
import personal.project.dao.MemberDao;
import personal.project.util.Component;
import personal.project.util.HttpServletRequest;
import personal.project.util.HttpServletResponse;
import personal.project.util.Servlet;
import personal.project.vo.Member;

@Component(value = "/member/detail")
public class MemberDetailServlet implements Servlet {

  MemberDao memberDao;

  public MemberDetailServlet(MemberDao memberDao) {
    this.memberDao = memberDao;
  }


  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Member member = memberDao.findBy(Integer.parseInt(request.getParameter("no")));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원</h1>");

    if (member == null) {
      out.println("<p>해당 번호의 회원이 없습니다!</p>");
    } else {
      out.println("<form action='/member/update' method='post'>");
      out.println("<table border='1'>");
      out.printf("<tr><th style='width:120px;'>번호</th>"
          + " <td style='width:300px;'><input type='text' name='no' value='%d' readonly></td></tr>\n",
          member.getNo());
      out.printf(
          "<tr><th>구분</th>\n" + " <td><select name='authority'>\n"
              + " <option value='S' %s>학생</option>\n"
              + " <option value='T' %s>교사</option></select></td></tr>\n",
          ("S".equals(member.getAuthority()) ? "selected" : ""),
          ("T".equals(member.getAuthority()) ? "selected" : ""));
      out.printf("<tr><th>이름</th>" + " <td><input type='text' name='name' value='%s'></td></tr>\n",
          member.getName());
      out.printf(
          "<tr><th>생년월일</th>" + " <td><input type='text' name='birth' value='%s'></td></tr>\n",
          member.getBirth());
      out.printf("<tr><th>학년</th>" + " <td><input type='text' name='grade' value='%d'></td></tr>\n",
          member.getGrade());
      out.printf(
          "<tr><th>이메일</th>" + " <td><input type='email' name='email' value='%s'></td></tr>\n",
          member.getEmail());
      out.println("<tr><th>암호</th>" + " <td><input type='password' name='password'></td></tr>");
      out.printf(
          "<tr><th>성별</th>\n" + " <td><select name='gender'>\n"
              + " <option value='M' %s>남자</option>\n"
              + " <option value='W' %s>여자</option></select></td></tr>\n",
          (member.getGender() == 'M' ? "selected" : ""),
          (member.getGender() == 'W' ? "selected" : ""));
      out.printf(
          "<tr><th>국어점수</th>"
              + " <td><input type='text' name='koreanScore' value='%d'></td></tr>\n",
          member.getKoreanScore());
      out.printf(
          "<tr><th>영어점수</th>"
              + " <td><input type='text' name='englishScore' value='%d'></td></tr>\n",
          member.getEnglishScore());
      out.printf(
          "<tr><th>수학점수</th>" + " <td><input type='text' name='mathScore' value='%d'></td></tr>\n",
          member.getMathScore());
      out.printf(
          "<tr><th>평균점수</th>"
              + " <td><input type='text' name='scoreAvg' value='%.2f' readonly></td></tr>\n",
          member.getScoreAvg());
      out.printf(
          "<tr><th>재학(재직) 여부</th>\n" + " <td><select name='status'>\n"
              + " <option value='1' %s>재학(재직)</option>\n"
              + " <option value='0' %s>퇴학(퇴직)</option></select></td></tr>\n",
          (member.getStatus() ? "selected" : ""), (!member.getStatus() ? "selected" : ""));
      out.printf(
          "<tr><th>등록일</th>"
              + " <td><input type='text' name='createdDate' value='%s' readonly></td></tr>\n",
          member.getCreatedDate().toString());
      out.println("</table>");

      out.println("<div>");
      out.println("<button>변경</button>");
      out.println("<button type='reset'>초기화</button>");
      out.printf("<a href='/member/delete?no=%d'>삭제</a>\n", member.getNo());
      out.println("<a href='/member/list'>목록</a>\n");
      out.println("</div>");
      out.println("</form>");
    }
    out.println("</body>");
    out.println("</html>");
  }
}

