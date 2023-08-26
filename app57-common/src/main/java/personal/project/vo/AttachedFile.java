package personal.project.vo;

public class AttachedFile {
  private int fileNo;
  private int FreeBoardNo;
  private String filePath;
  private String originFileName;
  private String saveFileName;

  public int getFileNo() {
    return fileNo;
  }

  public void setFileNo(int fileNo) {
    this.fileNo = fileNo;
  }

  public int getFreeBoardNo() {
    return FreeBoardNo;
  }

  public void setFreeBoardNo(int freeBoardNo) {
    FreeBoardNo = freeBoardNo;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  public String getOriginFileName() {
    return originFileName;
  }

  public void setOriginFileName(String originFileName) {
    this.originFileName = originFileName;
  }

  public String getSaveFileName() {
    return saveFileName;
  }

  public void setSaveFileName(String saveFileName) {
    this.saveFileName = saveFileName;
  }

  @Override
  public String toString() {
    return "AttachedFile{" +
            "fileNo=" + fileNo +
            ", FreeBoardNo=" + FreeBoardNo +
            ", filePath='" + filePath + '\'' +
            ", originFileName='" + originFileName + '\'' +
            ", saveFileName='" + saveFileName + '\'' +
            '}';
  }
}
