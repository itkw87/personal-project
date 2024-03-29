package personal.project.controller;

import personal.project.vo.Board;
import personal.util.BreadcrumbPrompt;
import personal.util.Iterator;
import personal.util.List;

public class BoardListListener extends AbstractBoardListener {
  public BoardListListener(List<Board> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("---------------------------------------");
    System.out.println("번호, 제목, 작성자, 조회수, 등록일");
    System.out.println("---------------------------------------");

    Iterator<Board> iterator = list.iterator();

    while (iterator.hasNext()) {
      Board board = iterator.next();
      System.out.printf("%d, %s, %s, %d, %tY-%5$tm-%5$td\n", board.getNo(), board.getTitle(),
          board.getWriter(), board.getViewCount(), board.getCreatedDate());
    }
  }
}
