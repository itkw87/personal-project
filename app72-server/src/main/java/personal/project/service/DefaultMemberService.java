package personal.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import personal.project.dao.MemberDao;
import personal.project.dao.ParticipantDao;
import personal.project.vo.Member;
import personal.project.vo.Participant;

import java.util.List;

@Service
public class DefaultMemberService implements MemberService {

  MemberDao memberDao;
  ParticipantDao participantDao;
  TransactionTemplate txTemplate;

  public DefaultMemberService(MemberDao memberDao, ParticipantDao participantDao, PlatformTransactionManager txManager) {
    this.memberDao = memberDao;
    this.participantDao = participantDao;
    this.txTemplate = new TransactionTemplate(txManager);
  }

  @Override
  public int add(Member member) throws Exception {
      return txTemplate.execute(status -> memberDao.insert(member));

  }

  @Override
  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }

  @Override
  public Member get(int memberNo) throws Exception {
    return memberDao.findBy(memberNo);
  }

  @Override
  public Member get(String memberId, String password) throws Exception {
    Member member = memberDao.findByEmailAndPassword(memberId, password);
    List<Participant> particiList = participantDao.findAllParticipant(member);
    if (particiList != null && !particiList.isEmpty()) {
      member.setParticipantList(particiList);
    }
    return member;
  }

  @Override
  public int update(Member member) throws Exception {
    return txTemplate.execute(status -> memberDao.update(member));
  }

  @Override
  public int delete(int memberNo) throws Exception {
    return txTemplate.execute(status -> memberDao.delete(memberNo));
  }
}
