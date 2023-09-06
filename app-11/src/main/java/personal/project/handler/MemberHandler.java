package personal.project.controller;

import personal.util.Prompt;
import personal.project.vo.Student;

public class MemberHandler {

  static final int MAX_SIZE = 100;
  static Student[] students = new Student[MAX_SIZE];

  static int userId = 1;
  static int length = 0;

  static final boolean ENROLLMENT = true;
  static final boolean DROPOUT = false;
  static final char MALE = 'M';
  static final char FEMALE = 'W';

  public static void inputMember() {
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }

    Student s = new Student();
    s.birth = Prompt.inputInt("생년월일? ");
    s.name = Prompt.inputString("이름? ");
    s.grade = Prompt.inputInt("학년? ");
    s.koreanScore = Prompt.inputInt("국어점수? ");
    s.englishScore = Prompt.inputInt("영어점수? ");
    s.mathScore = Prompt.inputInt("수학점수? ");
    s.scoreAvg = (float) (s.koreanScore + s.englishScore + s.mathScore) / 3;
    s.gender = inputGender((char) 0);
    s.status = inputStatus(false, "insert");

    s.no = userId++;

    students[length++] = s;

  }

  public static void printMembers() {
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println("------------------------------------------------------------------------------------------");

    for (int i = 0; i < length; i++) {
      Student s = students[i];
      System.out.printf(
          "  %d  |  %d  | %s |  %d  |   %d   |   %d   |   %d   |  %s  |  %s  |  %.1f\n",
          s.no, s.birth, s.name, s.grade, s.koreanScore, s.englishScore, s.mathScore,
          toBooleanString(s.status), toGenderString(s.gender), s.scoreAvg);
    }
  }

  public static void viewMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Student s = students[i];
      if (s.no == Integer.parseInt(memberNo)) {
        System.out.printf("생년월일: %d\n", s.birth);
        System.out.printf("이름: %s\n", s.name);
        System.out.printf("학년: %d\n", s.grade);
        System.out.printf("국어점수: %d\n", s.koreanScore);
        System.out.printf("영어점수: %d\n", s.englishScore);
        System.out.printf("수학점수: %d\n", s.mathScore);
        System.out.printf("재학여부: %s\n", toBooleanString(s.status));
        System.out.printf("성별: %s\n", toGenderString(s.gender));
        System.out.printf("평균점수: %.1f\n", s.scoreAvg);
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
      if (s.no == Integer.parseInt(memberNo)) {
        System.out.printf("생년월일(%d)? ", s.birth);
        s.birth = Prompt.inputInt("");
        System.out.printf("이름(%s)? ", s.name);
        s.name = Prompt.inputString("");
        System.out.printf("학년(%d)? ", s.grade);
        s.grade = Prompt.inputInt("");
        System.out.printf("국어점수(%d)? ", s.koreanScore);
        s.koreanScore = Prompt.inputInt("");
        System.out.printf("영어점수(%d)? ", s.englishScore);
        s.englishScore = Prompt.inputInt("");
        System.out.printf("수학점수(%d)? ", s.mathScore);
        s.mathScore = Prompt.inputInt("");
        s.status = inputStatus(s.status, "update");
        s.gender = inputGender(s.gender);
        s.scoreAvg = (float) (s.koreanScore + s.englishScore + s.mathScore) / 3;
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
      String menuNo = Prompt.inputString(label +
          "  1. 재학\n" +
          "  2. 퇴학\n" +
          "> ");

      switch (menuNo) {
        case "1":
          return ENROLLMENT;
        case "2":
          return DROPOUT;
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
      String menuNo = Prompt.inputString(label +
          " 1. 남자\n" +
          " 2. 여자\n" +
          "> ");

      switch (menuNo) {
        case "1":
          return MALE;
        case "2":
          return FEMALE;
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
      if (s.no == memberNo) {
        return i;
      }
    }
    return -1;
  }

  public static boolean available() {
    return length < MAX_SIZE;
  }
}
