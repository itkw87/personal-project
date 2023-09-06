package personal.project.controller;

import personal.project.dao.MemberDao;
import personal.project.vo.Member;
import personal.util.BreadcrumbPrompt;

public class MemberDetailListener implements MemberActionListener {

  MemberDao studentDao;

  public MemberDetailListener(MemberDao studentDao) {
    this.studentDao = studentDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int studentNo = prompt.inputInt("번호? ");
    Member m = studentDao.findBy(studentNo);
    if (m == null) {
      System.out.println("해당 번호의 학생이 없습니다!");
      return;
    }

    if ("S".equals(m.getAuthority())) {
      System.out.printf("학년: %d\n", m.getGrade());
      System.out.printf("국어점수: %d\n", m.getKoreanScore());
      System.out.printf("영어점수: %d\n", m.getEnglishScore());
      System.out.printf("수학점수: %d\n", m.getMathScore());
      System.out.printf("평균점수: %.1f\n", m.getScoreAvg());
    }
    System.out.printf("구분: %s\n", "S".equals(m.getAuthority()) ? "학생" : "교사");
    System.out.printf("생년월일: %s\n", m.getBirth());
    System.out.printf("이름: %s\n", m.getName());
    System.out.printf("성별: %s\n", m.getGender() == 'M' ? "남성" : "여성");
    System.out.printf("재학(재직) 여부: %s\n", m.getStatus() == true ? "재학(재직)" : "퇴학(퇴직)");
    System.out.printf("e - mail: %s\n", m.getEmail());
    System.out.printf("등록일: %s\n", m.getCreatedDate());
  }

}
