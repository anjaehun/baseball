package com.example.baseball.hitterRecord.repository;

import com.example.baseball.hitterRecord.HitterRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HitterRecordRepository extends JpaRepository<HitterRecordEntity, Integer> {

}
