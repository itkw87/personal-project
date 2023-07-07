package personal.project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import personal.dao.DaoBuilder;
import personal.net.RequestEntity;
import personal.project.dao.BoardDao;
import personal.project.dao.StudentDao;
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
import personal.util.BreadcrumbPrompt;
import personal.util.Menu;
import personal.util.MenuGroup;

public class ClientApp {
  Socket socket;
  DataOutputStream out;
  DataInputStream in;


  StudentDao studentDao;
  BoardDao boardDao;
  BoardDao freeBoardDao;

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();
  MenuGroup mainMenu = new MenuGroup("메인");

  public ClientApp(String ip, int port) throws Exception {
    this.socket = new Socket(ip, port);
    this.out = new DataOutputStream(socket.getOutputStream());
    this.in = new DataInputStream(socket.getInputStream());

    DaoBuilder DaoBuilder = new DaoBuilder(in, out);

    this.studentDao = DaoBuilder.build("student", StudentDao.class);
    this.boardDao = DaoBuilder.build("board", BoardDao.class);
    this.freeBoardDao = DaoBuilder.build("freeBoard", BoardDao.class);

    prepareMenu();
  }

  private void close() throws Exception {
    prompt.close();
    out.close();
    in.close();
    socket.close();
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 2) {
      System.out.println("실행 예) 'java ... personal.project.ClientApp 서버주소 포트번호' 와 같이 입력해야 합니다.");
      return;
    }

    ClientApp app = new ClientApp(args[0], Integer.parseInt(args[1]));
    app.execute();
    app.close();
  }


  static void printTitle() {
    System.out.println("학생별 성적정보 관리 시스템");
    System.out.println("----------------------------------");
  }

  public void execute() {
    printTitle();
    mainMenu.execute(prompt);

    try {
      out.writeUTF(new RequestEntity().command("quit").toJson());
    } catch (Exception e) {
      System.out.println("종료 오류!");
      e.printStackTrace();
    }
  }



  private void prepareMenu() {
    MenuGroup studentMenu = new MenuGroup("학생성적정보");
    studentMenu.add(new Menu("등록", new StudentAddListener(studentDao)));
    studentMenu.add(new Menu("목록", new StudentListListener(studentDao)));
    studentMenu.add(new Menu("조회", new StudentDetailListener(studentDao)));
    studentMenu.add(new Menu("변경", new StudentUpdateListener(studentDao)));
    studentMenu.add(new Menu("삭제", new StudentDeleteListener(studentDao)));
    mainMenu.add(studentMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardDao)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardDao)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardDao)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardDao)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardDao)));
    mainMenu.add(boardMenu);

    MenuGroup freeBoardMenu = new MenuGroup("자유게시글");
    freeBoardMenu.add(new Menu("등록", new BoardAddListener(freeBoardDao)));
    freeBoardMenu.add(new Menu("목록", new BoardListListener(freeBoardDao)));
    freeBoardMenu.add(new Menu("조회", new BoardDetailListener(freeBoardDao)));
    freeBoardMenu.add(new Menu("변경", new BoardUpdateListener(freeBoardDao)));
    freeBoardMenu.add(new Menu("삭제", new BoardDeleteListener(freeBoardDao)));
    mainMenu.add(freeBoardMenu);

    Menu helloMenu = new Menu("안녕!");
    helloMenu.addActionListener(new HeaderListener());
    helloMenu.addActionListener(new HelloListener());
    helloMenu.addActionListener(new FooterListener());
    mainMenu.add(helloMenu);
  }



}
