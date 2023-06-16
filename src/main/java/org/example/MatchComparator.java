package org.example;

import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {

    @Override
    public int compare(Match match1, Match match2) {
        long m1Total = match1.getTotalScore();
        long m2Total = match2.getTotalScore();
        return (m1Total < m2Total) ? -1 : ((m1Total == m2Total) ? match1.getStart().compareTo(match2.getStart()) : 1);
    }
}
