package finalAssesment3;

import java.sql.*;
import java.util.*;

/**
 * Handles all database operations including user authentication, 
 * question management, and score tracking.
 */
public class DBHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASS = "";

    /** @return A connection to the MySQL database. */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Checks credentials against hardcoded admin and database users.
     * @return User role (ADMIN/PLAYER) or null if login fails.
     */
    public String validateLogin(String username, String password) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            return "ADMIN";
        }
        
        try (Connection conn = getConnection()) {
            String query = "SELECT role FROM Competitors WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getString("role").toUpperCase();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Registers a new user with the default 'PLAYER' role. */
    public boolean registerUser(String username, String password, String firstName, String lastName) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO Competitors (username, password, role, firstName, lastName, " +
                           "score1, score2, score3, score4, score5) " +
                           "VALUES (?, ?, 'PLAYER', ?, ?, 0, 0, 0, 0, 0)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, firstName);
            pst.setString(4, lastName);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { 
                System.out.println("Username already exists.");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    /** Adds a new quiz question to the database. */
    public boolean addQuestion(String questionText, String optA, String optB, String optC, 
                               String optD, String correctOption, String difficulty) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO questions (question_text, option_a, option_b, option_c, " +
                         "option_d, correct_option, difficulty) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, questionText);
            pst.setString(2, optA);
            pst.setString(3, optB);
            pst.setString(4, optC);
            pst.setString(5, optD);
            pst.setString(6, correctOption);
            pst.setString(7, difficulty);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Retrieves all data for a specific question by ID. */
    public String[] getQuestionById(int id) {
        String sql = "SELECT question_text, option_a, option_b, option_c, option_d, " +
                     "correct_option, difficulty FROM questions WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new String[] {
                        rs.getString("question_text"), rs.getString("option_a"),
                        rs.getString("option_b"), rs.getString("option_c"),
                        rs.getString("option_d"), rs.getString("correct_option"),
                        rs.getString("difficulty")
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Updates an existing question's details. */
    public boolean updateQuestion(int id, String questionText, String optA, String optB, 
                                  String optC, String optD, String correctOption, String difficulty) {
        String sql = "UPDATE questions SET question_text = ?, option_a = ?, option_b = ?, " +
                     "option_c = ?, option_d = ?, correct_option = ?, difficulty = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, questionText.trim());
            pst.setString(2, optA.trim());
            pst.setString(3, optB.trim());
            pst.setString(4, optC.trim());
            pst.setString(5, optD.trim());
            pst.setString(6, correctOption.trim().toUpperCase());
            pst.setString(7, difficulty.trim());
            pst.setInt(8, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Deletes a question from the database. */
    public boolean deleteQuestion(int id) {
        String sql = "DELETE FROM questions WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Gets a random list of question IDs based on difficulty and limit. */
    public List<Integer> getQuestionIdsByDifficulty(String difficulty, int limit) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id FROM questions WHERE difficulty = ? ORDER BY RAND() LIMIT ?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, difficulty);
            pst.setInt(2, limit);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    /** Saves a competitor's profile and scores to the database. */
    public void saveCompetitor(SushantCompetitor comp) {
        String sql = "INSERT INTO Competitors (competitorID, firstName, lastName, " +
                     "score1, score2, score3, score4, score5) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, comp.getCompetitorID());
            String fullName = comp.getCompetitorName().getFullName();
            String[] names = fullName.trim().split("\\s+");
            pst.setString(2, names.length > 0 ? names[0] : "");
            pst.setString(3, names.length > 1 ? names[names.length - 1] : "");

            int[] scores = comp.getScoreArray();
            for (int i = 0; i < 5; i++) {
                pst.setInt(4 + i, (i < scores.length) ? scores[i] : 0);
            }

            pst.executeUpdate();
            System.out.println("Competitor saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Updates the 5 scores for a specific user. */
    public boolean updatePlayerScores(String username, int s1, int s2, int s3, int s4, int s5) {
        String sql = "UPDATE Competitors SET score1=?, score2=?, score3=?, score4=?, score5=? WHERE username=?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, s1); pst.setInt(2, s2); pst.setInt(3, s3);
            pst.setInt(4, s4); pst.setInt(5, s5); pst.setString(6, username.trim());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Overloaded method to update scores using an array. */
    public boolean updatePlayerScores(String username, int[] scores) {
        if (scores == null || scores.length == 0) return false;
        return updatePlayerScores(username,
                scores.length > 0 ? scores[0] : 0,
                scores.length > 1 ? scores[1] : 0,
                scores.length > 2 ? scores[2] : 0,
                scores.length > 3 ? scores[3] : 0,
                scores.length > 4 ? scores[4] : 0);
    }

    /** Fetches a competitor object from the DB using their username. */
    public SushantCompetitor getCompetitorByUsername(String username) {
        String sql = "SELECT * FROM Competitors WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username.trim());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Name name = new Name(rs.getString("firstName"), rs.getString("lastName"));
                    int[] scores = {
                        rs.getInt("score1"), rs.getInt("score2"), rs.getInt("score3"),
                        rs.getInt("score4"), rs.getInt("score5")
                    };
                    return new SushantCompetitor(rs.getInt("competitorID"), name, scores);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Records a new entry in the score history table. */
    public boolean insertPlayerScore(String username, int[] scores) {
        if (scores == null || scores.length != 5) return false;

        SushantCompetitor tempComp = new SushantCompetitor(0, new Name("Temp", "Temp"), scores);
        double overall = tempComp.getOverallScore();

        String sql = "INSERT INTO playerscores (username, score1, score2, score3, score4, " +
                     "score5, overall_score) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username.trim());
            for (int i = 0; i < 5; i++) { pst.setInt(2 + i, scores[i]); }
            pst.setDouble(7, overall);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** @return A list of previous scores and dates for a specific user. */
    public List<String[]> getPlayerScoreHistory(String username) {
        List<String[]> history = new ArrayList<>();
        String sql = "SELECT play_date, score1, score2, score3, score4, score5, overall_score " +
                     "FROM playerscores WHERE username = ? ORDER BY play_date DESC";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username.trim());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    history.add(new String[] {
                        rs.getString("play_date"),
                        String.valueOf(rs.getInt("score1")),
                        String.valueOf(rs.getInt("score2")),
                        String.valueOf(rs.getInt("score3")),
                        String.valueOf(rs.getInt("score4")),
                        String.valueOf(rs.getInt("score5")),
                        String.format("%.1f", rs.getDouble("overall_score"))
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }
}