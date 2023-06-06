package personal.project;

import java.util.Scanner;

public class App {
  static Scanner scanner = new Scanner(System.in);

  static final int MAX_SIZE = 100;
  static int[] no = new int[MAX_SIZE];
  static int[] birth = new int[MAX_SIZE];
  static String[] name = new String[MAX_SIZE];
  static int[] grade = new int[MAX_SIZE];
  static int[] koreanScore = new int[MAX_SIZE];
  static int[] englishScore = new int[MAX_SIZE];
  static int[] mathScore = new int[MAX_SIZE];
  static boolean[] status = new boolean[MAX_SIZE];
  static char[] gender = new char[MAX_SIZE];
  static float[] scoreAvg = new float[MAX_SIZE];
  static int userId = 1;
  static int length = 0;

  static final boolean ENROLLMENT = true;
  static final boolean DROPOUT = false;
  static final char MALE = 'M';
  static final char FEMALE = 'W';

  public static void main(String[] args) {

    // 키보드 스캐너 준비

    printTitle();

    while (length < MAX_SIZE) {
      inputMember();
      if (!promptContinue()) {
        break;
      }
    }

    printMembers();

    scanner.close();
  }

  static void printTitle() {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");
  }

  static void inputMember() {

    System.out.print("생년월일? ");
    birth[length] = scanner.nextInt();

    System.out.print("이름? ");
    name[length] = scanner.next();

    System.out.print("학년? ");
    grade[length] = scanner.nextInt();

    System.out.print("국어점수? ");
    koreanScore[length] = scanner.nextInt();

    System.out.print("영어점수? ");
    englishScore[length] = scanner.nextInt();

    System.out.print("수학점수? ");
    mathScore[length] = scanner.nextInt();

    scoreAvg[length] = (float) (koreanScore[length] + englishScore[length] + mathScore[length]) / 3;

    statusLoop: while (true) {
      switch (promptPickMenu("재학여부: ", "  1. true", "  2. false")) {
        case "1":
          status[length] = ENROLLMENT;
          break statusLoop;
        case "2":
          status[length] = DROPOUT;
          break statusLoop;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }

    genderLoop: while (true) {
      switch (promptPickMenu("성별: ", "  1. 남자", "  2. 여자")) {
        case "1":
          gender[length] = MALE;
          break genderLoop;
        case "2":
          gender[length] = FEMALE;
          break genderLoop;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }

    no[length] = userId++;
    length++;
  }

  static boolean promptContinue() {
    String response = prompt("계속 하시겠습니까?(Y/n) ");
    if (!response.equals("") && !response.equalsIgnoreCase("Y")) {
      return false;
    }
    return true;
  }

  static void printMembers() {
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println("------------------------------------------------------------------------------------------");

    for (int i = 0; i < length; i++) {
      System.out.printf("%d, %d, %s, %d, %d, %d, %d, %b, %c, %.1f\n",
          no[i], birth[i], name[i], grade[i], koreanScore[i], englishScore[i], mathScore[i], status[i], gender[i],
          scoreAvg[i]);
    }
  }

  static String promptPickMenu(String menuName, String menu1, String menu2) {
    System.out.println(menuName);
    System.out.println(menu1);
    System.out.println(menu2);
    System.out.print("> ");
    String menuNo = scanner.next();
    scanner.nextLine();
    return menuNo;
  }

  static String prompt(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }
}
