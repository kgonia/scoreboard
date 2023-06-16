package org.example;

import java.time.LocalDateTime;

class Match {

    private String homeTeam;

    private String awayTeam;

    private Integer homeTeamScore = 0;

    private Integer awayTeamScore = 0;

    private LocalDateTime start = LocalDateTime.now();

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    void setAwayTeamScore(Integer awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public Integer getAwayTeamScore() {
        return awayTeamScore;
    }

    public LocalDateTime getStart() {
        return start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (!homeTeam.equals(match.homeTeam)) return false;
        return awayTeam.equals(match.awayTeam);
    }

    @Override
    public int hashCode() {
        int result = homeTeam.hashCode();
        result = 31 * result + awayTeam.hashCode();
        return result;
    }
}