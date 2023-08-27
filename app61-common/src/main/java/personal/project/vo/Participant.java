package personal.project.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Participant {
  private int lectureNo;
  private int memberNo;
  private int partiTypeNo;
  private String partiStatus;

}
