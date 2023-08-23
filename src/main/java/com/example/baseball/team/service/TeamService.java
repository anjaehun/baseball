package com.example.baseball.team.service;

import com.example.baseball.team.Request.TeamPostRequest;
import com.example.baseball.team.TeamEntity;
import com.example.baseball.team.repository.TeamRepository;
import com.example.baseball.user.entity.UserEntity;
import com.example.baseball.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository ;

    public TeamService(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

//    public StoreEntity write(StorePostRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        String email;
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            email = userDetails.getUsername(); // 사용자 이메일 정보를 추출
//        } else {
//            email = "기본 닉네임";
//        }
//
//        // System.out.println("email: " + email);
//
//        String nickname = "";
//        Optional<UserEntity> existingEmail = userRepository.findByEmail(email);
//        if (existingEmail.isPresent()) {
//            UserEntity user = existingEmail.get();
//            // System.out.println(user);
//            nickname = user.getNickname(); // 사용자의 닉네임을 얻음
//        }
//
//        System.out.println(nickname);
//
//        LocalDateTime currentTime = LocalDateTime.now(); // 현재 시간 설정
//
//        var store = StoreEntity.builder()
//                .storeName(request.getStoreName())
//                .location(request.getLocation())
//                .explanation(request.getExplanation())
//                .xCoordinate(request.getXCoordinate())
//                .yCoordinate(request.getYCoordinate())
//                .author(nickname)
//                .star(0.0)
//                .storeImageUrlOne(request.getStoreImageUrlOne())
//                .storeImageUrlTwo(request.getStoreImageUrlTwo())
//                .storeImageUrlThree(request.getStoreImageUrlThree())
//                .registerDt(currentTime)
//                .isOpen(request.isOpen())
//                .build();
//        return storeRepository.save(store);
//    }

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
    public TeamEntity postTeam(TeamPostRequest request) {

        String name = username();
        String nickname = userNickname();
        String mainCoach = request.getMainCoach();

        LocalDateTime currentTime = LocalDateTime.now();

        Optional<UserEntity> existingEmail = userRepository.findByNickname(nickname);


            UserEntity user = existingEmail.get();
            // System.out.println(user);



        if(request.getMainCoach().equals("미정")){
            mainCoach = "공석";
        }

        var store = TeamEntity.builder()
                .masterName(name)
                .masterNickname(nickname)
                .teamName(request.getTeamName())
                .mainCoach(mainCoach)
                .teamImg(request.getTeamImg())
                .registerDt(currentTime)
                .id(user)
              .build();

        teamRepository.save(store);



        return null;
    }
}
