package personal.project;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {

    // 키보드 스캐너 준비
    Scanner scanner = new Scanner(System.in);

    final int MAX_SIZE = 100;
    int userId = 1;
    int length = 0;

    int[] no = new int[MAX_SIZE];
    int[] birth = new int[MAX_SIZE];
    String[] name = new String[MAX_SIZE];
    int[] grade = new int[MAX_SIZE];
    int[] koreanScore = new int[MAX_SIZE];
    int[] englishScore = new int[MAX_SIZE];
    int[] mathScore = new int[MAX_SIZE];
    boolean[] status = new boolean[MAX_SIZE];
    char[] gender = new char[MAX_SIZE];
    float[] scoreAvg = new float[MAX_SIZE];

    printTitle();

    for (int i = 0; i < MAX_SIZE; i++) {
      inputMember(scanner, i, no, birth, name, grade, koreanScore, englishScore, mathScore, status, gender,
          scoreAvg, userId++);
      length++;
      if (!promptContinue(scanner)) {
        break;
      }
    }

    printMembers(length, no, birth, name, grade, koreanScore, englishScore, mathScore, status, name, gender, scoreAvg);

    scanner.close();
  }

  static void printTitle() {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");
  }

  static void inputMember(Scanner scanner, int i, int[] no,
      int[] birth, String[] name, int[] grade, int[] koreanScore,
      int[] englishScore, int[] mathScore, boolean[] status, char[] gender, float[] scoreAvg, int userId) {

    System.out.print("생년월일? ");
    birth[i] = scanner.nextInt();

    System.out.print("이름? ");
    name[i] = scanner.next();

    System.out.print("학년? ");
    grade[i] = scanner.nextInt();

    System.out.print("국어점수? ");
    koreanScore[i] = scanner.nextInt();

    System.out.print("영어점수? ");
    englishScore[i] = scanner.nextInt();

    System.out.print("수학점수? ");
    mathScore[i] = scanner.nextInt();

    scoreAvg[i] = (float) (koreanScore[i] + englishScore[i] + mathScore[i]) / 3;

    statusLoop: while (true) {
      switch (promptPickMenu("재학여부: ", "  1. true", "  2. false", scanner)) {
        case "1":
          status[i] = true;
          break statusLoop;
        case "2":
          status[i] = false;
          break statusLoop;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }

    genderLoop: while (true) {
      switch (promptPickMenu("성별: ", "  1. 남자", "  2. 여자", scanner)) {
        case "1":
          gender[i] = 'M';
          break genderLoop;
        case "2":
          gender[i] = 'W';
          break genderLoop;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }

    no[i] = userId;
  }

  static boolean promptContinue(Scanner scanner) {
    System.out.print("계속 하시겠습니까?(Y/n) ");
    String response = scanner.nextLine();
    if (!response.equals("") && !response.equalsIgnoreCase("Y")) {
      return false;
    }
    return true;
  }

  static void printMembers(int length, int[] no, int[] birth, String[] name, int[] grade, int[] koreanScore,
      int[] englishScore, int[] mathScore, boolean[] status, String[] email, char[] gender, float[] scoreAvg) {
    System.out.println("----------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println("----------------------------------------------------------------------------------");

    for (int i = 0; i < length; i++) {
      System.out.printf("%d, %d, %s, %d, %d, %d, %d, %b, %c, %.1f\n",
          no[i], birth[i], name[i], grade[i], koreanScore[i], englishScore[i], mathScore[i], status[i], gender[i],
          scoreAvg[i]);
    }
  }

  static String promptPickMenu(String menuName, String menu1, String menu2, Scanner scanner) {
    System.out.println(menuName);
    System.out.println(menu1);
    System.out.println(menu2);
    System.out.print("> ");
    String menuNo = scanner.next();
    scanner.nextLine();
    return menuNo;
  }

}
