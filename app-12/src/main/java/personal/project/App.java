package personal.project;

import personal.project.controller.StudentHandler;
import personal.util.Prompt;

public class App {
  public static void main(String[] args) {

    printTitle();

    printMenu();

    while (true) {
      String menuNo = Prompt.inputString("메인> ");
      if (menuNo.equals("6")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        StudentHandler.inputMember();
      } else if (menuNo.equals("2")) {
        StudentHandler.printMembers();
      } else if (menuNo.equals("3")) {
        StudentHandler.viewMember();
      } else if (menuNo.equals("4")) {
        StudentHandler.updateMember();
      } else if (menuNo.equals("5")) {
        StudentHandler.deleteMember();
      } else {
        System.out.println(menuNo);
      }
    }

    Prompt.close();
  }

  static void printMenu() {
    System.out.println("1. 학생등록");
    System.out.println("2. 학생목록");
    System.out.println("3. 학생조회");
    System.out.println("4. 학생변경");
    System.out.println("5. 학생삭제");
    System.out.println("6. 종료");
  }

  static void printTitle() {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");
  }
}
