package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MatchComparatorTests {

    @Test
    void testComparatorWithDifferentTotalScores() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Match match1 = new Match("home1", "away1", now);
        match1.setHomeTeamScore(2);
        match1.setAwayTeamScore(3);  // Total score: 5

        Match match2 = new Match("home2", "away2", now.plusSeconds(1));
        match2.setHomeTeamScore(4);
        match2.setAwayTeamScore(5);  // Total score: 9

        MatchComparator comparator = new MatchComparator();

        // When
        int comparisonResult = comparator.compare(match1, match2);

        // Then
        assertThat(match1.getTotalScore()).isEqualTo(5);
        assertThat(match2.getTotalScore()).isEqualTo(9);
        assertThat(comparisonResult).isNegative();
    }

    @Test
    void testComparatorWithSameTotalScores() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Match match1 = new Match("home1", "away1", now);
        match1.setHomeTeamScore(3);
        match1.setAwayTeamScore(4);  // Total score: 7

        Match match2 = new Match("home2", "away2", now.plusSeconds(1));
        match2.setHomeTeamScore(2);
        match2.setAwayTeamScore(5);  // Total score: 7

        MatchComparator comparator = new MatchComparator();

        // When
        int comparisonResult = comparator.compare(match1, match2);

        // Then
        assertThat(match1.getTotalScore()).isEqualTo(7);
        assertThat(match2.getTotalScore()).isEqualTo(7);
        assertThat(comparisonResult).isNegative();  // match1 started before match2
    }
}
