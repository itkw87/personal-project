package personal.project.dao;

import java.util.List;
import personal.project.vo.Member;

public interface MemberDao {
  void insert(Member member);

  List<Member> findAll();

  Member findBy(int no);

  Member findByEmailAndPassword(Member member);

  int update(Member member);

  int delete(int no);

}
