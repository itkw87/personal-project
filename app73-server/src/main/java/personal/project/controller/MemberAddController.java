package personal.project.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import personal.project.dao.MemberDao;
import personal.project.service.MemberService;
import personal.project.vo.Member;
import personal.project.service.NcpObjectStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@Component("/member/add")
public class MemberAddController implements PageController {

  @Autowired
  MemberService memberService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/member/form.jsp";
    }

    try {
      Member m = new Member();
      m.setMemberCode(request.getParameter("memberCode"));
      m.setMemberId(request.getParameter("memberId"));
      m.setMemberPwd(request.getParameter("memberPwd"));
      m.setMemberName(request.getParameter("memberName"));
      m.setMemberEmail(request.getParameter("memberEmail"));
      m.setMemberGender(request.getParameter("memberGender"));
      m.setMemberTel(request.getParameter("memberTel"));
      m.setMemberZipcode(request.getParameter("memberZipCode"));
      m.setMemberAddr(request.getParameter("memberAddr"));
      m.setMemberDetailAddr(request.getParameter("memberDetailAddr"));

      Part photoPart = request.getPart("memberPhoto");
      if (photoPart.getSize() > 0) {
        String uploadFileUrl = ncpObjectStorageService.uploadFile(
                "bitcamp-nc7-bucket-03", "personal/member/", photoPart);
        m.setMemberPhoto(uploadFileUrl);
      }
      memberService.add(m);
      return "redirect:list";

    } catch (Exception e) {
      request.setAttribute("message", "회원 등록 오류!");
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
