package personal.project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import personal.net.NetProtocol;
import personal.project.dao.BoardDao;
import personal.project.dao.MemberDao;
import personal.project.dao.MySQLBoardDao;
import personal.project.dao.MySQLMemberDao;
import personal.project.controller.BoardAddListener;
import personal.project.controller.BoardDeleteListener;
import personal.project.controller.BoardDetailListener;
import personal.project.controller.BoardListListener;
import personal.project.controller.BoardUpdateListener;
import personal.project.controller.LoginListener;
import personal.project.controller.MemberAddListener;
import personal.project.controller.MemberDeleteListener;
import personal.project.controller.MemberDetailListener;
import personal.project.controller.MemberListListener;
import personal.project.controller.MemberUpdateListener;
import personal.project.vo.Member;
import personal.util.BreadcrumbPrompt;
import personal.util.Menu;
import personal.util.MenuGroup;

public class ServerApp {

  // 자바 스레드 풀 준비
  ExecutorService threadPool = Executors.newFixedThreadPool(10);

  public static Member loginUser;
  private final int BOARD = 1;
  private final int FREE_BOARD = 2;

  Connection con;
  MemberDao memberDao;
  BoardDao boardDao;
  BoardDao freeBoardDao;

  MenuGroup mainMenu = new MenuGroup("메인");

  int port;

  public ServerApp(int port) throws Exception {

    this.port = port;

    con = DriverManager.getConnection("jdbc:mysql://personal:1111@localhost:3306/personaldb" // JDBC
    // URL
    );

    this.memberDao = new MySQLMemberDao(con);
    this.boardDao = new MySQLBoardDao(con, BOARD);
    this.freeBoardDao = new MySQLBoardDao(con, FREE_BOARD);

    prepareMenu();
  }

  private void close() throws Exception {
    con.close();
  }

  public static void main(String[] args) throws Exception {
    ServerApp app = new ServerApp(8888);
    app.execute();
    app.close();

  }

  public void execute() {
    try (ServerSocket serverSocket = new ServerSocket(this.port)) {
      System.out.println("서버 실행 중...");

      while (true) {
        Socket socket = serverSocket.accept();
        threadPool.execute(() -> processRequest(socket));
      }
    } catch (Exception e) {
      System.out.println("서버 실행 오류!");
      e.printStackTrace();
    }
  }

  private void processRequest(Socket socket) {
    try (Socket s = socket;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      BreadcrumbPrompt prompt = new BreadcrumbPrompt(in, out);

      InetSocketAddress clientAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
      System.out.printf("%s 클라이언트 접속함!\n", clientAddress.getHostString());

      out.writeUTF("[학교 통합 정보관리 시스템]\n" + "-----------------------------------------");

      new LoginListener(memberDao).service(prompt);

      mainMenu.execute(prompt);
      out.writeUTF(NetProtocol.NET_END);

    } catch (Exception e) {
      System.out.println("클라이언트 통신 오류!");
      e.printStackTrace();
    }
  }

  private void prepareMenu() {
    MenuGroup memberMenu = new MenuGroup("회원");
    memberMenu.add(new Menu("등록", new MemberAddListener(memberDao)));
    memberMenu.add(new Menu("목록", new MemberListListener(memberDao)));
    memberMenu.add(new Menu("조회", new MemberDetailListener(memberDao)));
    memberMenu.add(new Menu("변경", new MemberUpdateListener(memberDao)));
    memberMenu.add(new Menu("삭제", new MemberDeleteListener(memberDao)));
    mainMenu.add(memberMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("등록", new BoardAddListener(boardDao)));
    boardMenu.add(new Menu("목록", new BoardListListener(boardDao)));
    boardMenu.add(new Menu("조회", new BoardDetailListener(boardDao)));
    boardMenu.add(new Menu("변경", new BoardUpdateListener(boardDao)));
    boardMenu.add(new Menu("삭제", new BoardDeleteListener(boardDao)));
    mainMenu.add(boardMenu);

    MenuGroup readingMenu = new MenuGroup("자유게시글");
    readingMenu.add(new Menu("등록", new BoardAddListener(freeBoardDao)));
    readingMenu.add(new Menu("목록", new BoardListListener(freeBoardDao)));
    readingMenu.add(new Menu("조회", new BoardDetailListener(freeBoardDao)));
    readingMenu.add(new Menu("변경", new BoardUpdateListener(freeBoardDao)));
    readingMenu.add(new Menu("삭제", new BoardDeleteListener(freeBoardDao)));
    mainMenu.add(readingMenu);

    // Menu helloMenu = new Menu("안녕!");
    // helloMenu.addActionListener(new HeaderListener());
    // helloMenu.addActionListener(new HelloListener());
    // helloMenu.addActionListener(new FooterListener());
    // mainMenu.add(helloMenu);
  }



}
