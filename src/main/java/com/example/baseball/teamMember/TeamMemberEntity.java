package com.example.baseball.teamMember;

import com.example.baseball.team.TeamEntity;
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
@Table(name = "teamMember")
public class TeamMemberEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long teamMemberId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity user;

    // 선수 이름
    @Column(columnDefinition = "VARCHAR(255) COMMENT '선수 이름'")
    private String name;

    // 선수 닉네임
    @Column(columnDefinition = "VARCHAR(255) COMMENT '선수 닉네임'")
    private String nickname;

    // 등 번호
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '등번호'")
    private Integer jerseyNumber;

    // 등 번호
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '키'")
    private Integer height;

    // 몸무게
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '몸무게'")
    private Integer weight;

    // 팀에서의 역활
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 0 COMMENT '팀 역활'")
    private Integer teamRole;


    private LocalDateTime registerDt;
}
