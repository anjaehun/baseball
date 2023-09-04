package com.example.baseball.teamMember.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamPitcherGetRequest {

    private double atBat;
    private double hit;
    private double doubleHit;
    private double tripleHit;
    private double homeRun;
    private double strikeout;
    private double unintentionalWalk;
    private double intentionalWalk;
    private double runsAllowed;
    private double earnedRun;
    private double earnedRunAverage;
    private double whip;
    private double strikeoutPercent;

    private String name ;
    private String teamName;
    private Integer jerseyNumber;

}
