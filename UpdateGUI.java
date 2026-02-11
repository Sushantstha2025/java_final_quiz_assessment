package finalAssesment3;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField option_1;
	private JTextField option_2;
	private JTextField option_3;
	private JTextField option_4;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateGUI frame = new UpdateGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UpdateGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 629);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUpdateQuestions = new JLabel("UPDATE QUESTIONS");
		lblUpdateQuestions.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblUpdateQuestions.setBounds(277, 24, 200, 21);
		contentPane.add(lblUpdateQuestions);
		
		/** * Home Button: 
		 * Disposes the current window and returns to the Admin dashboard.
		 */
		JButton btnNewButton = new JButton("Home");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 64, 128));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			    new AdminGUI().setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(57, 70, 84, 20);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("question:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setBounds(64, 172, 63, 21);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(57, 203, 660, 30);
		contentPane.add(textField);
		
		JLabel lblNewLabel_1 = new JLabel("options:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setBounds(64, 281, 63, 21);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("A.");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3.setBounds(126, 329, 19, 17);
		contentPane.add(lblNewLabel_3);
		
		option_1 = new JTextField();
		option_1.setColumns(10);
		option_1.setBounds(155, 329, 96, 18);
		contentPane.add(option_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("B.");
		lblNewLabel_3_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3_1.setBounds(420, 329, 19, 17);
		contentPane.add(lblNewLabel_3_1);
		
		option_2 = new JTextField();
		option_2.setColumns(10);
		option_2.setBounds(449, 329, 96, 18);
		contentPane.add(option_2);
		
		JLabel lblNewLabel_3_2 = new JLabel("C.");
		lblNewLabel_3_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3_2.setBounds(126, 385, 19, 17);
		contentPane.add(lblNewLabel_3_2);
		
		option_3 = new JTextField();
		option_3.setColumns(10);
		option_3.setBounds(155, 385, 96, 18);
		contentPane.add(option_3);
		
		JLabel lblNewLabel_3_3 = new JLabel("D.");
		lblNewLabel_3_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3_3.setBounds(420, 385, 19, 17);
		contentPane.add(lblNewLabel_3_3);
		
		option_4 = new JTextField();
		option_4.setColumns(10);
		option_4.setBounds(449, 385, 96, 18);
		contentPane.add(option_4);
		
		JLabel lblNewLabel_2 = new JLabel("Correct Option:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(177, 441, 102, 21);
		contentPane.add(lblNewLabel_2);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D"}));
		comboBox_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comboBox_1.setBounds(212, 472, 39, 24);
		contentPane.add(comboBox_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Difficulty:");
		lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2_1.setBounds(441, 441, 73, 21);
		contentPane.add(lblNewLabel_2_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Beginner", "Intermediate", "Advanced"}));
		comboBox.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comboBox.setBounds(426, 472, 101, 24);
		contentPane.add(comboBox);
		
		/** * Update Button: 
		 * Validates the modified data and sends it to the 
		 * DBHandler to update the existing database record.
		 */
		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String idStr = textField_1.getText().trim();
		        if (idStr.isEmpty()) {
		            JOptionPane.showMessageDialog(UpdateGUI.this,
		                    "Search for a question first (enter ID).",
		                    "No ID", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        try {
		            int id = Integer.parseInt(idStr);

		            String questionText = textField.getText().trim();
		            String optA = option_1.getText().trim();
		            String optB = option_2.getText().trim();
		            String optC = option_3.getText().trim();
		            String optD = option_4.getText().trim();
		            String correct = (String) comboBox_1.getSelectedItem();
		            String difficulty = (String) comboBox.getSelectedItem();
		            
		            if (questionText.isEmpty() || optA.isEmpty() || optB.isEmpty() ||
		                optC.isEmpty() || optD.isEmpty() || correct == null || difficulty == null) {
		                JOptionPane.showMessageDialog(UpdateGUI.this,
		                        "Cannot update: fill question, all options, correct option and difficulty.",
		                        "Incomplete Form", JOptionPane.WARNING_MESSAGE);
		                return;
		            }

		            DBHandler db = new DBHandler();
		            boolean success = db.updateQuestion(id, questionText, optA, optB, optC, optD, correct, difficulty);

		            if (success) {
		                JOptionPane.showMessageDialog(UpdateGUI.this,
		                        "Question ID " + id + " updated successfully!",
		                        "Updated", JOptionPane.INFORMATION_MESSAGE);

		                // Clear everything for next search
		                textField_1.setText("");   
		                textField.setText("");
		                option_1.setText("");
		                option_2.setText("");
		                option_3.setText("");
		                option_4.setText("");
		                comboBox_1.setSelectedIndex(-1);
		                comboBox.setSelectedIndex(-1);
		            } else {
		                JOptionPane.showMessageDialog(UpdateGUI.this,
		                        "Update failed. Question ID " + id + " may not exist or DB error.",
		                        "Update Failed", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(UpdateGUI.this,
		                    "Question ID must be a number.",
		                    "Invalid ID", JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});
		update.setForeground(new Color(64, 0, 64));
		update.setFont(new Font("Times New Roman", Font.BOLD, 14));
		update.setBackground(new Color(255, 255, 128));
		update.setBounds(305, 524, 84, 30);
		contentPane.add(update);
		
		JLabel lblNewLabel_4 = new JLabel("Search Question by ID:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_4.setBounds(187, 112, 153, 21);
		contentPane.add(lblNewLabel_4);
		
		textField_1 = new JTextField();
		textField_1.setBounds(350, 112, 39, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		/** * Search Button: 
		 * Queries the database for a specific question ID and 
		 * populates the text fields with existing data if found.
		 */
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String idStr = textField_1.getText().trim();
		        if (idStr.isEmpty()) {
		            JOptionPane.showMessageDialog(UpdateGUI.this,
		                    "Please enter a Question ID to search.",
		                    "Input Required", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        try {
		            int id = Integer.parseInt(idStr);
		            DBHandler db = new DBHandler();
		            String[] data = db.getQuestionById(id);

		            if (data != null) {
		                // Fill fields
		                textField.setText(data[0]);     // question
		                option_1.setText(data[1]);      // A
		                option_2.setText(data[2]);      // B
		                option_3.setText(data[3]);      // C
		                option_4.setText(data[4]);      // D
		                comboBox_1.setSelectedItem(data[5]);  // correct option A/B/C/D
		                comboBox.setSelectedItem(data[6]);    // difficulty
		            } else {
		                JOptionPane.showMessageDialog(UpdateGUI.this,
		                        "No question found with ID: " + id,
		                        "Not Found", JOptionPane.INFORMATION_MESSAGE);

		                // Clear fields if not found
		                textField.setText("");
		                option_1.setText("");
		                option_2.setText("");
		                option_3.setText("");
		                option_4.setText("");
		                comboBox_1.setSelectedIndex(-1);
		                comboBox.setSelectedIndex(-1);
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(UpdateGUI.this,
		                    "Question ID must be a number.",
		                    "Invalid ID", JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});
		search.setForeground(new Color(64, 0, 64));
		search.setBackground(new Color(255, 128, 64));
		search.setFont(new Font("Times New Roman", Font.BOLD, 14));
		search.setBounds(420, 113, 84, 21);
		contentPane.add(search);

	}

}
