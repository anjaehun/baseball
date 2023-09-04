package com.example.baseball.teamMember.service;

import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.hitterRecord.repository.HitterRecordRepository;
import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.pitcherRecord.repository.PitcherRecordRepository;
import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.team.repository.TeamRepository;
import com.example.baseball.teamMember.entity.TeamMemberEntity;
import com.example.baseball.teamMember.enumType.TeamFounderAcceptRole;
import com.example.baseball.teamMember.exception.*;
import com.example.baseball.teamMember.repository.TeamMemberRepository;
import com.example.baseball.teamMember.request.TeamMemberApplicationRequest;
import com.example.baseball.teamMember.request.TeamMemberCreaterApplicationRequest;
import com.example.baseball.user.entity.UserEntity;
import com.example.baseball.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository ;

    private final TeamMemberRepository teamMemberRepository;

    private final PitcherRecordRepository pitcherRecordRepository;

    private final HitterRecordRepository hitterRecordRepository;

    public TeamMemberService(UserRepository userRepository, TeamRepository teamRepository, TeamMemberRepository teamMemberRepository, PitcherRecordRepository pitcherRecordRepository, HitterRecordRepository hitterRecordRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.pitcherRecordRepository = pitcherRecordRepository;
        this.hitterRecordRepository = hitterRecordRepository;
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

    public TeamMemberEntity membershipApplication(Integer teamId, TeamMemberApplicationRequest request) throws NoTeamByOneException, JerseyNumberAlreadyExistsException, MemberAlreadyRegisteredException, NoMasterJoinTeamException {
        // 이름
        String name = username();
        // 닉네임
        String nickname = userNickname();
        // 등번호
        int jerseyNumber = request.getJerseyNumber();
        // 현재 시간
        LocalDateTime currentTime = LocalDateTime.now();
        // 팀 아이디 찾는 컬럼 및 예외처리
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NoTeamByOneException("팀정보가 없습니다"));
        //팀창설자 조회
        String masterNickname = team.getMasterNickname();

        // 등번호 조회 후 있다면 예외처리
        if (teamMemberRepository.existsByTeamAndJerseyNumber(team, jerseyNumber)) {
            throw new JerseyNumberAlreadyExistsException("해당 등번호는 이미 사용 중입니다.");
        }

        // 사용자 중복 등록 확인
        if (teamMemberRepository.existsByTeamAndNameAndNickname(team, name, nickname)) {
            throw new MemberAlreadyRegisteredException("이미 등록된 사용자입니다.");
        }

        // 창설자가 창설자의 팀을 가입했을 때의 에러
        if(masterNickname.equals(nickname)){
            throw new NoMasterJoinTeamException("창설자는 가입할 수 없습니다.");
        }

        // 팀가입 관련
        var teamJoin = TeamMemberEntity.builder()
                .name(name)
                .nickname(nickname)
                // WAITING_FOR_APPROVAL => 팀 창설자의 승인 대기
                .teamFounderAcceptRole(TeamFounderAcceptRole.WAITING_FOR_APPROVAL)
                .jerseyNumber(jerseyNumber)
                .height(request.getHeight())
                .weight(request.getWeight())
                .reasonForTeamMembership(request.getReasonForTeamMembership())
                .determinationForTheFuture(request.getDeterminationForTheFuture())
                .registerDt(currentTime)
                .team(team)
                .build();

        teamMemberRepository.save(teamJoin);

        return teamJoin;
    }





    public TeamMemberEntity updateApproval(int teamId, int teamMemberId) throws NoTeamByOneException, NotTeamMasterNicknameException, approvedTeamMemberException {

        // 닉네임
        String nickname = userNickname();

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NoTeamByOneException("팀정보가 없습니다"));

        String teamMasterNickname = team.getMasterNickname();

        TeamMemberEntity teamMember =  teamMemberRepository.findById(teamMemberId)
                .orElseThrow(() -> new NoTeamByOneException("팀멤버가 없습니다"));

        if(teamMember.getTeamFounderAcceptRole().equals(TeamFounderAcceptRole.TEAM_MEMBER_OK)){
            throw new approvedTeamMemberException("이미 팀 멤버입니다.");
        }

        if(!(nickname.equals(teamMasterNickname))){
            throw new NotTeamMasterNicknameException("팀 창설자만 승인을 할 수 있습니다. 다시 한번 확인해 주세요");
        }

        teamMember.setTeamFounderAcceptRole(TeamFounderAcceptRole.TEAM_MEMBER_OK);
        teamMemberRepository.save(teamMember);

