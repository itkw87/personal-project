package personal.project.dao;

import org.apache.ibatis.annotations.Param;
import personal.project.vo.Member;

import java.util.List;

public interface MemberDao {
  int insert(Member member);
  List<Member> findAll();
  Member findBy(int no);
  Member findByEmailAndPassword(@Param("memberId") String email, @Param("memberPwd") String password);
  int update(Member member);
  int delete(int no);
}