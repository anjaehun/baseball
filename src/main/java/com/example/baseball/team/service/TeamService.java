package com.example.baseball.team.service;

import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.hitterRecord.repository.HitterRecordRepository;
import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.pitcherRecord.repository.PitcherRecordRepository;
import com.example.baseball.team.Request.TeamPostRequest;
import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.team.exception.SameTeamNameException;
import com.example.baseball.team.repository.TeamRepository;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import com.example.baseball.teamMember.enumType.TeamFounderAcceptRole;
import com.example.baseball.teamMember.repository.TeamMemberRepository;
import com.example.baseball.teamPerformance.TeamPerformanceEntity;
import com.example.baseball.teamPerformance.repository.TeamPerformanceRepository;
import com.example.baseball.user.entity.UserEntity;
import com.example.baseball.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository ;
    private final PitcherRecordRepository pitcherRecordRepository;

    private final HitterRecordRepository hitterRecordRepository;

    private final TeamPerformanceRepository teamPerformanceRepository;



    public TeamService(UserRepository userRepository, TeamRepository teamRepository, TeamMemberRepository teamMemberRepository, PitcherRecordRepository pitcherRecordRepository, HitterRecordRepository hitterRecordRepository, TeamPerformanceRepository teamPerformanceRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.pitcherRecordRepository = pitcherRecordRepository;
        this.hitterRecordRepository = hitterRecordRepository;
        this.teamPerformanceRepository = teamPerformanceRepository;
    }

    /**
     * 회원 정보를 가져 오는 역활
     * -> 접속중인 유저의 닉네임을 가져온다
     * @return
     */
    public String userNickname(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = "기본 이메일";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername(); // 사용자 이메일 정보를 추출
        }

        // System.out.println("email: " + email);

        String nickname = "기본 닉네임";
        Optional<UserEntity> existingEmail = userRepository.findByEmail(email);

        if (existingEmail.isPresent()) {
            UserEntity user = existingEmail.get();
            System.out.println(user);
            nickname = user.getNickname(); // 사용자의 닉네임을 얻음
        }

        return nickname;
    }

    /**
     * 회원 정보를 가져 오는 역활
     * -> 접속중인 유저의 이름을 가져온다
     * @return
     */
    public String username(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = "기본 이메일";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername(); // 사용자 이메일 정보를 추출
        }

        // System.out.println("email: " + email);

        String name = "기본 이름";
        Optional<UserEntity> existingEmail = userRepository.findByEmail(email);

        if (existingEmail.isPresent()) {
            UserEntity user = existingEmail.get();
            System.out.println(user);
            name = user.getName(); // 사용자의 닉네임을 얻음
        }

        return name;
    }
    public TeamEntity postTeam(TeamPostRequest request) throws SameTeamNameException, NoTeamByOneException {

        String name = username();
        String nickname = userNickname();
        String mainCoach = request.getMainCoach();

        LocalDateTime currentTime = LocalDateTime.now();

        Optional<UserEntity> existingEmail = userRepository.findByNickname(nickname);
        Optional<TeamEntity> existingTeamName = teamRepository.findByTeamName(request.getTeamName());


        if (existingTeamName.isPresent()) {
            throw new SameTeamNameException("팀의 이름이 중복되었습니다. 다시 시도해 주세요");
        }
        UserEntity user;
        if (existingEmail.isPresent()) {
            user = existingEmail.get();
        } else {
            // 사용자를 찾지 못한 경우에 대한 처리
            user = null; // 또는 다른 값으로 설정
        }

        if(request.getMainCoach().equals("미정")){
            mainCoach = "공석";
        }

        var team = TeamEntity.builder()
                .masterName(name)
                .masterNickname(nickname)
                .teamName(request.getTeamName())
                .mainCoach(mainCoach)
                .teamImg(request.getTeamImg())
                .registerDt(currentTime)
                .id(user)
              .build();

        teamRepository.save(team);

        // 창설자 팀 멤버 등록

        // teamName -> 예외처리로 인해 중복된 팀 네임은 해결 될 수 있음
        TeamEntity findByTeamName = teamRepository.findByTeamName(request.getTeamName())
                .orElseThrow(() -> new NoTeamByOneException("팀정보가 없습니다"));

        var teamPerfomance = TeamPerformanceEntity.builder()
                .team(findByTeamName)
                .win(0.0)
                .lose(0.0)
                .winRate(0.0)
                .build();

        teamPerformanceRepository.save(teamPerfomance);

        var teamMember = TeamMemberEntity.builder()
                .name(name)
                .nickname(nickname)
                // TEAM_MEMBER_OK => 창설자는 따로 인증절차를 걸치지 않고 등록이 가능하다
                .teamFounderAcceptRole(TeamFounderAcceptRole.TEAM_MEMBER_OK)
                .jerseyNumber(request.getJerseyNumber())
                .height(request.getHeight())
                .weight(request.getWeight())
                .reasonForTeamMembership(request.getReasonForTeamMembership())
                .determinationForTheFuture(request.getDeterminationForTheFuture())
                .registerDt(currentTime)
                .team(findByTeamName)
                .build();

        teamMemberRepository.save(teamMember);

        TeamMemberEntity teamMemberByNickname = (TeamMemberEntity) teamMemberRepository.findByNickname(nickname)
                .orElseThrow(() -> new NoTeamByOneException("팀멤버가 없습니다"));

        var pitcherRecord = PitcherRecordEntity.builder()
                .teamName(team.getTeamName())
                .name(teamMemberByNickname.getName())
                .nickname(teamMemberByNickname.getNickname())
                .atBat(0.0).hit(0.0).doubleHit(0.0).tripleHit(0.0).homeRun(0.0).strikeout(0.0).unintentionalWalk(0.0).intentionalWalk(0.0).runsAllowed(0.0).earnedRun(0.0).earnedRunAverage(0.0).whip(0.0).strikeoutPercent(0.0)
                .team(team)
                .teamMember(teamMemberByNickname)
                .build();

        var hitterRecord = HitterRecordEntity.builder()
                .teamName(team.getTeamName())
                .name(teamMemberByNickname.getName())
                .nickname(teamMemberByNickname.getNickname())
                .atBat(0.0).hit(0.0).doubleHit(0.0).tripleHit(0.0).homeRun(0.0).unintentionalWalk(0.0).intentionalWalk(0.0).hitByPitch(0.0).runsBattedIn(0.0).runs(0.0).stolenBases(0.0).attempts(0.0).strikeout(0.0).battingAverage(0.0).sluggingPercentage(0.0).onBasePercentage(0.0).stolenBaseSuccessRate(0.0)
                .team(team)
                .teamMember(teamMemberByNickname)
                .build();

        pitcherRecordRepository.save(pitcherRecord);
        hitterRecordRepository.save(hitterRecord);

        return team;
    }


    public List<TeamEntity> getAllTeamList() {
        List<TeamEntity> teamList = teamRepository.findAll();
        return teamList;

    }

    public TeamEntity getTeamByTeamId(Integer teamId) throws NoTeamByOneException {
        Optional<TeamEntity> optionalTeam = teamRepository.findById(teamId);
        System.out.println(optionalTeam);
        

        if (optionalTeam.isPresent()) {
            return optionalTeam.get();
        } else {
            throw new NoTeamByOneException("팀정보가 없습니다");
        }
    }






}
