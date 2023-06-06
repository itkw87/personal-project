package personal.project;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");

    // 키보드 스캐너 준비
    Scanner scanner = new Scanner(System.in);

    System.out.print("번호? ");
    int no = scanner.nextInt();

    System.out.print("생년월일? ");
    int birth = scanner.nextInt();

    System.out.print("이름? ");
    String name = scanner.next();

    System.out.print("학년? ");
    int grade = scanner.nextInt();

    System.out.print("국어점수? ");
    int koreanScore = scanner.nextInt();

    System.out.print("영어점수? ");
    int englishScore = scanner.nextInt();

    System.out.print("수학점수? ");
    int mathScore = scanner.nextInt();

    System.out.print("재학여부(true/false)? ");
    boolean status = scanner.nextBoolean();

    System.out.print("성별(남자:M, 여자:W)? ");
    String str = scanner.next();
    char gender = str.charAt(0);

    System.out.print("성적평균? ");
    float scoreAvg = scanner.nextFloat();

    System.out.println("---------------------------------------");

    System.out.printf("번호: %d\n", no);
    System.out.printf("생년월일: %d\n", birth);
    System.out.printf("이름: %s\n", name);
    System.out.printf("학년: %d\n", grade);
    System.out.printf("국어점수: %d\n", koreanScore);
    System.out.printf("영어점수: %d\n", englishScore);
    System.out.printf("수학점수: %d\n", mathScore);
    System.out.printf("재학여부: %b\n", status);
    System.out.printf("성별(남자(M), 여자(W)): %c\n", gender);
    System.out.printf("성적평균: %.1f\n", scoreAvg);

    scanner.close();
  }
}
