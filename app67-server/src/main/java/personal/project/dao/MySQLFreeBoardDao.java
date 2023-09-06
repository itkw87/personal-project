package personal.project.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.vo.AttachedFile;
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
  public List<FreeBoard> findAll(FreeBoard freeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectList("personal.project.dao.FreeBoardDao.findAll", freeBoard);
  }


  @Override
  public FreeBoard findBy(int freeBoardNo) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectOne("personal.project.dao.FreeBoardDao.findBy", freeBoardNo);
  }

  @Override
  public int update(FreeBoard freeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("personal.project.dao.FreeBoardDao.update", freeBoard);
  }

  @Override
  public int updateCount(FreeBoard freeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("personal.project.dao.FreeBoardDao.updateCount", freeBoard);
  }

  @Override
  public int delete(FreeBoard freeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("personal.project.dao.FreeBoardDao.delete", freeBoard);
  }

  @Override
  public int insertFiles(FreeBoard freeBoard) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.insert("personal.project.dao.FreeBoardDao.insertFiles", freeBoard);
  }

  @Override
  public AttachedFile findFileBy(int fileNo) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectOne("personal.project.dao.FreeBoardDao.findFileBy", fileNo);
  }

  @Override
  public int deleteFile(int fileNo) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("personal.project.dao.FreeBoardDao.deleteFile", fileNo);
  }

  @Override
  public int deleteFiles(int freeBoardNo) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("personal.project.dao.FreeBoardDao.deleteFiles", freeBoardNo);
  }
}
