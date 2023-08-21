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
@Table(name = "playballRecord")
public class PlayballRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "home_team_lineup_id")
    private TeamLineupEntity homeTeamLineup;

    // 타자 순서
    @Column(columnDefinition = "INT COMMENT '타자 순서'")
    private Integer battingOrder;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '타격 결과'")
    private String battingResult;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '이닝 정보'")
    private String inning;

    private LocalDateTime registerDt;

    // Getter, Setter, toString 등 생략
}
