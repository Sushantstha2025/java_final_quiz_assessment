package finalAssesment3;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private DBHandler dbHandler = new DBHandler();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 758, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRegisterPage = new JLabel("REGISTER PAGE");
		lblRegisterPage.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblRegisterPage.setBounds(308, 57, 156, 27);
		contentPane.add(lblRegisterPage);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(308, 239, 76, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password:");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(308, 291, 76, 17);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Last Name: ");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(308, 185, 76, 17);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("First Name:");
		lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(308, 135, 76, 17);
		contentPane.add(lblNewLabel_1_3);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(422, 135, 96, 18);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(422, 185, 96, 18);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(422, 239, 96, 18);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(422, 291, 96, 18);
		contentPane.add(textField_3);
		
		
		/**  
		 * Checks if all fields are filled.
		 * Verifies if both password fields match.
		 * Calls DBHandler to insert the new user record.
		 */
		JButton btnRegisre = new JButton("Register");
		btnRegisre.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String firstName = textField.getText().trim();
		        String lastName = textField_1.getText().trim();
		        String username = textField_2.getText().trim();
		        String password = textField_3.getText().trim();
		        String confirmPassword = textField_4.getText().trim();

		        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
		            password.isEmpty() || confirmPassword.isEmpty()) {
		            JOptionPane.showMessageDialog(RegisterGUI.this,
		                    "All fields are required.",
		                    "Input Error", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        if (!password.equals(confirmPassword)) {
		            JOptionPane.showMessageDialog(RegisterGUI.this,
		                    "Passwords do not match.",
		                    "Password Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        boolean success = dbHandler.registerUser(username, password, firstName, lastName);

		        if (success) {
		            JOptionPane.showMessageDialog(RegisterGUI.this,
		                    "Registration successful! You can now login.",
		                    "Success", JOptionPane.INFORMATION_MESSAGE);
		            dispose();  
		            new LoginGUI().setVisible(true);
		            
		        } else {
		            JOptionPane.showMessageDialog(RegisterGUI.this,
		                    "Registration failed. Username may already exist.",
		                    "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		btnRegisre.setForeground(Color.WHITE);
		btnRegisre.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		btnRegisre.setBackground(new Color(0, 128, 0));
		btnRegisre.setBounds(350, 403, 96, 20);
		contentPane.add(btnRegisre);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Confirm Password:");
		lblNewLabel_1_1_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(254, 345, 130, 17);
		contentPane.add(lblNewLabel_1_1_1);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(422, 345, 96, 18);
		contentPane.add(textField_4);
		
		JButton btnBack = new JButton("Back to Login");
		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		btnBack.setBackground(new Color(139, 0, 0));
		btnBack.setBounds(334, 445, 140, 25);
		contentPane.add(btnBack);

		/** 
		 * Closes the registration screen and returns to the Login window.
		 */
		btnBack.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        dispose();  
		        new LoginGUI().setVisible(true);
		    }
		});

	}

}
