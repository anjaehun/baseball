package com.example.baseball.hitterRecord.controller;

import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.hitterRecord.service.HitterRecordService;
import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hitter")
public class HitterRecordController {

    private final HitterRecordService hitterRecordService;

    public HitterRecordController(HitterRecordService hitterRecordService) {
        this.hitterRecordService = hitterRecordService;
    }

    @GetMapping("/list/all")
    public ResponseEntity<List<HitterRecordEntity>> getPitcherRecord() {

            List<HitterRecordEntity> hitterRecord = hitterRecordService.getHitterAllRecords();
            return ResponseEntity.ok(hitterRecord);

    }
}
