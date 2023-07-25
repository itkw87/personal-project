package personal.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import personal.project.vo.Member;

public class MySQLMemberDao implements MemberDao {

  Connection con;

  public MySQLMemberDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Member member) {
    try (Statement stmt = con.createStatement()) {
      stmt.executeUpdate(String.format(
          "insert into ps_member(authority, birth, grade, name, gender, korean_score, english_score, math_score, scoreAvg, email, password, status) "
              + " values('%s','%s','%d', '%s', '%c', '%d', '%d', '%d', '%.1f', '%s', '%s', '%d')",
          member.getAuthority(), member.getBirth(), member.getGrade(), member.getName(),
          member.getGender(), member.getKoreanScore(), member.getEnglishScore(),
          member.getMathScore(), member.getScoreAvg(), member.getEmail(), member.getPassword(),
          member.getStatus() == true ? 1 : 0));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Member> list() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select member_no, authority, birth, name, gender, email, status from ps_member order by name asc")) {
      List<Member> list = new ArrayList<>();

      while (rs.next()) {
        Member m = new Member();
        m.setNo(rs.getInt("member_no"));
        m.setAuthority(rs.getString("authority"));
        m.setBirth(rs.getString("birth"));
        m.setName(rs.getString("name"));
        m.setGender(rs.getString("gender").charAt(0));
        m.setEmail(rs.getString("email"));
        m.setStatus(rs.getBoolean("status"));

        list.add(m);
      }

      return list;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Member findBy(int no) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select " + " member_no," + " authority," + " birth,"
            + " grade," + " name," + " gender," + " korean_score," + " english_score,"
            + " math_score," + " scoreAvg," + " email," + " status," + " created_date"
            + " from ps_member " + " where member_no=" + no)) {

      if (rs.next()) {
        Member m = new Member();
        m.setNo(rs.getInt("member_no"));
        m.setAuthority(rs.getString("authority"));
        m.setBirth(rs.getString("birth"));
        m.setName(rs.getString("name"));
        m.setEmail(rs.getString("email"));
        m.setStatus(rs.getBoolean("status"));
        m.setGender(rs.getString("gender").charAt(0));
        m.setCreatedDate(rs.getDate("created_date"));
        if ("S".equals(m.getAuthority())) {
          m.setGrade(rs.getInt("grade"));
          m.setKoreanScore(rs.getInt("korean_score"));
          m.setEnglishScore(rs.getInt("english_score"));
          m.setMathScore(rs.getInt("math_score"));
          m.setScoreAvg(rs.getInt("scoreAvg"));
        }
        return m;
      }

      return null;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Member member) {
    try (Statement stmt = con.createStatement()) {

      return stmt.executeUpdate(String.format(
          "update ps_member set" + " birth='%s'," + " grade='%d'," + " name='%s'," + " gender='%c',"
              + " korean_score='%d'," + " english_score='%d'," + " math_score='%d',"
              + " scoreAvg='%.1f'," + " email='%s'," + " password='%s'," + " status='%d'"
              + " where member_no=%d",
          member.getBirth(), member.getGrade(), member.getName(), member.getGender(),
          member.getKoreanScore(), member.getEnglishScore(), member.getMathScore(),
          member.getScoreAvg(), member.getEmail(), member.getPassword(),
          member.getStatus() == true ? 1 : 0, member.getNo()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (Statement stmt = con.createStatement()) {
      return stmt.executeUpdate(String.format("delete from ps_member where member_no=%d", no));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
