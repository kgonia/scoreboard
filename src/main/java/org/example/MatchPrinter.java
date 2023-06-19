package org.example;

import java.io.PrintStream;
import java.util.List;

public class MatchPrinter {
    private final PrintStream printStream = System.out;

    private final ScoreBoard scoreBoard;

    public MatchPrinter(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public void print(){
        List<Match> matches = scoreBoard.getMatchesOrdered();
        int i = 1;
        for(Match match : matches) {
            printStream.printf("%d. %s %d - %s %d\n",
                    i++,
                    match.getHomeTeam(), match.getHomeTeamScore(),
                    match.getAwayTeam(), match.getAwayTeamScore());
        }
    }
}
