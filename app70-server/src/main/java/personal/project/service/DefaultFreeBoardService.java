package personal.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import personal.project.dao.FreeBoardDao;
import personal.project.vo.AttachedFile;
import personal.project.vo.FreeBoard;

import java.util.List;

@Service
public class DefaultFreeBoardService implements FreeBoardService {

  FreeBoardDao freeBoardDao;
  PlatformTransactionManager txManager;

  public DefaultFreeBoardService(FreeBoardDao freeBoardDao, PlatformTransactionManager txManager) {
    this.freeBoardDao = freeBoardDao;
    this.txManager = txManager;
  }

  @Override
  public int add(FreeBoard freeBoard) throws Exception {
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = freeBoardDao.insert(freeBoard);
      if (freeBoard.getAttachedFiles().size() > 0) {
        freeBoardDao.insertFiles(freeBoard);
      }
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
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
    DefaultTransactionDefinition def = new DefaultTransactionAttribute();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      int count = freeBoardDao.update(freeBoard);
      if (count > 0 && freeBoard.getAttachedFiles().size() > 0) {
        freeBoardDao.insertFiles(freeBoard);
      }
      txManager.commit(status);
      return count;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }


    @Override
    public int delete(int freeBoardNo) throws Exception {
      DefaultTransactionDefinition def = new DefaultTransactionDefinition();
      def.setName("tx1");
      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus status = txManager.getTransaction(def);

      try {
        freeBoardDao.deleteFiles(freeBoardNo);
        int count = freeBoardDao.delete(freeBoardNo);
        txManager.commit(status);
        return count;
      } catch (Exception e) {
        txManager.rollback(status);
        throw e;
      }
    }

    @Override
    public int increaseViewCount(int freeBoardNo) throws Exception {
      DefaultTransactionDefinition def = new DefaultTransactionDefinition();
      def.setName("tx1");
      def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus status = txManager.getTransaction(def);

      try {
        int count = freeBoardDao.updateCount(freeBoardNo);
        txManager.commit(status);
        return count;
      } catch (Exception e) {
        txManager.rollback(status);
        throw e;
      }
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
