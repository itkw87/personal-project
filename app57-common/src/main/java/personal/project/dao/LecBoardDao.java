package personal.project.dao;

import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;
import personal.project.vo.LecBoard;

import java.util.List;

public interface LecBoardDao {
  void insert(LecBoard lecBoard);

  List<LecBoard> findAll(LecBoard lecBoard);

  LecBoard findBy(LecBoard lecBoard);

  int update(LecBoard lecBoard);

  int updateCount(LecBoard lecBoard);

  int delete(LecBoard board);

}
