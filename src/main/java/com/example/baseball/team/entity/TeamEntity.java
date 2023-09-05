package com.example.baseball.team.entity;

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
@Table(name = "team")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer teamId;

    @ManyToOne
    @JoinColumn(name = "id")
    private UserEntity id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '창설자 닉네임'")
    private String masterNickname ;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '창설자 네임'")
    private String masterName ;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '팀 이름'")
    private String teamName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '감독'")
    private String mainCoach;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '팀 설명'")
    private String teamDescription;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '팀로고'")
    private String teamLogoImage;

    @Column(columnDefinition = "VARCHAR(1000) COMMENT '팀이미지'")
    private String teamImg;


    private LocalDateTime registerDt;

}
