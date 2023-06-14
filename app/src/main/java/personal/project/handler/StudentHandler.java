package personal.project.handler;

import personal.project.vo.Student;
import personal.util.Prompt;

public class StudentHandler {

  private static final int MAX_SIZE = 100;
  // variable initializer(변수초기화 문장) => static 블록으로 이동
  // 단 final 변수는 static 블록에서 값을 할당하지 않고 그냥 상수로 취급한다.

  private Prompt prompt;

  private Student[] students = new Student[MAX_SIZE];
  // variable initializer(변수초기화 문장) => 생성자로 이동

  private int length;

  // 생성자: 인스턴스를 사용할 수 있도록 유효한 값으로 초기화시키는 일을 한다.
  // => 필요한 값을 외부에서 받고 싶으면 파라미터를 선언하라.
  public StudentHandler(Prompt prompt) {
    this.prompt = prompt;
  }


  public void inputStudent() {
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다!");
      return;
    }

    Student student = new Student();
    student.setBirth(this.prompt.inputInt("생년월일? "));
    student.setName(this.prompt.inputString("이름? "));
    student.setGrade(this.prompt.inputInt("학년? "));
    student.setKoreanScore(this.prompt.inputInt("국어점수? "));
    student.setEnglishScore(this.prompt.inputInt("영어점수? "));
    student.setMathScore(this.prompt.inputInt("수학점수? "));
    student.setScoreAvg(
        (float) (student.getKoreanScore() + student.getEnglishScore() + student.getMathScore())
            / 3);
    student.setGender(inputGender((char) 0));
    student.setStatus(inputStatus(false, "insert"));

    this.students[this.length++] = student;
  }

  public void printStudents() {
    System.out.println(
        "------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println(
        "------------------------------------------------------------------------------------------");

    for (int i = 0; i < this.length; i++) {
      Student s = this.students[i];
      System.out.printf(
          "  %d  |  %d  | %s |  %d  |   %d   |   %d   |   %d   |  %s  |  %s  |  %.1f\n", s.getNo(),
          s.getBirth(), s.getName(), s.getGrade(), s.getKoreanScore(), s.getEnglishScore(),
          s.getMathScore(), toBooleanString(s.getStatus()), toGenderString(s.getGender()),
          s.getScoreAvg());
    }
  }

  public void viewStudent() {
    String memberNo = this.prompt.inputString("번호? ");
    for (int i = 0; i < this.length; i++) {
      Student s = this.students[i];
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

  public void updateStudent() {
    String memberNo = this.prompt.inputString("번호? ");
    for (int i = 0; i < length; i++) {
      Student studnet = students[i];
      if (studnet.getNo() == Integer.parseInt(memberNo)) {
        studnet.setBirth(this.prompt.inputInt("생년월일(%d)? ", studnet.getBirth()));
        studnet.setName(this.prompt.inputString("이름(%s)? ", studnet.getName()));
        studnet.setGrade(this.prompt.inputInt("학년(%d)? ", studnet.getGrade()));
        studnet.setKoreanScore(this.prompt.inputInt("국어점수(%d)? ", studnet.getKoreanScore()));
        studnet.setEnglishScore(this.prompt.inputInt("영어점수(%d)? ", studnet.getEnglishScore()));
        studnet.setMathScore(this.prompt.inputInt("수학점수(%d)? ", studnet.getMathScore()));
        studnet.setStatus(inputStatus(studnet.getStatus(), "update"));
        studnet.setGender(inputGender(studnet.getGender()));
        studnet.setScoreAvg(
            (float) (studnet.getKoreanScore() + studnet.getEnglishScore() + studnet.getMathScore())
                / 3);
        return;
      }
    }
    System.out.println("해당 번호의 학생이 없습니다!");
  }

  private boolean inputStatus(boolean value, String transaction) {
    String label;
    if ("insert".equals(transaction)) {
      label = "재학여부\n";
    } else {
      label = String.format("재학여부(%s)\n", toBooleanString(value));
    }

    while (true) {
      String menuNo = this.prompt.inputString(label + "  1. 재학\n" + "  2. 퇴학\n" + "> ");

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

  private char inputGender(char gender) {
    String label;
    if (gender == 0) {
      label = "성별?\n";
    } else {
      label = String.format("성별(%s)?\n", toGenderString(gender));
    }

    while (true) {
      String menuNo = this.prompt.inputString(label + " 1. 남자\n" + " 2. 여자\n" + "> ");

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

  public void deleteStudent() {
    int memberNo = this.prompt.inputInt("번호? ");

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

  private int indexOf(int memberNo) {
    for (int i = 0; i < this.length; i++) {
      Student s = this.students[i];
      if (s.getNo() == memberNo) {
        return i;
      }
    }
    return -1;
  }

  public boolean available() {
    return this.length < MAX_SIZE;
  }
}
