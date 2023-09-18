package com.example.baseball.baseballStadiumAndReservation.repository;

import com.example.baseball.baseballStadiumAndReservation.entity.BaseballStadiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseballStadiumRepository extends JpaRepository<BaseballStadiumEntity , Integer> {
    boolean existsByStadiumName(String stadiumName);

    List<BaseballStadiumEntity> findByBaseballStadiumId(int baseballStadium);
}

