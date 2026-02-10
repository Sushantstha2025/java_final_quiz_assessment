package finalAssesment3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizGUI extends JFrame {

    private JLabel lblQuestion;
    private JRadioButton rbA, rbB, rcC, rdD;
    private ButtonGroup optionGroup;
    private JLabel lblScore;
    private JLabel lblQuestionNum;
    private JButton btnNext;

    private String username;
    private String difficulty;
    private UserGUI parentGUI;

    private List<Integer> questionIds = new ArrayList<>();
    private int currentRound = 0;          
    private int questionInRound = 0;        
    private int correctInRound = 0;
    private int[] roundScores = new int[5];

    private final int QUESTIONS_PER_ROUND = 5;
    private final int TOTAL_ROUNDS = 5;

    /**
     * Initializes the quiz window for a specific user and difficulty level.
     * @param username The player's username.
     * @param difficulty The chosen difficulty (Beginner, Intermediate, Advanced).
     * @param parentGUI Reference to the UserGUI to return results after finishing.
     */	
    public QuizGUI(String username, String difficulty, UserGUI parentGUI) {
        this.username = username;
        this.difficulty = difficulty;
        this.parentGUI = parentGUI;

        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblTitle = new JLabel("QUIZ GAME");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lblTitle.setBounds(336, 22, 154, 40);
        getContentPane().add(lblTitle);

        lblQuestionNum = new JLabel("Round 1 - Question 1 of 5");
        lblQuestionNum.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblQuestionNum.setBounds(50, 80, 300, 30);
        getContentPane().add(lblQuestionNum);

        lblScore = new JLabel("Round Score: 0 / 5");
        lblScore.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblScore.setBounds(550, 80, 200, 30);
        getContentPane().add(lblScore);

        lblQuestion = new JLabel("Loading question...");
        lblQuestion.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        lblQuestion.setBounds(50, 130, 700, 80);
        lblQuestion.setVerticalAlignment(SwingConstants.TOP);
        getContentPane().add(lblQuestion);

        optionGroup = new ButtonGroup();

        rbA = new JRadioButton();
        rbA.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rbA.setBounds(80, 220, 650, 30);
        getContentPane().add(rbA);
        optionGroup.add(rbA);

        rbB = new JRadioButton();
        rbB.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rbB.setBounds(80, 260, 650, 30);
        getContentPane().add(rbB);
        optionGroup.add(rbB);

        rcC = new JRadioButton();
        rcC.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rcC.setBounds(80, 300, 650, 30);
        getContentPane().add(rcC);
        optionGroup.add(rcC);

        rdD = new JRadioButton();
        rdD.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        rdD.setBounds(80, 340, 650, 30);
        getContentPane().add(rdD);
        optionGroup.add(rdD);

        btnNext = new JButton("Next");
        btnNext.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnNext.setBackground(new Color(0, 128, 0));
        btnNext.setForeground(Color.WHITE);
        btnNext.setBounds(320, 450, 120, 40);
        btnNext.addActionListener(e -> handleNext());
        getContentPane().add(btnNext);

        loadQuestions();
        showQuestion();

        setVisible(true);
    }
    
    /**
     * Fetches question IDs from the database based on difficulty 
     * and shuffles them for a random quiz experience.
     */
    private void loadQuestions() {
        DBHandler db = new DBHandler();
        questionIds = db.getQuestionIdsByDifficulty(
                difficulty,
                TOTAL_ROUNDS * QUESTIONS_PER_ROUND
        );
        Collections.shuffle(questionIds);
    }

    /**
     * Updates the UI labels and radio buttons with the data 
     * of the current question.
     */
    private void showQuestion() {
        int index = currentRound * QUESTIONS_PER_ROUND + questionInRound;

        if (index >= questionIds.size()) {
            finishQuiz();
            return;
        }

        DBHandler db = new DBHandler();
        String[] data = db.getQuestionById(questionIds.get(index));

        lblQuestionNum.setText(
                "Round " + (currentRound + 1) +
                " - Question " + (questionInRound + 1) + " of 5"
        );
        lblScore.setText("Round Score: " + correctInRound + " / 5");

        lblQuestion.setText("<html>" + data[0] + "</html>");
        rbA.setText("A. " + data[1]);
        rbB.setText("B. " + data[2]);
        rcC.setText("C. " + data[3]);
        rdD.setText("D. " + data[4]);

        optionGroup.clearSelection();
    }

    /**
     * Validates the user's selection, updates the score, and 
     * manages the transition between questions and rounds.
     */
    private void handleNext() {
        String selected = null;
        if (rbA.isSelected()) selected = "A";
        else if (rbB.isSelected()) selected = "B";
        else if (rcC.isSelected()) selected = "C";
        else if (rdD.isSelected()) selected = "D";

        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select an option before continuing.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int index = currentRound * QUESTIONS_PER_ROUND + questionInRound;
        DBHandler db = new DBHandler();
        String correct = db.getQuestionById(questionIds.get(index))[5];

        if (selected.equals(correct)) {
            correctInRound++;
        }

        questionInRound++;

        if (questionInRound == QUESTIONS_PER_ROUND) {
            roundScores[currentRound] = correctInRound;
            currentRound++;
            questionInRound = 0;
            correctInRound = 0;

            if (currentRound == TOTAL_ROUNDS) {
                finishQuiz();
                return;
            }
        }

        showQuestion();
    }

    /**
     * Displays final scores to the user and passes the round 
     * results back to the parent GUI for database storage.
     */
    private void finishQuiz() {
        JOptionPane.showMessageDialog(this,
                "Quiz completed!\n" +
                "Round scores: " +
                roundScores[0] + " " +
                roundScores[1] + " " +
                roundScores[2] + " " +
                roundScores[3] + " " +
                roundScores[4],
                "Quiz Finished", JOptionPane.INFORMATION_MESSAGE);

        parentGUI.saveQuizScores(roundScores);
        dispose();
    }
}
