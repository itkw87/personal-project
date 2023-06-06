package personal.project;

public class App {
  public static void main(String[] args) {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");

    System.out.print("번호");
    System.out.println(100);

    System.out.print("생년월일: ");
    System.out.println(20050101);

    System.out.printf("이름: %s", "최기현");
    System.out.println();

    System.out.println("학년: " + 3);

    System.out.println("국어: " + 70);
    System.out.println("영어: " + 80);
    System.out.println("수학: " + 50);

    System.out.printf("재학여부: %b\n", true);

    System.out.printf("성별(남자M), 여자(W)): %c\n", 'M');

    System.out.printf("성적평균: %.1f\n", 66.6f);

  }
}
