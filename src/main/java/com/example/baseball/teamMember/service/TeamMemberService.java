package com.example.baseball.teamMember.service;

import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.team.repository.TeamRepository;
import com.example.baseball.teamMember.TeamMemberEntity;
import com.example.baseball.teamMember.enumType.TeamFounderAcceptRole;
import com.example.baseball.teamMember.exception.JerseyNumberAlreadyExistsException;
import com.example.baseball.teamMember.exception.MemberAlreadyRegisteredException;
import com.example.baseball.teamMember.exception.NoCreaterJoinException;
import com.example.baseball.teamMember.exception.NoMasterJoinTeamException;
import com.example.baseball.teamMember.repository.TeamMemberRepository;
import com.example.baseball.teamMember.request.TeamMemberApplicationRequest;
import com.example.baseball.user.entity.UserEntity;
import com.example.baseball.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TeamMemberService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository ;

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(UserRepository userRepository, TeamRepository teamRepository, TeamMemberRepository teamMemberRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
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
        } else {
            System.out.println("등번호 중복 확인 통과");
        }

     // 사용자 중복 등록 확인
        if (teamMemberRepository.existsByTeamAndNameAndNickname(team, name, nickname)) {
            throw new MemberAlreadyRegisteredException("이미 등록된 사용자입니다.");
        } else {
            System.out.println("사용자 중복 등록 확인 통과");
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


}
