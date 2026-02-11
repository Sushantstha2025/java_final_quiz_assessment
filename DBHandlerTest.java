package finalAssesment3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

/**
 * JUnit 5 test class for DBHandler.java
 * Tests database operations including CRUD operations for questions.
 */
public class DBHandlerTest {
    
    private DBHandler dbHandler;
    private static final String TEST_USERNAME = "junit_test_user";
    private static final String TEST_PASSWORD = "test123";
    
    @BeforeEach
    public void setUp() {
        dbHandler = new DBHandler();
        // Clean up any existing test data BEFORE each test
        cleanUpTestData();
        
        // Small delay to ensure cleanup completes
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @AfterEach
    public void tearDown() {
        // Clean up test data AFTER each test
        cleanUpTestData();
    }
    
    private void cleanUpTestData() {
        try (Connection conn = DBHandler.getConnection()) {
            // Disable foreign key checks temporarily for clean deletion
            Statement disableFK = conn.createStatement();
            disableFK.execute("SET FOREIGN_KEY_CHECKS = 0");
            
            // Delete test scores from playerscores table FIRST
            String deleteScores = "DELETE FROM playerscores WHERE username = ?";
            PreparedStatement pst2 = conn.prepareStatement(deleteScores);
            pst2.setString(1, TEST_USERNAME);
            int scoresDeleted = pst2.executeUpdate();
            
            // Delete test user from Competitors table
            String deleteUser = "DELETE FROM Competitors WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(deleteUser);
            pst.setString(1, TEST_USERNAME);
            int usersDeleted = pst.executeUpdate();
            
            // Delete test questions
            String deleteQuestion = "DELETE FROM questions WHERE question_text LIKE 'JUNIT_TEST%'";
            Statement st = conn.createStatement();
            int questionsDeleted = st.executeUpdate(deleteQuestion);
            
            // Re-enable foreign key 
            Statement enableFK = conn.createStatement();
            enableFK.execute("SET FOREIGN_KEY_CHECKS = 1");
            
            if (usersDeleted > 0 || scoresDeleted > 0 || questionsDeleted > 0) {
                System.out.println("Cleanup: Deleted " + usersDeleted + " users, " + 
                                 scoresDeleted + " scores, " + questionsDeleted + " questions");
            }
            
        } catch (SQLException e) {
            System.err.println("Cleanup warning: " + e.getMessage());
        }
    }
    
    @Test
    public void testDatabaseConnection() {
        try (Connection conn = DBHandler.getConnection()) {
            assertNotNull(conn, "Database connection should not be null");
            assertFalse(conn.isClosed(), "Database connection should be open");
        } catch (SQLException e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testValidateLoginWithInvalidCredentials() {
        String result = dbHandler.validateLogin("invalid_user", "wrong_password");
        assertNull(result, "Invalid credentials should return null");
    }
    
    @Test
    public void testValidateLoginWithAdminCredentials() {
        String result = dbHandler.validateLogin("admin", "admin123");
        assertEquals("ADMIN", result, "Admin login should return ADMIN");
    }
    
    @Test
    public void testRegisterUser() {
        // @BeforeEach already cleaned up, so we can directly register
        boolean result = dbHandler.registerUser(
            TEST_USERNAME, 
            TEST_PASSWORD, 
            "JUnit", 
            "Test"
        );
        assertTrue(result, "User registration should succeed");
        
        // Verify user was created
        String role = dbHandler.validateLogin(TEST_USERNAME, TEST_PASSWORD);
        assertEquals("PLAYER", role, "Registered user should have PLAYER role");
    }
    
    @Test
    public void testRegisterDuplicateUser() {
        // @BeforeEach already cleaned up
        
        // Register user first time
        boolean firstAttempt = dbHandler.registerUser(TEST_USERNAME, TEST_PASSWORD, "JUnit", "Test");
        assertTrue(firstAttempt, "First registration should succeed");
        
        // Try to register again with same username
        boolean secondAttempt = dbHandler.registerUser(TEST_USERNAME, "different_pass", "Other", "Name");
        assertFalse(secondAttempt, "Duplicate username registration should fail");
    }
    
    @Test
    public void testAddQuestion() {
        boolean result = dbHandler.addQuestion(
            "JUNIT_TEST: What is 2+2?",
            "3",
            "4",
            "5",
            "6",
            "B",
            "Beginner"
        );
        assertTrue(result, "Question should be added successfully");
    }
    
    @Test
    public void testGetQuestionById() {
        // First add a question
        dbHandler.addQuestion(
            "JUNIT_TEST: What is the capital of France?",
            "London",
            "Paris",
            "Berlin",
            "Madrid",
            "B",
            "Intermediate"
        );
        
        // Get the question ID
        try (Connection conn = DBHandler.getConnection()) {
            String query = "SELECT id FROM questions WHERE question_text = ? LIMIT 1";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, "JUNIT_TEST: What is the capital of France?");
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                int questionId = rs.getInt("id");
                
                // Test retrieval
                String[] question = dbHandler.getQuestionById(questionId);
                assertNotNull(question, "Question should be found");
                assertEquals("JUNIT_TEST: What is the capital of France?", question[0]);
                assertEquals("Paris", question[2]);
                assertEquals("B", question[5]);
                assertEquals("Intermediate", question[6]);
            }
        } catch (SQLException e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetQuestionByIdNotFound() {
        String[] question = dbHandler.getQuestionById(999999);
        assertNull(question, "Non-existent question should return null");
    }
    
    @Test
    public void testUpdateQuestion() {
        // Add a question first
        dbHandler.addQuestion(
            "JUNIT_TEST: Original question",
            "A", "B", "C", "D",
            "A",
            "Beginner"
        );
        
        try (Connection conn = DBHandler.getConnection()) {
            String query = "SELECT id FROM questions WHERE question_text = ? LIMIT 1";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, "JUNIT_TEST: Original question");
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                int questionId = rs.getInt("id");
                
                // Update the question
                boolean result = dbHandler.updateQuestion(
                    questionId,
                    "JUNIT_TEST: Updated question",
                    "Option1", "Option2", "Option3", "Option4",
                    "C",
                    "Advanced"
                );
                
                assertTrue(result, "Question update should succeed");
                
                // Verify update
                String[] updated = dbHandler.getQuestionById(questionId);
                assertEquals("JUNIT_TEST: Updated question", updated[0]);
                assertEquals("C", updated[5]);
                assertEquals("Advanced", updated[6]);
            }
        } catch (SQLException e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testDeleteQuestion() {
        // Add a question first
        dbHandler.addQuestion(
            "JUNIT_TEST: Question to delete",
            "A", "B", "C", "D",
            "A",
            "Beginner"
        );
        
        try (Connection conn = DBHandler.getConnection()) {
            String query = "SELECT id FROM questions WHERE question_text = ? LIMIT 1";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, "JUNIT_TEST: Question to delete");
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                int questionId = rs.getInt("id");
                
                // Delete the question
                boolean result = dbHandler.deleteQuestion(questionId);
                assertTrue(result, "Question deletion should succeed");
                
                // Verify deletion
                String[] deleted = dbHandler.getQuestionById(questionId);
                assertNull(deleted, "Deleted question should not be found");
            }
        } catch (SQLException e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetQuestionIdsByDifficulty() {
        // Add some test questions
        dbHandler.addQuestion("JUNIT_TEST: Beginner Q1", "A", "B", "C", "D", "A", "Beginner");
        dbHandler.addQuestion("JUNIT_TEST: Beginner Q2", "A", "B", "C", "D", "B", "Beginner");
        dbHandler.addQuestion("JUNIT_TEST: Advanced Q1", "A", "B", "C", "D", "C", "Advanced");
        
        java.util.List<Integer> beginnerIds = dbHandler.getQuestionIdsByDifficulty("Beginner", 10);
        assertNotNull(beginnerIds, "Should return a list");
        assertTrue(beginnerIds.size() >= 2, "Should find at least 2 beginner questions");
    }
    
    @Test
    public void testInsertPlayerScore() {
        // @BeforeEach already cleaned up, register user
        dbHandler.registerUser(TEST_USERNAME, TEST_PASSWORD, "JUnit", "Test");
        
        int[] scores = {5, 4, 3, 5, 4};
        boolean result = dbHandler.insertPlayerScore(TEST_USERNAME, scores);
        assertTrue(result, "Player score insertion should succeed");
    }
    
    @Test
    public void testInsertPlayerScoreInvalidArray() {
        int[] invalidScores = {5, 4, 3}; // Only 3 scores instead of 5
        boolean result = dbHandler.insertPlayerScore(TEST_USERNAME, invalidScores);
        assertFalse(result, "Invalid score array should fail");
    }
    
    @Test
    public void testGetPlayerScoreHistory() {
        // @BeforeEach already cleaned up, register user
        dbHandler.registerUser(TEST_USERNAME, TEST_PASSWORD, "JUnit", "Test");
        
        int[] scores1 = {5, 4, 3, 5, 4};
        int[] scores2 = {3, 3, 3, 3, 3};
        
        dbHandler.insertPlayerScore(TEST_USERNAME, scores1);
        dbHandler.insertPlayerScore(TEST_USERNAME, scores2);
        
        java.util.List<String[]> history = dbHandler.getPlayerScoreHistory(TEST_USERNAME);
        assertNotNull(history, "History should not be null");
        assertEquals(2, history.size(), "Should have exactly 2 score records");
    }
    
    /**
     * Manual cleanup test 
     */
    @Test
    public void aaa_ManualCleanup_RunThisFirst() {
        cleanUpTestData();
        
        String role = dbHandler.validateLogin(TEST_USERNAME, TEST_PASSWORD);
        assertNull(role, "Test user should not exist after cleanup");
        
        System.out.println("✅ Manual cleanup completed successfully!");
    }
}