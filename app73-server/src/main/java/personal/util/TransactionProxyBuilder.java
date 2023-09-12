package personal.util;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionProxyBuilder {

  TransactionTemplate txTemplate;

  public TransactionProxyBuilder(PlatformTransactionManager txManager) {
    this.txTemplate = new TransactionTemplate(txManager);
  }

  public Object build(Object originalWorker) {
    // 1) 인터페이스 알아내기
    // => DAO인터페이스를 구현한 구현체들은 현재 오직 단 한개의 인터페이스를 구현하였기 때문에
    //    DAO구현체(클래스)가 구현한 인터페이스 중 0번째 인터페이스에 대한 정보만 가져온다.
    Class<?> clazz = originalWorker.getClass().getInterfaces()[0];

    // 2) 인터페이스 구현체를 만들어 리턴한다.
    return Proxy.newProxyInstance(
            this.getClass().getClassLoader(),
            new Class[]{clazz},
            new InvocationHandler() {
              @Override
              public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 프록시 객체의 메서드를 호출할 때,
                // @Transactional이 붙은 메서드를 호출할 때는 TransactionTemplate으로 처리하고
                // @Transactional이 붙지 않은 메서드는 그냥 오리지널 객체의 메서드를 그대로 호출한다.

                // 1) 프록시 객체의 메서드와 일치하는 오리지널 작업 객체의 메서드를 가져온다.
                Method originalMethod = getOriginalMethod(originalWorker, method);

                // 2) 오리지널 객체의 메서드에서 @Transaction 애노테이션을 추출한다.
                Transactional transactional = originalMethod.getAnnotation(Transactional.class);
                if (transactional != null) {
                  // @Transactional 애노테이션이 붙은 메서드라면 TransactionTemplate을 통해 실행하고
                  return txTemplate.execute(status -> {
                    System.out.printf("%s() - 트랜잭션으로 별도 처리가 필요한 메서드!\n", originalMethod.getName());
                    try {
                      return originalMethod.invoke(originalWorker, args);
                    } catch (Exception e) {
                      throw new RuntimeException(e);
                    }
                  });
                } else {
                  // @Transactional 애노테이션이 안 붙은 메서드라면 그냥 호출한다.
                  System.out.printf("%s() - 트랜잭션이 아니므로 별도처리가 필요없는 메서드 호출!\n", originalMethod.getName());
                  return originalMethod.invoke(originalWorker, args);
                }
              }
            });
  }

  private Method getOriginalMethod(Object originalWorker, Method method) {
    Method[] methods = originalWorker.getClass().getDeclaredMethods();
    for (Method originalMethod : methods) {
      // DAO인터페이스를 구현한 클래스의 메서드 이름과 DAO 인터페이스의 메서드 이름이 같으며,
      // DAO인터페이스를 구현한 클래스의 메서드 파라미터 개수와 DAO 인터페이스의 파라미터개수가 같은 경우
      if (originalMethod.getName().equals(method.getName()) && originalMethod.getParameterCount() == method.getParameterCount()) {
        return originalMethod;
      }
    }
    return null;
  }
}
