package personal.project;

import personal.project.controller.BoardHandler;
import personal.project.controller.BoardHandler2;
import personal.project.controller.StudentHandler;
import personal.util.Prompt;

public class App {
  public static void main(String[] args) {

    printTitle();

    printMenu();

    while (true) {
      String menuNo = Prompt.inputString("메인> ");
      if (menuNo.equals("99")) {
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
      } else if (menuNo.equals("6")) {
        BoardHandler.inputBoard();
      } else if (menuNo.equals("7")) {
        BoardHandler.printBoards();
      } else if (menuNo.equals("8")) {
        BoardHandler.viewBoard();
      } else if (menuNo.equals("9")) {
        BoardHandler.updateBoard();
      } else if (menuNo.equals("10")) {
        BoardHandler.deleteBoard();
      } else if (menuNo.equals("11")) {
        BoardHandler2.inputBoard();
      } else if (menuNo.equals("12")) {
        BoardHandler2.printBoards();
      } else if (menuNo.equals("13")) {
        BoardHandler2.viewBoard();
      } else if (menuNo.equals("14")) {
        BoardHandler2.updateBoard();
      } else if (menuNo.equals("15")) {
        BoardHandler2.deleteBoard();
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
    System.out.println("6. 게시글등록");
    System.out.println("7. 게시글목록");
    System.out.println("8. 게시글조회");
    System.out.println("9. 게시글변경");
    System.out.println("10. 게시글삭제");
    System.out.println("11. 자유게시글 등록");
    System.out.println("12. 자유게시글 목록");
    System.out.println("13. 자유게시글 조회");
    System.out.println("14. 자유게시글 변경");
    System.out.println("15. 자유게시글 삭제");
    System.out.println("99. 종료");
  }

  static void printTitle() {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");
  }
}
