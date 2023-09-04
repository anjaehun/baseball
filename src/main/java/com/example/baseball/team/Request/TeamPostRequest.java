package com.example.baseball.team.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class TeamPostRequest {
    private String teamName;
    private String mainCoach;
    private String teamDescription;
    private String teamImg; // "teamImage" 필드 대신 "teamImg"로 변경
    private Integer jerseyNumber;
    private Integer height;
    private Integer weight;
    private String reasonForTeamMembership;
    private String determinationForTheFuture;

}
