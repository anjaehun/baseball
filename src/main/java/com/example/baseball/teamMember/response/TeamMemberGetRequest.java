package com.example.baseball.teamMember.response;

import com.example.baseball.teamMember.enumType.TeamRole;
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
public class TeamMemberGetRequest {
     private int teamId ;
     private int TeamMemberId;
     private String name;
     private String teamName;
     private double jerseyNumber;
     private TeamRoleEnumType teamRole;
     private LocalDateTime registerDt;
     private String reasonForTeamMembership;
     private String determinationForTheFuture;




}
