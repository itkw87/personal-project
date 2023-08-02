package personal.project.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.MemberDao;
import personal.project.util.BreadcrumbPrompt;
import personal.project.util.Component;
import personal.project.vo.Member;

@Component(value = "/member/update")
public class MemberUpdateListener implements MemberActionListener {
  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;

  public MemberUpdateListener(MemberDao memberDao, SqlSessionFactory sqlSessionFactory) {
    this.memberDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int memberNo = prompt.inputInt("번호? ");
    Member m = memberDao.findBy(memberNo);

    if (m == null) {
      prompt.println("해당 번호의 학생이 없습니다!");
      return;
    }
    m.setBirth(prompt.inputString("생년월일(%s)? ", m.getBirth()));
    m.setName(prompt.inputString("이름(%s)? ", m.getName()));
    m.setGender(MemberActionListener.inputGender(m.getGender(), prompt));
    m.setEmail(prompt.inputString("e - mail(%s)? ", m.getEmail()));
    m.setPassword(prompt.inputString("새암호? "));
    m.setStatus(MemberActionListener.inputStatus(m.getStatus(), "update", prompt));
    if ("S".equals(m.getAuthority())) {
      m.setGrade(prompt.inputInt("학년(%d)? ", m.getGrade()));
      m.setKoreanScore(prompt.inputInt("국어점수(%d)? ", m.getKoreanScore()));
      m.setEnglishScore(prompt.inputInt("영어점수(%d)? ", m.getEnglishScore()));
      m.setMathScore(prompt.inputInt("수학점수(%d)? ", m.getMathScore()));
      m.setScoreAvg((float) (m.getKoreanScore() + m.getEnglishScore() + m.getMathScore()) / 3);
    }
    try {
      memberDao.update(m);
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }



}
