package com.example.baseball.baseballStadiumAndReservation.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationTimeRequest {

    // 원하는 시간 (시작)
    private LocalDateTime startDateTime;

    // 원하는 시간 (끝)
    private LocalDateTime endDatetime;
}
