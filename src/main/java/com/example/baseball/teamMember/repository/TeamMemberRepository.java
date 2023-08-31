package com.example.baseball.teamMember.repository;

import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.teamMember.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, Integer> {

    boolean existsByTeamAndJerseyNumber(TeamEntity team, int jerseyNumber);

    boolean existsByTeamAndNameAndNickname(TeamEntity team, String name, String nickname);

    List<TeamMemberEntity> findByTeam(TeamEntity team);
}
