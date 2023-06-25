package personal.project.handler;

import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;
import personal.util.List;

public class StudentDeleteListener extends AbstractStudentListener {
  public StudentDeleteListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    if (!this.list.remove(new Student(prompt.inputInt("번호? ")))) {
      System.out.println("해당 번호의 학생이 없습니다!");
    }
  }
}
