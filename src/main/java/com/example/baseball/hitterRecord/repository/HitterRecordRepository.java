package com.example.baseball.hitterRecord.repository;

import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HitterRecordRepository extends JpaRepository<HitterRecordEntity, Integer> {


    List<HitterRecordEntity> findByTeamMember(TeamMemberEntity teamMember);
}
