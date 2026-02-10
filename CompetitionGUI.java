package finalAssesment3;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompetitionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField;
	private JTextArea displayArea;

	private CompetitorList competitorList;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				CompetitionGUI frame = new CompetitionGUI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public CompetitionGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 710, 600);
		setTitle("Competition Manager");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null); 

		competitorList = new CompetitorList();

		JButton reportButton = new JButton("Show Full Report");
		reportButton.setBounds(27, 35, 150, 20);
		contentPane.add(reportButton);

		JLabel idLabel = new JLabel("Enter Competitor ID:");
		idLabel.setBounds(205, 37, 120, 16);
		contentPane.add(idLabel);

		idField = new JTextField();
		idField.setBounds(330, 36, 60, 18);
		contentPane.add(idField);

		JButton lookupButton = new JButton("Show Details");
		lookupButton.setBounds(400, 35, 120, 20);
		contentPane.add(lookupButton);

		JButton home = new JButton("Home");
		home.setBounds(602, 35, 84, 20);
		contentPane.add(home);

		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setLineWrap(true);
		displayArea.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(displayArea);
		scrollPane.setBounds(28, 80, 658, 460);
		contentPane.add(scrollPane);

		/** Action for the Report Button: Displays all competitors in the text area. */
		reportButton.addActionListener(e -> {
			displayArea.setText(competitorList.generateFullReport());
			displayArea.setCaretPosition(0);
		});

		/** 
		 * \Action for the Lookup Button: Triggers the search logic.
		 */
		lookupButton.addActionListener(e -> showCompetitorDetails());

		idField.addActionListener(e -> showCompetitorDetails());
		
		/** 
		 Action for the Home Button: Returns to the Admin dashboard. 
		 */
		home.addActionListener(e -> {
			dispose();
			new AdminGUI().setVisible(true);
		});
	}
	
	/**
	 * Validates the ID input and retrieves competitor details from the list.
	 * Shows an error message if the ID is not found or is not a number.
	 */
	private void showCompetitorDetails() {
		String input = idField.getText().trim();

		if (input.isEmpty()) {
			JOptionPane.showMessageDialog(this,
					"Please enter a Competitor ID.",
					"Input Required",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			int id = Integer.parseInt(input);
			SushantCompetitor comp = competitorList.getCompetitorByID(id);

			if (comp == null) {
				JOptionPane.showMessageDialog(this,
						"No competitor found with ID: " + id,
						"Not Found",
						JOptionPane.ERROR_MESSAGE);
				displayArea.setText("");
			} else {
				displayArea.setText(
						comp.getShortDetails() +
						"\n\n" +
						comp.getFullDetails()
				);
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this,
					"Competitor ID must be a number.",
					"Invalid Input",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
