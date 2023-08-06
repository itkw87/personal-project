package personal.project.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import personal.project.config.AppConfig;

// IoC 컨테이너 = Bean 컨테이너
// - 자바 설정 클래스(예: AppConfig)에서 @Bean 애노테이션이 붙은 메서드를 찾아 호출하고,
// 그 리턴 값을 컨테이너에 보관한다.
// - 자바 설정 클래스(예: AppConfig)에서 @ComponentScan 애노테이션을 찾아서 패키지 정보를 알아낸다.
// 패키지에 소속된 모든 클래스에 대해 인스턴스를 생성하여 컨테이너에 보관한다.
public class ApplicationContext {
  // 객체 보관소
  Map<String, Object> beanContainer = new HashMap<>();

  public ApplicationContext(Class<?> configClass) throws Exception {
    processBeanAnnotation(configClass);


    ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
    if (componentScan != null) {
      processComponentScanAnnotation(componentScan);
    }
  }


  private void processBeanAnnotation(Class<?> configClass) throws Exception {
    System.out.println("@Bean ------------------------------");

    // 클래스의 기본 생성자를 알아낸다.
    Constructor<?> constructor = configClass.getConstructor();

    // 기본 생성자를 이용하여 객체를 생성한다.
    // 객체를 생성하는 이유는 리플렉션 API의 invoke() 를 사용하여
    // 메서드를 호출해야하는데 호출하려는 메서드가 인스턴스 메서드인 경우 인스턴스를 넘겨줘야하기 때문임.
    Object obj = constructor.newInstance();

    // 해당 클래스에 정의된 메서드 목록만 가져온다.
    Method[] methods = configClass.getDeclaredMethods();
    for (Method m : methods) {

      // 메서드의 리턴 타입이 없다면 무시한다.
      if (m.getReturnType() == void.class) {
        continue;
      }

      // @Bean 애노테이션이 붙지 않은 메서드라면 무시한다.
      Bean beanAnnotation = m.getAnnotation(Bean.class);
      if (beanAnnotation == null) {
        continue;
      }

      // 메서드 중에서 리턴 값이 있는 메서드를 호출한다.
      // 즉 오직 값을 리턴하는 메서드만 호출한다.
      Object returnValue = m.invoke(obj);

      // 메서드가 리턴한 값을 컨테이너에 저장한다.
      if (beanAnnotation.value().length() > 0) {
        // bean애노테이션의 value프로퍼티에 값이 지정되어 있으면 그 이름으로 객체를 저장
        beanContainer.put(beanAnnotation.value(), returnValue);
      } else {
        // 애노테이션에 설정된 이름이 없다면 메서드 이름을 사용하여 객체를 저장한다.
        beanContainer.put(m.getName(), returnValue);
      }
      System.out.printf("%s 객체 생성 후 BeanContainer에 저장\n", m.getName());
    }
  }


  private void processComponentScanAnnotation(ComponentScan componentScan) throws Exception {
    for (String basePackage : componentScan.basePackages()) {
      System.out.println(basePackage + "-----------------------------------");
      createBeans(basePackage);
    }
  }


