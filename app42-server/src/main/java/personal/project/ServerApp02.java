package personal.project;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import personal.net.RequestEntity;
import personal.net.ResponseEntity;
import personal.project.dao.BoardListDao;
import personal.project.dao.StudentListDao;

// 1) 클라이언트가 보낸 명령을 데이터이름과 메서드 이름으로 분리한다.
// 2) 클라이언트가 요청한 DAO 객체와 메서드를 찾는다.
public class ServerApp02 {

  int port;
  ServerSocket serverSocket;

  // 클라이언트 요청을 처리할 DAO객체를 맵에 보관한다.
  HashMap<String, Object> daoMap = new HashMap<>();

  public ServerApp02(int port) throws Exception {
    this.port = port;

    // DAO 객체 생성 및 보관
    daoMap.put("student", new StudentListDao("student.json"));
    daoMap.put("board", new BoardListDao("board.json"));
    daoMap.put("freeBoard", new BoardListDao("freeBoard.json"));
  }

  private void close() throws Exception {
    serverSocket.close();
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("실행 예) 'java ... personal.project.ServerApp 포트번호' 와 같이 입력해야 합니다.");
      return;
    }

    ServerApp02 app = new ServerApp02(Integer.parseInt(args[0]));
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


      String[] values = command.split("/");
      String dataName = values[0];
      String methodName = values[1];

      // 데이터 이름으로 DAO 객체를 꺼낸다.
      Object dao = daoMap.get(dataName);
      if (dao == null) {
        out.writeUTF(
            new ResponseEntity().status(ResponseEntity.ERROR).result("데이터를 찾을 수 없습니다").toJson());
        continue;
      }


      // DAO에 해당 메서드가 있는지 알아낸다.
      Method[] methods = dao.getClass().getDeclaredMethods();
      Method method = null;
      for (int i = 0; i < methods.length; i++) {
        if (methods[i].getName().equals(methodName)) {
          method = methods[i];
          break;
        }
      }

      if (method == null) {
        // 만약 클라이언트가 요청한 메서드를 찾지 못한다면 오류 정보를 클라이언트에게 보낸다.
        out.writeUTF(
            new ResponseEntity().status(ResponseEntity.ERROR).result("메서드를 찾을 수 없습니다.").toJson());
        continue;
      }
      System.out.printf("%s.%s\n", dataName, methodName);

      ResponseEntity response = new ResponseEntity();


      switch (command) {
        // case "board/list":
        // response.status(ResponseEntity.SUCCESS).result(boardDao.list());
        // break;
        // case "board/insert":
        // boardDao.insert(request.getObject(Board.class));
        // response.status(ResponseEntity.SUCCESS);
        // break;
        // case "board/findBy":
        // Board board = boardDao.findBy(request.getObject(Integer.class));
        // if (board == null) {
        // response.status(ResponseEntity.FAILURE).result("해당 번호의 게시글이 없습니다!");
        // } else {
        // response.status(ResponseEntity.SUCCESS).result(board);
        // }
        // break;
        // case "board/update":
        // int value = boardDao.update(request.getObject(Board.class));
        // response.status(ResponseEntity.SUCCESS).result(value);
        // break;
        // case "board/delete":
        // value = boardDao.delete(request.getObject(Integer.class));
        // response.status(ResponseEntity.SUCCESS).result(value);
        // break;
        // case "student/list":
        // response.status(ResponseEntity.SUCCESS).result(studentDao.list());
        // break;
        // case "student/insert":
        // studentDao.insert(request.getObject(Student.class));
        // response.status(ResponseEntity.SUCCESS);
        // break;
        // case "student/findBy":
        // Student student = studentDao.findBy(request.getObject(Integer.class));
        // if (student == null) {
        // response.status(ResponseEntity.FAILURE).result("해당 번호의 회원이 없습니다!");
        // } else {
        // response.status(ResponseEntity.SUCCESS).result(student);
        // }
        // break;
        // case "student/update":
        // value = studentDao.update(request.getObject(Student.class));
        // response.status(ResponseEntity.SUCCESS).result(value);
        // break;
        // case "student/delete":
        // value = studentDao.delete(request.getObject(Integer.class));
        // response.status(ResponseEntity.SUCCESS).result(value);
        // break;
        // case "freeBoard/list":
        // response.status(ResponseEntity.SUCCESS).result(freeBoardDao.list());
        // break;
        // case "freeBoard/insert":
        // freeBoardDao.insert(request.getObject(Board.class));
        // response.status(ResponseEntity.SUCCESS);
        // break;
        // case "freeBoard/findBy":
        // board = freeBoardDao.findBy(request.getObject(Integer.class));
        // if (board == null) {
        // response.status(ResponseEntity.FAILURE).result("해당 번호의 게시글이 없습니다!");
        // } else {
        // response.status(ResponseEntity.SUCCESS).result(board);
        // }
        // break;
        // case "freeBoard/update":
        // value = freeBoardDao.update(request.getObject(Board.class));
        // response.status(ResponseEntity.SUCCESS).result(value);
        // break;
        // case "freeBoard/delete":
        // value = freeBoardDao.delete(request.getObject(Integer.class));
        // response.status(ResponseEntity.SUCCESS).result(value);
        // break;
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
