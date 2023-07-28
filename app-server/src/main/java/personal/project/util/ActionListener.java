package personal.project.util;

import java.io.IOException;

// caller: Menu
// callee: 메뉴를 처리할 객체
//
public interface ActionListener {
  void service(BreadcrumbPrompt prompt) throws IOException;
}
