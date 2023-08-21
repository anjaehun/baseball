package com.example.baseball.teamLineup;

import com.example.baseball.team.TeamEntity;
import com.example.baseball.teamMember.TeamMemberEntity;
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
@Table(name = "homeTeamLineUpEntity")
public class TeamLineupEntity {


    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "team_member_id")
    private TeamMemberEntity teamMember;

    @Enumerated(EnumType.STRING)
    private HomeAwayYnRole homeAwayYnRole;

    // 투수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '경기 이름'")
    private String gameName;

    // 투수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '투수'")
    private String pitcher;

    // 포수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '포수'")
    private String catcher;

    // 1루수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '1루수'")
    private String firstBaseman;

    // 2루수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '2루수'")
    private String secondBaseman;

    // 3루수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '3루수'")
    private String thirdBaseman;

    // 유격수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '3루수'")
    private String shortStop;

    // 좌익수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '좌익수'")
    private String leftFielder;

    // 중견수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '중견수'")
    private String centerFielder;

    // 우익수
    @Column(columnDefinition = "VARCHAR(255) COMMENT '중견수'")
    private String rightFielder;

    // 지명타자
    @Column(columnDefinition = "VARCHAR(255) COMMENT '지명타자'")
    private String designatedHitter;

    // 후보1
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 1'")
    private String candidate1;

    // 타순
    @Column(columnDefinition = "VARCHAR(255) COMMENT '1번타자'")
    private String Batter1;

    // 타순
    @Column(columnDefinition = "VARCHAR(255) COMMENT '2번타자'")
    private String Batter2;

    // 타순
    @Column(columnDefinition = "VARCHAR(255) COMMENT '3번타자'")
    private String Batter3;

    // 타순
    @Column(columnDefinition = "VARCHAR(255) COMMENT '4번타자'")
    private String Batter4;

    // 타순
    @Column(columnDefinition = "VARCHAR(255) COMMENT '5번타자'")
    private String Batter5;

    // 타순
    @Column(columnDefinition = "VARCHAR(255) COMMENT '6번타자'")
    private String Batter6;

    // 타순
    @Column(columnDefinition = "VARCHAR(255) COMMENT '7번타자'")
    private String Batter7;

    // 타순
    @Column(columnDefinition = "VARCHAR(255) COMMENT '8번타자'")
    private String Batter8;

    // 타순
    @Column(columnDefinition = "VARCHAR(255) COMMENT '8번타자'")
    private String Batter9;

    // 후보2
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 2'")
    private String candidate2;

    // 후보3
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 3'")
    private String candidate3;

    // 후보4
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 4'")
    private String candidate4;

    // 후보5
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 5'")
    private String candidate5;

    // 후보6
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 6'")
    private String candidate6;

    // 후보7
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 7'")
    private String candidate7;

    // 후보8
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 8'")
    private String candidate8;

    // 후보9
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 9'")
    private String candidate9;

    // 후보10
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 10'")
    private String candidate10;

    // 후보11
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 11'")
    private String candidate11;

    // 후보12
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 12'")
    private String candidate12;

    // 후보13
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 13'")
    private String candidate13;

    // 후보14
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 14'")
    private String candidate14;

    // 후보15
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 15'")
    private String candidate15;

    // 후보16
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 16'")
    private String candidate16;

    // 후보17
    @Column(columnDefinition = "VARCHAR(255) COMMENT '후보 17'")
    private String candidate17;


}
