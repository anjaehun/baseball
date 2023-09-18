package playball.controller;

import com.example.baseball.baseballStadiumAndReservation.response.BaseballStadiumPostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playball.entity.PlayBallEntity;
import playball.request.PlayBallRequest;
import playball.service.PlayBallService;

@RestController
@RequestMapping("api/v1/record")
public class PlayBallController {

    private final PlayBallService playBallService;

    public PlayBallController(PlayBallService playBallService) {
        this.playBallService = playBallService;
    }

    @PostMapping("/game")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> recordPlayBall(
            @RequestBody PlayBallRequest playBallRequest
            ){
        PlayBallEntity createdBoard = playBallService.recordPlayBall(playBallRequest);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }



}
