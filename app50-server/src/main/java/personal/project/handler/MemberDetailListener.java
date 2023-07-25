package personal.project.handler;

import java.io.IOException;
import personal.project.dao.MemberDao;
import personal.project.vo.Member;
import personal.util.BreadcrumbPrompt;

public class MemberDetailListener implements MemberActionListener {

  MemberDao studentDao;

  public MemberDetailListener(MemberDao studentDao) {
    this.studentDao = studentDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int studentNo = prompt.inputInt("번호? ");
    Member m = studentDao.findBy(studentNo);
    if (m == null) {
      prompt.println("해당 번호의 학생이 없습니다!");
      return;
    }

    if ("S".equals(m.getAuthority())) {
      prompt.printf("학년: %d\n", m.getGrade());
      prompt.printf("국어점수: %d\n", m.getKoreanScore());
      prompt.printf("영어점수: %d\n", m.getEnglishScore());
      prompt.printf("수학점수: %d\n", m.getMathScore());
      prompt.printf("평균점수: %.1f\n", m.getScoreAvg());
    }
    prompt.printf("구분: %s\n", "S".equals(m.getAuthority()) ? "학생" : "교사");
    prompt.printf("생년월일: %s\n", m.getBirth());
    prompt.printf("이름: %s\n", m.getName());
    prompt.printf("성별: %s\n", m.getGender() == 'M' ? "남성" : "여성");
    prompt.printf("재학(재직) 여부: %s\n", m.getStatus() == true ? "재학(재직)" : "퇴학(퇴직)");
    prompt.printf("e - mail: %s\n", m.getEmail());
    prompt.printf("등록일: %s\n", m.getCreatedDate());
  }

}
