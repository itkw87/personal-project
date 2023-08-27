package personal.project.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;


@Getter
@Setter
@ToString
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
}


