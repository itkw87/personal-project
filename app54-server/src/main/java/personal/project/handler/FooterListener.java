package personal.project.controller;

import personal.project.util.ActionListener;
import personal.project.util.BreadcrumbPrompt;

public class FooterListener implements ActionListener {
  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("Copyright \u00a9 by 네클7기----------------------------");
  }
}
