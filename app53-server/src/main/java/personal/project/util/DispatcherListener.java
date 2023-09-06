package personal.project.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import personal.project.dao.BoardDao;
import personal.project.dao.MemberDao;
import personal.project.dao.MySQLBoardDao;
import personal.project.dao.MySQLMemberDao;
import personal.project.controller.BoardAddListener;
import personal.project.controller.BoardDeleteListener;
import personal.project.controller.BoardDetailListener;
import personal.project.controller.BoardListListener;
import personal.project.controller.BoardUpdateListener;
import personal.project.controller.LoginListener;
import personal.project.controller.MemberAddListener;
import personal.project.controller.MemberDeleteListener;
import personal.project.controller.MemberDetailListener;
import personal.project.controller.MemberListListener;
import personal.project.controller.MemberUpdateListener;

public class DispatcherListener implements ActionListener {
  private final int BOARD = 1;
  private final int FREE_BOARD = 2;

  // 객체 보관소
  Map<String, Object> beanContainer = new HashMap<>();

  public DispatcherListener() throws Exception {

    // Mybatis 준비
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("config/mybatis-config.xml")));
    beanContainer.put("sqlSessionFactory", sqlSessionFactory);

    // DAO 준비
    MemberDao memberDao = new MySQLMemberDao(sqlSessionFactory);
    BoardDao boardDao = new MySQLBoardDao(sqlSessionFactory);
    beanContainer.put("memberDao", memberDao);
    beanContainer.put("boardDao", boardDao);

    // Listener 준비
    beanContainer.put("login", new LoginListener(memberDao));

    beanContainer.put("member/add", new MemberAddListener(memberDao, sqlSessionFactory));
    beanContainer.put("member/list", new MemberListListener(memberDao));
    beanContainer.put("member/detail", new MemberDetailListener(memberDao));
    beanContainer.put("member/update", new MemberUpdateListener(memberDao, sqlSessionFactory));
    beanContainer.put("member/delete", new MemberDeleteListener(memberDao, sqlSessionFactory));

    beanContainer.put("board/add", new BoardAddListener(BOARD, boardDao, sqlSessionFactory));
    beanContainer.put("board/list", new BoardListListener(BOARD, boardDao));
    beanContainer.put("board/detail", new BoardDetailListener(BOARD, boardDao, sqlSessionFactory));
    beanContainer.put("board/update", new BoardUpdateListener(BOARD, boardDao, sqlSessionFactory));
    beanContainer.put("board/delete", new BoardDeleteListener(BOARD, boardDao, sqlSessionFactory));

    beanContainer.put("freeBoard/add",
        new BoardAddListener(FREE_BOARD, boardDao, sqlSessionFactory));
    beanContainer.put("freeBoard/list", new BoardListListener(FREE_BOARD, boardDao));
    beanContainer.put("freeBoard/detail",
        new BoardDetailListener(FREE_BOARD, boardDao, sqlSessionFactory));
    beanContainer.put("freeBoard/update",
        new BoardUpdateListener(FREE_BOARD, boardDao, sqlSessionFactory));
    beanContainer.put("freeBoard/delete",
        new BoardDeleteListener(FREE_BOARD, boardDao, sqlSessionFactory));
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    ActionListener listener = (ActionListener) beanContainer.get(prompt.getAttribute("menuPath"));
    if (listener == null) {
      throw new RuntimeException("해당 요청을 처리할 수 없습니다.");
    }
    listener.service(prompt);
  }

  public Object getBean(String name) {
    return beanContainer.get(name);
  }
}
