package personal.project.controller;

import java.util.List;
import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;

public class StudentAddListener extends AbstractStudentListener {

  public StudentAddListener(List<Student> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Student s = new Student();
    s.setNo(Student.userId++);
    s.setBirth(prompt.inputInt("생년월일? "));
    s.setName(prompt.inputString("이름? "));
    s.setGrade(prompt.inputInt("학년? "));
    s.setKoreanScore(prompt.inputInt("국어점수? "));
    s.setEnglishScore(prompt.inputInt("영어점수? "));
    s.setMathScore(prompt.inputInt("수학점수? "));
    s.setScoreAvg((float) (s.getKoreanScore() + s.getEnglishScore() + s.getMathScore()) / 3);
    s.setGender(inputGender((char) 0, prompt));
    s.setStatus(inputStatus(s.getStatus(), "update", prompt));

    this.list.add(s);
  }

}
