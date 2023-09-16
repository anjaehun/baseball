package com.example.baseball.baseballStadiumAndReservation.service;

import com.example.baseball.baseballStadiumAndReservation.entity.BaseballStadiumEntity;
import com.example.baseball.baseballStadiumAndReservation.entity.StadiumReservationEntity;
import com.example.baseball.baseballStadiumAndReservation.enumType.AwayReservationOkRole;
import com.example.baseball.baseballStadiumAndReservation.enumType.HomeReservationOkRole;
import com.example.baseball.baseballStadiumAndReservation.exception.AlreadyReservationException;
import com.example.baseball.baseballStadiumAndReservation.exception.NoReservationException;
import com.example.baseball.baseballStadiumAndReservation.exception.NoTeamByNicknameException;
import com.example.baseball.baseballStadiumAndReservation.exception.NotStadiumException;
import com.example.baseball.baseballStadiumAndReservation.repository.BaseballStadiumRepository;
import com.example.baseball.baseballStadiumAndReservation.repository.StadiumReservationRepository;
import com.example.baseball.baseballStadiumAndReservation.request.ReservationTimeRequest;
import com.example.baseball.baseballStadiumAndReservation.response.BaseballStadiumPostResponse;
import com.example.baseball.team.entity.TeamEntity;
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
public class StadiumReservationService {

    private final UserRepository userRepository;

    private final StadiumReservationRepository stadiumReservationRepository;

    private final TeamRepository teamRepository;
    private final BaseballStadiumRepository baseballStadiumRepository;

    public StadiumReservationService(UserRepository userRepository, StadiumReservationRepository stadiumReservationRepository, TeamRepository teamRepository, BaseballStadiumRepository baseballStadiumRepository) {
        this.userRepository = userRepository;
        this.stadiumReservationRepository = stadiumReservationRepository;
        this.teamRepository = teamRepository;
        this.baseballStadiumRepository = baseballStadiumRepository;
    }




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
     * 홈팀 등록 -> 홈팀이 등록 됨에 따라 어웨이팀도 등록을 할 수 있다.
     * @param baseballStadiumId
     * @param request
     * @return
     * @throws NoTeamByNicknameException
     * @throws NotStadiumException
     * @throws AlreadyReservationException
     */
    public BaseballStadiumPostResponse homeReservation(Integer baseballStadiumId
            ,ReservationTimeRequest request) throws NoTeamByNicknameException, NotStadiumException, AlreadyReservationException {

        String nickname = userNickname();

        Optional<TeamEntity> optionalStadium = teamRepository.findByMasterNickname(nickname);

        Optional<BaseballStadiumEntity> optionalBaseballStadium = baseballStadiumRepository.findById(baseballStadiumId);

        LocalDateTime wantStartDateTime = request.getStartDateTime();

        LocalDateTime wantEndDateTime = request.getEndDatetime();

        if(optionalStadium.isEmpty()){
            throw new NoTeamByNicknameException("귀하의 팀이 없습니다. 팀을 생성하고 등록해 주세요");
        }

        if(optionalBaseballStadium.isEmpty()){
            throw new NotStadiumException("경기장이 없습니다. 다시 한번 확인해 주세요");
        }

        BaseballStadiumEntity baseballStadium = optionalBaseballStadium.orElse(null);

        if(optionalBaseballStadium.isPresent()){

            List<StadiumReservationEntity> stadiumReservationEntity = stadiumReservationRepository.findAllByBaseballStadium(baseballStadium);

            for (StadiumReservationEntity reservation : stadiumReservationEntity) {
                if (reservation.getHomeTeam().equals(optionalStadium.get().getTeamName())
                        && reservation.getStartDateTime().equals(wantStartDateTime)
                        && reservation.getEndDatetime().equals(wantEndDateTime)) {
                    throw new AlreadyReservationException("홈팀은 이미 예약이 되어있습니다.");
                }
            }
        }

        var stadiumReservation = StadiumReservationEntity.builder()
                .baseballStadium(baseballStadium)
                .homeReservationOkRole(HomeReservationOkRole.HOME_RESERVATION_OK)
                .awayReservationOkRole(AwayReservationOkRole.AWAY_RESERVATION_HOLD)
                .homeTeam(optionalStadium.get().getTeamName())
                .awayTeam("미정")
                .startDateTime(wantStartDateTime)
                .endDatetime(wantEndDateTime)
                .build();

        stadiumReservationRepository.save(stadiumReservation);

        var response = BaseballStadiumPostResponse.builder()
                .postOk("홈팀 예약이 완료 되었습니다.")
                .build();

        return response ;

    }


    public BaseballStadiumPostResponse awayReservation(
           int StadiumReservationId) throws NoReservationException, NoTeamByNicknameException {

        String nickname = userNickname();

        Optional<TeamEntity> optionalStadium = teamRepository.findByMasterNickname(nickname);

        String homeTeam = optionalStadium.get().getTeamName();

        if(!optionalStadium.isPresent()){
            throw new NoTeamByNicknameException("귀하의 팀이 없습니다. 팀을 생성하고 등록해 주세요");
        }

        Optional<StadiumReservationEntity> optionalBaseballStadium = stadiumReservationRepository.findById(StadiumReservationId);

        StadiumReservationEntity stadiumReservation = optionalBaseballStadium.orElseThrow(() -> new NoReservationException("게시글을 찾을 수 없습니다"));

        if(homeTeam.equals(stadiumReservation.getHomeTeam())){
            throw new NoReservationException("이미 홈팀으로 예약을 하셨습니다.");
        }

        stadiumReservation.setAwayReservationOkRole(AwayReservationOkRole.AWAY_RESERVATION_OK);
        stadiumReservation.setAwayTeam(homeTeam);

        stadiumReservationRepository.save(stadiumReservation);

        var postOk = BaseballStadiumPostResponse.builder().
                postOk("어웨이팀 등록이 완료 되었습니다. 감사합니다!").
                build();

        return  postOk;
    }

    public Optional<StadiumReservationEntity> getReservationAll(int stadiumReservationId) {

        Optional<StadiumReservationEntity> optionalBaseballStadium = stadiumReservationRepository.findById(stadiumReservationId);



        return optionalBaseballStadium;
    }
}
