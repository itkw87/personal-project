package personal.project.dao;

import java.util.List;
import personal.project.vo.Member;

public interface MemberDao {
  void insert(Member student);

  List<Member> list();

  Member findBy(int no);

  int update(Member student);

  int delete(int no);

  Member findByEmailAndPassword(Member m);
}
