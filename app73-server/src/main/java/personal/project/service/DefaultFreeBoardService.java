package personal.project.service;

import personal.project.dao.FreeBoardDao;
import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;
import personal.util.Transactional;

import java.util.List;

public class DefaultFreeBoardService implements FreeBoardService {
  FreeBoardDao freeBoardDao;

  public DefaultFreeBoardService(FreeBoardDao freeBoardDao) {
    this.freeBoardDao = freeBoardDao;
  }

  @Transactional// 해당 메서드는 트랜잭션 상태에서 실행하라고 지정
  @Override
  public int add(FreeBoard freeBoard) throws Exception {
    int count = freeBoardDao.insert(freeBoard);
    if (freeBoard.getAttachedFiles().size() > 0) {
      freeBoardDao.insertFiles(freeBoard);
    }
    return count;
  }

  @Override
  public List<FreeBoard> list(FreeBoard freeBoard) throws Exception {
    return freeBoardDao.findAll(freeBoard);
  }

  @Override
  public FreeBoard get(int freeBoardNo) throws Exception {
    return freeBoardDao.findBy(freeBoardNo);
  }

  @Transactional
  @Override
  public int update(FreeBoard freeBoard) throws Exception {
    int count = freeBoardDao.update(freeBoard);
    if (count > 0 && freeBoard.getAttachedFiles().size() > 0) {
      freeBoardDao.insertFiles(freeBoard);
    }
    return count;
  }

  @Transactional
  @Override
  public int delete(int freeBoardNo) throws Exception {
    freeBoardDao.deleteFiles(freeBoardNo);
    return freeBoardDao.delete(freeBoardNo);
  }

  @Transactional
  @Override
  public int increaseViewCount(int freeBoardNo) throws Exception {
    return freeBoardDao.updateCount(freeBoardNo);
  }

  @Override
  public AttachedFile getAttachedFile(int fileNo) throws Exception {
    return freeBoardDao.findFileBy(fileNo);
  }

  @Override
  public int deleteAttachedFile(int fileNo) throws Exception {
    return freeBoardDao.deleteFile(fileNo);
  }
}
