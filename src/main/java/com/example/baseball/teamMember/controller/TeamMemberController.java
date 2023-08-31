package com.example.baseball.teamMember.controller;
import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.teamMember.TeamMemberEntity;
import com.example.baseball.teamMember.exception.*;
import com.example.baseball.teamMember.request.TeamMemberApplicationRequest;
import com.example.baseball.teamMember.service.TeamMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    /**
     * 특정 팀 조회
     * @param teamId
     * @return
     */
    @GetMapping("/list/{teamId}/all/team/members")
    public ResponseEntity<List<TeamMemberEntity>> getTeamMemberById(@PathVariable int teamId) {
        try {
            List<TeamMemberEntity> teamMember = teamMemberService.getTeamMemberByIdService(teamId);
            return ResponseEntity.ok(teamMember);
        } catch (NoTeamByOneException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 특정 팀원의 타자 기록 조회
     *
     * @param teamMemberId
     * @return
     */
    @GetMapping("/list/{teamMemberId}/hitter")
    public ResponseEntity<List<HitterRecordEntity>> getHitterRecord(@PathVariable int teamMemberId ) {
        try {
            List<HitterRecordEntity> hitterRecord = teamMemberService.getHitterRecordByTeamMember(teamMemberId);
            return ResponseEntity.ok(hitterRecord);
        } catch (NoTeamByOneException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 특정 팀원의 투수 기록 조회
     *
     * @param teamMemberId
     * @return
     */
    @GetMapping("/list/{teamMemberId}/pitcher")
    public ResponseEntity<List<PitcherRecordEntity>> getPitcherRecord(@PathVariable int teamMemberId ) {
        try {
            List<PitcherRecordEntity> pitcherRecord = teamMemberService.getPitcherRecordByTeamMember(teamMemberId);
            return ResponseEntity.ok(pitcherRecord);
        } catch (NoTeamByOneException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  팀 멤버 승인
     * @param teamId
     * @param teamMemberId
     * @return
     */
    @PutMapping("/{teamId}/{teamMemberId}/membership/approval")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> teamUpdate(
            @PathVariable int teamId,
            @PathVariable int teamMemberId
    ){
        try {
            TeamMemberEntity teamMemberEntity = teamMemberService.updateApproval(teamId, teamMemberId);
            Map<String, Object> response = new HashMap<>();
            response.put("승인 성공하였습니다!", "Success");
            response.put("팀 정보 : ", teamMemberEntity);
            return ResponseEntity.ok(response);
        } catch (NoTeamByOneException e){
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (NotTeamMasterNicknameException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch(approvedTeamMemberException e){
            Map<String, String> response = new HashMap<>();
            response.put("alert", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }










}
