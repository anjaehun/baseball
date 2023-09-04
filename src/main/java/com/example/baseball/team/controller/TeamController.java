package com.example.baseball.team.controller;

import com.example.baseball.team.Request.TeamPostRequest;
import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.team.exception.SameTeamNameException;
import com.example.baseball.team.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/team")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> createTeam(@RequestBody TeamPostRequest request) {
        try {
            TeamEntity createdTeam = teamService.postTeam(request);
            return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
        } catch (SameTeamNameException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NoTeamByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<TeamEntity>> getAllTeams() {
        List<TeamEntity> teamList = teamService.getAllTeamList();
        return ResponseEntity.ok(teamList);
    }

    @GetMapping("/list/{teamId}")
    public ResponseEntity<Object> getTeamById(@PathVariable Integer teamId) {
        try {
            TeamEntity team = teamService.getTeamByTeamId(teamId);
            return ResponseEntity.ok(team);
        } catch (NoTeamByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
