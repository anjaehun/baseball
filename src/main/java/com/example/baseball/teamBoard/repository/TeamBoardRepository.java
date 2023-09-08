package com.example.baseball.teamBoard.repository;

import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.teamBoard.entity.TeamBoardEntity;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamBoardRepository extends JpaRepository<TeamBoardEntity, Integer> {
    List<TeamBoardEntity> findAllByTeam(TeamEntity team);


    Optional<TeamBoardEntity> findByTeamAndTeamBoardId(TeamEntity team, Integer boardId);
}
