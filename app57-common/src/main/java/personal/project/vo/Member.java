package personal.project.vo;

import java.sql.Date;
import java.util.List;

public class Member {

  private int memberNo;
  private String memberCode;
  private String memberId;
  private String memberPwd;
  private String memberName;
  private String memberEmail;
  private String memberGender;
  private String memberTel;
  private String memberZipcode;
  private String memberAddr;
  private String memberDetailAddr;
  private Date memberDate;
  private String memberStatus;

  private List<Participant> ParticipantList;

  public int getMemberNo() {
    return memberNo;
  }

  public void setMemberNo(int memberNo) {
    this.memberNo = memberNo;
  }

  public String getMemberCode() {
    return memberCode;
  }

  public void setMemberCode(String memberCode) {
    this.memberCode = memberCode;
  }

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getMemberPwd() {
    return memberPwd;
  }

  public void setMemberPwd(String memberPwd) {
    this.memberPwd = memberPwd;
  }

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

  public String getMemberEmail() {
    return memberEmail;
  }

  public void setMemberEmail(String memberEmail) {
    this.memberEmail = memberEmail;
  }

  public String getMemberGender() {
    return memberGender;
  }

  public void setMemberGender(String memberGender) {
    this.memberGender = memberGender;
  }

  public String getMemberTel() {
    return memberTel;
  }

  public void setMemberTel(String memberTel) {
    this.memberTel = memberTel;
  }

  public String getMemberZipcode() {
    return memberZipcode;
  }

  public void setMemberZipcode(String memberZipcode) {
    this.memberZipcode = memberZipcode;
  }

  public String getMemberAddr() {
    return memberAddr;
  }

  public void setMemberAddr(String memberAddr) {
    this.memberAddr = memberAddr;
  }

  public String getMemberDetailAddr() {
    return memberDetailAddr;
  }

  public void setMemberDetailAddr(String memberDetailAddr) {
    this.memberDetailAddr = memberDetailAddr;
  }

  public Date getMemberDate() {
    return memberDate;
  }

  public void setMemberDate(Date memberDate) {
    this.memberDate = memberDate;
  }

  public String getMemberStatus() {
    return memberStatus;
  }

  public void setMemberStatus(String memberStatus) {
    this.memberStatus = memberStatus;
  }

  public List<Participant> getParticipantList() {
    return ParticipantList;
  }

  public void setParticipantList(List<Participant> participantList) {
    ParticipantList = participantList;
  }

  @Override
  public String toString() {
    return "Member{" +
            "memberNo=" + memberNo +
            ", memberCode='" + memberCode + '\'' +
            ", memberId='" + memberId + '\'' +
            ", memberPwd='" + memberPwd + '\'' +
            ", memberName='" + memberName + '\'' +
            ", memberEmail='" + memberEmail + '\'' +
            ", memberGender='" + memberGender + '\'' +
            ", memberTel='" + memberTel + '\'' +
            ", memberZipcode='" + memberZipcode + '\'' +
            ", memberAddr='" + memberAddr + '\'' +
            ", memberDetailAddr='" + memberDetailAddr + '\'' +
            ", memberDate=" + memberDate +
            ", memberStatus='" + memberStatus + '\'' +
            ", ParticipantList=" + ParticipantList +
            '}';
  }
}
