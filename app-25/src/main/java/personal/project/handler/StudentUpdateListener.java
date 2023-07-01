package personal.project.handler;

import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;
import personal.util.List;

public class StudentUpdateListener extends AbstractStudentListener {
  public StudentUpdateListener(List<Student> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int studentNo = prompt.inputInt("번호? ");
    Student s = this.findBy(studentNo);

    if (s == null) {
      System.out.println("해당 번호의 학생이 없습니다!");
      return;
    }

    s.setBirth(prompt.inputInt("생년월일(%d)? ", s.getBirth()));
    s.setName(prompt.inputString("이름(%s)? ", s.getName()));
    s.setGrade(prompt.inputInt("학년(%d)? ", s.getGrade()));
    s.setKoreanScore(prompt.inputInt("국어점수(%d)? ", s.getKoreanScore()));
    s.setEnglishScore(prompt.inputInt("영어점수(%d)? ", s.getEnglishScore()));
    s.setMathScore(prompt.inputInt("수학점수(%d)? ", s.getMathScore()));
    s.setStatus(inputStatus(s.getStatus(), "update", prompt));
    s.setGender(inputGender(s.getGender(), prompt));
    s.setScoreAvg((float) (s.getKoreanScore() + s.getEnglishScore() + s.getMathScore()) / 3);
  }
}
