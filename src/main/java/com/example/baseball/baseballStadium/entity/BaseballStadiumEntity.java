package com.example.baseball.baseballStadium.entity;

import com.example.baseball.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "baseballStadium")
public class BaseballStadiumEntity {
    // test

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer baseballStadiumReservationId;

    @ManyToOne
    @JoinColumn(name = "id")
    private UserEntity id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '창설자 닉네임'")
    private String stadiumOwnerNickname;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '창설자 네임'")
    private String stadiumOwnerName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '경기장 이름'")
    private String stadiumName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '지역'")
    private String region;


    @Column(columnDefinition = "VARCHAR(1000) COMMENT '경기장 설명'")
    private String stadiumDescription;

    @Column(columnDefinition = "VARCHAR(1000) COMMENT '경기장 대표 이미지'")
    private String stadiumReprecentImage;

    // 예약 가능 시작 시간
    private LocalTime possibleReservationStartTime;

    // 예약 가능 종료 시간
    private LocalTime possibleReservationEndTime;

    // 예약 가능 종료 시간
    private LocalDateTime postDate;

}
