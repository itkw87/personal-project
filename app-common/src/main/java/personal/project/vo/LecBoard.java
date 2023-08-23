package personal.project.vo;

import java.sql.Timestamp;

public class LecBoard {

  private int lecBoardNo;
  private int lectureNo;
  private String lecTitle;
  private String lecContent;
  private Member lecWriter;
  private int lecViewCount;
  private Timestamp lecRegDate;
  private Timestamp lecMdfDate;
  private String lecStatus;

  private SearchParam searchParam;

  public int getLecBoardNo() {
    return lecBoardNo;
  }

  public void setLecBoardNo(int lecBoardNo) {
    this.lecBoardNo = lecBoardNo;
  }

  public int getLectureNo() {
    return lectureNo;
  }

  public void setLectureNo(int lectureNo) {
    this.lectureNo = lectureNo;
  }

  public String getLecTitle() {
    return lecTitle;
  }

  public void setLecTitle(String lecTitle) {
    this.lecTitle = lecTitle;
  }

  public String getLecContent() {
    return lecContent;
  }

  public void setLecContent(String lecContent) {
    this.lecContent = lecContent;
  }

  public Member getLecWriter() {
    return lecWriter;
  }

  public void setLecWriter(Member lecWriter) {
    this.lecWriter = lecWriter;
  }

  public int getLecViewCount() {
    return lecViewCount;
  }

  public void setLecViewCount(int lecViewCount) {
    this.lecViewCount = lecViewCount;
  }

  public Timestamp getLecRegDate() {
    return lecRegDate;
  }

  public void setLecRegDate(Timestamp lecRegDate) {
    this.lecRegDate = lecRegDate;
  }

  public Timestamp getLecMdfDate() {
    return lecMdfDate;
  }

  public void setLecMdfDate(Timestamp lecMdfDate) {
    this.lecMdfDate = lecMdfDate;
  }

  public String getLecStatus() {
    return lecStatus;
  }

  public void setLecStatus(String lecStatus) {
    this.lecStatus = lecStatus;
  }

  public SearchParam getSearchParam() {
    return searchParam;
  }

  public void setSearchParam(SearchParam searchParam) {
    this.searchParam = searchParam;
  }

  @Override
  public String toString() {
    return "LecBoard{" +
            "lecBoardNo=" + lecBoardNo +
            ", lectureNo=" + lectureNo +
            ", lecTitle='" + lecTitle + '\'' +
            ", lecContent='" + lecContent + '\'' +
            ", lecWriter=" + lecWriter +
            ", lecViewCount=" + lecViewCount +
            ", lecRegDate=" + lecRegDate +
            ", lecMdfDate=" + lecMdfDate +
            ", lecStatus='" + lecStatus + '\'' +
            ", searchParam=" + searchParam +
            '}';
  }
}


