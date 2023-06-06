package personal.project;

public class App {
  public static void main(String[] args) {
    System.out.println("학생별 성적 관리 시스템");
    System.out.println("----------------------------------");

    int no = 100;
    String birth = 2005010;
    String name = "최기현";
    int grade = 3;
    int koreanScore = 70;
    int englishScore = 80;
    int mathScore = 50;
    boolean status = true;
    char gender = 'M';
    float scoreAvg = 66.6f;

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

  }
}
