package personal.project.handler;

import personal.project.dao.StudentDao;
import personal.util.BreadcrumbPrompt;

public class StudentDeleteListener implements StudentActionListener {

  StudentDao studentDao;

  public StudentDeleteListener(StudentDao studentDao) {
    this.studentDao = studentDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    if (studentDao.delete(prompt.inputInt("번호? ")) == 0) {
      System.out.println("해당 번호의 학생이 없습니다!");
    }
  }
}
