package personal.project.listener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import personal.project.dao.*;
import personal.util.NcpConfig;
import personal.util.NcpObjectStorageService;
import personal.util.SqlSessionFactoryProxy;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.text.SimpleDateFormat;

// 웹 애플리케이션이 시작될 때 웹 애플리케이션 실행에 필요한 설정이나 객체를 준비
@WebListener
public class ContextLoaderListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    // 준비한 객체를 담을 수 있도록 보관소를 꺼낸다.
    ServletContext ctx = sce.getServletContext();

    // 서블릿들이 공통으로 사용할 객체를 준비한다.
    try {
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryProxy(
              new SqlSessionFactoryBuilder().build(
                      Resources.getResourceAsStream(ctx.getInitParameter("mybatis-config"))));

      LecBoardDao lecBoardDao = new MySQLLecBoardDao(sqlSessionFactory);
      FreeBoardDao freeBoardDao = new MySQLFreeBoardDao(sqlSessionFactory);
      MemberDao memberDao = new MySQLMemberDao(sqlSessionFactory);
      ParticipantDao participantDao = new MySQLParticipantDao(sqlSessionFactory);
      NcpObjectStorageService ncpObjectStorageService = new NcpObjectStorageService(new NcpConfig());

      // 준비한 객체를 꺼내 쓸 수 있도록 보관소에 저장한다.
      ctx.setAttribute("sqlSessionFactory", sqlSessionFactory);
      ctx.setAttribute("freeBoardDao", freeBoardDao);
      ctx.setAttribute("lecBoardDao", lecBoardDao);
      ctx.setAttribute("memberDao", memberDao);
      ctx.setAttribute("participantDao", participantDao);
      ctx.setAttribute("ncpObjectStorageService", ncpObjectStorageService);
      ctx.setAttribute("simpleDateFormatter", new SimpleDateFormat("yyyy-MM-dd"));

      System.out.println("ContextLoaderListener.contextInitialized() - 공통 객체 준비 완료!");

    } catch (Exception e) {
      System.out.println("ContextLoaderListener.contextInitialized() - 실행 중 오류 발생!");
      e.printStackTrace();
    }

  }
}
