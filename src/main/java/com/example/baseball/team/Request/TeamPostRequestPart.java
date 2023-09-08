package com.example.baseball.team.Request;


import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;

@Data
public class TeamPostRequestPart {
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
