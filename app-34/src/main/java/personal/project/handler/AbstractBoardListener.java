package personal.project.handler;

import java.util.List;
import personal.project.vo.Board;
import personal.util.ActionListener;

public abstract class AbstractBoardListener implements ActionListener {

  public List<Board> list;

  public AbstractBoardListener(List<Board> list) {
    this.list = list;
  }

  protected Board findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Board b = this.list.get(i);
      if (b.getNo() == no) {
        return b;
      }
    }
    return null;
  }

}
