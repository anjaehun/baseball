package com.example.baseball.hitterRecord.service;

import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.hitterRecord.repository.HitterRecordRepository;
import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HitterRecordService {

    private final HitterRecordRepository hitterRecordRepository;

    public HitterRecordService(HitterRecordRepository hitterRecordRepository) {
        this.hitterRecordRepository = hitterRecordRepository;
    }


    public List<HitterRecordEntity> getHitterAllRecords() {
        List<HitterRecordEntity> hitterRecord = hitterRecordRepository.findAll();
        return hitterRecord;
    }
}
