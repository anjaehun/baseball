package playball.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayBallRequest {

    private String playBallName;

    private String baseOneRunner;

    private String baseTwoRunner;

    private String baseThreeRunner;

    private String attackTeamName;

    private String defendTeamName;

    private String hitterName;

    private String pitcherName;

    private String hitterResult;

    private String StealBase;

    private String winnerTeam;

}
