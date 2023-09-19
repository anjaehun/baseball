package com.example.baseball.pitcherRecord.repository;

import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PitcherRecordRepository extends JpaRepository<PitcherRecordEntity, Integer> {

    Optional<PitcherRecordEntity> findByTeamMember(TeamMemberEntity teamMember);

    PitcherRecordEntity findByNameAndTeamName(String pitcherName, String defendTeamName);
}
