package personal.project.handler;

import personal.project.vo.Student;
import personal.util.Prompt;

public class StudentHandler {

  static final int MAX_SIZE = 100;
  static Student[] students = new Student[MAX_SIZE];


  static int length = 0;

  public static void inputMember() {
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }

    Student s = new Student();
    s.setBirth(Prompt.inputInt("생년월일? "));
    s.setName(Prompt.inputString("이름? "));
    s.setGrade(Prompt.inputInt("학년? "));
    s.setKoreanScore(Prompt.inputInt("국어점수? "));
    s.setEnglishScore(Prompt.inputInt("영어점수? "));
    s.setMathScore(Prompt.inputInt("수학점수? "));
    s.setScoreAvg((float) (s.getKoreanScore() + s.getEnglishScore() + s.getMathScore()) / 3);
    s.setGender(inputGender((char) 0));
    s.setStatus(inputStatus(false, "insert"));

    students[length++] = s;
  }

  public static void printMembers() {
    System.out.println(
        "------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println(
        "------------------------------------------------------------------------------------------");

    for (int i = 0; i < length; i++) {
      Student s = students[i];
      System.out.printf(
          "  %d  |  %d  | %s |  %d  |   %d   |   %d   |   %d   |  %s  |  %s  |  %.1f\n", s.getNo(),
          s.getBirth(), s.getName(), s.getGrade(), s.getKoreanScore(), s.getEnglishScore(),
          s.getMathScore(), toBooleanString(s.getStatus()), toGenderString(s.getGender()),
          s.getScoreAvg());
    }
  }

  public static void viewMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Student s = students[i];
      if (s.getNo() == Integer.parseInt(memberNo)) {
        System.out.printf("생년월일: %d\n", s.getBirth());
        System.out.printf("이름: %s\n", s.getName());
        System.out.printf("학년: %d\n", s.getGrade());
        System.out.printf("국어점수: %d\n", s.getKoreanScore());
        System.out.printf("영어점수: %d\n", s.getEnglishScore());
        System.out.printf("수학점수: %d\n", s.getMathScore());
        System.out.printf("재학여부: %s\n", toBooleanString(s.getStatus()));
        System.out.printf("성별: %s\n", toGenderString(s.getGender()));
        System.out.printf("평균점수: %.1f\n", s.getScoreAvg());
        return;
      }
    }
    System.out.println("해당 번호의 학생이 없습니다!");
  }

  public static String toBooleanString(Boolean value) {
    return value == true ? "재학" : "퇴학";
  }

  public static String toGenderString(char gender) {
    return gender == 'M' ? "남성" : "여성";
  }

  public static void updateMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Student s = students[i];
      if (s.getNo() == Integer.parseInt(memberNo)) {
        System.out.printf("생년월일(%d)? ", s.getBirth());
        s.setBirth(Prompt.inputInt(""));
        System.out.printf("이름(%s)? ", s.getName());
        s.setName(Prompt.inputString(""));
        System.out.printf("학년(%d)? ", s.getGrade());
        s.setGrade(Prompt.inputInt(""));
        System.out.printf("국어점수(%d)? ", s.getKoreanScore());
        s.setKoreanScore(Prompt.inputInt(""));
        System.out.printf("영어점수(%d)? ", s.getEnglishScore());
        s.setEnglishScore(Prompt.inputInt(""));
        System.out.printf("수학점수(%d)? ", s.getMathScore());
        s.setMathScore(Prompt.inputInt(""));
        s.setStatus(inputStatus(s.getStatus(), "update"));
        s.setGender(inputGender(s.getGender()));
        s.setScoreAvg((float) (s.getKoreanScore() + s.getEnglishScore() + s.getMathScore()) / 3);
        return;
      }
    }
    System.out.println("해당 번호의 학생이 없습니다!");
  }

  private static boolean inputStatus(boolean value, String transaction) {
    String label;
    if ("insert".equals(transaction)) {
      label = "재학여부\n";
    } else {
      label = String.format("재학여부(%s)\n", toBooleanString(value));
    }
    loop: while (true) {
      String menuNo = Prompt.inputString(label + "  1. 재학\n" + "  2. 퇴학\n" + "> ");

      switch (menuNo) {
        case "1":
          return Student.ENROLLMENT;
        case "2":
          return Student.DROPOUT;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }
  }

  private static char inputGender(char gender) {
    String label;
    if (gender == 0) {
      label = "성별?\n";
    } else {
      label = String.format("성별(%s)?\n", toGenderString(gender));
    }
    loop: while (true) {
      String menuNo = Prompt.inputString(label + " 1. 남자\n" + " 2. 여자\n" + "> ");

      switch (menuNo) {
        case "1":
          return Student.MALE;
        case "2":
          return Student.FEMALE;
        default:
          System.out.println("무효한 번호입니다.");
      }
    }
  }

  public static void deleteMember() {
    int memberNo = Prompt.inputInt("번호? ");

    int deletedIndex = indexOf(memberNo);
    if (deletedIndex == -1) {
      System.out.println("해당 번호의 학생이 없습니다!");
      return;
    }

    for (int i = deletedIndex; i < length - 1; i++) {
      students[i] = students[i + 1];
    }

    students[--length] = null;
  }

  private static int indexOf(int memberNo) {
    for (int i = 0; i < length; i++) {
      Student s = students[i];
      if (s.getNo() == memberNo) {
        return i;
      }
    }
    return -1;
  }

  public static boolean available() {
    return length < MAX_SIZE;
  }
}
