package personal.project.dao;

import java.util.List;
import personal.project.vo.Member;

public interface MemberDao {
  void insert(Member student);

  List<Member> findAll();

  Member findBy(int no);

  Member findByEmailAndPassword(Member m);

  int update(Member student);

  int delete(int no);

}
