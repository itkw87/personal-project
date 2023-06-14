package personal.util;

import java.io.InputStream;
import java.util.Scanner;

public class Prompt {

  private Scanner scanner;

  // default constructor정의
  public Prompt() {
    scanner = new Scanner(System.in); // 입력도구 중 키보드를 가리킴
  }

  // 만약 다른 입력 도구와 연결한다면
  public Prompt(InputStream in) {
    this.scanner = new Scanner(in);
  }

  public String inputString(String title, Object... args) {
    System.out.printf(title, args);
    return this.scanner.nextLine();
  }

  public int inputInt(String title, Object... args) {
    return Integer.parseInt(inputString(title, args));
  }

  public void close() {
    this.scanner.close();
  }
}
