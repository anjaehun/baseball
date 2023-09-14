package com.example.baseball.baseballStadiumAndReservation.repository;

import com.example.baseball.baseballStadiumAndReservation.entity.BaseballStadiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseballStadiumRepository extends JpaRepository<BaseballStadiumEntity , Integer> {
    boolean existsByStadiumName(String stadiumName);
}

