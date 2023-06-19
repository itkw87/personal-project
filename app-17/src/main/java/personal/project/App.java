package personal.project;


import personal.project.handler.BoardHandler;
import personal.project.handler.Handler;
import personal.project.handler.StudentHandler;
import personal.util.Prompt;

public class App {
  public static void main(String[] args) {

    // 기본 생성자를 이용해 Prompt 인스턴스를 준비한다.
    // => 기본 생성자는 Scanner를 키보드와 연결한다.
    Prompt prompt = new Prompt();

    Handler studentHandler = new StudentHandler(prompt, "학생");
    Handler boardHandler = new BoardHandler(prompt, "게시글");
    Handler readingHandler = new BoardHandler(prompt, "자유게시글");

    printTitle();

    printMenu();

    while (true) {
      String menuNo = prompt.inputString("메인> ");
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        studentHandler.execute();
      } else if (menuNo.equals("2")) {
        boardHandler.execute();
      } else if (menuNo.equals("3")) {
        readingHandler.execute();
      } else {
        System.out.println("메뉴 번호가 옳지 않습니다!");
      }
    }

    prompt.close();
  }

  static void printMenu() {
    System.out.println("1. 학생");
    System.out.println("2. 게시글");
    System.out.println("3. 자유게시글");
    System.out.println("0. 종료");

  }

  static void printTitle() {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");
  }
}
