package personal.project.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
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
  private List<AttachedFile> attachedFiles;

}


