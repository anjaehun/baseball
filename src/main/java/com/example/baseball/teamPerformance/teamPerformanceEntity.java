package com.example.baseball.teamPerformance;

import com.example.baseball.team.entity.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teamPerformance")
public class teamPerformanceEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long teamPerformanceId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;


    @Column(columnDefinition = "DOUBLE COMMENT '승'")
    private Double win;

    @Column(columnDefinition = "DOUBLE COMMENT '패'")
    private Double lose;

    @Column(columnDefinition = "DOUBLE COMMENT '승률'")
    private Double winRate;
}
