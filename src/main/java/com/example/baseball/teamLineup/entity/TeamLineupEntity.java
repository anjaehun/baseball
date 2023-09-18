package com.example.baseball.teamLineup.entity;

import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
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
@Table(name = "teamLineUp")
public class TeamLineupEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long teamLineUpId;

    @ManyToOne
    @JoinColumn(name = "teamMemberId")
    private TeamMemberEntity teamMember;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private TeamEntity team;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '경기이름'")
    private String matchName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '선수이름'")
    private String playerName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '등번호'")
    private Integer jerseyNumber;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '타순'")
    private String battingOrder;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보여부'")
    private Boolean subYn;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '팀이름'")
    private String teamName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '야구 포지션'")
    private String baseballPosition;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '등록시간'")
    private LocalDateTime postDt;
}
