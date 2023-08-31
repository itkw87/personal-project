package personal.project.dao;

import personal.project.vo.Member;
import personal.project.vo.Participant;

import java.util.List;

public interface ParticipantDao {
  List<Participant> findAllParticipant(Member member);

}
