package personal.project.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@ToString
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
  private String memberPhoto;

  private List<Participant> ParticipantList;



}
