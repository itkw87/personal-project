package personal.project.controller;

import personal.project.dao.StudentDao;
import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;

public class StudentDetailListener implements StudentActionListener {

  StudentDao studentDao;

  public StudentDetailListener(StudentDao studentDao) {
    this.studentDao = studentDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    int studentNo = prompt.inputInt("번호? ");
    Student s = studentDao.findBy(studentNo);
    if (s == null) {
      System.out.println("해당 번호의 학생이 없습니다!");
      return;
    }

    System.out.printf("생년월일: %d\n", s.getBirth());
    System.out.printf("이름: %s\n", s.getName());
    System.out.printf("학년: %d\n", s.getGrade());
    System.out.printf("국어점수: %d\n", s.getKoreanScore());
    System.out.printf("영어점수: %d\n", s.getEnglishScore());
    System.out.printf("수학점수: %d\n", s.getMathScore());
    System.out.printf("재학여부: %s\n", s.getStatus() == true ? "재학" : "퇴학");
    System.out.printf("성별: %s\n", s.getGender() == 'M' ? "남성" : "여성");
    System.out.printf("평균점수: %.1f\n", s.getScoreAvg());
  }

}
