package personal.project.controller;

import personal.project.dao.MemberDao;
import personal.util.BreadcrumbPrompt;

public class MemberDeleteListener implements MemberActionListener {

  MemberDao studentDao;

  public MemberDeleteListener(MemberDao studentDao) {
    this.studentDao = studentDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    if (studentDao.delete(prompt.inputInt("번호? ")) == 0) {
      System.out.println("해당 번호의 학생이 없습니다!");
    }
  }
}
