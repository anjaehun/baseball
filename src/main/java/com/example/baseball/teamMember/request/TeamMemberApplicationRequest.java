package com.example.baseball.teamMember.request;

import com.example.baseball.teamMember.enumType.TeamFounderAcceptRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberApplicationRequest {

    private int jerseyNumber;
    private int height;
    private int weight;
    private String reasonForTeamMembership;
    private String determinationForTheFuture;


}
