package personal.project.handler;

import personal.project.vo.Student;

public class StudentList {
  private static final int DEFAULT_SIZE = 3;

  private Student[] students = new Student[DEFAULT_SIZE];
  private int length;

  public boolean add(Student s) {
    if (this.length == students.length) {
      increase();
    }
    this.students[this.length++] = s;
    return true;
  }

  private void increase() {
    Student[] arr = new Student[students.length + (students.length >> 1)];
    for (int i = 0; i < students.length; i++) {
      arr[i] = students[i];
    }
    students = arr;
    System.out.println("배열 확장: " + students.length);
  }

  public Student[] list() {
    // 리턴할 값을 담을 배열을 생성
    Student[] arr = new Student[this.length];

    // 원본 배열에서 입력된 인스턴스 주소를 꺼내
    // 새 배열에 담는다.
    for (int i = 0; i < this.length; i++) {
      arr[i] = this.students[i];
    }

    // 새 배열을 리턴한다.
    return arr;
  }

  public Student get(int no) {
    for (int i = 0; i < this.length; i++) {
      Student s = this.students[i];
      if (s.getNo() == no) {
        return s;
      }
    }
    return null;
  }

  public boolean delete(int no) {
    int deletedIndex = indexOf(no);
    if (deletedIndex == -1) {
      return false;
    }

    for (int i = deletedIndex; i < this.length - 1; i++) {
      this.students[i] = this.students[i + 1];
    }
    this.students[--this.length] = null;
    return true;
  }

  private int indexOf(int studentNo) {
    for (int i = 0; i < this.length; i++) {
      Student m = this.students[i];
      if (m.getNo() == studentNo) {
        return i;
      }
    }
    return -1;
  }
}
