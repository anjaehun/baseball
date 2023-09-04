package com.example.baseball.team.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamListShowByTeamIdResponse {
    private int teamId;
    private String teamName;
    private String mainCoach;
    private String teamDescription;
    private String teamLogoImage;
    private String teamImg;
}
