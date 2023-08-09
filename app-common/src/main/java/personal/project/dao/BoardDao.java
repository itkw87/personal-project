package personal.project.dao;

import java.util.List;
import personal.project.vo.Board;

public interface BoardDao {
  void insert(Board board);

  List<Board> findAll(Board board);

  Board findBy(int category, int no);

  int update(Board board);

  int updateCount(Board board);

  int delete(Board board);
}
