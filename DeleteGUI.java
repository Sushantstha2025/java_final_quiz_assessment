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
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField id;
	private JTextField question;
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
					DeleteGUI frame = new DeleteGUI();
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
	public DeleteGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 751, 614);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDeleteQuestion = new JLabel("DELETE QUESTION");
		lblDeleteQuestion.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblDeleteQuestion.setBounds(295, 26, 184, 21);
		contentPane.add(lblDeleteQuestion);
		
		JButton btnNewButton = new JButton("Home");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 160));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AdminGUI().setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(67, 78, 84, 20);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_4 = new JLabel("Search Question by ID:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_4.setBounds(191, 130, 153, 21);
		contentPane.add(lblNewLabel_4);
		
		id = new JTextField();
		id.setColumns(10);
		id.setBounds(354, 132, 39, 21);
		contentPane.add(id);
		
		JLabel lblNewLabel = new JLabel("question:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setBounds(75, 183, 63, 21);
		contentPane.add(lblNewLabel);
		
		question = new JTextField();
		question.setColumns(10);
		question.setBounds(67, 214, 660, 30);
		contentPane.add(question);
		
		JLabel lblNewLabel_1 = new JLabel("options:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setBounds(75, 289, 63, 21);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("A.");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3.setBounds(143, 328, 19, 17);
		contentPane.add(lblNewLabel_3);
		
		option_1 = new JTextField();
		option_1.setColumns(10);
		option_1.setBounds(172, 328, 96, 18);
		contentPane.add(option_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("B.");
		lblNewLabel_3_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3_1.setBounds(437, 328, 19, 17);
		contentPane.add(lblNewLabel_3_1);
		
		option_2 = new JTextField();
		option_2.setColumns(10);
		option_2.setBounds(466, 328, 96, 18);
		contentPane.add(option_2);
		
		JLabel lblNewLabel_3_2 = new JLabel("C.");
		lblNewLabel_3_2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3_2.setBounds(143, 384, 19, 17);
		contentPane.add(lblNewLabel_3_2);
		
		option_3 = new JTextField();
		option_3.setColumns(10);
		option_3.setBounds(172, 384, 96, 18);
		contentPane.add(option_3);
		
		JLabel lblNewLabel_3_3 = new JLabel("D.");
		lblNewLabel_3_3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3_3.setBounds(437, 384, 19, 17);
		contentPane.add(lblNewLabel_3_3);
		
		option_4 = new JTextField();
		option_4.setColumns(10);
		option_4.setBounds(466, 384, 96, 18);
		contentPane.add(option_4);
		
		JLabel lblNewLabel_2 = new JLabel("Correct Option:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(191, 436, 102, 21);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Difficulty:");
		lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2_1.setBounds(455, 436, 73, 21);
		contentPane.add(lblNewLabel_2_1);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D"}));
		comboBox_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comboBox_1.setBounds(226, 467, 39, 24);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Beginner", "Intermediate", "Advanced"}));
		comboBox.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comboBox.setBounds(440, 467, 101, 24);
		contentPane.add(comboBox);
		
		JButton search = new JButton("Search");
		/** triggers the seach button to delete*/
		search.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String idStr = id.getText().trim();
		        if (idStr.isEmpty()) {
		            JOptionPane.showMessageDialog(DeleteGUI.this,
		                    "Please enter a Question ID to search.",
		                    "Input Required", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        try {
		            int qId = Integer.parseInt(idStr);
		            DBHandler db = new DBHandler();
		            String[] data = db.getQuestionById(qId);  

		            if (data != null) {
		                question.setText(data[0]);     // question
		                option_1.setText(data[1]);     // A
		                option_2.setText(data[2]);     // B
		                option_3.setText(data[3]);     // C
		                option_4.setText(data[4]);     // D
		                comboBox_1.setSelectedItem(data[5]);  // correct A/B/C/D
		                comboBox.setSelectedItem(data[6]);    // difficulty
		            } else {
		                JOptionPane.showMessageDialog(DeleteGUI.this,
		                        "No question found with ID: " + qId,
		                        "Not Found", JOptionPane.INFORMATION_MESSAGE);

		                question.setText("");
		                option_1.setText("");
		                option_2.setText("");
		                option_3.setText("");
		                option_4.setText("");
		                comboBox_1.setSelectedIndex(-1);
		                comboBox.setSelectedIndex(-1);
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(DeleteGUI.this,
		                    "Question ID must be a number.",
		                    "Invalid ID", JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});
		search.setForeground(new Color(64, 0, 64));
		search.setFont(new Font("Times New Roman", Font.BOLD, 14));
		search.setBackground(new Color(255, 128, 64));
		search.setBounds(403, 131, 84, 21);
		contentPane.add(search);
		
		/** delete button to delete the selected question*/
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String idStr = id.getText().trim();
		        if (idStr.isEmpty()) {
		            JOptionPane.showMessageDialog(DeleteGUI.this,
		                    "Please search for a question first (enter ID).",
		                    "No ID", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        try {
		            int qId = Integer.parseInt(idStr);
		            int confirm = JOptionPane.showConfirmDialog(DeleteGUI.this,
		                    "Are you sure you want to DELETE question ID " + qId + "?\nThis cannot be undone.",
		                    "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		            if (confirm != JOptionPane.YES_OPTION) {
		                return;  
		            }

		            DBHandler db = new DBHandler();
		            boolean deleted = db.deleteQuestion(qId);

		            if (deleted) {
		                JOptionPane.showMessageDialog(DeleteGUI.this,
		                        "Question ID " + qId + " deleted successfully.",
		                        "Deleted", JOptionPane.INFORMATION_MESSAGE);

		                id.setText("");
		                question.setText("");
		                option_1.setText("");
		                option_2.setText("");
		                option_3.setText("");
		                option_4.setText("");
		                comboBox_1.setSelectedIndex(-1);
		                comboBox.setSelectedIndex(-1);
		            } else {
		                JOptionPane.showMessageDialog(DeleteGUI.this,
		                        "Delete failed. Question ID " + qId + " may not exist or DB error.",
		                        "Delete Failed", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(DeleteGUI.this,
		                    "Question ID must be a number.",
		                    "Invalid ID", JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});
		btnDelete.setForeground(new Color(255, 255, 255));
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDelete.setBackground(new Color(255, 0, 0));
		btnDelete.setBounds(319, 519, 84, 30);
		contentPane.add(btnDelete);

	}

}
