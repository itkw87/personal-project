package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;
import personal.project.dao.MemberDao;
import personal.project.vo.Member;
import personal.project.service.NcpObjectStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@Component("/member/update")
public class MemberUpdateController implements PageController {

  MemberDao memberDao;
  SqlSessionFactory sqlSessionFactory;
  NcpObjectStorageService ncpObjectStorageService;

  public MemberUpdateController(
          MemberDao memberDao,
          SqlSessionFactory sqlSessionFactory,
          NcpObjectStorageService ncpObjectStorageService) {
    this.memberDao = memberDao;
    this.sqlSessionFactory = sqlSessionFactory;
    this.ncpObjectStorageService = ncpObjectStorageService;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Member member = new Member();
      member.setMemberNo(Integer.parseInt(request.getParameter("memberNo")));
      member.setMemberCode(request.getParameter("memberCode"));
      member.setMemberName(request.getParameter("memberName"));
      member.setMemberEmail(request.getParameter("memberEmail"));
      member.setMemberPwd(request.getParameter("memberPwd"));
      member.setMemberGender(request.getParameter("memberGender"));
      member.setMemberTel(request.getParameter("memberTel"));
      member.setMemberZipcode(request.getParameter("memberZipcode"));
      member.setMemberAddr(request.getParameter("memberAddr"));
      member.setMemberDetailAddr(request.getParameter("memberDetailAddr"));


      Part photoPart = request.getPart("memberPhoto");
      if (photoPart.getSize() > 0) {
        String uploadFileUrl = ncpObjectStorageService.uploadFile(
                "bitcamp-nc7-bucket-03", "personal/member/", photoPart);
        member.setMemberPhoto(uploadFileUrl);
      }

      if (memberDao.update(member) == 0) {
        throw new Exception("회원이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        return "redirect:list";
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
