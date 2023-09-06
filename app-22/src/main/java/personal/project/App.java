package personal.project;


import personal.project.controller.BoardHandler;
import personal.project.controller.Handler;
import personal.project.controller.StudentHandler;
import personal.util.ArrayList;
import personal.util.LinkedList;
import personal.util.MenuPrompt;

public class App {
  public static void main(String[] args) {

    MenuPrompt prompt = new MenuPrompt();
    prompt.appendBreadcrumb("메인", getMenu());

    Handler studentHandler = new StudentHandler(prompt, "학생", new ArrayList());
    Handler boardHandler = new BoardHandler(prompt, "게시글", new LinkedList());
    Handler freeBoardHandler = new BoardHandler(prompt, "자유게시글", new LinkedList());

    printTitle();

    prompt.printMenu();

    loop: while (true) {
      String menuNo = prompt.inputMenu();
      switch (menuNo) {
        case "0":
          break loop;
        case "1":
          studentHandler.execute();
          break;
        case "2":
          boardHandler.execute();
          break;
        case "3":
          freeBoardHandler.execute();
          break;
      }
    }

    prompt.close();
  }

  static String getMenu() {
    StringBuilder menu = new StringBuilder();
    menu.append("1. 학생\n");
    menu.append("2. 게시글\n");
    menu.append("3. 자유게시글\n");
    menu.append("0. 종료\n");
    return menu.toString();
  }

  static void printTitle() {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");
  }
}
