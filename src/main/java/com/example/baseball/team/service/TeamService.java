package com.example.baseball.team.service;

import com.example.baseball.team.Request.TeamPostRequest;
import com.example.baseball.team.entity.TeamEntity;
import com.example.baseball.team.exception.NoTeamByOneException;
import com.example.baseball.team.exception.SameTeamNameException;
import com.example.baseball.team.repository.TeamRepository;
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
    private final TeamRepository teamRepository ;

    public TeamService(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
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
    public TeamEntity postTeam(TeamPostRequest request) throws SameTeamNameException {

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
