package personal.project;


import java.util.ArrayList;
import java.util.LinkedList;
import personal.project.controller.BoardAddListener;
import personal.project.controller.BoardDeleteListener;
import personal.project.controller.BoardDetailListener;
import personal.project.controller.BoardListListener;
import personal.project.controller.BoardUpdateListener;
import personal.project.controller.FooterListener;
import personal.project.controller.HeaderListener;
import personal.project.controller.HelloListener;
import personal.project.controller.StudentAddListener;
import personal.project.controller.StudentDeleteListener;
import personal.project.controller.StudentDetailListener;
import personal.project.controller.StudentListListener;
import personal.project.controller.StudentUpdateListener;
import personal.project.vo.Board;
import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;
import personal.util.Menu;
import personal.util.MenuGroup;

public class App {
  public static void main(String[] args) {
    ArrayList<Student> studentList = new ArrayList<>();
    LinkedList<Board> boardList = new LinkedList<>();
    LinkedList<Board> freeBoardList = new LinkedList<>();

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
    freeBoardMenu.add(new Menu("등록", new BoardAddListener(freeBoardList)));
    freeBoardMenu.add(new Menu("목록", new BoardListListener(freeBoardList)));
    freeBoardMenu.add(new Menu("조회", new BoardDetailListener(freeBoardList)));
    freeBoardMenu.add(new Menu("변경", new BoardUpdateListener(freeBoardList)));
    freeBoardMenu.add(new Menu("삭제", new BoardDeleteListener(freeBoardList)));
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
