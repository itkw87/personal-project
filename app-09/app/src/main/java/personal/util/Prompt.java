package personal.util;

import java.util.Scanner;

public class Prompt {

  public static Scanner scanner = new Scanner(System.in);

  public static String inpuString(String title) {
    scanner.nextLine();
    System.out.print(title);
    return scanner.nextLine();
  }

  public static int inpuInt(String title) {
    System.out.print(title);
    return scanner.nextInt();
  }

  public static void close() {
    scanner.close();
  }

  public static String promptPickMenu(String menuName, String menu1, String menu2) {
    System.out.println(menuName);
    System.out.println(menu1);
    System.out.println(menu2);
    System.out.print("> ");

    String menuNo = scanner.next();

    return menuNo;
  }
}
