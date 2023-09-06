package personal.project.controller;

import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;
import personal.util.Iterator;
import personal.util.List;

public class StudentListListener extends AbstractStudentListener {

  public StudentListListener(List<Student> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println(
        "------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println(
        "------------------------------------------------------------------------------------------");

    // 목록에서 데이터를 대신 꺼내주는 객체를 얻는다.
    Iterator<Student> iterator = list.iterator();
    while (iterator.hasNext()) {
      Student s = iterator.next();
      System.out.printf(
          "  %d  |  %d  | %s |  %d  |   %d   |   %d   |   %d   |  %s  |  %s  |  %.1f\n", s.getNo(),
          s.getBirth(), s.getName(), s.getGrade(), s.getKoreanScore(), s.getEnglishScore(),
          s.getMathScore(), toBooleanString(s.getStatus()), toGenderString(s.getGender()),
          s.getScoreAvg());
    }
  }
}
