package personal.project.vo;

public class Participant {
  private int lectureNo;
  private int memberNo;
  private int partiTypeNo;
  private String partiStatus;

  public int getLectureNo() {
    return lectureNo;
  }

  public void setLectureNo(int lectureNo) {
    this.lectureNo = lectureNo;
  }

  public int getMemberNo() {
    return memberNo;
  }

  public void setMemberNo(int memberNo) {
    this.memberNo = memberNo;
  }

  public int getPartiTypeNo() {
    return partiTypeNo;
  }

  public void setPartiTypeNo(int partiTypeNo) {
    this.partiTypeNo = partiTypeNo;
  }

  public String getPartiStatus() {
    return partiStatus;
  }

  public void setPartiStatus(String partiStatus) {
    this.partiStatus = partiStatus;
  }

  @Override
  public String toString() {
    return "Participant{" +
            "lectureNo=" + lectureNo +
            ", memberNo=" + memberNo +
            ", partiTypeNo=" + partiTypeNo +
            ", partiStatus='" + partiStatus + '\'' +
            '}';
  }
}
