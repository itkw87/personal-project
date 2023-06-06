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
    birth[length] = Prompt.inpuInt("생년월일? ");
    name[length] = Prompt.inpuString("이름? ");
    grade[length] = Prompt.inpuInt("학년? ");
    koreanScore[length] = Prompt.inpuInt("국어점수? ");
    englishScore[length] = Prompt.inpuInt("영어점수? ");
    mathScore[length] = Prompt.inpuInt("수학점수? ");

    scoreAvg[length] = (float) (koreanScore[length] + englishScore[length] + mathScore[length]) / 3;

    statusLoop: while (true) {
      switch (Prompt.promptPickMenu("재학여부: ", "  1. true", "  2. false")) {
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
      switch (Prompt.promptPickMenu("성별: ", "  1. 남자", "  2. 여자")) {
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

  public static void printMembers() {
    System.out.println("------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println("------------------------------------------------------------------------------------------");

    for (int i = 0; i < length; i++) {
      System.out.printf(
          "  %d  |  %d  | %s |  %d  |   %d   |   %d   |   %d   |  %b  |  %c  |  %.1f\n",
          no[i], birth[i], name[i], grade[i], koreanScore[i], englishScore[i], mathScore[i], status[i], gender[i],
          scoreAvg[i]);
    }
  }

  public static boolean available() {
    return length < MAX_SIZE;
  }
}
