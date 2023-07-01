package personal.project;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import personal.project.vo.Board;
import personal.project.vo.Student;
import personal.util.BreadcrumbPrompt;
import personal.util.Menu;
import personal.util.MenuGroup;

public class App {
  ArrayList<Student> studentList = new ArrayList<>();
  LinkedList<Board> boardList = new LinkedList<>();
  LinkedList<Board> freeBoardList = new LinkedList<>();

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public App() {
    prepareMenu();
  }

  public static void main(String[] args) {
    new App().execute();
  }

  static void printTitle() {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");
  }

  public void execute() {
    printTitle();

    loadData();
    mainMenu.execute(prompt);
    saveData();

    prompt.close();
  }

  private void loadData() {
    loadStudent("student.csv", studentList);
    loadBoard("board.csv", boardList);
    loadBoard("freeBoard.csv", freeBoardList);
  }



  private void saveData() {
    saveStudent("student.csv", studentList);
    saveBoard("board.csv", boardList);
    saveBoard("freeBoard.csv", freeBoardList);
  }



  private void prepareMenu() {
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
  }

  private void loadStudent(String filename, List<Student> list) {
    try {
      FileReader in0 = new FileReader(filename);
      BufferedReader in = new BufferedReader(in0); // <== Decorator 역할을 수행!

      String line = null;

      while ((line = in.readLine()) != null) {
        String[] values = line.split(",");

        Student student = new Student();
        student.setNo(Integer.parseInt(values[0]));
        student.setBirth(Integer.parseInt(values[1]));
        student.setName(values[2]);
        student.setGrade(Integer.parseInt(values[3]));
        student.setKoreanScore(Integer.parseInt(values[4]));
        student.setEnglishScore(Integer.parseInt(values[5]));
        student.setMathScore(Integer.parseInt(values[6]));
        student.setStatus("true".equals(values[7]) ? true : false);
        student.setGender(values[8].charAt(0));
        student.setScoreAvg(Float.parseFloat(values[9]));

        list.add(student);
      }


      // 데이터를 로딩한 이후에 추가할 회원의 번호를 설정한다.
      Student.userId = list.get(list.size() - 1).getNo() + 1;
      in.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 읽는 중 오류 발생!");
    }


  }


  private void loadBoard(String filename, List<Board> list) {
    try {
      FileReader in0 = new FileReader(filename);
      BufferedReader in = new BufferedReader(in0); // <== Decorator 역할을 수행!

      String line = null;

      while ((line = in.readLine()) != null) {
        String[] values = line.split(",");

        Board board = new Board();
        board.setTitle(values[1]);
        board.setContent(values[2]);
        board.setWriter(values[3]);
        board.setPassword(values[4]);
        board.setViewCount(Integer.parseInt(values[5]));
        board.setCreatedDate(Long.parseLong(values[6]));
        list.add(board);
      }
      if (list.size() > 0) {
        Board.boardNo = Math.max(Board.boardNo, list.get(list.size() - 1).getNo() + 1);
      }

      in.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 읽는 중 오류 발생!");
    }
  }



  private void saveStudent(String filename, List<Student> list) {
    try {
      FileWriter out0 = new FileWriter(filename);
      BufferedWriter out1 = new BufferedWriter(out0); // <== Decorator 역할을 수행!
      PrintWriter out = new PrintWriter(out1); // <== Decorator 역할을 수행!

      for (Student student : list) {
        out.printf("%d,%d,%s,%d,%d,%d,%d,%b,%c,%f\n", student.getNo(), student.getBirth(),
            student.getName(), student.getGrade(), student.getKoreanScore(),
            student.getEnglishScore(), student.getMathScore(), student.getStatus(),
            student.getGender(), student.getScoreAvg());
      }
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

  private void saveBoard(String filename, List<Board> list) {
    try {
      FileWriter out0 = new FileWriter(filename);
      BufferedWriter out1 = new BufferedWriter(out0); // <== Decorator 역할을 수행!
      PrintWriter out = new PrintWriter(out1); // <== Decorator 역할을 수행!

      for (Board board : list) {
        out.printf("%d,%s,%s,%s,%s,%d,%d\n", board.getNo(), board.getTitle(), board.getContent(),
            board.getWriter(), board.getPassword(), board.getViewCount(), board.getCreatedDate());
      }
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

}
