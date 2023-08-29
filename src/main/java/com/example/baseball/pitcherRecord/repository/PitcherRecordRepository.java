package com.example.baseball.pitcherRecord.repository;

import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PitcherRecordRepository extends JpaRepository<PitcherRecordEntity, Integer> {
}
