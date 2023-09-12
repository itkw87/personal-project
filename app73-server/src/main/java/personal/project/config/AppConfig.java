package personal.project.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import personal.project.dao.FreeBoardDao;
import personal.project.dao.MemberDao;
import personal.project.dao.ParticipantDao;
import personal.project.service.DefaultFreeBoardService;
import personal.project.service.DefaultMemberService;
import personal.project.service.FreeBoardService;
import personal.project.service.MemberService;
import personal.util.TransactionProxyBuilder;

import javax.sql.DataSource;

// Application을 실행하는데 필요한 객체를 설정하는 일을 한다.
@ComponentScan(basePackages = {
        "personal.project.dao",
        "personal.project.controller",
        "personal.project.service"})
@PropertySource({"classpath:personal/project/config/ncloud/jdbc.properties"})
@MapperScan("personal.project.dao") // Mybatis가 자동으로 생성할 DAO 객체의 인터페이스 패키지 지정
public class AppConfig {

  public AppConfig() {
    System.out.println("AppConfig() 호출됨!");
  }

  // Mybatis 객체 준비
  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext appCtx) throws Exception {
    System.out.println("AppConfig.sqlSessionFactory() 호출됨!");

    // Mybatis에서 Log4j 2.x 버전을 사용하도록 활성화시킨다.
    // 활성화시키지 않으면 Mybatis에서 로그를 출력하지 않는다.
    org.apache.ibatis.logging.LogFactory.useLog4J2Logging();

    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setTypeAliasesPackage("personal.project.vo");
    factoryBean.setMapperLocations(appCtx.getResources("classpath:personal/project/dao/mysql/*Dao.xml"));
    return factoryBean.getObject();
  }

  @Bean
  public DataSource dataSource(
          @Value("${jdbc.driver}") String driver,
          @Value("${jdbc.url}") String url,
          @Value("${jdbc.username}") String username,
          @Value("${jdbc.password}") String password) {
    System.out.println("AppConfig.dataSource() 호출됨!");

    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(driver);
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);

    return ds;
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    System.out.println("AppConfig.transactionManager() 호출됨!");
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public TransactionProxyBuilder txProxyBuilder(PlatformTransactionManager txManager) {
    // 주어진 객체에 트랜잭션 다루는 기능을 덧붙여 새로운 객체를 만드는 일을 한다.
    return new TransactionProxyBuilder(txManager);
  }

  @Bean
  public FreeBoardService freeBoardService(TransactionProxyBuilder txProxyBuilder, FreeBoardDao freeBoardDao) {
    // 서비스 객체 + 트랜잭션 다루는 기능을 하는 FreeBoardService구현체 생성 => 리턴
    return (FreeBoardService) txProxyBuilder.build(new DefaultFreeBoardService(freeBoardDao));
  }

  @Bean
  public MemberService MemberService(TransactionProxyBuilder txProxyBuilder, MemberDao memberDao, ParticipantDao participantDao) {
    // 서비스 객체 + 트랜잭션 다루는 기능을 하는 MemberService구현체 생성 => 리턴
    return (MemberService) txProxyBuilder.build(new DefaultMemberService(memberDao, participantDao));
  }
}
