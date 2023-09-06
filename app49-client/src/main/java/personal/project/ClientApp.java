package personal.project;

import java.sql.Connection;
import java.sql.DriverManager;
import personal.project.dao.BoardDao;
import personal.project.dao.MemberDao;
import personal.project.dao.MySQLBoardDao;
import personal.project.dao.MySQLMemberDao;
import personal.project.controller.BoardAddListener;
import personal.project.controller.BoardDeleteListener;
import personal.project.controller.BoardDetailListener;
import personal.project.controller.BoardListListener;
import personal.project.controller.BoardUpdateListener;
import personal.project.controller.FooterListener;
import personal.project.controller.HeaderListener;
import personal.project.controller.HelloListener;
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

public class ClientApp {
  public static Member loginUser;
  private final int BOARD = 1;
  private final int FREE_BOARD = 2;

  MemberDao memberDao;
  BoardDao boardDao;
  BoardDao freeBoardDao;

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public ClientApp(String ip, int port) throws Exception {

    Connection con =
        DriverManager.getConnection("jdbc:mysql://personal:1111@localhost:3306/personaldb" // JDBC
        // URL
        );

    this.memberDao = new MySQLMemberDao(con);
    this.boardDao = new MySQLBoardDao(con, BOARD);
    this.freeBoardDao = new MySQLBoardDao(con, FREE_BOARD);

    prepareMenu();
  }

  private void close() throws Exception {
    prompt.close();
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
    System.out.println("학교 통합정보 관리시스템");
    System.out.println("----------------------------------");
  }

  public void execute() {
    printTitle();

    new LoginListener(memberDao).service(prompt);

    mainMenu.execute(prompt);
  }



  private void prepareMenu() {
    MenuGroup studentMenu = new MenuGroup("회원정보");
    studentMenu.add(new Menu("등록", new MemberAddListener(memberDao)));
    studentMenu.add(new Menu("목록", new MemberListListener(memberDao)));
    studentMenu.add(new Menu("조회", new MemberDetailListener(memberDao)));
    studentMenu.add(new Menu("변경", new MemberUpdateListener(memberDao)));
    studentMenu.add(new Menu("삭제", new MemberDeleteListener(memberDao)));
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
