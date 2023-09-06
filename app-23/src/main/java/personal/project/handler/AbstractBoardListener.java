package personal.project.controller;

import personal.project.vo.Board;
import personal.util.ActionListener;
import personal.util.List;

public abstract class AbstractBoardListener implements ActionListener {

  public List list;

  public AbstractBoardListener(List list) {
    this.list = list;
  }

  protected Board findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Board b = (Board) this.list.get(i);
      if (b.getNo() == no) {
        return b;
      }
    }
    return null;
  }

}
