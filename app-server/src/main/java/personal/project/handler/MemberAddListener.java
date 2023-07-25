package personal.project.handler;

import java.io.IOException;
import personal.project.dao.MemberDao;
import personal.project.vo.Member;
import personal.util.BreadcrumbPrompt;

public class MemberAddListener implements MemberActionListener {

  MemberDao studentDao;

  public MemberAddListener(MemberDao studentDao) {
    this.studentDao = studentDao;

  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Member s = new Member();
    s.setAuthority(prompt.inputString("구분(학생:S, 교사: T)? ").toUpperCase());
    s.setBirth(prompt.inputString("생년월일? "));
    s.setName(prompt.inputString("이름? "));
    s.setGender(MemberActionListener.inputGender((char) 0, prompt));
    s.setStatus(MemberActionListener.inputStatus(s.getStatus(), "insert", prompt));
    s.setEmail(prompt.inputString("이메일? "));
    s.setPassword(prompt.inputString("암호? "));
    if ("S".equals(s.getAuthority())) {
      s.setGrade(prompt.inputInt("학년? "));
      s.setKoreanScore(prompt.inputInt("국어점수? "));
      s.setEnglishScore(prompt.inputInt("영어점수? "));
      s.setMathScore(prompt.inputInt("수학점수? "));
      s.setScoreAvg((float) (s.getKoreanScore() + s.getEnglishScore() + s.getMathScore()) / 3);
    }

    studentDao.insert(s);
  }

}
