package personal.project.dao;

import personal.project.vo.FreeBoard;

import java.util.List;

public interface FreeBoardDao {
  void insert(FreeBoard board);

  List<FreeBoard> findAll(FreeBoard board);

  FreeBoard findBy(FreeBoard board);

  int update(FreeBoard board);

  int updateCount(FreeBoard board);

  int delete(FreeBoard board);
}
