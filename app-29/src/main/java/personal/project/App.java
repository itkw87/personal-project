package personal.project;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import personal.project.io.BufferedDataInputStream;
import personal.project.io.BufferedDataOutputStream;
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
    loadStudent("student.data", studentList);
    loadBoard("board.data", boardList);
    loadBoard("freeBoard.data", freeBoardList);
  }



  private void saveData() {
    saveStudent("student.data", studentList);
    saveBoard("board.data", boardList);
    saveBoard("freeBoard.data", freeBoardList);
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
      BufferedDataInputStream in = new BufferedDataInputStream(filename);

      int size = in.readShort();

      for (int i = 0; i < size; i++) {
        Student student = new Student();
        student.setNo(in.readInt());
        student.setBirth(in.readInt());
        student.setName(in.readUTF());
        student.setGrade(in.readInt());
        student.setKoreanScore(in.readInt());
        student.setEnglishScore(in.readInt());
        student.setMathScore(in.readInt());
        student.setStatus(in.readBoolean());
        student.setGender(in.readChar());
        student.setScoreAvg(in.readFloat());
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
      BufferedDataInputStream in = new BufferedDataInputStream(filename);

      int size = in.readShort();

      for (int i = 0; i < size; i++) {
        Board board = new Board();
        board.setNo(in.readInt());
        board.setTitle(in.readUTF());
        board.setContent(in.readUTF());
        board.setWriter(in.readUTF());
        board.setPassword(in.readUTF());
        board.setViewCount(in.readInt());
        board.setCreatedDate(in.readLong());
        list.add(board);
      }
      Board.boardNo = Math.max(Board.boardNo, list.get(list.size() - 1).getNo() + 1);
      in.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 읽는 중 오류 발생!");
    }
  }



  private void saveStudent(String filename, List<Student> list) {
    try {
      BufferedDataOutputStream out = new BufferedDataOutputStream(filename);
      // Student 필드순서 => 번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균
      // 저장할 데이터의 개수를 먼저 출력한다.
      out.writeShort(list.size());

      for (Student student : list) {
        out.writeInt(student.getNo());
        out.writeInt(student.getBirth());
        out.writeUTF(student.getName());
        out.writeInt(student.getGrade());
        out.writeInt(student.getKoreanScore());
        out.writeInt(student.getEnglishScore());
        out.writeInt(student.getMathScore());
        out.writeBoolean(student.getStatus());
        out.writeChar(student.getGender());
        out.writeFloat(student.getScoreAvg());
      }
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

  private void saveBoard(String filename, List<Board> list) {
    try {
      BufferedDataOutputStream out = new BufferedDataOutputStream(filename);

      out.writeShort(list.size());

      for (Board board : list) {
        out.writeInt(board.getNo());
        out.writeUTF(board.getTitle());
        out.writeUTF(board.getContent());
        out.writeUTF(board.getWriter());
        out.writeUTF(board.getPassword());
        out.writeInt(board.getViewCount());
        out.writeLong(board.getCreatedDate());
      }
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

}
