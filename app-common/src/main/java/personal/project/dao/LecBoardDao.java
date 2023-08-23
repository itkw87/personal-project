package personal.project.dao;

import personal.project.vo.LecBoard;

import java.util.List;

public interface LecBoardDao {
  void insert(LecBoard board);

  List<LecBoard> findAll(LecBoard board);

  LecBoard findBy(LecBoard board);

  int update(LecBoard board);

  int updateCount(LecBoard board);

  int delete(LecBoard board);
}
