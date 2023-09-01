package com.example.baseball.teamMember.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberCreaterApplicationRequest {

    private int jerseyNumber;
    private int height;
    private int weight;
    private String reasonForTeamMembership;
    private String determinationForTheFuture;

}
