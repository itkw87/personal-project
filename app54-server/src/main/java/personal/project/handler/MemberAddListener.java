package personal.project.controller;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.MemberDao;
import personal.project.util.BreadcrumbPrompt;
import personal.project.util.Component;
import personal.project.vo.Member;

@Component(value = "/member/add")
public class MemberAddListener implements MemberActionListener {
  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;

  public MemberAddListener(MemberDao memberDao, SqlSessionFactory sqlSessionFactory) {
    this.memberDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
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
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }



  }

}
