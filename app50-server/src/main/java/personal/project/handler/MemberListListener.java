package personal.project.handler;

import java.util.List;
import personal.project.dao.MemberDao;
import personal.project.vo.Member;
import personal.util.BreadcrumbPrompt;

public class MemberListListener implements MemberActionListener {

  MemberDao studentDao;

  public MemberListListener(MemberDao studentDao) {
    this.studentDao = studentDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println(
        "------------------------------------------------------------------------------------------");
    prompt.println("번호 | 구분 | 생년월일 | 이름 | 성별 |  e - mail  | 재학(재직)여부");
    prompt.println(
        "------------------------------------------------------------------------------------------");

    // 목록에서 데이터를 대신 꺼내주는 객체를 얻는다.
    List<Member> list = studentDao.list();
    for (Member m : list) {
      prompt.printf("  %d  | %s | %s | %s | %s | %s | %s \n", m.getNo(),
          "S".equals(m.getAuthority()) ? "학생" : "교사", m.getBirth(), m.getName(),
          m.getGender() == 'M' ? "남성" : "여성", m.getEmail(),
          m.getStatus() == true ? "재학(재직)" : "퇴학(퇴직)");
    }
  }
}
