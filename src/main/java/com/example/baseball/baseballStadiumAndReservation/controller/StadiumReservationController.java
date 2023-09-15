package com.example.baseball.baseballStadiumAndReservation.controller;

import com.example.baseball.baseballStadiumAndReservation.exception.NotStadiumException;
import com.example.baseball.baseballStadiumAndReservation.request.ReservationTimeRequest;
import com.example.baseball.baseballStadiumAndReservation.response.BaseballStadiumPostResponse;
import com.example.baseball.baseballStadiumAndReservation.exception.AlreadyReservationException;
import com.example.baseball.baseballStadiumAndReservation.exception.NoTeamByNicknameException;
import com.example.baseball.baseballStadiumAndReservation.service.StadiumReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/stadium/reservation")
public class StadiumReservationController {

     private final StadiumReservationService stadiumReservationService;

    public StadiumReservationController(StadiumReservationService stadiumReservationService) {
        this.stadiumReservationService = stadiumReservationService;
    }

    @PostMapping("/home/{baseballStadiumId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> homeReservation(
            @PathVariable Long baseballStadiumId,
            @RequestBody ReservationTimeRequest reservationTimeRequest
    ) {
        try {
            BaseballStadiumPostResponse createdBoard = stadiumReservationService.homeReservation(Math.toIntExact(baseballStadiumId), reservationTimeRequest);
            return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
        } catch (NoTeamByNicknameException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch(NotStadiumException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch(AlreadyReservationException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }




}
