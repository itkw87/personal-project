package personal.project;



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import personal.net.RequestEntity;
import personal.net.ResponseEntity;
import personal.project.dao.BoardListDao;
import personal.project.dao.StudentListDao;


public class ServerApp {

  int port;
  ServerSocket serverSocket;

  HashMap<String, Object> daoMap = new HashMap<>();

  // 스레드를 리턴해 줄 스레드풀 준비
  ExecutorService threadPool = Executors.newFixedThreadPool(10);

  public ServerApp(int port) throws Exception {
    this.port = port;

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

    ServerApp app = new ServerApp(Integer.parseInt(args[0]));
    app.execute();
    app.close();
  }



  public void execute() throws Exception {
    System.out.println("학생별 성적정보 관리 어플리케이션");

    this.serverSocket = new ServerSocket(port);
    System.out.println("서버 실행 중...");


    while (true) {
      Socket socket = serverSocket.accept();
      threadPool.execute(() -> processRequest(socket));
    }

  }


  public static Method findMethod(Object obj, String methodName) {
    Method[] methods = obj.getClass().getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].getName().equals(methodName)) {
        return methods[i];
      }
    }
    return null;
  }


  public static Object call(Object obj, Method method, RequestEntity request) throws Exception {
    Parameter[] params = method.getParameters();
    if (params.length > 0) {
      return method.invoke(obj, request.getObject(params[0].getType()));
    } else {
      return method.invoke(obj);
    }
  }


  public void processRequest(Socket socket) {

    try (Socket s = socket;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
      System.out.printf("%s:%s 클라이언트가 접속했음!\n", socketAddress.getHostString(),
          socketAddress.getPort());

      // 클라이언트 요청을 반복해서 처리하지 않는다.
      // => 접속 -> 요청 -> 실행 -> 응답 -> 연결 끊기
      RequestEntity request = RequestEntity.fromJson(in.readUTF());

      String command = request.getCommand();
      System.out.println("요청 받은 작업: " + command);


      String[] values = command.split("/");
      String dataName = values[0];
      String methodName = values[1];

      Object dao = daoMap.get(dataName);
      if (dao == null) {
        out.writeUTF(
            new ResponseEntity().status(ResponseEntity.ERROR).result("데이터를 찾을 수 없습니다").toJson());
        return;
      }


      Method method = findMethod(dao, methodName);
      if (method == null) {
        out.writeUTF(
            new ResponseEntity().status(ResponseEntity.ERROR).result("메서드를 찾을 수 없습니다.").toJson());
        return;
      }

      try {
        Object result = call(dao, method, request);


        ResponseEntity response = new ResponseEntity();
        response.status(ResponseEntity.SUCCESS);
        response.result(result);
        out.writeUTF(response.toJson());

      } catch (Exception e) {
        ResponseEntity response = new ResponseEntity();
        response.status(ResponseEntity.ERROR);
        response.result(e.getMessage());
        out.writeUTF(response.toJson());
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}

