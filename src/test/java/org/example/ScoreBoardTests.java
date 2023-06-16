package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.ScoreBoard.NEGATIVE_SCORES_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ScoreBoardTests {

    String homeTeam = "home";
    String awayTeam = "away";

    @Test
    void WhenNewMatchIsAdded_ShouldReturnTrue() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

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

        int ongoingMatchesBeforeRemoval = scoreBoard.ongoingMatches();
        // when
        boolean result = scoreBoard.finishMatch(homeTeam, awayTeam);

        // then match is successfully removed
        assertFalse(result);
        // ongoing matches is reduces and equal 0
        assertThat(scoreBoard.ongoingMatches()).isEqualTo(ongoingMatchesBeforeRemoval).isEqualTo(0);
    }

    @Test
    void WhenNonExistingMatchIsUpdate_shouldReturnTrue() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        scoreBoard.addMatch(homeTeam, awayTeam);
        scoreBoard.addMatch(homeTeam, awayTeam);

        // when
        boolean result = scoreBoard.updateScore(homeTeam, awayTeam, 0, 1);

        // then
        assertTrue(result);
    }


    @Test
    void WhenMatchIsUpdated_scoreIsUpdated() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        // when
        boolean result = scoreBoard.updateScore(homeTeam, awayTeam, 0, 1);

        // then
        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({"-1,1", "3,-2", "-4,-3"})
    void WhenScoreIsLessThanZero_throwException(int homeTeamScore, int awayTeamScore) {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        // when .. then
        assertThatThrownBy(() -> {
            scoreBoard.updateScore(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(NEGATIVE_SCORES_ERROR_MESSAGE);
    }
}
