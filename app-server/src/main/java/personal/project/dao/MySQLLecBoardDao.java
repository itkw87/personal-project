package personal.project.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.vo.LecBoard;

import java.util.List;

public class MySQLLecBoardDao implements LecBoardDao {
  SqlSessionFactory sqlSessionFactory;

  public MySQLLecBoardDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(LecBoard lecBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("personal.project.dao.LecBoardDao.insert", lecBoard);

  }

  @Override
  public List<LecBoard> findAll(LecBoard lecBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectList("personal.project.dao.LecBoardDao.findAll", lecBoard);
  }


  @Override
  public LecBoard findBy(LecBoard lecBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectOne("personal.project.dao.LecBoardDao.findBy", lecBoard);
  }

  @Override
  public int update(LecBoard lecBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("personal.project.dao.LecBoardDao.update", lecBoard);
  }

  @Override
  public int updateCount(LecBoard lecBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("personal.project.dao.LecBoardDao.updateCount", lecBoard);
  }

  @Override
  public int delete(LecBoard lecBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("personal.project.dao.LecBoardDao.delete", lecBoard);
  }
}
