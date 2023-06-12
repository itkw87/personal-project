package personal.project.handler;

import personal.util.Prompt;

public class MemberHandler {

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

  public static void inputMember() {
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }
    birth[length] = Prompt.inputInt("생년월일? ");
    name[length] = Prompt.inputString("이름? ");
    grade[length] = Prompt.inputInt("학년? ");
    koreanScore[length] = Prompt.inputInt("국어점수? ");
    englishScore[length] = Prompt.inputInt("영어점수? ");
    mathScore[length] = Prompt.inputInt("수학점수? ");
    scoreAvg[length] = (float) (koreanScore[length] + englishScore[length] + mathScore[length]) / 3;
    // gender[length] = inputGender('\u0000');
    gender[length] = inputGender((char) 0);
    status[length] = inputStatus(false);

    no[length] = userId++;
    length++;
  }

  public static void printMembers() {
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println("------------------------------------------------------------------------------------------");

    for (int i = 0; i < length; i++) {
      System.out.printf(
          "  %d  |  %d  | %s |  %d  |   %d   |   %d   |   %d   |  %s  |  %s  |  %.1f\n",
          no[i], birth[i], name[i], grade[i], koreanScore[i], englishScore[i], mathScore[i], toBooleanString(status[i]),
          toGenderString(gender[i]),
          scoreAvg[i]);
    }
  }

  public static void viewMember() {
    String memberNo = Prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      if (no[i] == Integer.parseInt(memberNo)) {
        System.out.printf("생년월일: %d\n", birth[i]);
        System.out.printf("이름: %s\n", name[i]);
        System.out.printf("학년: %d\n", grade[i]);
        System.out.printf("국어점수: %d\n", koreanScore[i]);
        System.out.printf("영어점수: %d\n", englishScore[i]);
        System.out.printf("수학점수: %d\n", mathScore[i]);
        System.out.printf("재학여부: %s\n", toBooleanString(status[i]));
        System.out.printf("성별: %s\n", toGenderString(gender[i]));
        System.out.printf("평균점수: %.1f\n", scoreAvg[i]);
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
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
      if (no[i] == Integer.parseInt(memberNo)) {
        System.out.printf("생년월일(%d)? ", birth[i]);
        birth[i] = Prompt.inputInt("");
        System.out.printf("이름(%s)? ", name[i]);
        name[i] = Prompt.inputString("");
        System.out.printf("학년(%d)? ", grade[i]);
        grade[i] = Prompt.inputInt("");
        System.out.printf("국어점수(%d)? ", koreanScore[i]);
        koreanScore[i] = Prompt.inputInt("");
        System.out.printf("영어점수(%d)? ", englishScore[i]);
        englishScore[i] = Prompt.inputInt("");
        System.out.printf("수학점수(%d)? ", mathScore[i]);
        mathScore[i] = Prompt.inputInt("");
        status[i] = inputStatus(status[i]);
        gender[i] = inputGender(gender[i]);
        scoreAvg[i] = (float) (koreanScore[i] + englishScore[i] + mathScore[i]) / 3;
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다!");
  }

  private static boolean inputStatus(boolean value) {
    String label;
    if (value == false) {
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
      System.out.println("해당 번호의 회원이 없습니다!");
      return;
    }

    for (int i = deletedIndex; i < length - 1; i++) {
      no[i] = no[i + 1];
      birth[i] = birth[i + 1];
      name[i] = name[i + 1];
      grade[i] = grade[i + 1];
      koreanScore[i] = koreanScore[i + 1];
      englishScore[i] = englishScore[i + 1];
      mathScore[i] = mathScore[i + 1];
      status[i] = status[i + 1];
      gender[i] = gender[i + 1];
      scoreAvg[i] = scoreAvg[i + 1];
    }

    no[length - 1] = 0;
    birth[length - 1] = 0;
    name[length - 1] = null;
    grade[length - 1] = 0;
    koreanScore[length - 1] = 0;
    englishScore[length - 1] = 0;
    mathScore[length - 1] = 0;
    status[length - 1] = false;
    gender[length - 1] = (char) 0;
    scoreAvg[length - 1] = 0.0f;

    length--;
  }

  private static int indexOf(int memberNo) {
    for (int i = 0; i < length; i++) {
      if (no[i] == memberNo) {
        return i;
      }
    }
    return -1;
  }

  public static boolean available() {
    return length < MAX_SIZE;
  }
}
