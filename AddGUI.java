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
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AddGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField option_1;
	private JTextField option_2;
	private JTextField option_3;
	private JTextField option_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddGUI frame = new AddGUI();
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
	public AddGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 654);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddQuestions = new JLabel("ADD QUESTIONS");
		lblAddQuestions.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblAddQuestions.setBounds(321, 31, 153, 21);
		contentPane.add(lblAddQuestions);
		
		JButton btnNewButton = new JButton("Home");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			    new AdminGUI().setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(31, 32, 84, 20);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("question:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setBounds(52, 107, 63, 21);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(52, 138, 660, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("options:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setBounds(52, 224, 63, 21);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		comboBox.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Beginner", "Intermediate", "Advanced"}));
		comboBox.setBounds(433, 462, 101, 24);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Correct Option:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(136, 431, 102, 21);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Difficulty:");
		lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2_1.setBounds(444, 431, 73, 21);
		contentPane.add(lblNewLabel_2_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		comboBox_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D"}));
		comboBox_1.setBounds(167, 462, 39, 24);
		contentPane.add(comboBox_1);
		
		/** * Save Button: Validates the input and calls DBHandler to 
		 * store the new question in the database. 
		 */
		JButton btnNewButton_1 = new JButton("Save");
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String questionText = textField.getText().trim();

		        String optA = option_1.getText().trim();
		        String optB = option_2.getText().trim();
		        String optC = option_3.getText().trim();
		        String optD = option_4.getText().trim();

		        String correctLetter = (String) comboBox_1.getSelectedItem();  
		        String difficulty = (String) comboBox.getSelectedItem();       

		        if (questionText.isEmpty() || optA.isEmpty() || optB.isEmpty() ||
		            optC.isEmpty() || optD.isEmpty() || correctLetter == null || difficulty == null) {
		            JOptionPane.showMessageDialog(AddGUI.this,
		                    "Please fill in:\n- The question\n- All four options (A, B, C, D)\n- Select correct option and difficulty",
		                    "Missing Information", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        DBHandler db = new DBHandler();
		        boolean success = db.addQuestion(
		            questionText,
		            optA, optB, optC, optD,
		            correctLetter,
		            difficulty
		        );

		        if (success) {
		            JOptionPane.showMessageDialog(AddGUI.this,
		                    "Question added successfully!",
		                    "Success", JOptionPane.INFORMATION_MESSAGE);

		            textField.setText("");
		            option_1.setText("");
		            option_2.setText("");
		            option_3.setText("");
		            option_4.setText("");
		            comboBox_1.setSelectedIndex(-1); 
		        } else {
		            JOptionPane.showMessageDialog(AddGUI.this,
		                    "Failed to save question.\n\nPossible reasons:\n• Database not running\n• Table 'questions' missing or columns don't match\n• Connection problem",
		                    "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(0, 64, 128));
		btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton_1.setBounds(282, 523, 84, 30);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_3 = new JLabel("A.");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3.setBounds(161, 284, 19, 17);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("B.");
		lblNewLabel_3_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3_1.setBounds(455, 284, 19, 17);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("C.");
		lblNewLabel_3_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3_2.setBounds(161, 340, 19, 17);
		contentPane.add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_3 = new JLabel("D.");
		lblNewLabel_3_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3_3.setBounds(455, 340, 19, 17);
		contentPane.add(lblNewLabel_3_3);
		
		option_1 = new JTextField();
		option_1.setBounds(190, 284, 96, 18);
		contentPane.add(option_1);
		option_1.setColumns(10);
		
		option_2 = new JTextField();
		option_2.setColumns(10);
		option_2.setBounds(484, 284, 96, 18);
		contentPane.add(option_2);
		
		option_3 = new JTextField();
		option_3.setColumns(10);
		option_3.setBounds(190, 340, 96, 18);
		contentPane.add(option_3);
		
		option_4 = new JTextField();
		option_4.setColumns(10);
		option_4.setBounds(484, 340, 96, 18);
		contentPane.add(option_4);

	}
}
