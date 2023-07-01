package personal.project.vo;

import java.io.Serializable;

public class Student implements Serializable, CsvObject {

  private static final long serialVersionUID = 1L;

  public static int userId = 1;

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

  public static Student fromCsv(String csv) {
    String[] values = csv.split(",");

    Student student = new Student();
    student.setNo(Integer.parseInt(values[0]));
    student.setBirth(Integer.parseInt(values[1]));
    student.setName(values[2]);
    student.setGrade(Integer.parseInt(values[3]));
    student.setKoreanScore(Integer.parseInt(values[4]));
    student.setEnglishScore(Integer.parseInt(values[5]));
    student.setMathScore(Integer.parseInt(values[6]));
    student.setStatus("true".equals(values[7]) ? true : false);
    student.setGender(values[8].charAt(0));
    student.setScoreAvg(Float.parseFloat(values[9]));

    if (Student.userId <= student.getNo()) {
      Student.userId = student.getNo() + 1;
    }

    return student;
  }


  @Override
  public String toCsvString() {
    return String.format("%d,%d,%s,%d,%d,%d,%d,%b,%c,%f", this.getNo(), this.getBirth(),
        this.getName(), this.getGrade(), this.getKoreanScore(), this.getEnglishScore(),
        this.getMathScore(), this.getStatus(), this.getGender(), this.getScoreAvg());
  }

  // 같은 기능을 수행하는 생성자가 위에 있다.
  // 다만 파라미터가 다를 뿐이다.
  // => "생성자 오버로딩(overloading)"
  public Student(int no) {
    this.no = no;
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
