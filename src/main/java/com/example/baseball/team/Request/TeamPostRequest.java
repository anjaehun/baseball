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
    private String teamImg;
    private Integer jerseyNumber;
    private Integer height;
    private Integer weight;
    private String teamLogoImage;
    private String reasonForTeamMembership;
    private String determinationForTheFuture;

}
