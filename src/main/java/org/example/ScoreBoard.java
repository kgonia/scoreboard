package org.example;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a scoreboard for matches. It allows adding, finishing, and updating the score of matches.
 * Also, it provides a way to retrieve the list of matches in order of their total scores and start time.
 */
public class ScoreBoard {

    static final String NULL_VALUES_ERROR_MESSAGE = "Null value isn't acceptable as team name";
    static final String NEGATIVE_SCORES_ERROR_MESSAGE = "Scores can't be less than zero";
    static final String NULL_SCORE_ERROR_MESSAGE = "Score cannot be null.";
    static final String EMPTY_VALUES_ERROR_MESSAGE = "Empty string is not acceptable as a team name";
    private final HashMap<Match, Match> matches = new HashMap<>();
    private final Clock clock;
    private final Comparator<Match> matchComparator = Comparator.comparingLong(Match::getTotalScore)
            .reversed()
            .thenComparing(Match::getStart, Comparator.reverseOrder());

    public ScoreBoard() {
        this(Clock.systemDefaultZone());
    }

    public ScoreBoard(Clock clock) {
        this.clock = clock;
    }

    /**
     * Adds a new match to the scoreboard.
     *
     * @param homeTeam The home team name.
     * @param awayTeam The away team name.
     * @throws IllegalArgumentException if any of the parameters are null or only consists of white spaces or empty.
     * @return true if match was added successfully, false if match already exists.
     */
    public boolean addMatch(String homeTeam, String awayTeam){
        if(homeTeam == null || awayTeam == null){
            throw new IllegalArgumentException(NULL_VALUES_ERROR_MESSAGE);
        }
        if(homeTeam.trim().isEmpty() || awayTeam.trim().isEmpty()){
            throw new IllegalArgumentException(EMPTY_VALUES_ERROR_MESSAGE);
        }
        Match match = new Match(homeTeam, awayTeam, LocalDateTime.now(clock));
        return matches.putIfAbsent(match, match)==null;
    }

    /**
     * Gets the number of ongoing matches.
     *
     * @return The number of ongoing matches.
     */
    public int ongoingMatches(){
        return matches.size();
    }

    /**
     * Finishes a match by removing it from the scoreboard.
     *
     * @param homeTeam The home team name.
     * @param awayTeam The away team name.
     * @throws IllegalArgumentException if any of the parameters are null.
     * @return true if the match was successfully removed, false if the match was not found.
     */
    public boolean finishMatch(String homeTeam, String awayTeam) {
        if(homeTeam == null || awayTeam == null){
            throw new IllegalArgumentException(NULL_VALUES_ERROR_MESSAGE);
        }
        return matches.remove(new Match(homeTeam, awayTeam))!=null;
    }

    /**
     * Updates the score for a match.
     *
     * @param homeTeam The home team name.
     * @param awayTeam The away team name.
     * @param homeTeamScore The home team score.
     * @param awayTeamScore The away team score.
     * @throws IllegalArgumentException if any of the parameters are null or if scores are negative.
     * @return true if score was updated successfully, false if match was not found.
     */
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

    /**
     * Returns a list of matches in descending order of total score. In case of a tie, the match that started earlier is considered first.
     *
     * @return List of matches in descending order of total score.
     */
    public List<Match> getMatchesOrdered(){
        return matches
                .values()
                .stream()
                .sorted(matchComparator)
                .toList();
    }

}
