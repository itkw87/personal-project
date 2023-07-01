package personal.project;


import java.io.FileInputStream;
import java.io.FileOutputStream;
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

      FileInputStream in = new FileInputStream(filename);
      int size = in.read() << 8;
      size |= in.read();

      byte[] buf = new byte[1000];


      for (int i = 0; i < size; i++) {
        Student student = new Student();

        student.setNo(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        student.setBirth(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        int length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        student.setName(new String(buf, 0, length, "UTF-8"));

        student.setGrade(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        student.setKoreanScore(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        student.setEnglishScore(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        student.setMathScore(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        student.setStatus(in.read() == 1 ? true : false);

        student.setGender((char) (in.read() << 8 | in.read()));

        student.setScoreAvg(
            Float.intBitsToFloat(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read()));
        list.add(student);
      }

      // 데이터를 로딩한 이후에 추가할 회원의 번호를 설정한다.
      Student.userId = list.get(list.size() - 1).getNo() + 1;
      in.close;
      
    } catch (Exception e) {
      System.out.println(filename + " 파일을 읽는 중 오류 발생!");
    }
  }


  private void loadBoard(String filename, List<Board> list) {
    try {
      FileInputStream in = new FileInputStream(filename);

      // 저장할 Board객체 데이터의 수를 save할 때 2byte만큼의 공간만 사용해서 출력했었음
      // 근데 in.read()를 하면 4byte크기의 int메모리로 반환되나 유효한 값은 맨끝 1byte만큼만 유효
      // 따라서 2byte중 첫번째 1byte에 해당하는 값을 int로 반환받아 그 중 유효한 값을 1byte만
      // 8비트(1byte)왼쪽으로 이동 시키고 나머지 1byte에 해당하는 값과 서로 OR 비트연산을 통해
      // 출력했던 값으로 만들 수 있음
      int size = in.read() << 8;
      size |= in.read();

      byte[] buf = new byte[1000];

      for (int i = 0; i < size; i++) {
        Board board = new Board();
        board.setNo(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        // length만큼 읽어서 buf 바이트 배열의 0번째 인덱스 부터 length - 1인덱스까지에 저장
        // buf 바이트 배열의 0번 인덱스 ~ length - 1 인덱스까지 UTF-8 문자 인코딩을 사용하여
        // String객체(문자열) 생성
        int length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        board.setTitle(new String(buf, 0, length, "UTF-8"));

        length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        board.setContent(new String(buf, 0, length, "UTF-8"));

        length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        board.setWriter(new String(buf, 0, length, "UTF-8"));

        length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        board.setPassword(new String(buf, 0, length, "UTF-8"));

        board.setViewCount(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        board.setCreatedDate((long) in.read() << 56 | (long) in.read() << 48
            | (long) in.read() << 40 | (long) in.read() << 32 | (long) in.read() << 24
            | (long) in.read() << 16 | (long) in.read() << 8 | in.read());

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
      FileOutputStream out = new FileOutputStream(filename);
      // Student 필드순서 => 번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균
      // 저장할 데이터의 개수를 먼저 출력한다.
      int size = list.size();
      out.write(size >> 8);
      out.write(size);

      for (Student student : list) {
        int no = student.getNo();
        out.write(no >> 24);
        out.write(no >> 16);
        out.write(no >> 8);
        out.write(no);

        int birth = student.getBirth();
        out.write(birth >> 24);
        out.write(birth >> 16);
        out.write(birth >> 8);
        out.write(birth);

        byte[] bytes = student.getName().getBytes("UTF-8");
        // 출력할 바이트의 개수를 2바이트로 표시한다.
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        // 문자열의 바이트를 출력한다.
        out.write(bytes);

        int grade = student.getGrade();
        out.write(grade >> 24);
        out.write(grade >> 16);
        out.write(grade >> 8);
        out.write(grade);

        int kScore = student.getKoreanScore();
        out.write(kScore >> 24);
        out.write(kScore >> 16);
        out.write(kScore >> 8);
        out.write(kScore);

        int eScore = student.getEnglishScore();
        out.write(eScore >> 24);
        out.write(eScore >> 16);
        out.write(eScore >> 8);
        out.write(eScore);

        int mScore = student.getMathScore();
        out.write(mScore >> 24);
        out.write(mScore >> 16);
        out.write(mScore >> 8);
        out.write(mScore);

        boolean status = student.getStatus();
        out.write(status ? (byte) 1 : (byte) 0);

        char gender = student.getGender();
        out.write(gender >> 8);
        out.write(gender);

        int floatBits = Float.floatToIntBits(student.getScoreAvg());
        out.write(floatBits >> 24);
        out.write(floatBits >> 16);
        out.write(floatBits >> 8);
        out.write(floatBits);
      }
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

  private void saveBoard(String filename, List<Board> list) {
    try {
      FileOutputStream out = new FileOutputStream(filename);

      // 저장할 데이터의 개수를 먼저 출력한다.
      int size = list.size();
      out.write(size >> 8);
      out.write(size);

      for (Board board : list) {
        int no = board.getNo();
        out.write(no >> 24);
        out.write(no >> 16);
        out.write(no >> 8);
        out.write(no);

        byte[] bytes = board.getTitle().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);


        bytes = board.getContent().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = board.getWriter().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = board.getPassword().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        int viewCount = board.getViewCount();
        out.write(viewCount >> 24);
        out.write(viewCount >> 16);
        out.write(viewCount >> 8);
        out.write(viewCount);

        long createdDate = board.getCreatedDate();
        out.write((int) (createdDate >> 56));
        out.write((int) (createdDate >> 48));
        out.write((int) (createdDate >> 40));
        out.write((int) (createdDate >> 32));
        out.write((int) (createdDate >> 24));
        out.write((int) (createdDate >> 16));
        out.write((int) (createdDate >> 8));
        out.write((int) createdDate);
      }
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

}
