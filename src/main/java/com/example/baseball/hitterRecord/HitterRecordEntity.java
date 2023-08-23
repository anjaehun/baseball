package com.example.baseball.hitterRecord;

import com.example.baseball.hitterRecord.enumType.BattingType;
import com.example.baseball.team.TeamEntity;
import com.example.baseball.user.entity.UserEntity;
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
@Table(name = "hitterRecord")
public class HitterRecordEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long hitterId;

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

    /**
     * 소속 팀
     */

    @Column(columnDefinition = "VARCHAR(255) COMMENT '팀 네임 '")
    private String teamName;


    /**
     * 개인 정보 수정에서 넣을 수 있음
     */
    // 좌타 or 우타 설정
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) COMMENT '우타/좌타'")
    private BattingType battingType;

    /**
     * 안타 , 2루타 등 단순 기록을 할 수 있음
     */

    // 타수(타석에 들어간 횟수)
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '타수'")
    private double atBat;

    // 안타
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

    // 몸에 맞는 공
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '몸에 맞는 공'")
    private Double hitByPitch;

    // 타점
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '타점'")
    private Double runsBattedIn;

    // 득점
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '타점'")
    private Double runs;

    // 도루 횟수
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '도루'")
    private double stolenBases;

    // 도루 시도 횟수
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '도루 시도'")
    private double attempts;

    // 삼진
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '삼진'")
    private double strikeout;

    /**
     * 기본 기록을 토대로 한 계산 컬럼
     */

    // 타율
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '타율'")
    private Double BattingAverage;

    // 장타율
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '장타율'")
    private Double sluggingPercentage;

    // 출루율
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '출루율'")
    private Double onBasePercentage;

    // 도루 성공률
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '도루 성공율'")
    private Double stolenBaseSuccessRate;

// ...

//    @Entity
//    @Table(name = "hitterRecord")
//    public class HitterRecordEntity {
//
//        // ... (기존 필드들)
//
//        // 타율 계산
//        @Transient
//        private Double battingAverage;
//
//        // 장타율 계산
//        @Transient
//        private Double sluggingPercentage;
//
//        // PrePersist: 엔티티가 생성되기 전에 호출되는 메서드
//        @PrePersist
//        @PreUpdate
//        public void updateDerivedFields() {
//            // 타율 계산: 안타 수 / 타수
//            if (atBat > 0) {
//                battingAverage = hit / atBat;
//            } else {
//                battingAverage = 0.0;
//            }
//
//            // 장타율 계산
//            double totalBases = hit + (2 * doubleHit) + (3 * tripleHit) + (4 * homeRun);
//            if (atBat > 0) {
//                sluggingPercentage = totalBases / atBat;
//            } else {
//                sluggingPercentage = 0.0;
//            }
//        }
//
//        // ... (Getter와 Setter)
//    }




}
