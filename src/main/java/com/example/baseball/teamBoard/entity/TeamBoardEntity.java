package com.example.baseball.teamBoard.entity;

import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.teamBoard.enumType.BoardCategoryEnum;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import com.example.baseball.teamMember.enumType.TeamFounderAcceptRole;
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
@Table(name = "teamBoard")
public class TeamBoardEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer teamBoardId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "team_member_id")
    private TeamMemberEntity teamMember;

    // 선수 이름
    @Column(columnDefinition = "VARCHAR(255) COMMENT '선수 이름'")
    private String teamMembername;

    // 선수 닉네임
    @Column(columnDefinition = "VARCHAR(255) COMMENT '선수 닉네임'")
    private String teamMemberNickname;

    // 공지 / 일반 여부
    @Column(columnDefinition = "VARCHAR(255) COMMENT '공지/일반 여부'")
    private BoardCategoryEnum teamPostCategory;

    // 제목
    @Column(columnDefinition = "VARCHAR(255) COMMENT '제목'")
    private String boardTitle;

    // 제목
    @Column(columnDefinition = "VARCHAR(255) COMMENT '내용'")
    private String boardContent;

    private LocalDateTime teamPostDate;

    private LocalDateTime modificationDate;


}
