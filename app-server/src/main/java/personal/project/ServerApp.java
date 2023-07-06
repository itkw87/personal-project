package personal.project;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import personal.net.RequestEntity;
import personal.net.ResponseEntity;
import personal.project.dao.BoardDao;
import personal.project.dao.BoardListDao;
import personal.project.dao.StudentDao;
import personal.project.dao.StudentListDao;
import personal.project.vo.Board;
import personal.project.vo.Student;

public class ServerApp {

  int port;
  ServerSocket serverSocket;

  StudentDao studentDao = new StudentListDao("student.json");
  BoardDao boardDao = new BoardListDao("board.json");
  BoardDao freeBoardDao = new BoardListDao("freeBoard.json");

  public ServerApp(int port) throws Exception {
    this.port = port;
  }

  private void close() throws Exception {
    serverSocket.close();
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("실행 예) 'java ... personal.project.ServerApp 포트번호' 와 같이 입력해야 합니다.");
      return;
    }

    ServerApp app = new ServerApp(Integer.parseInt(args[0]));
    app.execute();
    app.close();
  }


  public void execute() throws Exception {
    System.out.println("학생별 성적정보 관리 어플리케이션");

    this.serverSocket = new ServerSocket(port);
    System.out.println("서버 실행 중...");

    Socket socket = serverSocket.accept();
    DataInputStream in = new DataInputStream(socket.getInputStream());
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    while (true) {
      RequestEntity request = RequestEntity.fromJson(in.readUTF());

      String command = request.getCommand();
      System.out.println("요청 받은 작업: " + command);

      if (command.equals("quit")) {
        break;
      }

      ResponseEntity response = new ResponseEntity();

      switch (command) {
        case "board/list":
          response.status(ResponseEntity.SUCCESS).result(boardDao.list());
          break;
        case "board/insert":
          boardDao.insert(request.getObject(Board.class));
          response.status(ResponseEntity.SUCCESS);
          break;
        case "board/findBy":
          Board board = boardDao.findBy(request.getObject(Integer.class));
          if (board == null) {
            response.status(ResponseEntity.FAILURE).result("해당 번호의 게시글이 없습니다!");
          } else {
            response.status(ResponseEntity.SUCCESS).result(board);
          }
          break;
        case "board/update":
          int value = boardDao.update(request.getObject(Board.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        case "board/delete":
          value = boardDao.delete(request.getObject(Integer.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        case "student/list":
          response.status(ResponseEntity.SUCCESS).result(studentDao.list());
          break;
        case "student/insert":
          studentDao.insert(request.getObject(Student.class));
          response.status(ResponseEntity.SUCCESS);
          break;
        case "student/findBy":
          Student student = studentDao.findBy(request.getObject(Integer.class));
          if (student == null) {
            response.status(ResponseEntity.FAILURE).result("해당 번호의 회원이 없습니다!");
          } else {
            response.status(ResponseEntity.SUCCESS).result(student);
          }
          break;
        case "student/update":
          value = studentDao.update(request.getObject(Student.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        case "student/delete":
          value = studentDao.delete(request.getObject(Integer.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        case "freeBoard/list":
          response.status(ResponseEntity.SUCCESS).result(freeBoardDao.list());
          break;
        case "freeBoard/insert":
          freeBoardDao.insert(request.getObject(Board.class));
          response.status(ResponseEntity.SUCCESS);
          break;
        case "freeBoard/findBy":
          board = freeBoardDao.findBy(request.getObject(Integer.class));
          if (board == null) {
            response.status(ResponseEntity.FAILURE).result("해당 번호의 게시글이 없습니다!");
          } else {
            response.status(ResponseEntity.SUCCESS).result(board);
          }
          break;
        case "freeBoard/update":
          value = freeBoardDao.update(request.getObject(Board.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        case "freeBoard/delete":
          value = freeBoardDao.delete(request.getObject(Integer.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        default:
          response.status(ResponseEntity.ERROR).result("해당 명령을 지원하지 않습니다!");
      }
      out.writeUTF(response.toJson());

    }
    in.close();
    out.close();
    socket.close();
  }



}
