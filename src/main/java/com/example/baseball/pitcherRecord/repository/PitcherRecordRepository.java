package com.example.baseball.pitcherRecord.repository;

import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.teamMember.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PitcherRecordRepository extends JpaRepository<PitcherRecordEntity, Integer> {

    List<PitcherRecordEntity> findByTeamMember(TeamMemberEntity teamMember);
}