package com.example.baseball.pitcherRecord;

import com.example.baseball.team.TeamEntity;
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
@Table(name = "pitcherRecord")
public class PitcherRecordEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long pitcherId;


    @ManyToOne
    @JoinColumn(name = "team_id") // 이 부분은 실제 팀 엔티티의 ID 컬럼과 연결
    private TeamEntity team;

    /**
     * 기본 정보
     */
    // 선수 이름
    @Column(columnDefinition = "VARCHAR(255) COMMENT '선수 이름'")
    private String name;

    // 선수 닉네임
    @Column(columnDefinition = "VARCHAR(255) COMMENT '선수 닉네임'")
    private String nickname;

    // 타수(타석에 들어간 횟수)
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '타수'")
    private double atBat;

    // 피안타
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '안타'")
    private double hit;

    // 2루타
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '2루타'")
    private double DoubleHit;

    // 3루타
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '3루타'")
    private double tripleHit;

    // 홈런
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '홈런'")
    private double homeRun;

    // 볼넷
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '무의도 볼넷'")
    private Double unintentionalWalk;

    // 고의 사구
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '고의 사구'")
    private Double intentionalWalk;

    // 실점
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '실점'")
    private Double runsAllowed;

    // 자책점
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '자책점'")
    private Double earnedRun;

    /**
     * 기본 기록을 토대로 한 계산 컬럼
     */

    // 방어율
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '방어율'")
    private Double earnedRunAverage;

    // whip(Walks plus Hits per Inning Pitched)
    // WHIP는 투수가 허용한 볼넷과 허용한 안타의 합을 이닝 수로 나눈 값을 나타냅니다
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '이닝당 주자 허용수'")
    private Double whip;
}
