package org.example;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MatchTests {
    private Match match1 = new Match("home1", "away1");

    @Test
    void whenTwoMatchesHaveSameTeamNames_TheyAreConsideredEqual() {
        Match anotherMatch1 = new Match("home1", "away1");
        assertThat(match1).isEqualTo(anotherMatch1);
    }

    @Test
    void whenTwoMatchesDoNotHaveSameTeamNames_TheyAreNotConsideredEqual() {
        Match match2 = new Match("home2", "away2");
        assertThat(match1).isNotEqualTo(match2);
    }

    @Test
    void whenTwoMatchesHaveDifferentHomeTeams_TheyAreNotConsideredEqual() {
        Match differentHomeTeamMatch = new Match("home4", "away1");
        assertThat(match1).isNotEqualTo(differentHomeTeamMatch);
    }

    @Test
    void whenTwoMatchesHaveDifferentAwayTeams_TheyAreNotConsideredEqual() {
        Match differentAwayTeamMatch = new Match("home1", "away4");
        assertThat(match1).isNotEqualTo(differentAwayTeamMatch);
    }
}

