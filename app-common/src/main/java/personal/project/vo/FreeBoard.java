package personal.project.vo;

import java.sql.Timestamp;

public class FreeBoard {

  private int freeBoardNo;
  private String freeTitle;
  private String freeContent;
  private Member freeWriter;
  private int freeViewCount;
  private Timestamp freeRegDate;
  private Timestamp freeMdfDate;
  private String freeStatus;
  private SearchParam searchParam;

  public int getFreeBoardNo() {
    return freeBoardNo;
  }

  public String getFreeTitle() {
    return freeTitle;
  }

  public String getFreeContent() {
    return freeContent;
  }

  public Member getFreeWriter() {
    return freeWriter;
  }

  public int getFreeViewCount() {
    return freeViewCount;
  }

  public Timestamp getFreeRegDate() {
    return freeRegDate;
  }

  public Timestamp getFreeMdfDate() {
    return freeMdfDate;
  }

  public String getFreeStatus() {
    return freeStatus;
  }

  public void setFreeBoardNo(int freeBoardNo) {
    this.freeBoardNo = freeBoardNo;
  }

  public void setFreeTitle(String freeTitle) {
    this.freeTitle = freeTitle;
  }

  public void setFreeContent(String freeContent) {
    this.freeContent = freeContent;
  }

  public void setFreeWriter(Member freeWriter) {
    this.freeWriter = freeWriter;
  }

  public void setFreeViewCount(int freeViewCount) {
    this.freeViewCount = freeViewCount;
  }

  public void setFreeRegDate(Timestamp freeRegDate) {
    this.freeRegDate = freeRegDate;
  }

  public void setFreeMdfDate(Timestamp freeMdfDate) {
    this.freeMdfDate = freeMdfDate;
  }

  public void setFreeStatus(String freeStatus) {
    this.freeStatus = freeStatus;
  }

  public SearchParam getSearchParam() {
    return searchParam;
  }

  public void setSearchParam(SearchParam searchParam) {
    this.searchParam = searchParam;
  }

  @Override
  public String toString() {
    return "FreeBoardDao{" +
            "freeBoardNo=" + freeBoardNo +
            ", freeTitle='" + freeTitle + '\'' +
            ", freeContent='" + freeContent + '\'' +
            ", freeWriter=" + freeWriter +
            ", freeViewCount=" + freeViewCount +
            ", freeRegDate=" + freeRegDate +
            ", freeMdfDate=" + freeMdfDate +
            ", freeStatus='" + freeStatus + '\'' +
            ", searchParam=" + searchParam +
            '}';
  }
}


