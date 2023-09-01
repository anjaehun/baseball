package com.example.baseball.teamPerformance.repository;

import com.example.baseball.teamPerformance.TeamPerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPerformanceRepository extends JpaRepository<TeamPerformanceEntity, Integer> {

}
