package personal.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    try (PreparedStatement stmt = con.prepareStatement(
        "insert into ps_member(authority, birth, grade, name, gender, korean_score, english_score, math_score, scoreAvg, email, password, status) "
            + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sha1(?), ?)")) {

      stmt.setString(1, member.getAuthority());
      stmt.setString(2, member.getBirth());
      stmt.setInt(3, member.getGrade());
      stmt.setString(4, member.getName());
      stmt.setString(5, member.getGender() + "");
      stmt.setInt(6, member.getKoreanScore());
      stmt.setInt(7, member.getEnglishScore());
      stmt.setInt(8, member.getMathScore());
      stmt.setFloat(9, member.getScoreAvg());
      stmt.setString(10, member.getEmail());
      stmt.setString(11, member.getPassword());
      stmt.setBoolean(12, member.getStatus());

      stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Member> list() {
    try (PreparedStatement stmt = con.prepareStatement(
        "select member_no, authority, birth, name, gender, email, status from ps_member order by member_no asc");
        ResultSet rs = stmt.executeQuery()) {
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
    try (PreparedStatement stmt = con.prepareStatement(
        "select " + " member_no," + " authority," + " birth," + " grade," + " name," + " gender,"
            + " korean_score," + " english_score," + " math_score," + " scoreAvg," + " email,"
            + " status," + " created_date" + " from ps_member " + " where member_no=?");) {

      stmt.setInt(1, no);

      try (ResultSet rs = stmt.executeQuery()) {
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
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Member findByEmailAndPassword(Member param) {
    try (PreparedStatement stmt = con.prepareStatement("select " + " member_no," + " authority,"
        + " birth," + " grade," + " name," + " gender," + " korean_score," + " english_score,"
        + " math_score," + " scoreAvg," + " email," + " status," + " created_date"
        + " from ps_member" + " where email=? and password=sha1(?)")) {

      stmt.setString(1, param.getEmail());
      stmt.setString(2, param.getPassword());

      try (ResultSet rs = stmt.executeQuery()) {
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
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public int update(Member member) {
    try (PreparedStatement stmt = con.prepareStatement(
        "update ps_member set" + " birth=?," + " grade=?," + " name=?," + " gender=?,"
            + " korean_score=?," + " english_score=?," + " math_score=?," + " scoreAvg=?,"
            + " email=?," + " password=sha1(?)," + "status=?" + " where member_no=?")) {

      stmt.setString(1, member.getBirth());
      stmt.setInt(2, member.getGrade());
      stmt.setString(3, member.getName());
      stmt.setString(4, member.getGender() + "");
      stmt.setInt(5, member.getKoreanScore());
      stmt.setInt(6, member.getEnglishScore());
      stmt.setInt(7, member.getMathScore());
      stmt.setFloat(8, member.getScoreAvg());
      stmt.setString(9, member.getEmail());
      stmt.setString(10, member.getPassword());
      stmt.setBoolean(11, member.getStatus());
      stmt.setInt(12, member.getNo());

      return stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(int no) {
    try (PreparedStatement stmt = con.prepareStatement("delete from ps_member where member_no=?")) {

      stmt.setInt(1, no);

      return stmt.executeUpdate();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
