package personal.project.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import personal.project.vo.Member;
import personal.project.vo.Participant;

import java.util.List;

@Component
public class MySQLParticipantDao implements ParticipantDao {

  SqlSessionFactory sqlSessionFactory;

  public MySQLParticipantDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public List<Participant> findAllParticipant(Member member) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectList("personal.project.dao.ParticipantDao.findAllParticipant", member);
  }


}
