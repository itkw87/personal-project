package personal.project.listener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import personal.project.dao.*;
import personal.project.config.NcpConfig;
import personal.project.service.NcpObjectStorageService;
import personal.util.SqlSessionFactoryProxy;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.text.SimpleDateFormat;

// 웹애플리케이션 실행에 필요한 설정이나 객체를 준비한다.
//
//@WebListener
public class ContextLoaderListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {


  }
}
