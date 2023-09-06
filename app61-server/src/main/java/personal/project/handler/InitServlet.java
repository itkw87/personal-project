package personal.project.controller;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import personal.project.dao.*;
import personal.project.util.NcpConfig;
import personal.project.util.NcpObjectStorageService;
import personal.project.util.SqlSessionFactoryProxy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(value = "/init", loadOnStartup = 1)
public class InitServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public static SqlSessionFactory sqlSessionFactory;
  public static LecBoardDao lecBoardDao;
  public static FreeBoardDao freeBoardDao;
  public static MemberDao memberDao;
  public static ParticipantDao participantDao;
  public static NcpObjectStorageService ncpObjectStorageService;

  @Override
  public void init() throws ServletException {
    System.out.println("InitServlet.init() 호출됨!");

    try {
      sqlSessionFactory = new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder()
          .build(Resources.getResourceAsStream("personal/project/config/mybatis-config.xml")));

      lecBoardDao = new MySQLLecBoardDao(sqlSessionFactory);
      freeBoardDao = new MySQLFreeBoardDao(sqlSessionFactory);
      memberDao = new MySQLMemberDao(sqlSessionFactory);
      participantDao = new MySQLParticipantDao(sqlSessionFactory);
      ncpObjectStorageService = new NcpObjectStorageService(new NcpConfig());

    } catch (Exception e) {
      System.out.println("InitServlet.init() 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
