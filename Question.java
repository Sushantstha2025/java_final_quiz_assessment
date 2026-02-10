package finalAssesment3;

/**
 * Represents a single quiz question with its answer and difficulty level.
 */
public class Question {
    private int id;
    private String text;
    private String answer;
    private String difficulty;

    /**
     * Constructs a new Question.
     * @param id Unique identifier for the question.
     * @param text The actual question content.
     * @param answer The correct answer.
     * @param difficulty The level of difficulty (e.g., Easy, Medium, Hard).
     */
    public Question(int id, String text, String answer, String difficulty) {
        this.id = id;
        this.text = text;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    /** @return The question's ID. */
    public int getId() { 
        return id; 
    }
    
    /** @return The question text. */
    public String getText() { 
        return text; 
    }
    
    /** @return The correct answer string. */
    public String getAnswer() { 
        return answer; 
    }
    
    /** @return The difficulty level. */
    public String getDifficulty() { 
        return difficulty; 
    }
    
    /** @param id The new ID to set. */
    public void setId(int id) {
        this.id = id;
    }
    
    /** @param text The new question text to set. */
    public void setText(String text) {
        this.text = text;
    }
    
    /** @param answer The new correct answer to set. */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    /** @param difficulty The new difficulty level to set. */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}