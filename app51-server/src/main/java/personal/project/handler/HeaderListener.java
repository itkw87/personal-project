package personal.project.controller;

import personal.util.ActionListener;
import personal.util.BreadcrumbPrompt;

public class HeaderListener implements ActionListener {
  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("=============================[비트캠프!]==");
  }
}
