package org.example;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ScoreBoard {

    static final String NULL_VALUES_ERROR_MESSAGE = "null value isn't acceptable as team name";
    static final String NEGATIVE_SCORES_ERROR_MESSAGE = "scores can't be less than zero";
    static final String NULL_SCORE_ERROR_MESSAGE = "Score cannot be null.";

    private final HashMap<Match, Match> matches = new HashMap<>();

    private final Comparator<Match> matchComparator = Comparator.comparingLong(Match::getTotalScore)
            .reversed()
            .thenComparing(Match::getStart);

    public boolean addMatch(String homeTeam, String awayTeam){
        if(homeTeam == null || awayTeam == null){
            throw new IllegalArgumentException(NULL_VALUES_ERROR_MESSAGE);
        }
        Match match = new Match(homeTeam, awayTeam);
        return matches.putIfAbsent(match, match)==null;
    }

    public int ongoingMatches(){
        return matches.size();
    }

    public boolean finishMatch(String homeTeam, String awayTeam) {
        if(homeTeam == null || awayTeam == null){
            throw new IllegalArgumentException(NULL_VALUES_ERROR_MESSAGE);
        }
        return matches.remove(new Match(homeTeam, awayTeam))!=null;
    }

    public boolean updateScore(String homeTeam, String awayTeam, Integer homeTeamScore, Integer awayTeamScore) {
        if(homeTeamScore == null || awayTeamScore == null){
            throw new IllegalArgumentException(NULL_SCORE_ERROR_MESSAGE);
        }

        if(homeTeamScore < 0 || awayTeamScore < 0){
            throw new IllegalArgumentException(NEGATIVE_SCORES_ERROR_MESSAGE);
        }

        return Optional
                .ofNullable(matches.get(new Match(homeTeam, awayTeam)))
                .map(match -> {
                    match.setHomeTeamScore(homeTeamScore);
                    match.setAwayTeamScore(awayTeamScore);
                    return match;
                }).isPresent();
    }

    public List<Match> getMatchesOrdered(){
        return matches
                .values()
                .stream()
                .sorted(matchComparator)
                .toList();
    }

}
