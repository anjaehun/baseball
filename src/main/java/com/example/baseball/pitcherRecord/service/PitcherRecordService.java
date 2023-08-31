package com.example.baseball.pitcherRecord.service;

import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.pitcherRecord.repository.PitcherRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PitcherRecordService {


    private final PitcherRecordRepository pitcherRecordRepository;

    public PitcherRecordService(PitcherRecordRepository pitcherRecordRepository) {
        this.pitcherRecordRepository = pitcherRecordRepository;
    }

    public List<PitcherRecordEntity> getPitcherAllRecords() {
        List<PitcherRecordEntity> picherRecord = pitcherRecordRepository.findAll();
        return picherRecord ;
    }
}
