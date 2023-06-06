package personal.project;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");

    // 키보드 스캐너 준비
    Scanner scanner = new Scanner(System.in);

    final int SIZE = 100;

    int[] no = new int[SIZE];
    int[] birth = new int[SIZE];
    String[] name = new String[SIZE];
    int[] grade = new int[SIZE];
    int[] koreanScore = new int[SIZE];
    int[] englishScore = new int[SIZE];
    int[] mathScore = new int[SIZE];
    boolean[] status = new boolean[SIZE];
    char[] gender = new char[SIZE];
    float[] scoreAvg = new float[SIZE];

    for (int i = 0; i < SIZE; i++) {
      System.out.print("번호? ");
      no[i] = scanner.nextInt();

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

      System.out.print("재학여부(true/false)? ");
      status[i] = scanner.nextBoolean();

      System.out.print("성별(남자:M, 여자:W)? ");
      String str = scanner.next();
      gender[i] = str.charAt(0);

      System.out.print("성적평균? ");
      scoreAvg[i] = scanner.nextFloat();
    }

    System.out.println("---------------------------------------");

    for (int i = 0; i < SIZE; i++) {
      System.out.printf("번호: %d\n", no[i]);
      System.out.printf("생년월일: %d\n", birth[i]);
      System.out.printf("이름: %s\n", name[i]);
      System.out.printf("학년: %d\n", grade[i]);
      System.out.printf("국어점수: %d\n", koreanScore[i]);
      System.out.printf("영어점수: %d\n", englishScore[i]);
      System.out.printf("수학점수: %d\n", mathScore[i]);
      System.out.printf("재학여부: %b\n", status[i]);
      System.out.printf("성별(남자(M), 여자(W)): %c\n", gender[i]);
      System.out.printf("성적평균: %.1f\n", scoreAvg[i]);
    }
    scanner.close();
  }
}
