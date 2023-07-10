package personal.project.dao;

import java.util.ArrayList;
import java.util.List;
import personal.project.vo.Student;
import personal.util.JsonDataHelper;

public class StudentListDao implements StudentDao {

  String filename;
  ArrayList<Student> list = new ArrayList<>();

  public StudentListDao(String filename) {
    this.filename = filename;
    JsonDataHelper.loadJson(filename, list, Student.class);
  }

  @Override
  public void insert(Student student) {
    student.setNo(Student.userId++);
    this.list.add(student);
    JsonDataHelper.saveJson(filename, list);
  }

  @Override
  public List<Student> list() {
    return this.list;
  }

  @Override
  public Student findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Student s = this.list.get(i);
      if (s.getNo() == no) {
        return s;
      }
    }
    return null;
  }

  @Override
  public int update(Student student) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == student.getNo()) {
        list.set(i, student);
        JsonDataHelper.saveJson(filename, list);
        return 1;
      }
    }
    return 0;
  }


  @Override
  public int delete(int no) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == no) {
        list.remove(i);
        JsonDataHelper.saveJson(filename, list);
        return 1;
      }
    }
    return 0;
  }

}
