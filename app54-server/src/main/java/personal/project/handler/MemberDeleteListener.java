package personal.project.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.dao.MemberDao;
import personal.project.util.BreadcrumbPrompt;
import personal.project.util.Component;

@Component(value = "/member/delete")
public class MemberDeleteListener implements MemberActionListener {
  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;

  public MemberDeleteListener(MemberDao memberDao, SqlSessionFactory sqlSessionFactory) {
    this.memberDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    try {
      if (memberDao.delete(prompt.inputInt("번호? ")) == 0) {
        prompt.println("해당 번호의 회원이 없습니다!");
      }
      prompt.println("삭제했습니다!");
      sqlSessionFactory.openSession(false).commit();
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }

}
