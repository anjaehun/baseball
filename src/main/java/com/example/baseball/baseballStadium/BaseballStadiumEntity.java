package com.example.baseball.baseballStadium;

import com.example.baseball.user.entity.UserEntity;
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
@Table(name = "baseballStadium")
public class BaseballStadiumEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer baseballStadiumReservationId;

    @ManyToOne
    @JoinColumn(name = "id")
    private UserEntity id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '창설자 닉네임'")
    private String stadiumOwnerNickname;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '창설자 네임'")
    private String masterName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '경기장 이름'")
    private String stadiumName;

    @Column(columnDefinition = "VARCHAR(1000) COMMENT '경기장 설명'")
    private String stadiumDescription;

    // 예약 가능 시작 시간
    private LocalDateTime possibleReservationStartTime;

    // 예약 가능 종료 시간
    private LocalDateTime possibleReservationEndTime;



}
