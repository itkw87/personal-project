package personal.project.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import personal.util.SqlSessionFactoryProxy;


@ComponentScan(basePackages = {
        "personal.project.dao",
        "personal.project.controller",
        "personal.project.service"})
public class AppConfig {

  public AppConfig() {
    System.out.println("AppConfig() 호출됨!");
  }

  // Mybatis 객체 준비
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    System.out.println("AppConfig.sqlSessionFactory() 호출됨!");
    return new SqlSessionFactoryProxy(
            new SqlSessionFactoryBuilder().build(
                    Resources.getResourceAsStream("personal/project/config/mybatis-config.xml")));
  }
}
