package finalAssesment3;

import java.util.Arrays;

/**
 * Represents a competitor in the system, managing their ID, name, and scores.
 */
public class SushantCompetitor {

    private int competitorID;
    private Name competitorName;
    private int[] scores;
    
    /**
     * @param id The unique ID for the competitor.
     * @param name The Name object associated with the competitor.
     * @param scores An array of integer scores.
     */
    public SushantCompetitor(int id, Name name, int[] scores) {
        this.competitorID = id;
        this.competitorName = name;
        this.scores = scores;
    }

    /** @return The competitor's ID number. */
    public int getCompetitorID() {
        return competitorID;
    }

    /** @return The Name object of the competitor. */
    public Name getCompetitorName() {
        return competitorName;
    }

    /** @return The raw array of scores. */
    public int[] getScoreArray() {
        return scores;
    }

    /** @param scores The updated array of scores to set. */
    public void setScores(int[] scores) {
        this.scores = scores;
    }

    /**
     * Calculates the average score after removing the highest and lowest values.
     * @return The weighted average rounded to one decimal place.
     */
    public double getOverallScore() {
        if (scores == null || scores.length < 3) return 0.0;

        int sum = 0;
        int min = scores[0];
        int max = scores[0];

        for (int s : scores) {
            sum += s;
            if (s < min) min = s;
            if (s > max) max = s;
        }

        double result = (double) (sum - min - max) / (scores.length - 2);
        return Math.round(result * 10.0) / 10.0;
    }

    /**
     * @return A multi-line string containing the full name, ID, scores, and overall score.
     */
    public String getFullDetails() {
        String scoreString = Arrays.toString(scores)
                                   .replace("[", "")
                                   .replace("]", "");

        return "Competitor number " + competitorID +
               ", name " + competitorName.getFullName() + ".\n" +
               "Scores: " + scoreString + ".\n" +
               "Overall score: " + getOverallScore() + ".";
    }

    /**
     * @return A brief summary string using ID, initials, and overall score.
     */
    public String getShortDetails() {
        return "CN " + competitorID +
               " (" + competitorName.getInitials() +
               ") has overall score " + getOverallScore() + ".";
    }
}