package com.example.baseball.baseballStadiumAndReservation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseballStadiumListResponse {
    private String stadiumName;
    private String stadiumDescription;
    private String region;
    private String stadiumReprecentImage;
    private Integer baseballStadiumId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime reservationStartTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime reservationEndTime;

}
