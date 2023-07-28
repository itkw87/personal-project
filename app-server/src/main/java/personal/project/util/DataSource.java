package personal.project.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

  String jdbcUrl;
  String username;
  String password;

  // 커넥션 목록을 유지하기 위한 객체: 커넥션 풀(connection pool)
  List<Connection> connectionPool = new ArrayList<>();

  // 현재 스레드에 값을 넣고 꺼내는 일을 하는 객체
  // ThreadLocal
  ThreadLocal<Connection> connectionBox = new ThreadLocal<>();

  public DataSource(String jdbcUrl, String username, String password) {
    this.jdbcUrl = jdbcUrl;
    this.username = username;
    this.password = password;
  }

  public Connection getConnection(boolean autoCommit) throws Exception {
    Connection con = this.getConnection();
    con.setAutoCommit(autoCommit);
    return con;
  }

  public Connection getConnection() throws Exception {

    // 현재 스레드의 ThreadLocal에 저장된 Connection 객체를 꺼낸다.
    Connection con = connectionBox.get();

    if (con == null) { // 현재 스레드가 ThreadLocal에 저장하고 있는 DB Connection 객체가 없다면

      if (connectionPool.size() > 0) { // 커넥션풀에 사용할 수 있는 Connection 객체가 있다면 꺼낸다.
        con = connectionPool.remove(0); // 커넥션풀에서 꺼낼 커넥션을 삭제하고 꺼낸다.
        System.out.printf("[%s] - DB 커넥션풀에서 꺼냄!\n", Thread.currentThread().getName());

      } else {
        // 커넥션 풀에 현재 사용할 수 있는 Connection 객체가 없다면 새로 생성한다.
        con = DriverManager.getConnection(jdbcUrl, username, password);
        con.setAutoCommit(true);
        System.out.printf("[%s] - 새 DB 커넥션 생성!\n", Thread.currentThread().getName());
      }

      // Connection Pool을 통해 받은 것이든 새로 생성한 것이든 con변수에 담은 Connection객체를 현재 스레드의 ThreadLocal에 저장한다.
      connectionBox.set(con);
      System.out.printf("[%s] - 스레드에 커넥션 객체 보관!\n", Thread.currentThread().getName());

    } else { // 현재 스레드가 ThreadLocal에 보관(장착)하고 있는 스레드가 있다면
      System.out.printf("[%s] - 스레드에 보관된 커넥션 사용!\n", Thread.currentThread().getName());
    }
    return con;
  }


  public void clean() {
    // 스레드가 작업을 끝냈으면, 이 스레드에 보관된 Connection 객체를 제거한다.
    Connection con = connectionBox.get();
    if (con != null) {

      // 커넥션을 커넥션풀에 반납하기 전에 커넥션에서 수행한 작업 중 미완료 작업은 취소한다.
      try {
        con.rollback();
      } catch (Exception e) {
      }

      // 스레드가 사용한 DB 커넥션은 다른 스레드에서 사용할 수 있도록 커넥션 풀에 저장한다.
      // 다음에 다시 사용해야 하기 때문에 close()하면 안된다.
      connectionPool.add(con);
      System.out.printf("[%s] - 커넥션풀에 DB 커넥션 저장!\n", Thread.currentThread().getName());


      // 현재 스레드가 종료되더라도 ThreadLocal에는 값이 남아 메모리가 누수될 수 있으니 마지막에 명시적으로 값을 제거해야한다.
      connectionBox.remove();
      System.out.printf("[%s] - 스레드에서 ThreadLocal객체 비우기!\n", Thread.currentThread().getName());
    }



  }



}
