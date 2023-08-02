package personal.project.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import personal.project.util.Bean;
import personal.project.util.ComponentScan;
import personal.project.util.SqlSessionFactoryProxy;

// Application을 실행하는데 필요한 객체를 설정하는 일을 한다.
@ComponentScan(basePackages = {"personal.project.dao", "personal.project.handler"})

public class AppConfig {

  // ApplicationContext에서 @Bean 붙은 메서드를 호출하여 객체를 반환 받을 것임. 따라서 메서드명을 명사형태로 하였음.
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    return new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("config/mybatis-config.xml")));
  }
}
