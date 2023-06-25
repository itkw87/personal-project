package personal.project.handler;

import personal.project.vo.Student;
import personal.util.ActionListener;
import personal.util.BreadcrumbPrompt;
import personal.util.List;

public abstract class AbstractStudentListener implements ActionListener {

  public List list;

  public AbstractStudentListener(List list) {
    this.list = list;
  }

  protected static String toBooleanString(Boolean value) {
    return value == true ? "재학" : "퇴학";
  }


  protected static String toGenderString(char gender) {
    return gender == 'M' ? "남성" : "여성";
  }

  protected Student findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Student s = (Student) this.list.get(i);
      if (s.getNo() == no) {
        return s;
      }
    }
    return null;
  }


  protected boolean inputStatus(boolean value, String transaction, BreadcrumbPrompt prompt) {
    String label;
    if ("insert".equals(transaction)) {
      label = "재학여부\n";
    } else {
      label = String.format("재학여부(%s)\n", toBooleanString(value));
    }
    while (true) {
      String menuNo = prompt.inputString(label + "  1. 재학\n" + "  2. 퇴학\n" + "> ");

      switch (menuNo) {
        case "1":
          return Student.ENROLLMENT;
        case "2":
          return Student.DROPOUT;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }
  }

  protected char inputGender(char gender, BreadcrumbPrompt prompt) {
    String label;
    if (gender == 0) {
      label = "성별?\n";
    } else {
      label = String.format("성별(%s)?\n", toGenderString(gender));
    }
    while (true) {
      String menuNo = prompt.inputString(label + " 1. 남자\n" + " 2. 여자\n" + "> ");

      switch (menuNo) {
        case "1":
          return Student.MALE;
        case "2":
          return Student.FEMALE;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }
  }

}
