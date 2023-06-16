package org.example;

import java.util.HashMap;
import java.util.Optional;

public class ScoreBoard {

    static final String NEGATIVE_SCORES_ERROR_MESSAGE = "scores can't be less than zero";

    private final HashMap<Match, Match> matches = new HashMap<>();

    public boolean addMatch(String homeTeam, String awayTeam){
        Match match = new Match(homeTeam, awayTeam);
        return matches.putIfAbsent(match, match)==null;
    }

    public int ongoingMatches(){
        return matches.size();
    }

    public boolean finishMatch(String homeTeam, String awayTeam) {
        return matches.remove(new Match(homeTeam, awayTeam))!=null;
    }

    public boolean updateScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
        if(homeTeamScore < 0 || awayTeamScore < 0){
            throw new IllegalArgumentException(NEGATIVE_SCORES_ERROR_MESSAGE);
        }

        return Optional
                .ofNullable(matches.get(new Match(homeTeam, awayTeam)))
                .map(match -> {
                    match.setHomeTeamScore(homeTeamScore);
                    match.setHomeTeamScore(awayTeamScore);
                    return match;
                }).isPresent();
    }

}
