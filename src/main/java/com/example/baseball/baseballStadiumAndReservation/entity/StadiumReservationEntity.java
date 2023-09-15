package com.example.baseball.baseballStadiumAndReservation.entity;

import com.example.baseball.baseballStadiumAndReservation.enumType.AwayReservationOkRole;
import com.example.baseball.baseballStadiumAndReservation.enumType.HomeReservationOkRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stadiumReservation")
public class StadiumReservationEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer stadium_reservation_id;

    @ManyToOne
    @JoinColumn(name = "baseballStadiumId")
    private BaseballStadiumEntity baseballStadium;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '홈팀'")
    private String homeTeam;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '어웨이팀'")
    private String awayTeam;

    // 원하는 시간 (시작)
    private LocalDateTime startDateTime;

    // 원하는 시간 (끝)
    private LocalDateTime endDatetime;

    // 홈 예약 상태
    private HomeReservationOkRole homeReservationOkRole;

    // 어웨이 예약 상태
    private AwayReservationOkRole awayReservationOkRole;




}
