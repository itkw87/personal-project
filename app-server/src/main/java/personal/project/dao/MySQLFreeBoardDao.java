package personal.project.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.vo.FreeBoard;

import java.util.List;

public class MySQLFreeBoardDao implements FreeBoardDao {
  SqlSessionFactory sqlSessionFactory;

  public MySQLFreeBoardDao(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(FreeBoard freeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("personal.project.dao.FreeBoardDao.insert", freeBoard);

  }

  @Override
  public List<FreeBoard> findAll(FreeBoard FreeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectList("personal.project.dao.FreeBoardDao.findAll", FreeBoard);
  }


  @Override
  public FreeBoard findBy(FreeBoard FreeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectOne("personal.project.dao.FreeBoardDao.findBy", FreeBoard);
  }

  @Override
  public int update(FreeBoard FreeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("personal.project.dao.FreeBoardDao.update", FreeBoard);
  }

  @Override
  public int updateCount(FreeBoard FreeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("personal.project.dao.FreeBoardDao.updateCount", FreeBoard);
  }

  @Override
  public int delete(FreeBoard FreeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("personal.project.dao.FreeBoardDao.delete", FreeBoard);
  }
}
