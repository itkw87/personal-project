package personal.project.vo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttachedFile {
  private int fileNo;
  private int FreeBoardNo;
  private String filePath;
  private String originFileName;
  private String saveFileName;

}
