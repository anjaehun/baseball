package com.example.baseball.teamLineup.service;

import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.team.repository.TeamRepository;
import com.example.baseball.teamLineup.entity.TeamLineupEntity;
import com.example.baseball.teamLineup.exception.NotSearchByTeamMemberException;
import com.example.baseball.teamLineup.repository.TeamLineupRepository;
import com.example.baseball.teamLineup.request.TeamLineupHomeRequest;
import com.example.baseball.teamLineup.request.TeamLineupRequest;
import com.example.baseball.teamLineup.response.TeamLineupResponse;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import com.example.baseball.teamMember.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeamLineUpService {

    private final TeamRepository teamRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final TeamLineupRepository teamLineupRepository;


    public TeamLineUpService(TeamRepository teamRepository, TeamMemberRepository teamMemberRepository, TeamLineupRepository teamLineupRepository) {
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.teamLineupRepository = teamLineupRepository;
    }

    public TeamLineupResponse createTeamLineup(
            Long teamId, TeamLineupRequest teamLineup) throws NotSearchByTeamMemberException, NoduplicateJerseyNumberException {

        TeamEntity team = teamRepository.findById(Math.toIntExact(teamId))
                .orElseThrow(() -> new NotSearchByTeamMemberException("Team not found for ID: " + teamId));


        String name = teamLineup.getPlayerName();
        int jerseyNumber = teamLineup.getJerseyNumber();
        String teamName = team.getTeamName();
        // 데이터 -> {homeTeamName} vs {awayTeamName}
        String matchName = teamLineup.getMatchName();


        Optional<TeamMemberEntity> teamMembers = teamMemberRepository.findByNameAndJerseyNumber(name,jerseyNumber);

        if (teamMembers.isEmpty()) {
            throw new NotSearchByTeamMemberException("해당 팀("+teamName+")의 이름: " + name + " 과 등번호: " + jerseyNumber+"를 찾지 못하였습니다.");
        }

        List<TeamLineupEntity> teamLineUpByCheckList = teamLineupRepository.findByJerseyNumber(jerseyNumber);
       if (teamLineUpByCheckList.size() > 1) {
            // 조회 결과가 여러 개인 경우 예외 처리 또는 원하는 작업 수행
           throw new NoduplicateJerseyNumberException("라인업은 중복이 되면 안됩니다.");
        }

        Boolean subYn = teamLineup.getSubYn();
        String battingOrder = teamLineup.getBattingOrder();
        String baseballPosition = teamLineup.getBaseballPosition();


        LocalDateTime postDt = LocalDateTime.now();

        var teamLineUp = TeamLineupEntity.builder()
                .team(team)

                .matchName(matchName)
                .playerName(name)
                .teamName(teamName)
                .subYn(subYn)
                .battingOrder(battingOrder)
                .jerseyNumber(jerseyNumber)
                .baseballPosition(baseballPosition)
                .postDt(postDt)
                .build();

        teamLineupRepository.save(teamLineUp);

        return TeamLineupResponse.builder()
                .postOk(matchName + " 전 라인업 등록완료")
                .build();
    }

    public List<TeamLineupEntity> homeLineupList(TeamLineupHomeRequest teamLineup) {

        List<TeamLineupEntity> homeLineup = teamLineupRepository.findByTeamNameAndMatchName(teamLineup.getTeamName(), teamLineup.getMatchName());
        return homeLineup;
    }


}
