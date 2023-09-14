package com.example.baseball.baseballStadium.repository;

import com.example.baseball.baseballStadium.entity.BaseballStadiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseballStadiumRepository extends JpaRepository<BaseballStadiumEntity , Integer> {
    boolean existsByStadiumName(String stadiumName);
}

