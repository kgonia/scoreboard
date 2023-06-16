package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ScoreBoardTests {

    @Test
    void WhenNewMatchIsAdded_ShouldReturnTrue() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeam = "home";
        String awayTeam = "away";

        // when match is added
        boolean result = scoreBoard.addMatch(homeTeam, awayTeam);

        // then
        assertThat(result).isTrue();
        assertThat(scoreBoard.ongoingMatches()).isEqualTo(1);
    }

    @Test
    void WhenDuplicatedMatchIsAdded_nothingChanges() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeam = "home";
        String awayTeam = "away";

        // when match is added
        scoreBoard.addMatch(homeTeam, awayTeam);
        boolean result = scoreBoard.addMatch(homeTeam, awayTeam);

        // then
        assertFalse(result);
        assertThat(scoreBoard.ongoingMatches()).isEqualTo(1);
    }

    @Test
    void WhenMatchIsRemoved_ongoingMatchesShouldDecrease() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeam = "home";
        String awayTeam = "away";

        // when match is added
        scoreBoard.addMatch(homeTeam, awayTeam);
        int ongoingMatchesAfterAddition = scoreBoard.ongoingMatches();

        // and removed
        boolean result = scoreBoard.finishMatch(homeTeam, awayTeam);

        // then match is successfully removed
        assertTrue(result);
        // ongoing matches is reduces and equal 0
        assertThat(scoreBoard.ongoingMatches()).isEqualTo(ongoingMatchesAfterAddition - 1).isEqualTo(0);
    }

    @Test
    void WhenNonExistingMatchIsRemoved_nothingChanges() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        String homeTeam = "home";
        String awayTeam = "away";

        int ongoingMatchesBeforeRemoval = scoreBoard.ongoingMatches();
        // when
        boolean result = scoreBoard.finishMatch(homeTeam, awayTeam);

        // then match is successfully removed
        assertFalse(result);
        // ongoing matches is reduces and equal 0
        assertThat(scoreBoard.ongoingMatches()).isEqualTo(ongoingMatchesBeforeRemoval).isEqualTo(0);
    }
}
