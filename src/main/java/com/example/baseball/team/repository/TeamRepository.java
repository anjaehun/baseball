package com.example.baseball.team.repository;

import com.example.baseball.team.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {
    Optional<TeamEntity> findByTeamName(String teamName);

}
