package personal.project.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Member implements Serializable {

  private static final long serialVersionUID = 1L;

  public static int userId = 1;

  public static final boolean ENROLLMENT = true;
  public static final boolean DROPOUT = false;
  public static final char MALE = 'M';
  public static final char FEMALE = 'W';

  private int no;
  private String authority;
  private String birth;
  private int grade;
  private String name;
  private char gender;
  private int koreanScore;
  private int englishScore;
  private int mathScore;
  private float scoreAvg;
  private String email;
  private String password;
  private boolean status;
  private Date createdDate;

  @Override
  public int hashCode() {
    return Objects.hash(no);
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Member other = (Member) obj;
    return no == other.no;
  }


  public int getNo() {
    return no;
  }


  public void setNo(int no) {
    this.no = no;
  }


  public String getAuthority() {
    return authority;
  }


  public void setAuthority(String authority) {
    this.authority = authority;
  }


  public String getBirth() {
    return birth;
  }


  public void setBirth(String birth) {
    this.birth = birth;
  }


  public int getGrade() {
    return grade;
  }


  public void setGrade(int grade) {
    this.grade = grade;
  }


  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public char getGender() {
    return gender;
  }


  public void setGender(char gender) {
    this.gender = gender;
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


  public float getScoreAvg() {
    return scoreAvg;
  }


  public void setScoreAvg(float scoreAvg) {
    this.scoreAvg = scoreAvg;
  }


  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  public boolean getStatus() {
    return status;
  }


  public void setStatus(boolean status) {
    this.status = status;
  }


  public Date getCreatedDate() {
    return createdDate;
  }


  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }


  @Override
  public String toString() {
    return "Member [no=" + no + ", authority=" + authority + ", birth=" + birth + ", grade=" + grade
        + ", name=" + name + ", gender=" + gender + ", koreanScore=" + koreanScore
        + ", englishScore=" + englishScore + ", mathScore=" + mathScore + ", scoreAvg=" + scoreAvg
        + ", email=" + email + ", password=" + password + ", status=" + status + ", createdDate="
        + createdDate + "]";
  }



}
