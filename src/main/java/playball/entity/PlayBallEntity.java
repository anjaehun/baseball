package playball.entity;

import com.example.baseball.baseballStadiumAndReservation.entity.BaseballStadiumEntity;
import com.example.baseball.baseballStadiumAndReservation.entity.StadiumReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playBall")
public class PlayBallEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long playBallId;

    @ManyToOne
    @JoinColumn(name = "baseballStadiumId")
    private BaseballStadiumEntity baseballStadium;

    @ManyToOne
    @JoinColumn(name = "stadiumReservationId")
    private StadiumReservationEntity stadiumReservation;

    private String playBallName;

    private String baseOneRunner;

    private String baseTwoRunner;

    private String baseThreeRunner;

    private String attackTeamName ;

    private String defendTeamName ;

    private String hitterName;

    private String pitcherName ;

    private String hitterResult ;

    private String stealBase;

    private String winnerTeam ;

    private LocalDateTime resultDate ;


}
