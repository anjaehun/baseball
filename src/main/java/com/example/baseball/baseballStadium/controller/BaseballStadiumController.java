package com.example.baseball.baseballStadium.controller;

import com.example.baseball.baseballStadium.exception.SameStadiumNameException;
import com.example.baseball.baseballStadium.request.StadiumPostRequest;
import com.example.baseball.baseballStadium.response.BaseballStadiumPostResponse;
import com.example.baseball.baseballStadium.service.BaseBallStadiumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/stadium")
public class BaseballStadiumController {

    private final BaseBallStadiumService baseBallStadiumService;

    public BaseballStadiumController(BaseBallStadiumService baseBallStadiumService) {
        this.baseBallStadiumService = baseBallStadiumService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasAuthority('STADIUM_USER')")
    public ResponseEntity<Object> createTeamBoardGeneral(
            @RequestPart StadiumPostRequest stadiumPostRequest,
            @RequestPart("fileStadium") MultipartFile fileStadium) {
        try {
            BaseballStadiumPostResponse createdBoard = baseBallStadiumService.createStadiumCreate(stadiumPostRequest,fileStadium);
            return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
        } catch (SameStadiumNameException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