//       승인에 성공 하면 팀 정식 일원으로 인정이 되며, 그에 따라 투수 기록과
//       타자 기록이 생성된다. 또한 기본 기록은 전부 0이며, 경기의 결과에 따라 업데이트 된다.
        var pitcherRecord = PitcherRecordEntity.builder()
                .teamName(team.getTeamName())
                .name(teamMember.getName())
                .nickname(teamMember.getNickname())
                .atBat(0.0).hit(0.0).doubleHit(0.0).tripleHit(0.0).homeRun(0.0).strikeout(0.0).unintentionalWalk(0.0).intentionalWalk(0.0).runsAllowed(0.0).earnedRun(0.0).earnedRunAverage(0.0).whip(0.0).strikeoutPercent(0.0)
                .team(team)
                .teamMember(teamMember)
                .build();

        var hitterRecord = HitterRecordEntity.builder()
                .teamName(team.getTeamName())
                .name(teamMember.getName())
                .nickname(teamMember.getNickname())
                .atBat(0.0).hit(0.0).doubleHit(0.0).tripleHit(0.0).homeRun(0.0).unintentionalWalk(0.0).intentionalWalk(0.0).hitByPitch(0.0).runsBattedIn(0.0).runs(0.0).stolenBases(0.0).attempts(0.0).strikeout(0.0).battingAverage(0.0).sluggingPercentage(0.0).onBasePercentage(0.0).stolenBaseSuccessRate(0.0)
                .team(team)
                .teamMember(teamMember)
                .build();

        pitcherRecordRepository.save(pitcherRecord);
        hitterRecordRepository.save(hitterRecord);

        return teamMember;
    }

    public TeamMemberEntity membershipCreaterApplication(int teamId, TeamMemberCreaterApplicationRequest request) throws NoTeamByOneException, approvedTeamMemberException, NotTeamMasterNicknameException {
        String nickname = userNickname();

        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NoTeamByOneException("팀정보가 없습니다"));

        String teamMasterNickname = team.getMasterNickname();

        TeamMemberEntity teamMember =  teamMemberRepository.findById(teamId)
                .orElseThrow(() -> new NoTeamByOneException("팀멤버가 없습니다"));

        if(teamMember.getTeamFounderAcceptRole().equals(TeamFounderAcceptRole.TEAM_MEMBER_OK)){
            throw new approvedTeamMemberException("이미 팀 멤버입니다.");
        }

        if(!(nickname.equals(teamMasterNickname))){
            throw new NotTeamMasterNicknameException("팀 창설자만 승인을 할 수 있습니다. 다시 한번 확인해 주세요");
        }

        teamMember.setTeamFounderAcceptRole(TeamFounderAcceptRole.TEAM_MEMBER_OK);
        teamMemberRepository.save(teamMember);

//       승인에 성공 하면 팀 정식 일원으로 인정이 되며, 그에 따라 투수 기록과
//       타자 기록이 생성된다. 또한 기본 기록은 전부 0이며, 경기의 결과에 따라 업데이트 된다.
        var pitcherRecord = PitcherRecordEntity.builder()
                .teamName(team.getTeamName())
                .name(teamMember.getName())
                .nickname(teamMember.getNickname())
                .atBat(0.0).hit(0.0).doubleHit(0.0).tripleHit(0.0).homeRun(0.0).strikeout(0.0).unintentionalWalk(0.0).intentionalWalk(0.0).runsAllowed(0.0).earnedRun(0.0).earnedRunAverage(0.0).whip(0.0).strikeoutPercent(0.0)
                .team(team)
                .teamMember(teamMember)
                .build();

        var hitterRecord = HitterRecordEntity.builder()
                .teamName(team.getTeamName())
                .name(teamMember.getName())
                .nickname(teamMember.getNickname())
                .atBat(0.0).hit(0.0).doubleHit(0.0).tripleHit(0.0).homeRun(0.0).unintentionalWalk(0.0).intentionalWalk(0.0).hitByPitch(0.0).runsBattedIn(0.0).runs(0.0).stolenBases(0.0).attempts(0.0).strikeout(0.0).battingAverage(0.0).sluggingPercentage(0.0).onBasePercentage(0.0).stolenBaseSuccessRate(0.0)
                .team(team)
                .teamMember(teamMember)
                .build();

        pitcherRecordRepository.save(pitcherRecord);
        hitterRecordRepository.save(hitterRecord);

        return teamMember;

    }

    // 조회
    @Transactional
    public List<TeamMemberEntity> getTeamMemberByIdService(int teamId) throws NoTeamByOneException {
        TeamEntity team = teamRepository.findById(Math.toIntExact(teamId))
                .orElseThrow(() -> new NoTeamByOneException("팀을 찾을수 없습니다."));

        List<TeamMemberEntity> teamMember = teamMemberRepository.findByTeam(team);

        return teamMember;
    }

    public List<HitterRecordEntity> getHitterRecordByTeamMember(int teamMemberId) throws NoTeamByOneException {
        Optional<TeamMemberEntity> teamMemberOptional = teamMemberRepository.findById(teamMemberId);

        if (teamMemberOptional.isEmpty()) {
            throw new NoTeamByOneException("팀 멤버를 찾을 수 없습니다.");
        }

        TeamMemberEntity teamMember = teamMemberOptional.get();
        List<HitterRecordEntity> hitterRecords = hitterRecordRepository.findByTeamMember(teamMember);

        return hitterRecords;
    }


    public List<PitcherRecordEntity> getPitcherRecordByTeamMember(int teamMemberId) throws NoTeamByOneException {
        Optional<TeamMemberEntity> teamMemberOptional = teamMemberRepository.findById(teamMemberId);

        if (teamMemberOptional.isEmpty()) {
            throw new NoTeamByOneException("팀 멤버를 찾을 수 없습니다.");
        }

        TeamMemberEntity teamMember = teamMemberOptional.get();
        List<PitcherRecordEntity> pitcherRecords = pitcherRecordRepository.findByTeamMember(teamMember);

        return pitcherRecords;
    }



}
