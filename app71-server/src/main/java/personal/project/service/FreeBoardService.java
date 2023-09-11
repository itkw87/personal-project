package personal.project.service;

import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;

import java.util.List;

// 비즈니스 로직을 수행하는 객체의 사용 규칙을 정의
// 메서드 이름은 업무와 관련된 이름을 사용할 것
public interface FreeBoardService {
  int add(FreeBoard freeBoard) throws Exception;
  List<FreeBoard> list(FreeBoard freeBoard) throws Exception;
  FreeBoard get(int freeBoardNo) throws Exception;
  int update(FreeBoard freeBoard) throws Exception;
  int delete(int freeBoardNo) throws Exception;
  int increaseViewCount(int freeBoardNo) throws Exception;


  AttachedFile getAttachedFile(int fileNo) throws Exception;
  int deleteAttachedFile(int fileNo) throws Exception;
}
