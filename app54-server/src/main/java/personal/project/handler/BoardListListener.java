package personal.project.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import personal.project.dao.BoardDao;
import personal.project.util.ActionListener;
import personal.project.util.BreadcrumbPrompt;
import personal.project.util.Component;
import personal.project.vo.Board;

@Component(value = "/board/list")
public class BoardListListener implements ActionListener {

  int category;
  BoardDao boardDao;
  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  public BoardListListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("---------------------------------------");
    prompt.println("번호, 제목, 작성자, 조회수, 등록일");
    prompt.println("---------------------------------------");

    int category = Integer.parseInt((String) prompt.getAttribute("category"));
    List<Board> list = boardDao.findAll(category);

    for (Board board : list) {
      prompt.printf("%d, %s, %s, %d, %s\n", board.getNo(), board.getTitle(),
          board.getWriter().getName(), board.getViewCount(),
          dateFormatter.format(board.getCreatedDate()));
    }
  }

}
