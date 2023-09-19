package com.example.baseball.teamLineup.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamLineupRequest {
    private String playerName;
    private Boolean subYn;
    private String battingOrder;
    private String baseballPosition;
    private Integer jerseyNumber;
    private String matchName;

}
