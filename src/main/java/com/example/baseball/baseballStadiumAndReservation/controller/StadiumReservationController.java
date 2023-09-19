package com.example.baseball.baseballStadiumAndReservation.controller;

import com.example.baseball.baseballStadiumAndReservation.entity.BaseballStadiumEntity;
import com.example.baseball.baseballStadiumAndReservation.entity.StadiumReservationEntity;
import com.example.baseball.baseballStadiumAndReservation.exception.NoReservationException;
import com.example.baseball.baseballStadiumAndReservation.exception.NotStadiumException;
import com.example.baseball.baseballStadiumAndReservation.request.ReservationTimeRequest;
import com.example.baseball.baseballStadiumAndReservation.response.BaseballStadiumPostResponse;
import com.example.baseball.baseballStadiumAndReservation.exception.AlreadyReservationException;
import com.example.baseball.baseballStadiumAndReservation.exception.NoTeamByNicknameException;
import com.example.baseball.baseballStadiumAndReservation.service.StadiumReservationService;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.team.response.TeamListShowByTeamIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PutMapping("/away/{stadiumReservationId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> awayReservation(
            @PathVariable Long stadiumReservationId
    ) {
        try {
            BaseballStadiumPostResponse createdBoard = stadiumReservationService.awayReservation(
                    Math.toIntExact(stadiumReservationId)
            );
            return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
        } catch (NoReservationException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NoTeamByNicknameException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * 예약 전체 보기
     * @param baseballStadiumId
     * @return
     */
    @GetMapping("/list/all/{baseballStadiumId}")
    public ResponseEntity<Object> getTeamById(@PathVariable Long baseballStadiumId) {

        List<StadiumReservationEntity> team = stadiumReservationService.getReservationAll(Math.toIntExact(baseballStadiumId));
        return ResponseEntity.ok(team);
    }

    /**
     * 예약한개보기
     * @param stadiumReservationId
     * @return
     */
    @GetMapping("/list/{stadiumReservationId}")
    public ResponseEntity<Object> getReservationId(@PathVariable Long stadiumReservationId) {

        Optional<StadiumReservationEntity> team = stadiumReservationService.getReservationById(Math.toIntExact(stadiumReservationId));
        return ResponseEntity.ok(team);
    }


}
