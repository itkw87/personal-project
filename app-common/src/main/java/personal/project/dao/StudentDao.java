package personal.project.dao;

import java.util.List;
import personal.project.vo.Student;

public interface StudentDao {
  void insert(Student student);

  List<Student> list();

  Student findBy(int no);

  int update(Student student);

  int delete(int no);
}
