package com.example.baseball.team.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamPostRequest {
    private String teamName;
    private String mainCoach;
    private String teamDescription;
    private String teamImg;
}
