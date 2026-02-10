package finalAssesment3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Question.java
 * Tests the Question model class getters and setters.
 */
public class QuestionTest {
    
    private Question question;
    
    @BeforeEach
    public void setUp() {
        question = new Question(
            1, 
            "What is 2+2?", 
            "B", 
            "Beginner"
        );
    }
    
    @Test
    public void testGetId() {
        assertEquals(1, question.getId());
    }
    
    @Test
    public void testGetText() {
        assertEquals("What is 2+2?", question.getText());
    }
    
    @Test
    public void testGetAnswer() {
        assertEquals("B", question.getAnswer());
    }
    
    @Test
    public void testGetDifficulty() {
        assertEquals("Beginner", question.getDifficulty());
    }
    
    @Test
    public void testSetId() {
        question.setId(99);
        assertEquals(99, question.getId());
    }
    
    @Test
    public void testSetText() {
        question.setText("What is the capital of France?");
        assertEquals("What is the capital of France?", question.getText());
    }
    
    @Test
    public void testSetAnswer() {
        question.setAnswer("A");
        assertEquals("A", question.getAnswer());
    }
    
    @Test
    public void testSetDifficulty() {
        question.setDifficulty("Advanced");
        assertEquals("Advanced", question.getDifficulty());
    }
    
    @Test
    public void testQuestionCreationWithAllParameters() {
        Question q = new Question(42, "Test question", "C", "Intermediate");
        assertEquals(42, q.getId());
        assertEquals("Test question", q.getText());
        assertEquals("C", q.getAnswer());
        assertEquals("Intermediate", q.getDifficulty());
    }
}