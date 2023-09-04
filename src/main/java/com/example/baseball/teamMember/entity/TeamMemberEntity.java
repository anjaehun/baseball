package com.example.baseball.teamMember.entity;

import com.example.baseball.team.entity.TeamEntity;

import com.example.baseball.teamMember.enumType.TeamFounderAcceptRole;
import com.example.baseball.teamMember.enumType.TeamRoleEnumType;
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
@Table(name = "teamMember")
public class TeamMemberEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer teamMemberId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;


    // 선수 이름
    @Column(columnDefinition = "VARCHAR(255) COMMENT '선수 이름'")
    private String name;

    // 선수 닉네임
    @Column(columnDefinition = "VARCHAR(255) COMMENT '선수 닉네임'")
    private String nickname;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '팀 생성자 승인 여부'")
    private TeamFounderAcceptRole teamFounderAcceptRole;

    // 등 번호
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '등번호'")
    private Integer jerseyNumber;

    // 키
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '키'")
    private Integer height;

    // 몸무게
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '몸무게'")
    private Integer weight;

    // 팀에서의 역활
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 0 COMMENT '팀 역활'")
    private TeamRoleEnumType teamRole;

    // 팀 가입 이유
    @Column(columnDefinition = "TEXT DEFAULT 0 COMMENT '팀 가입 이유'")
    private String reasonForTeamMembership;

    // 앞으로의 각오
    @Column(columnDefinition = "TEXT DEFAULT 0 COMMENT '앞으로의 각오'")
    private String determinationForTheFuture;



    private LocalDateTime registerDt;
}
