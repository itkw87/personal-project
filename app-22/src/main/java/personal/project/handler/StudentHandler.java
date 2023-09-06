package personal.project.controller;

import personal.project.vo.Student;
import personal.util.List;
import personal.util.MenuPrompt;

public class StudentHandler implements Handler {

  private List list;
  private MenuPrompt prompt;
  private String title;

  // 생성자: 인스턴스를 사용할 수 있도록 유효한 값으로 초기화시키는 일을 한다.
  // => 필요한 값을 외부에서 받고 싶으면 파라미터를 선언하라.
  public StudentHandler(MenuPrompt prompt, String title, List list) {
    this.prompt = prompt;
    this.title = title;
    this.list = list;
  }

  public void execute() {

    prompt.appendBreadcrumb(this.title, getMenu());

    prompt.printMenu();

    while (true) {
      String menuNo = prompt.inputMenu();
      switch (menuNo) {
        case "0":
          prompt.removeBreadcrumb();
          return;
        case "1":
          this.inputStudent();
          break;
        case "2":
          this.printStudents();;
          break;
        case "3":
          this.viewStudent();
          break;
        case "4":
          this.updateStudent();
          break;
        case "5":
          this.deleteStudent();
          break;
      }
    }

  }

  private static String getMenu() {
    StringBuilder menu = new StringBuilder();
    menu.append("1. 등록\n");
    menu.append("2. 목록\n");
    menu.append("3. 조회\n");
    menu.append("4. 변경\n");
    menu.append("5. 삭제\n");
    menu.append("0. 메인\n");
    return menu.toString();
  }


  public void inputStudent() {
    Student s = new Student();
    s.setBirth(this.prompt.inputInt("생년월일? "));
    s.setName(this.prompt.inputString("이름? "));
    s.setGrade(this.prompt.inputInt("학년? "));
    s.setKoreanScore(this.prompt.inputInt("국어점수? "));
    s.setEnglishScore(this.prompt.inputInt("영어점수? "));
    s.setMathScore(this.prompt.inputInt("수학점수? "));
    s.setScoreAvg((float) (s.getKoreanScore() + s.getEnglishScore() + s.getMathScore()) / 3);
    s.setGender(inputGender((char) 0));
    s.setStatus(inputStatus(false, "insert"));

    this.list.add(s);
  }

  public void printStudents() {
    System.out.println(
        "------------------------------------------------------------------------------------------");
    System.out.println("번호 | 생년월일 | 이름 | 학년 | 국어점수 | 영어점수 | 수학점수 | 재학여부 | 성별 | 성적평균");
    System.out.println(
        "------------------------------------------------------------------------------------------");

    for (int i = 0; i < this.list.size(); i++) {
      Student s = (Student) this.list.get(i);
      System.out.printf(
          "  %d  |  %d  | %s |  %d  |   %d   |   %d   |   %d   |  %s  |  %s  |  %.1f\n", s.getNo(),
          s.getBirth(), s.getName(), s.getGrade(), s.getKoreanScore(), s.getEnglishScore(),
          s.getMathScore(), toBooleanString(s.getStatus()), toGenderString(s.getGender()),
          s.getScoreAvg());
    }
  }

  public void viewStudent() {
    int studentNo = this.prompt.inputInt("번호? ");
    Student s = this.findBy(studentNo);
    if (s == null) {
      System.out.println("해당 번호의 학생이 없습니다!");
      return;
    }

    System.out.printf("생년월일: %d\n", s.getBirth());
    System.out.printf("이름: %s\n", s.getName());
    System.out.printf("학년: %d\n", s.getGrade());
    System.out.printf("국어점수: %d\n", s.getKoreanScore());
    System.out.printf("영어점수: %d\n", s.getEnglishScore());
    System.out.printf("수학점수: %d\n", s.getMathScore());
    System.out.printf("재학여부: %s\n", toBooleanString(s.getStatus()));
    System.out.printf("성별: %s\n", toGenderString(s.getGender()));
    System.out.printf("평균점수: %.1f\n", s.getScoreAvg());
  }

  public static String toBooleanString(Boolean value) {
    return value == true ? "재학" : "퇴학";
  }

  public static String toGenderString(char gender) {
    return gender == 'M' ? "남성" : "여성";
  }

  public void updateStudent() {
    int studentNo = this.prompt.inputInt("번호? ");
    Student s = this.findBy(studentNo);

    if (s == null) {
      System.out.println("해당 번호의 학생이 없습니다!");
      return;
    }

    s.setBirth(this.prompt.inputInt("생년월일(%d)? ", s.getBirth()));
    s.setName(this.prompt.inputString("이름(%s)? ", s.getName()));
    s.setGrade(this.prompt.inputInt("학년(%d)? ", s.getGrade()));
    s.setKoreanScore(this.prompt.inputInt("국어점수(%d)? ", s.getKoreanScore()));
    s.setEnglishScore(this.prompt.inputInt("영어점수(%d)? ", s.getEnglishScore()));
    s.setMathScore(this.prompt.inputInt("수학점수(%d)? ", s.getMathScore()));
    s.setStatus(inputStatus(s.getStatus(), "update"));
    s.setGender(inputGender(s.getGender()));
    s.setScoreAvg((float) (s.getKoreanScore() + s.getEnglishScore() + s.getMathScore()) / 3);
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
    if (!this.list.remove(new Student(this.prompt.inputInt("번호? ")))) {
      System.out.println("해당 번호의 학생이 없습니다!");
    }
  }

  private Student findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Student s = (Student) this.list.get(i);
      if (s.getNo() == no) {
        return s;
      }
    }
    return null;
  }
}
