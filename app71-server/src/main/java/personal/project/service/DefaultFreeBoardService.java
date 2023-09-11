package personal.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import personal.project.dao.FreeBoardDao;
import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;
import personal.util.TransactionCallback;
import personal.util.TransactionTemplate;

import java.util.List;

@Service
public class DefaultFreeBoardService implements FreeBoardService {

  FreeBoardDao freeBoardDao;
  TransactionTemplate txTemplate;

  public DefaultFreeBoardService(FreeBoardDao freeBoardDao, PlatformTransactionManager txManager) {
    this.freeBoardDao = freeBoardDao;
    this.txTemplate = new TransactionTemplate(txManager);
  }

  @Override
  public int add(FreeBoard freeBoard) throws Exception {
    return txTemplate.execute(new TransactionCallback<Integer>() {
      @Override
      public Integer doInTransaction(TransactionStatus status) {
        int count = freeBoardDao.insert(freeBoard);
        if (freeBoard.getAttachedFiles().size() > 0) {
          freeBoardDao.insertFiles(freeBoard);
        }
        return count;
      }
    });
  }

  @Override
  public List<FreeBoard> list(FreeBoard freeBoard) throws Exception {
    return freeBoardDao.findAll(freeBoard);
  }

  @Override
  public FreeBoard get(int freeBoardNo) throws Exception {
    return freeBoardDao.findBy(freeBoardNo);
  }

  @Override
  public int update(FreeBoard freeBoard) throws Exception {
    return txTemplate.execute(status -> {
      int count = freeBoardDao.update(freeBoard);
      if (count > 0 && freeBoard.getAttachedFiles().size() > 0) {
        freeBoardDao.insertFiles(freeBoard);
      }
      return count;
    });
  }

  @Override
  public int delete(int freeBoardNo) throws Exception {
    return txTemplate.execute(status -> {
      freeBoardDao.deleteFiles(freeBoardNo);
      return freeBoardDao.delete(freeBoardNo);
    });
  }

  @Override
  public int increaseViewCount(int freeBoardNo) throws Exception {
    return txTemplate.execute(status -> freeBoardDao.updateCount(freeBoardNo));
  }

  @Override
  public AttachedFile getAttachedFile(int fileNo) throws Exception {
    return freeBoardDao.findFileBy(fileNo);
  }

  @Override
  public int deleteAttachedFile(int fileNo) throws Exception {
    return txTemplate.execute((TransactionStatus status) -> freeBoardDao.deleteFile(fileNo));
  }
}
