package personal.project.controller;

import personal.project.util.ActionListener;
import personal.project.util.BreadcrumbPrompt;

public class HeaderListener implements ActionListener {
  @Override
  public void service(BreadcrumbPrompt prompt) {
    prompt.println("=============================[비트캠프!]==");
  }
}
