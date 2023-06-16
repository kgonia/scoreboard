package org.example;

import java.util.HashSet;
import java.util.Set;

public class ScoreBoard {

    private final Set<Match> matches = new HashSet<>();

    public boolean addMatch(String homeTeam, String awayTeam){
        return matches.add(new Match(homeTeam, awayTeam));
    }

    public int ongoingMatches(){
        return matches.size();
    }

    public boolean finishMatch(String homeTeam, String awayTeam) {
        return matches.remove(new Match(homeTeam, awayTeam));
    }


}
