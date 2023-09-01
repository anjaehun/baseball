package com.example.baseball.hitterRecord.repository;

import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.teamMember.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HitterRecordRepository extends JpaRepository<HitterRecordEntity, Integer> {


    List<HitterRecordEntity> findByTeamMember(TeamMemberEntity teamMember);
}
