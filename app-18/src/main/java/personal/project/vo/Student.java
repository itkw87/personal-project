package personal.project.vo;

public class Student {

  private static int userId = 1;

  public static final boolean ENROLLMENT = true;
  public static final boolean DROPOUT = false;
  public static final char MALE = 'M';
  public static final char FEMALE = 'W';

  private int no;
  private int birth;
  private String name;
  private int grade;
  private int koreanScore;
  private int englishScore;
  private int mathScore;
  private boolean status;
  private char gender;
  private float scoreAvg;

  public Student() {
    this.no = userId++;
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass()) {
      return false;
    }

    Student s = (Student) obj;

    if (this.getNo() != s.getNo()) {
      return false;
    }

    // if (this.getBirth() != s.getBirth()) {
    // return false;
    // }
    //
    // if (this.getName() != null && !this.getName().equals(s.getName())) {
    // return false;
    // }
    //
    // if (this.getGrade() != s.getGrade()) {
    // return false;
    // }
    //
    // if (this.getKoreanScore() != s.getKoreanScore()) {
    // return false;
    // }
    //
    // if (this.getEnglishScore() != s.getEnglishScore()) {
    // return false;
    // }
    //
    // if (this.getMathScore() != s.getMathScore()) {
    // return false;
    // }
    //
    // if (this.getStatus() != s.getStatus()) {
    // return false;
    // }
    //
    // if (this.getGender() != s.getGender()) {
    // return false;
    // }
    //
    // if (this.getScoreAvg() != s.getScoreAvg()) {
    // return false;
    // }

    return true;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public int getBirth() {
    return birth;
  }

  public void setBirth(int birth) {
    this.birth = birth;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public int getKoreanScore() {
    return koreanScore;
  }

  public void setKoreanScore(int koreanScore) {
    this.koreanScore = koreanScore;
  }

  public int getEnglishScore() {
    return englishScore;
  }

  public void setEnglishScore(int englishScore) {
    this.englishScore = englishScore;
  }

  public int getMathScore() {
    return mathScore;
  }

  public void setMathScore(int mathScore) {
    this.mathScore = mathScore;
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public char getGender() {
    return gender;
  }

  public void setGender(char gender) {
    this.gender = gender;
  }

  public float getScoreAvg() {
    return scoreAvg;
  }

  public void setScoreAvg(float scoreAvg) {
    this.scoreAvg = scoreAvg;
  }


}
