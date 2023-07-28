package personal.project.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.BoardDao;
import personal.project.util.ActionListener;
import personal.project.util.BreadcrumbPrompt;
import personal.project.vo.Board;
import personal.project.vo.Member;

public class BoardAddListener implements ActionListener {
  int category;
  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardAddListener(int category, BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.category = category;
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }


  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Board board = new Board();
    board.setTitle(prompt.inputString("제목? "));
    board.setContent(prompt.inputString("내용? "));
    board.setWriter((Member) prompt.getAttribute("loginUser"));
    board.setCategory(this.category);


    try {
      boardDao.insert(board);
      Thread.sleep(5000);

      boardDao.insert(board);
      Thread.sleep(5000);

      boardDao.insert(board);
      Thread.sleep(5000);

      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }


  }
}
