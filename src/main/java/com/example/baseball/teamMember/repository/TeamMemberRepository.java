package com.example.baseball.teamMember.repository;

import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMemberEntity, Integer> {

    boolean existsByTeamAndJerseyNumber(TeamEntity team, int jerseyNumber);

    boolean existsByTeamAndNameAndNickname(TeamEntity team, String name, String nickname);

    List<TeamMemberEntity> findByTeam(TeamEntity team);

    Optional<Object> findByNickname(String nickname);

    Optional<Object> findByTeamAndNickname(TeamEntity team, String nickname);



}
