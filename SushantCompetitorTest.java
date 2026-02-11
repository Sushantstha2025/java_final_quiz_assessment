package finalAssesment3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for SushantCompetitor.java
 * Tests competitor functionality including score calculations and display methods.
 */
public class SushantCompetitorTest {
    
    private SushantCompetitor competitor1;
    private SushantCompetitor competitor2;
    
    @BeforeEach
    public void setUp() {
        Name name1 = new Name("John", "Smith");
        Name name2 = new Name("Alice", "Johnson");
        
        int[] scores1 = {5, 4, 3, 5, 4};
        int[] scores2 = {2, 2, 2, 2, 2};
        
        competitor1 = new SushantCompetitor(101, name1, scores1);
        competitor2 = new SushantCompetitor(102, name2, scores2);
    }
    
    @Test
    public void testGetCompetitorID() {
        assertEquals(101, competitor1.getCompetitorID());
        assertEquals(102, competitor2.getCompetitorID());
    }
    
    @Test
    public void testGetCompetitorName() {
        assertEquals("John Smith", competitor1.getCompetitorName().getFullName());
        assertEquals("Alice Johnson", competitor2.getCompetitorName().getFullName());
    }
    
    @Test
    public void testGetScoreArray() {
        int[] scores = competitor1.getScoreArray();
        assertArrayEquals(new int[]{5, 4, 3, 5, 4}, scores);
    }
    
    @Test
    public void testGetOverallScore() {
        // For scores {5, 4, 3, 5, 4}
        // Remove min (3) and max (5)
        // Average of {5, 4, 4} = 13/3 = 4.3 (rounded to 1 decimal)
        assertEquals(4.3, competitor1.getOverallScore(), 0.01);
        	
        // For scores {2, 2, 2, 2, 2}
        // Remove one 2 (min) and one 2 (max)
        // Average of {2, 2, 2} = 6/3 = 2.0
        assertEquals(2.0, competitor2.getOverallScore(), 0.01);
    }
    
    @Test
    public void testGetFullDetails() {
        String details = competitor1.getFullDetails();
        assertTrue(details.contains("Competitor number 101"));
        assertTrue(details.contains("John Smith"));
        assertTrue(details.contains("5, 4, 3, 5, 4"));
        assertTrue(details.contains("4.3"));
    }
    
    @Test
    public void testGetShortDetails() {
        String shortDetails = competitor1.getShortDetails();
        assertTrue(shortDetails.contains("CN 101"));
        assertTrue(shortDetails.contains("JS"));
        assertTrue(shortDetails.contains("4.3"));
    }
    
    @Test
    public void testSetScores() {
        int[] newScores = {5, 5, 5, 5, 5};
        competitor1.setScores(newScores);
        assertArrayEquals(newScores, competitor1.getScoreArray());
        assertEquals(5.0, competitor1.getOverallScore(), 0.01);
    }
    
    @Test
    public void testOverallScoreWithMinimumScores() {
        int[] scores = {5, 4, 3, 2, 1};
        SushantCompetitor comp = new SushantCompetitor(103, new Name("Test", "User"), scores);
        // Remove min (1) and max (5)
        // Average of {4, 3, 2} = 9/3 = 3.0
        assertEquals(3.0, comp.getOverallScore(), 0.01);
    }
    
    @Test
    public void testOverallScoreWithAllSameScores() {
        int[] scores = {3, 3, 3, 3, 3};
        SushantCompetitor comp = new SushantCompetitor(104, new Name("Same", "Score"), scores);
        // Remove one 3 (min) and one 3 (max)
        // Average of {3, 3, 3} = 9/3 = 3.0
        assertEquals(3.0, comp.getOverallScore(), 0.01);
    }
}