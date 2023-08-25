package com.example.baseball.teamMember.controller;

import com.example.baseball.team.Request.TeamPostRequest;
import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.team.exception.SameTeamNameException;
import com.example.baseball.teamMember.TeamMemberEntity;
import com.example.baseball.teamMember.exception.JerseyNumberAlreadyExistsException;
import com.example.baseball.teamMember.exception.MemberAlreadyRegisteredException;
import com.example.baseball.teamMember.exception.NoCreaterJoinException;
import com.example.baseball.teamMember.exception.NoMasterJoinTeamException;
import com.example.baseball.teamMember.request.TeamMemberApplicationRequest;
import com.example.baseball.teamMember.service.TeamMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/team/teamMember")
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    /**
     * 팀 멤버 아이디를 찾고 팀 등록을 한다.
     * @param teamId
     * @param request
     * @return
     */
    @PostMapping("/{teamId}/membership/application")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> teamPost(
            @PathVariable int teamId,
            @RequestBody TeamMemberApplicationRequest request){
        try {
            TeamMemberEntity teamMemberEntity = teamMemberService.membershipApplication(teamId, request);
            Map<String, Object> response = new HashMap<>();
            response.put("등록 성공하였습니다!", "Success");
            response.put("팀 정보 : ", teamMemberEntity);
            return ResponseEntity.ok(response);
        } catch (NoTeamByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (JerseyNumberAlreadyExistsException | MemberAlreadyRegisteredException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (NoMasterJoinTeamException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }







}
