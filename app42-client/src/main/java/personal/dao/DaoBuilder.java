package personal.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.List;
import personal.net.RequestEntity;
import personal.net.ResponseEntity;
import personal.project.ClientApp;

public class DaoBuilder {
  String serverAddress;
  int port;

  public DaoBuilder(String serverAddress, int port) {
    this.serverAddress = serverAddress;
    this.port = port;
  }

  @SuppressWarnings("unchecked")
  public <T> T build(String dataName, Class<T> type) {


    return (T) Proxy.newProxyInstance(ClientApp.class.getClassLoader(), new Class[] {type},
        (proxy, method, args) -> {

          // 요청 정보 준비
          RequestEntity requestEntity = new RequestEntity();
          requestEntity.command(dataName + "/" + method.getName());
          if (args != null) {
            requestEntity.data(args[0]); // BoardDao인터페이스의 메서드는 다 인자를 하나만 받는 메서드임
          }

          try (Socket socket = new Socket(serverAddress, port);
              DataOutputStream out = new DataOutputStream(socket.getOutputStream());
              DataInputStream in = new DataInputStream(socket.getInputStream());) {
            // 요청 정보 전송
            out.writeUTF(requestEntity.toJson());


            // 응답 정보 수신
            ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
            if (response.getStatus().equals(ResponseEntity.ERROR)) {
              throw new RuntimeException(response.getResult());
            }

            // 리턴 타입 조사
            Class<?> returnType = method.getReturnType();

            if (returnType == int.class) {
              return response.getObject(int.class);
            } else if (returnType == void.class) {
              return null;
            } else if (returnType == List.class) {
              ParameterizedType paramType = (ParameterizedType) method.getGenericReturnType();
              Class<?> itemType = (Class<?>) paramType.getActualTypeArguments()[0];
              // ParameterizedType.getActualTypeArgumentsget() 메서드는 Type배열을 반환하는데
              // List나 Set같은 경우는 제네릭 항목이 1개이지만 MAP같이 제네릭 항목이 여러개인 경우도 있기 때문임
              return response.getList(itemType);
            } else {
              return response.getObject(returnType);
            }
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }


}
