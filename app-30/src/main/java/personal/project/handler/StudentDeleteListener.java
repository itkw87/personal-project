package personal.project.handler;

import java.util.List;
import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;

public class StudentDeleteListener extends AbstractStudentListener {
  public StudentDeleteListener(List<Student> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    if (!this.list.remove(new Student(prompt.inputInt("번호? ")))) {
      System.out.println("해당 번호의 학생이 없습니다!");
    }
  }
}
