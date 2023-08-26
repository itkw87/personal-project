package personal.project.vo;

import java.sql.Timestamp;
import java.util.List;

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

  private List<AttachedFile> attachedFiles;

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

  public List<AttachedFile> getAttachedFiles() {
    return attachedFiles;
  }

  public void setAttachedFiles(List<AttachedFile> attachedFiles) {
    this.attachedFiles = attachedFiles;
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
            ", attachedFiles=" + attachedFiles +
            '}';
  }
}


