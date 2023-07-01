package personal.util;

public class Stack<E> extends LinkedList<E> {

  public void push(E value) {
    this.add(value);
  }

  public E pop() {
    if (this.empty()) {
      return null;
    }
    return this.remove(this.size() - 1);
  }

  public E peek() {
    if (this.empty()) {
      return null;
    }
    return this.get(this.size() - 1);
  }

  private boolean empty() {
    return this.size() == 0;
  }
}
