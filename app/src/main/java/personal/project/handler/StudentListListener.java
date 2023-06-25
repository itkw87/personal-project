package personal.project.handler;

import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;
import personal.util.List;

public class StudentListListener extends AbstractStudentListener {

  public StudentListListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println(
        "------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println(
        "------------------------------------------------------------------------------------------");

    for (int i = 0; i < this.list.size(); i++) {
      Student s = (Student) this.list.get(i);
      System.out.printf(
          "  %d  |  %d  | %s |  %d  |   %d   |   %d   |   %d   |  %s  |  %s  |  %.1f\n", s.getNo(),
          s.getBirth(), s.getName(), s.getGrade(), s.getKoreanScore(), s.getEnglishScore(),
          s.getMathScore(), toBooleanString(s.getStatus()), toGenderString(s.getGender()),
          s.getScoreAvg());
    }
  }
}
