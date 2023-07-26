package personal.project.handler;

import java.io.IOException;
import personal.project.dao.BoardDao;
import personal.project.vo.Board;
import personal.util.ActionListener;
import personal.util.BreadcrumbPrompt;
import personal.util.DataSource;

public class BoardDetailListener implements ActionListener {

  BoardDao boardDao;
  DataSource ds;

  public BoardDetailListener(BoardDao boardDao, DataSource ds) {
    this.boardDao = boardDao;
    this.ds = ds;
  }


  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    int boardNo = prompt.inputInt("번호? ");

    Board board = boardDao.findBy(boardNo);
    if (board == null) {
      prompt.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    prompt.printf("제목: %s\n", board.getTitle());
    prompt.printf("내용: %s\n", board.getContent());
    prompt.printf("작성자: %s\n", board.getWriter().getName());
    prompt.printf("조회수: %s\n", board.getViewCount());
    prompt.printf("등록일: %tY-%1$tm-%1$td\n", board.getCreatedDate());
    board.setViewCount(board.getViewCount() + 1);

    try {
      boardDao.update(board);
    } catch (Exception e) {
      try {
        ds.getConnection().rollback();
      } catch (Exception e2) {
        throw new RuntimeException(e);
      }
    }

  }


}
