package com.example.baseball.teamLineup.repository;

import com.example.baseball.teamLineup.entity.TeamLineupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamLineupRepository extends JpaRepository<TeamLineupEntity, Long> {
    List<TeamLineupEntity> findByJerseyNumber(int jerseyNumber);

    List<TeamLineupEntity> findByTeamNameAndMatchName(String teamName, String matchName);


    // 여기에 추가적인 쿼리 메서드를 정의할 수 있음
}
