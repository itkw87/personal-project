package personal.project.controller;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.BoardDao;
import personal.project.util.ActionListener;
import personal.project.util.BreadcrumbPrompt;
import personal.project.util.Component;
import personal.project.vo.Board;
import personal.project.vo.Member;

@Component(value = "/board/add")
public class BoardAddListener implements ActionListener {
  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardAddListener(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }


  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Board board = new Board();
    board.setTitle(prompt.inputString("제목? "));
    board.setContent(prompt.inputString("내용? "));
    board.setWriter((Member) prompt.getAttribute("loginUser"));
    board.setCategory(Integer.parseInt((String) prompt.getAttribute("category")));

    try {
      boardDao.insert(board);
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }


  }
}
