package personal.project.handler;

import java.util.List;
import personal.project.dao.StudentDao;
import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;

public class StudentListListener implements StudentActionListener {

  StudentDao studentDao;

  public StudentListListener(StudentDao studentDao) {
    this.studentDao = studentDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println(
        "------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println(
        "------------------------------------------------------------------------------------------");

    // 목록에서 데이터를 대신 꺼내주는 객체를 얻는다.
    List<Student> list = studentDao.list();
    for (Student s : list) {
      System.out.printf(
          "  %d  |  %d  | %s |  %d  |   %d   |   %d   |   %d   |  %s  |  %s  |  %.1f\n", s.getNo(),
          s.getBirth(), s.getName(), s.getGrade(), s.getKoreanScore(), s.getEnglishScore(),
          s.getMathScore(), s.getStatus() == true ? "재학" : "퇴학", s.getGender() == 'M' ? "남성" : "여성",
          s.getScoreAvg());
    }
  }
}
