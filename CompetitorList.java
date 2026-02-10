package finalAssesment3;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * Manages a collection of SushantCompetitor objects.
 * Responsible for loading competitor data from the MySQL database,
 * providing access to individual competitors, calculating statistics,
 * and generating formatted reports.
 */
public class CompetitorList {

    private ArrayList<SushantCompetitor> competitors;

    /**
     * Creates a new CompetitorList and loads all competitors
     * from the database.
     */
    public CompetitorList() {
        this.competitors = new ArrayList<>();
        loadFromDatabase();
    }

    /**
     * Loads all competitor records from the Competitors table.
     */
    private void loadFromDatabase() {
        try (Connection conn = DBHandler.getConnection()) {

            String query = "SELECT * FROM Competitors WHERE role = 'PLAYER'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Name name = new Name(
                        rs.getString("firstName"),
                        rs.getString("lastName")
                );

                int[] scores = {
                        rs.getInt("score1"),
                        rs.getInt("score2"),
                        rs.getInt("score3"),
                        rs.getInt("score4"),
                        rs.getInt("score5")
                };

                SushantCompetitor comp = new SushantCompetitor(
                        rs.getInt("competitorID"),
                        name,
                        scores
                );

                competitors.add(comp);
            }

            competitors.sort(Comparator.comparingDouble(SushantCompetitor::getOverallScore).reversed());

        } catch (SQLException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    public ArrayList<SushantCompetitor> getCompetitors() {
        return competitors;
    }

    public SushantCompetitor getCompetitorByID(int id) {
        for (SushantCompetitor c : competitors) {
            if (c.getCompetitorID() == id) {
                return c;
            }
        }
        return null;
    }

    public SushantCompetitor getTopPerformer() {
        if (competitors.isEmpty()) return null;
        return competitors.get(0);
    }

    /**
     * Calculates frequency of individual scores.
     */
    public Map<Integer, Integer> getScoreFrequencies() {
        Map<Integer, Integer> freqMap = new HashMap<>();

        for (SushantCompetitor c : competitors) {
            for (int score : c.getScoreArray()) {
                freqMap.put(score, freqMap.getOrDefault(score, 0) + 1);
            }
        }
        return freqMap;
    }

    /**
     * Generates a formatted leaderboard and statistics report.
     * Competitors are ranked by overall score (highest to lowest).
     */
    public String generateFullReport() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("                    LEADERBOARD & STATISTICS                     \n");
        
        sb.append("Competitor Leaderboard (Ranked by Overall Score):\n\n");
        sb.append(String.format(
                "%-6s %-8s %-20s %-18s %s%n",
                "Rank", "ID", "Name", "Scores", "Overall"
        ));

        int rank = 1;
        for (SushantCompetitor c : competitors) {
            String scores = String.join(" ",
                    Arrays.stream(c.getScoreArray())
                            .mapToObj(String::valueOf)
                            .toArray(String[]::new));

            sb.append(String.format(
                    "%-6d %-8d %-20s %-18s %.1f%n",
                    rank++,
                    c.getCompetitorID(),
                    c.getCompetitorName().getFullName(),
                    scores,
                    c.getOverallScore()
            ));
        }

        sb.append("\n");
        sb.append("                     STATISTICAL SUMMARY                         \n");

        SushantCompetitor top = getTopPerformer();
        if (top != null) {
            sb.append("Top Performer: ")
              .append(top.getCompetitorName().getFullName())
              .append(" with overall score: ")
              .append(String.format("%.1f", top.getOverallScore()))
              .append("\n\n");
        }

        sb.append("• Total number of competitors: ")
          .append(competitors.size())
          .append("\n\n");

        Map<Integer, Integer> freq = getScoreFrequencies();

       

        return sb.toString();
    }

    public static void main(String[] args) {
        CompetitorList list = new CompetitorList();
        System.out.println(list.generateFullReport());
    }
}