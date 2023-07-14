package personal.util;

import java.util.ArrayList;

public class ThreadPool implements ResourcePool<ManagedThread> {

  ArrayList<ManagedThread> list = new ArrayList<>();

  @Override
  public ManagedThread getResource() {
    ManagedThread t = null;

    if (list.size() == 0) {
      t = new ManagedThread(this);
      System.out.printf("새 스레드 생성: %d\n", t.no);
      t.start();

      // 위에서 생성한 스레드가 바로 실행될 수 있도록,
      // main 스레드의 CPU 사용권을 반납할 기회를 만들기 위해 sleep() 실행
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      return t;
    }

    t = list.remove(0);
    System.out.printf("기존 스레드 리턴: %d\n", t.no);
    return t;
  }

  @Override
  public void returnResource(ManagedThread resource) {
    list.add(resource);
    System.out.printf("스레드 반납: %d\n", resource.no);
  }

}
