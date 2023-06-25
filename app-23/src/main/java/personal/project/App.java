package personal.project;


import personal.project.handler.BoardAddListener;
import personal.project.handler.BoardDeleteListener;
import personal.project.handler.BoardDetailListener;
import personal.project.handler.BoardListListener;
import personal.project.handler.BoardUpdateListener;
import personal.project.handler.FooterListener;
import personal.project.handler.HeaderListener;
import personal.project.handler.HelloListener;
import personal.project.handler.StudentAddListener;
import personal.project.handler.StudentDeleteListener;
import personal.project.handler.StudentDetailListener;
import personal.project.handler.StudentListListener;
import personal.project.handler.StudentUpdateListener;
import personal.util.ArrayList;
import personal.util.BreadcrumbPrompt;
import personal.util.LinkedList;
import personal.util.Menu;
import personal.util.MenuGroup;

public class App {
  public static void main(String[] args) {
    ArrayList studentList = new ArrayList();
    LinkedList boardList = new LinkedList();
    LinkedList freeBoardList = new LinkedList();

    BreadcrumbPrompt prompt = new BreadcrumbPrompt();

    MenuGroup mainMenu = new MenuGroup("메인");

    MenuGroup studentMenu = new MenuGroup("학생성적정보");
    studentMenu.add(new Menu("등록", new StudentAddListener(studentList)));
    studentMenu.add(new Menu("목록", new StudentListListener(studentList)));
    studentMenu.add(new Menu("조회", new StudentDetailListener(studentList)));
    studentMenu.add(new Menu("변경", new StudentUpdateListener(studentList)));
    studentMenu.add(new Menu("삭제", new StudentDeleteListener(studentList)));
    mainMenu.add(studentMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardList)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardList)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardList)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardList)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardList)));
    mainMenu.add(boardMenu);

    MenuGroup freeBoardMenu = new MenuGroup("자유게시글");
    freeBoardMenu.add(new Menu("등록", new BoardAddListener(boardList)));
    freeBoardMenu.add(new Menu("목록", new BoardListListener(boardList)));
    freeBoardMenu.add(new Menu("조회", new BoardDetailListener(boardList)));
    freeBoardMenu.add(new Menu("변경", new BoardUpdateListener(boardList)));
    freeBoardMenu.add(new Menu("삭제", new BoardDeleteListener(boardList)));
    mainMenu.add(freeBoardMenu);

    Menu helloMenu = new Menu("안녕!");
    helloMenu.addActionListener(new HeaderListener());
    helloMenu.addActionListener(new HelloListener());
    helloMenu.addActionListener(new FooterListener());
    mainMenu.add(helloMenu);


    printTitle();

    mainMenu.execute(prompt);

    prompt.close();
  }

  static void printTitle() {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");
  }
}
