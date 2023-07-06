package personal.project.handler;

import personal.project.vo.Student;
import personal.util.ActionListener;
import personal.util.BreadcrumbPrompt;

public interface StudentActionListener extends ActionListener {

  static boolean inputStatus(boolean value, String transaction, BreadcrumbPrompt prompt) {
    String label;
    if ("insert".equals(transaction)) {
      label = "재학여부\n";
    } else {
      label = String.format("재학여부(%s)\n", value == true ? "재학" : "퇴학");
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

  static char inputGender(char gender, BreadcrumbPrompt prompt) {
    String label;
    if (gender == 0) {
      label = "성별?\n";
    } else {
      label = String.format("성별(%s)?\n", gender == 'M' ? "남성" : "여성");
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
