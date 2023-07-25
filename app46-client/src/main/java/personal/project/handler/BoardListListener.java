package personal.project.handler;

import java.text.SimpleDateFormat;
import java.util.List;
import personal.project.dao.BoardDao;
import personal.project.vo.Board;
import personal.util.ActionListener;
import personal.util.BreadcrumbPrompt;

public class BoardListListener implements ActionListener {

  BoardDao boardDao;
  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  public BoardListListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("---------------------------------------");
    System.out.println("번호, 제목, 작성자, 조회수, 등록일");
    System.out.println("---------------------------------------");

    List<Board> list = boardDao.list();

    for (Board board : list) {
      System.out.printf("%d, %s, %s, %d, %s\n", board.getNo(), board.getTitle(), board.getWriter(),
          board.getViewCount(), dateFormatter.format(board.getCreatedDate()));
    }
  }

}
