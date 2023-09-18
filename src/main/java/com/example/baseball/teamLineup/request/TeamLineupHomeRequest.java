package com.example.baseball.teamLineup.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamLineupHomeRequest {
    private String teamName;
    private String matchName;
}
