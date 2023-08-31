package com.example.baseball.pitcherRecord.controller;

import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.pitcherRecord.service.PitcherRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pitcher")
public class PitcherRecordController {

    private final PitcherRecordService pitcherRecordService;

    public PitcherRecordController(PitcherRecordService pitcherRecordService) {
        this.pitcherRecordService = pitcherRecordService;
    }

    @GetMapping("/list/all")
    public ResponseEntity<List<PitcherRecordEntity>> getPitcherRecord() {

        List<PitcherRecordEntity> hitterRecord = pitcherRecordService.getPitcherAllRecords();
        return ResponseEntity.ok(hitterRecord);

    }
}
