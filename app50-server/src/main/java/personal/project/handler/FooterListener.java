package personal.project.handler;

import personal.util.ActionListener;
import personal.util.BreadcrumbPrompt;

public class FooterListener implements ActionListener {
  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("Copyright \u00a9 by 네클7기----------------------------");
  }
}
