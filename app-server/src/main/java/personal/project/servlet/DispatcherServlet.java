package personal.project.servlet;

import org.apache.ibatis.session.SqlSessionFactory;
import personal.project.controller.*;
import personal.project.dao.FreeBoardDao;
import personal.project.dao.MemberDao;
import personal.project.dao.ParticipantDao;
import personal.util.NcpObjectStorageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// DispatcherServlet 클래스는 FrontController로 모든 요청을 받아 각각의 Controller에게 전달하는 역할을 한다.
@WebServlet("/app/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  Map<String, PageController> controllerMap = new HashMap<>();

  @Override
  public void init() throws ServletException {
    MemberDao memberDao = (MemberDao) this.getServletContext().getAttribute("memberDao");
    FreeBoardDao freeBoardDao = (FreeBoardDao) this.getServletContext().getAttribute("freeBoardDao");
    ParticipantDao participantDao = (ParticipantDao) this.getServletContext().getAttribute("participantDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");

    controllerMap.put("/", new HomeController());
    controllerMap.put("/auth/login", new LoginController(memberDao, participantDao));
    controllerMap.put("/auth/logout", new LogoutController());
    controllerMap.put("/member/list", new MemberListController(memberDao));
    controllerMap.put("/member/add", new MemberAddController(memberDao, sqlSessionFactory, ncpObjectStorageService));
    controllerMap.put("/member/detail", new MemberDetailController(memberDao));
    controllerMap.put("/member/update", new MemberUpdateController(memberDao, sqlSessionFactory, ncpObjectStorageService));
    controllerMap.put("/member/delete", new MemberDeleteController(memberDao, sqlSessionFactory));
    controllerMap.put("/freeBoard/list", new FreeBoardListController(freeBoardDao));
    controllerMap.put("/freeBoard/add", new FreeBoardAddController(freeBoardDao, sqlSessionFactory, ncpObjectStorageService));
    controllerMap.put("/freeBoard/detail", new FreeBoardDetailController(freeBoardDao, sqlSessionFactory));
    controllerMap.put("/freeBoard/update", new FreeBoardUpdateController(freeBoardDao, sqlSessionFactory, ncpObjectStorageService));
    controllerMap.put("/freeBoard/delete", new FreeBoardDeleteController(freeBoardDao, sqlSessionFactory));
    controllerMap.put("/freeBoard/fileDelete", new FreeBoardFileDeleteController(freeBoardDao, sqlSessionFactory));

  }


  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String pageControllerPath = request.getPathInfo();

    response.setContentType("text/html;charset=UTF-8");

    // 클라이언트가 요청한 페이지 컨트롤러를 찾는다.
    PageController pageController = controllerMap.get(pageControllerPath);
    if (pageController == null) {
      throw new ServletException("해당 요청을 처리할 수 없습니다!");
    }

    // 페이지 컨트롤러를 실행한다.
    try {

      String viewUrl = pageController.execute(request, response);
      System.out.println("-------------------------------------------------");
      System.out.println(viewUrl);
      if (viewUrl.startsWith("redirect:")) {
        response.sendRedirect(viewUrl.substring(9)); // 예) redirect:/app/board/list
      } else {
        request.getRequestDispatcher(viewUrl).include(request, response);
      }

    } catch (Exception e) {
      // 페이지 컨트롤러 실행 중 오류가 발생했다면, 예외를 던진다.
      throw new ServletException("요청 처리 중 오류 발생!", e);
    }

  }
}
