package com.example.baseball.team.repository;

import com.example.baseball.team.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {
}
