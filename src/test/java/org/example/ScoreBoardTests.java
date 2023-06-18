package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.ScoreBoard.NEGATIVE_SCORES_ERROR_MESSAGE;
import static org.example.ScoreBoard.NULL_VALUES_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ScoreBoardTests {

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
    void WhenNullAsTeamNameIsProvided_throwException() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        // when .. then
        assertThatThrownBy(() -> scoreBoard.addMatch(homeTeam, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(NULL_VALUES_ERROR_MESSAGE);
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
    void WhenNullAsTeamNameIsProvidedToFinish_throwException() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        // when .. then
        assertThatThrownBy(() -> scoreBoard.finishMatch(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(NULL_VALUES_ERROR_MESSAGE);
    }

    @Test
    void WhenUpdateIsCalledForNonexistentMatch_NoUpdateOccurs() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        scoreBoard.addMatch(homeTeam, awayTeam);

        // when
        boolean result = scoreBoard.updateScore("homeTeam2", "awayTeam2", 0, 1);

        // then
        assertFalse(result);
    }

    @Test
    void WhenMatchScoreIsUpdated_UpdatesAreReflected() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.addMatch(homeTeam, awayTeam);

        // when
        boolean result = scoreBoard.updateScore(homeTeam, awayTeam, 0, 1);

        // then
        assertTrue(result);
    }

    @Test
    void WhenNullScoreIsProvidedToUpdate_throwException() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.addMatch(homeTeam, awayTeam);

        // when .. then
        assertThatThrownBy(() -> scoreBoard.updateScore(homeTeam, awayTeam, null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ScoreBoard.NULL_SCORE_ERROR_MESSAGE);

        assertThatThrownBy(() -> scoreBoard.updateScore(homeTeam, awayTeam, 1, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ScoreBoard.NULL_SCORE_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @CsvSource({"-1,1", "3,-2", "-4,-3"})
    void WhenScoreIsLessThanZero_throwException(int homeTeamScore, int awayTeamScore) {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        // when .. then
        assertThatThrownBy(() -> scoreBoard.updateScore(homeTeam, awayTeam, homeTeamScore, awayTeamScore))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(NEGATIVE_SCORES_ERROR_MESSAGE);
    }

    @Test
    void whenScoresAreUpdated_TheyShouldBeUpdatedCorrectly() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.addMatch(homeTeam, awayTeam);

        int homeScore = 2;
        int awayScore = 3;

        // when
        scoreBoard.updateScore(homeTeam, awayTeam, homeScore, awayScore);

        // then
        assertThat(scoreBoard.ongoingMatches()).isEqualTo(1);

        Match match = scoreBoard.getMatchesOrdered().get(0);

        assertThat(match.getHomeTeamScore()).isEqualTo(homeScore);
        assertThat(match.getAwayTeamScore()).isEqualTo(awayScore);
    }

    @Test
    void whenMultipleMatchesAreAdded_TheyShouldBeOrderedByTotalScore() {
        // given
        ScoreBoard scoreBoard = new ScoreBoard();

        String homeTeam2 = "home2";
        String awayTeam2 = "away2";

        scoreBoard.addMatch(homeTeam, awayTeam);
        scoreBoard.addMatch(homeTeam2, awayTeam2);

        int homeScore1 = 2;
        int awayScore1 = 3;

        int homeScore2 = 1;
        int awayScore2 = 5;

        scoreBoard.updateScore(homeTeam, awayTeam, homeScore1, awayScore1);
        scoreBoard.updateScore(homeTeam2, awayTeam2, homeScore2, awayScore2);

        // when
        List<Match> orderedMatches = scoreBoard.getMatchesOrdered();

        // then
        assertThat(scoreBoard.ongoingMatches()).isEqualTo(2);

        assertThat(orderedMatches.get(0).getTotalScore()).isGreaterThan(orderedMatches.get(1).getTotalScore());
    }

    @Test
    public void matchesShouldBeSortedByTotalScoreAndTime() {
        ScoreBoard scoreBoard = new ScoreBoard();

        // Add matches to the scoreboard
        scoreBoard.addMatch("home2", "away2");
        scoreBoard.updateScore("home2", "away2", 2, 2); // Total score: 5

        scoreBoard.addMatch("home3", "away3");
        scoreBoard.updateScore("home3", "away3", 2, 2); // Total score: 4

        scoreBoard.addMatch(homeTeam, awayTeam);
        scoreBoard.updateScore(homeTeam, awayTeam, 3, 2); // Total score: 4

        // Get the ordered matches
        List<Match> matchesOrdered = scoreBoard.getMatchesOrdered();

        // Assert the order of matches
        assertThat(matchesOrdered).hasSize(3);
        assertThat(matchesOrdered.get(0)).isEqualTo(new Match(homeTeam, awayTeam)); // The match with the highest score comes first
        assertThat(matchesOrdered.get(1)).isEqualTo(new Match("home2", "away2")); // Then the earlier added match with score 4
        assertThat(matchesOrdered.get(2)).isEqualTo(new Match("home3", "away3")); // Then the most recently added match with score 4
    }

}
