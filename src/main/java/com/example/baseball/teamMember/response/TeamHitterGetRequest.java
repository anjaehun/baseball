package com.example.baseball.teamMember.response;

import com.example.baseball.teamMember.enumType.TeamRoleEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamHitterGetRequest {
     private String name ;
     private String teamName;
     private Integer jerseyNumber;

     private double atBat;
     private double hit;
     private double doubleHit;
     private double tripleHit;

     private double homeRun;

     private double unintentionalWalk;

     private double intentionalWalk;

     private double hitByPitch;

     private double runsBattedIn;
     private double runs;

     private double stolenBases;

     private double attempts;

     private double strikeout ;

     private double battingAverage;

     private double sluggingPercentage ;

     private double onBasePercentage ;

     private double stolenBaseSuccessRate;




}
