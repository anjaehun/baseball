package com.example.baseball.teamLineup.controller;

import com.example.baseball.teamLineup.entity.TeamLineupEntity;
import com.example.baseball.teamLineup.exception.NotSearchByTeamMemberException;
import com.example.baseball.teamLineup.request.TeamLineupHomeRequest;
import com.example.baseball.teamLineup.request.TeamLineupRequest;

import com.example.baseball.teamLineup.response.TeamLineupResponse;
import com.example.baseball.teamLineup.service.NoduplicateJerseyNumberException;
import com.example.baseball.teamLineup.service.TeamLineUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/team/lineups")
public class TeamLineUpController {
    private final TeamLineUpService teamLineupService;

    public TeamLineUpController(TeamLineUpService teamLineupService) {
        this.teamLineupService = teamLineupService;
    }

    @PostMapping("/{teamId}/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object>createTeamLineup(
            @PathVariable Long teamId,
            @RequestBody TeamLineupRequest teamLineup
    ) {
        try {
            TeamLineupResponse response = teamLineupService.createTeamLineup(teamId, teamLineup);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (NotSearchByTeamMemberException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (NoduplicateJerseyNumberException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/list/match/home")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> listHomeLineUp(
            @RequestBody TeamLineupHomeRequest teamLineup
    ) {
        List<TeamLineupEntity> response = teamLineupService.homeLineupList(teamLineup);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
