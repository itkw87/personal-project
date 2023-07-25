package personal.util;

import java.io.IOException;
import java.util.ArrayList;

public class Menu {

  private String title;
  private ArrayList listeners = new ArrayList();

  public Menu(String title) {
    this.title = title;
  }

  public Menu(String title, ActionListener listener) {
    this(title);
    this.addActionListener(listener);
  }

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  public void removeActionListener(ActionListener listener) {
    listeners.remove(listener);
  }

  public String getTitle() {
    return title;
  }

  public void execute(BreadcrumbPrompt prompt) {
    try {
      for (int i = 0; i < listeners.size(); i++) {
        ActionListener listener = (ActionListener) listeners.get(i);
        listener.service(prompt);
      }
    } catch (Exception e) {
      prompt.clear();
      prompt.println(e.getMessage());

    } finally {
      try {
        prompt.end();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
