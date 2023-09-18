package com.example.baseball.hitterRecord.repository;

import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HitterRecordRepository extends JpaRepository<HitterRecordEntity, Integer> {


    Optional<HitterRecordEntity> findByTeamMember(TeamMemberEntity teamMember);

    HitterRecordEntity findByNameAndTeamName(String hitterName, String attackTeamName);
}
