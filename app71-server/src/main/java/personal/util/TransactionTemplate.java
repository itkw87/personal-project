package personal.util;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionTemplate {
  PlatformTransactionManager txManager;

  public TransactionTemplate(PlatformTransactionManager txManger) {
    this.txManager = txManger;
  }

  public <T> T execute(TransactionCallback<T> cb) throws Exception {
    // 파라미터로 받은 객체를 트랜잭션 상태에서 수행한다.
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setName("tx1");
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus status = txManager.getTransaction(def);

    try {
      T rv = cb.doInTransaction(status);
      txManager.commit(status);
      return rv;
    } catch (Exception e) {
      txManager.rollback(status);
      throw e;
    }
  }
}
