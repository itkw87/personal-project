package personal.project.controller;

import java.io.IOException;
import personal.project.dao.MemberDao;
import personal.project.vo.Member;
import personal.util.BreadcrumbPrompt;
import personal.util.DataSource;

public class MemberAddListener implements MemberActionListener {
  MemberDao memberDao;
  DataSource ds;

  public MemberAddListener(MemberDao memberDao, DataSource ds) {
    this.memberDao = memberDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Member m = new Member();
    m.setAuthority(prompt.inputString("구분(학생:S, 교사: T)? ").toUpperCase());
    m.setBirth(prompt.inputString("생년월일? "));
    m.setName(prompt.inputString("이름? "));
    m.setGender(MemberActionListener.inputGender((char) 0, prompt));
    m.setStatus(MemberActionListener.inputStatus(m.getStatus(), "insert", prompt));
    m.setEmail(prompt.inputString("이메일? "));
    m.setPassword(prompt.inputString("암호? "));
    if ("S".equals(m.getAuthority())) {
      m.setGrade(prompt.inputInt("학년? "));
      m.setKoreanScore(prompt.inputInt("국어점수? "));
      m.setEnglishScore(prompt.inputInt("영어점수? "));
      m.setMathScore(prompt.inputInt("수학점수? "));
      m.setScoreAvg((float) (m.getKoreanScore() + m.getEnglishScore() + m.getMathScore()) / 3);
    }

    try {
      memberDao.insert(m);
      ds.getConnection().commit();

    } catch (Exception e) {
      try {
        ds.getConnection().rollback();
      } catch (Exception e2) {
      }
      throw new RuntimeException(e);
    }



  }

}
