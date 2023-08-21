package com.example.baseball.playballRecord;

import com.example.baseball.teamLineup.TeamLineupEntity;
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
@Table(name = "playBallRecord")
public class PlayballRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playBallId;

    @ManyToOne
    @JoinColumn(name = "home_team_lineup_id")
    private TeamLineupEntity homeTeamLineup;

    // 타자 순서
    @Column(columnDefinition = "INT COMMENT '타자 순서'")
    private Integer battingOrder;

    // 타자 순서
    @Column(columnDefinition = "INT COMMENT '배터 이름'")
    private Integer batterName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '타격 결과'")
    private String battingResult;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '투수 이름'")
    private String pictherName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '투수 이름'")
    private String gameName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '공격 관련 여부'")
    private AttackRole attackRole;


    @Column(columnDefinition = "VARCHAR(255) COMMENT '이닝 정보'")
    private String inning;

    private LocalDateTime registerDt;

    // Getter, Setter, toString 등 생략
}
