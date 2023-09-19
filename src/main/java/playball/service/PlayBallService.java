package playball.service;

import com.example.baseball.baseballStadiumAndReservation.repository.BaseballStadiumRepository;
import com.example.baseball.baseballStadiumAndReservation.repository.StadiumReservationRepository;
import com.example.baseball.hitterRecord.HitterRecordEntity;
import com.example.baseball.hitterRecord.repository.HitterRecordRepository;
import com.example.baseball.pitcherRecord.PitcherRecordEntity;
import com.example.baseball.pitcherRecord.repository.PitcherRecordRepository;
import org.springframework.stereotype.Service;
import playball.entity.PlayBallEntity;
import playball.repository.PlayBallRepository;
import playball.request.PlayBallRequest;

import java.time.LocalDateTime;

@Service
public class PlayBallService {
    private final BaseballStadiumRepository baseballStadiumRepository;

    private final StadiumReservationRepository stadiumReservationRepository;

    private final HitterRecordRepository hitterRecordRepository;

    private final PitcherRecordRepository pitcherRecordRepository ;


    private final PlayBallRepository playballRepository;

    public PlayBallService(BaseballStadiumRepository baseballStadiumRepository, StadiumReservationRepository stadiumReservationRepository, HitterRecordRepository hitterRecordRepository, PitcherRecordRepository pitcherRecordRepository, PlayBallRepository playballRepository) {
        this.baseballStadiumRepository = baseballStadiumRepository;
        this.stadiumReservationRepository = stadiumReservationRepository;
        this.hitterRecordRepository = hitterRecordRepository;
        this.pitcherRecordRepository = pitcherRecordRepository;
        this.playballRepository = playballRepository;
    }


    public PlayBallEntity recordPlayBall(PlayBallRequest playBallRequest) {

        String hitterResult = playBallRequest.getHitterResult();

        if("안타".equals(hitterResult)) {
            HitterRecordEntity hitterRecord = hitterRecordRepository
                    .findByNameAndTeamName(playBallRequest.getHitterName(), playBallRequest.getAttackTeamName());

            Double plusHit = hitterRecord.getHit() + 1;
            Double atBat = hitterRecord.getAtBat() + 1;

            hitterRecord.setHit(plusHit);
            hitterRecord.setAtBat(atBat);
            hitterRecordRepository.save(hitterRecord);


            PitcherRecordEntity pitcherRecord = pitcherRecordRepository
                    .findByNameAndTeamName(playBallRequest.getPitcherName(),playBallRequest.getDefendTeamName());

            Double HitAllow = pitcherRecord.getHit() + 1;
            Double atBatByPitcher = pitcherRecord.getAtBat() + 1 ;

            pitcherRecord.setHit(HitAllow);
            pitcherRecord.setAtBat(atBatByPitcher);
        }


        var playBall = PlayBallEntity.builder()
                .playBallName(playBallRequest.getPlayBallName())
                .playBallName(playBallRequest.getBaseOneRunner())
                .baseTwoRunner(playBallRequest.getBaseTwoRunner())
                .baseThreeRunner(playBallRequest.getBaseThreeRunner())
                .attackTeamName(playBallRequest.getAttackTeamName())
                .defendTeamName(playBallRequest.getDefendTeamName())
                .hitterName(playBallRequest.getHitterName())
                .pitcherName(playBallRequest.getPitcherName())
                .hitterResult(hitterResult)
                .winnerTeam(playBallRequest.getWinnerTeam())
                .resultDate(LocalDateTime.now())
                .build();

        playballRepository.save(playBall);

       return playBall;
    }
}
