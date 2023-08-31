package personal.project.dao;

import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;

import java.util.List;

public interface FreeBoardDao {
  void insert(FreeBoard freeBoard);

  List<FreeBoard> findAll(FreeBoard freeBoard);

  FreeBoard findBy(int freeBoardNo);

  int update(FreeBoard freeBoard);

  int updateCount(FreeBoard freeBoard);

  int delete(FreeBoard freeBoard);

  int insertFiles(FreeBoard freeBoard);

  AttachedFile findFileBy(int fileNo);

  int deleteFile(int fileNo);
}
