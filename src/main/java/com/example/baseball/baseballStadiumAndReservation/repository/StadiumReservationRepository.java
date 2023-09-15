package com.example.baseball.baseballStadiumAndReservation.repository;

import com.example.baseball.baseballStadiumAndReservation.entity.BaseballStadiumEntity;
import com.example.baseball.baseballStadiumAndReservation.entity.StadiumReservationEntity;
import com.example.baseball.team.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StadiumReservationRepository extends JpaRepository<StadiumReservationEntity, Integer> {

    List<StadiumReservationEntity> findAllByBaseballStadium(BaseballStadiumEntity BaseballStadium);
}

