package com.example.baseball.teamBoard.controller;

import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.teamBoard.entity.TeamBoardEntity;
import com.example.baseball.teamBoard.exception.NoBoardByOneException;
import com.example.baseball.teamBoard.exception.NotTheAuthorOfThePostException;
import com.example.baseball.teamBoard.request.BoardRequest;
import com.example.baseball.teamBoard.request.BoardUpdateRequest;
import com.example.baseball.teamBoard.service.TeamBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/team/teamMember/board")
public class TeamBoardController {
    private final TeamBoardService teamBoardService;

    public TeamBoardController(TeamBoardService teamBoardService) {
        this.teamBoardService = teamBoardService;
    }

    /**
     * 일반 게시판 작성
     * @param teamId
     * @param boardRequest
     * @return
     */
    @PostMapping("/create/{teamId}/general")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> createTeamBoardGeneral(
            @PathVariable Long teamId,
            @RequestBody BoardRequest boardRequest) {
        try {
            TeamBoardEntity createdBoard = teamBoardService.createTeamBoardGeneral(teamId, boardRequest);
            return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NoTeamByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * 공지 게시판 작성
     * @param teamId
     * @param boardRequest
     * @return
     */
    @PostMapping("/create/{teamId}/notice")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> createTeamBoardNotice(
            @PathVariable Long teamId,
            @RequestBody BoardRequest boardRequest) {
        try {
            TeamBoardEntity createdBoard = teamBoardService.createTeamBoardNotice(teamId, boardRequest);
            return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NoTeamByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/team/{teamId}/boards")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<TeamBoardEntity>> getAllTeamBoards(@PathVariable Long teamId) {
        try {
            List<TeamBoardEntity> boards = teamBoardService.getAllTeamBoards(teamId);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        } catch (NoTeamByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((List<TeamBoardEntity>) response);
        }
    }

    @GetMapping("/team/{teamId}/boards/{boardId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> getTeamBoardById(
            @PathVariable Long teamId,
            @PathVariable Integer boardId
    ) {
        try {
            TeamBoardEntity board = teamBoardService.getTeamBoardById(teamId, boardId);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (NoTeamByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NoBoardByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/team/{teamId}/boards/{boardId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Object> updateTeamBoard(
            @PathVariable Long teamId,
            @PathVariable Integer boardId,
            @RequestBody BoardUpdateRequest boardUpdateRequest
    ) {
        try {
            TeamBoardEntity updatedBoard = teamBoardService.updateTeamBoard(teamId, boardId, boardUpdateRequest);
            return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
        } catch (NoBoardByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NotTheAuthorOfThePostException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (NoTeamByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/team/{teamId}/boards/{boardId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> deleteTeamBoard(
            @PathVariable Long teamId,
            @PathVariable Integer boardId
    ) {
        try {
            teamBoardService.deleteTeamBoardById(teamId, boardId);
            return new ResponseEntity<>("게시글이 삭제되었습니다.", HttpStatus.OK);
        } catch (NoTeamByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        } catch (NoBoardByOneException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        } catch (NotTheAuthorOfThePostException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        }
    }

}
