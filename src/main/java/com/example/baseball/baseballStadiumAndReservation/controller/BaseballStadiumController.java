package com.example.baseball.baseballStadiumAndReservation.controller;

import com.example.baseball.baseballStadiumAndReservation.entity.BaseballStadiumEntity;
import com.example.baseball.baseballStadiumAndReservation.exception.SameStadiumNameException;
import com.example.baseball.baseballStadiumAndReservation.request.StadiumPostRequest;
import com.example.baseball.baseballStadiumAndReservation.response.BaseballStadiumListResponse;
import com.example.baseball.baseballStadiumAndReservation.response.BaseballStadiumPostResponse;
import com.example.baseball.baseballStadiumAndReservation.service.BaseBallStadiumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/stadium")
public class BaseballStadiumController {

    private final BaseBallStadiumService baseBallStadiumService;

    public BaseballStadiumController(BaseBallStadiumService baseBallStadiumService) {
        this.baseBallStadiumService = baseBallStadiumService;
    }

    /**
     * 1건 등록
     * @param stadiumPostRequest
     * @param fileStadium
     * @return
     */
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

    /**
     * 리스트 전체 보기
     * @return
     */
    @GetMapping("/list")
    public List<BaseballStadiumEntity> getAllStadiums() {
        return baseBallStadiumService.getAllStadiums();
    }

    /**
     * 1건 등록
     * @param baseballStadiumId
     * @return
     */
    @GetMapping("/list/{baseballStadiumId}")
    public ResponseEntity<Optional<BaseballStadiumEntity>> getStadiumById(@PathVariable Long baseballStadiumId) {
        // baseballStadiumId를 사용하여 해당 스타디움 정보를 데이터베이스에서 가져오는 코드를 작성하세요.
        Optional<BaseballStadiumEntity> stadium = baseBallStadiumService.getStadiumById(baseballStadiumId);

        return ResponseEntity.ok(stadium);

    }


}
