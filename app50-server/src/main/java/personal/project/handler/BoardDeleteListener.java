package personal.project.controller;

import java.io.IOException;
import personal.project.dao.BoardDao;
import personal.project.vo.Board;
import personal.project.vo.Member;
import personal.util.ActionListener;
import personal.util.BreadcrumbPrompt;

public class BoardDeleteListener implements ActionListener {

  BoardDao boardDao;

  public BoardDeleteListener(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {

    Board b = new Board();
    b.setNo(prompt.inputInt("번호? "));
    b.setWriter((Member) prompt.getAttribute("loginUser"));

    if (boardDao.delete(b) == 0) {
      prompt.println("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
    } else {
      prompt.println("삭제했습니다.");
    }
  }
}
