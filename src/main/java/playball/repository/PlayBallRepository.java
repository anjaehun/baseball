package playball.repository;

import com.example.baseball.baseballStadiumAndReservation.entity.StadiumReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import playball.entity.PlayBallEntity;

public interface PlayBallRepository extends JpaRepository<PlayBallEntity, Integer> {

}
