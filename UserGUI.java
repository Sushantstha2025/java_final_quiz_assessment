package finalAssesment3;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import java.awt.Dimension;

public class UserGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblWelcome;
    private JComboBox<String> comboBox;

    private String username;

    public UserGUI(String firstName, String username) {
        this.username = username;

        setTitle("Quiz Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 771, 638);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("QUIZ GAME");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblTitle.setBounds(327, 23, 119, 29);
        contentPane.add(lblTitle);

        lblWelcome = new JLabel("Welcome");
        lblWelcome.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblWelcome.setBounds(80, 136, 227, 15);
        contentPane.add(lblWelcome);

        lblWelcome.setText("Welcome, " + firstName + "!");

        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Times New Roman", Font.BOLD, 14));
        logout.addActionListener(e -> {
            dispose();
            new LoginGUI().setVisible(true);
        });
        logout.setBounds(628, 98, 84, 20);
        contentPane.add(logout);

        comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Times New Roman", Font.BOLD, 14));
        comboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                "Beginner", "Intermediate", "Advanced"
        }));
        comboBox.setBounds(565, 247, 90, 29);
        contentPane.add(comboBox);

        /** 
         * Play Button: Starts a new QuizGUI session using the 
         * selected difficulty level. 
         */
        JButton play = new JButton("PLAY QUIZ");
        play.addActionListener(e -> {
            String selectedDifficulty = (String) comboBox.getSelectedItem();
            if (selectedDifficulty == null) {
                JOptionPane.showMessageDialog(this,
                        "Please select a difficulty level.",
                        "No Difficulty", JOptionPane.WARNING_MESSAGE);
                return;
            }

            new QuizGUI(username, selectedDifficulty, this).setVisible(true);
        });
        play.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 16));
        play.setBounds(327, 278, 119, 37);
        contentPane.add(play);

        /** 
         *  View Stats Button: Retrieves and displays the player's 
         * personal score history from the database. 
         */
        JButton view = new JButton("View Stats");
        view.addActionListener(e -> showMyStats());
        view.setFont(new Font("Times New Roman", Font.BOLD, 14));
        view.setBounds(141, 389, 111, 29);
        contentPane.add(view);

        /** 
         * Leaderboard Button: Displays a full report of all 
         * competitors using the CompetitorList logic. 
         */
        JButton leaderboard = new JButton("Leaderboard");
        leaderboard.addActionListener(e -> {
            CompetitorList list = new CompetitorList();
            String report = list.generateFullReport();

            JTextArea textArea = new JTextArea(report);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(700, 500));

            JOptionPane.showMessageDialog(this, scrollPane,
                    "Leaderboard & Statistics", JOptionPane.PLAIN_MESSAGE);
        });
        leaderboard.setFont(new Font("Times New Roman", Font.BOLD, 14));
        leaderboard.setBounds(529, 389, 126, 29);
        contentPane.add(leaderboard);

        JLabel lblDifficulty = new JLabel("Choose Difficulty:");
        lblDifficulty.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        lblDifficulty.setBounds(555, 210, 111, 27);
        contentPane.add(lblDifficulty);
    }
    
    /**
     * Queries the DBHandler for the current user's past quiz results 
     * and displays them in a scrollable format.
     */
    private void showMyStats() {
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No username. Login again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DBHandler db = new DBHandler();
        java.util.List<String[]> history = db.getPlayerScoreHistory(username);

        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No play history yet. Complete a quiz!", "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Your Play History (most recent first):\n\n");

        for (String[] rec : history) {
            sb.append("Date: ").append(rec[0]).append("\n");
            sb.append("Scores: ").append(rec[1]).append(" ").append(rec[2]).append(" ")
              .append(rec[3]).append(" ").append(rec[4]).append(" ").append(rec[5]).append("\n");
            sb.append("Overall: ").append(rec[6]).append("\n");
            sb.append("------------------------\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(550, 400));

        JOptionPane.showMessageDialog(this, scroll, "Your Stats History", JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * Callback method used by QuizGUI to save results upon completion.
     * It updates both the main competitor record and the history table.
     * @param roundScores An array of 5 integers representing scores for each round.
     */
    public void saveQuizScores(int[] roundScores) {
        if (roundScores == null || roundScores.length != 5) {
            JOptionPane.showMessageDialog(this, "Invalid score data.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DBHandler db = new DBHandler();
        
        boolean updateSuccess = db.updatePlayerScores(username, roundScores);
        
        boolean insertSuccess = db.insertPlayerScore(username, roundScores);

        if (updateSuccess && insertSuccess) {
            JOptionPane.showMessageDialog(this,
                    "Quiz completed! Your scores have been saved.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (insertSuccess) {
            JOptionPane.showMessageDialog(this,
                    "Quiz completed! History saved, but main score update failed.",
                    "Partial Success", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to save your quiz scores.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UserGUI frame = new UserGUI("Guest", "guest_user");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}