  private void createBeans(String basePackage) throws Exception {
    // 패키지 이름에 해당하는 디렉토리 정보를 알아낸다.
    // 1) 패키지 이름을 파일 경로로 변환한다(. --> /)
    String packagePath = basePackage.replaceAll("[.]", "/");

    // 2) 클래스 경로(CLASSPATH)를 관리하는 객체를 준비한다.
    ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

    // 3) 클래스로더에게 패키지 경로에 해당하는 디렉토리 정보를 읽을 수 있는 도구를 달라고 요구한다.
    InputStream dirInputStream = systemClassLoader.getResourceAsStream(packagePath);
    if (dirInputStream == null) {
      return;
    }

    // 4) 패키지 디렉토리 안에 들어있는 파일 이름이나 하위 디렉토리 이름을 읽을 도구를 준비한다.


    // 캐릭터스트림(어뎁터역할 스트림(바이트스트림))
    BufferedReader dirReader = new BufferedReader(new InputStreamReader(dirInputStream));

    // 5) 디렉토리 리더를 통해 해당 디렉토리 정보를 읽을 수 있는 도구를 달라고 요구한다.
    // .class 파일을 로딩하여 Class 객체를 준비한다.

    // ==> 전통적인 컬렉션 데이터 가공 방식
    // - 한 줄씩 읽으면 된다.
    // Set<Class<?>> classes = new HashSet<>();
    // String filename = null;
    // while ((filename = dirReader.readLine()) != null) {
    //
    // // 파일 확장자가 .class 로 끝나는 파일만 처리한다.
    // if (filename.endsWith(".class")) {
    //
    // // 패키지 이름과 클래스 이름(.class 확장자를 뺀 이름)을 합쳐서
    // // Fully_Qualified class name을 만든 다음에
    // // Class.forName()을 사용하여 클래스를 메모리(Method Area)에 로딩한다.
    // Class<?> clazz = Class.forName(basePackage + "." + filename.replace(".class", ""));
    // System.out.printf("%s 클래스 메모리에 로딩완료\n", clazz.getName());
    //
    //
    // // 로딩한 클래스 정보를 Set 컬렉션에 담는다.
    // classes.add(clazz);
    // }
    // }

    // ==> 스트림 프로그래밍 기법을 이용하여 컬렉션 데이터를 가공하는 방식
    // Set<Class<?>> classes = dirReader.lines() // 오리지널 문자 단위 스트림을 줄 단위로 문자열로 나열한 스트림으로 변환
    // .filter(new Predicate<String>() { // 문자열 스트림의 값(파일이름)들을 filter로 전달하여 test의 인자로 전달하여 검사
    // @Override
    // public boolean test(String filename) {
    // // 이 필터는 파일의 확장자가 .class로 끝나는 경우에만 처리한다.
    // return filename.endsWith(".class");
    // } // map() 스트림의 => 각 요소에 대해 Function구현체를 사용하여 변환할 때 사용
    // }).map(new Function<String, Class<?>>() { // 파일 이름을 받아서 Class 정보를 리턴하는 플러그인 장착한다.
    // @Override
    // public Class<?> apply(String filename) {
    // try {
    // // Class.forName => 클래스를 메모리에 로딩 => 메서드를 호출하기 위해서는 클래스 정보가
    // // 먼저 메모리에 로딩되어야 함.
    // return Class.forName(basePackage + "." + filename.replace(".class", ""));
    // } catch (Exception e) {
    // // 클래스 로딩하다가 예외가 발생하면 무시한다.
    // }
    // return null;
    //
    // }
    // }).collect(Collectors.toSet()); // 스트림을 수행하여 최종 결과물은 Set 컬렉션에 담아서 리턴하라고 명령한다.


    // ==> 스트림 프로그래밍 방식(+람다)
    Set<Class<?>> classes = dirReader.lines() // 오리지널 문자 단위 스트림을 줄 단위로 문자열로 나열한 스트림으로 변환
        .filter((filename) -> filename.endsWith(".class")).map((filename) -> {
          try {
            return Class.forName(basePackage + "." + filename.replace(".class", ""));
          } catch (Exception e) {
            return null;
          }
        }).collect(Collectors.toSet()); // 스트림을 수행하여 최종 결과물은 Set 컬렉션에 담아서 리턴하라고 명령한다.


    // 6) 로딩된 클래스 정보를 활용하여 객체를 생성한다.
    for (Class<?> clazz : classes) {
      if (clazz.isEnum() || clazz.isInterface() || clazz.isLocalClass() || clazz.isMemberClass()) {
        // 패키지 멤버 클래스가 아닌 경우 객체 생성 대상에서 제외한다.
        continue;
      }

      Component compAnno = clazz.getAnnotation(Component.class);
      if (compAnno == null) {
        // @Component 애노테이션이 붙지 않은 클래스는 객체 생성 대상에서 제외한다.
        continue;
      }

      System.out.println(Arrays.toString(clazz.getConstructors()));

      // - 클래스 정보를 가지고 클래스의 생성자를 알아낸다.
      // @Component가 달린 클래스의 생성자는 매개변수가 1개이므로 [0]번째만 저장
      Constructor<?> constructor = clazz.getConstructors()[0];

      // - 생성자의 파라미터 정보를 알아낸다.
      Parameter[] params = constructor.getParameters();

      // - 생성자를 파라미터를 가지고 호출 할 때 넘겨 줄 아규먼트를 준비한다.
      Object[] args = prepareArguments(params);

      // - 준비한 아규먼트를 가지고 생성자를 통한 객체를 생성한다.
      Object obj = constructor.newInstance(args);

      // - 생성된 객체를 컨테이너에 저장한다.
      if (compAnno.value().length() > 0) {
        // @Component 애노테이션에 객체 이름이 지정되어 있다면 그 이름으로 객체를 저장한다.
        beanContainer.put(compAnno.value(), obj);
      } else {
        // 그렇지 않다면, 클래스 이름으로 객체를 저장한다.
        beanContainer.put(clazz.getSimpleName(), obj);
      }

      System.out.printf("%s 객체 생성!\n", clazz.getName());
    }

  }


  private Object[] prepareArguments(Parameter[] params) {
    // 아규먼트를 담을 컬렉션을 준비한다.
    ArrayList<Object> args = new ArrayList<>();

    for (Parameter param : params) {
      // - 파라미터 타입에 해당하는 객체를 컨테이너에서 찾는다.
      args.add(getBean(param.getType()));
    }
    return args.toArray();
  }

  @SuppressWarnings("unchecked")
  public <T> T getBean(Class<T> type) {
    Collection<?> list = beanContainer.values();
    for (Object obj : list) {
      if (type.isInstance(obj)) {
        return (T) obj;
      }
    }
    return null;
  }

  public Object getBean(String name) {
    return beanContainer.get(name);
  }

  private String[] getBeanNames() {
    return beanContainer.keySet().toArray(new String[0]);
  }

  public static void main(String[] args) throws Exception {
    ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);

    System.out.println("생성된 객체 목록: ");
    for (String name : applicationContext.getBeanNames()) {
      System.out.println(name);
    }
  }
}
