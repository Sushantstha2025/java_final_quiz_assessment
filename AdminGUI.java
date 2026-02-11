package finalAssesment3;

import java.awt.EventQueue;
import java.util.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI();
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
	public AdminGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 788, 647);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADMIN PANEL");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(312, 30, 139, 21);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int confirm = JOptionPane.showConfirmDialog(
		            AdminGUI.this,
		            "Are you sure you want to logout?",
		            "Logout Confirmation",
		            JOptionPane.YES_NO_OPTION
		        );
		        if (confirm == JOptionPane.YES_OPTION) {
		            dispose();                       // Close AdminGUI
		            new LoginGUI().setVisible(true); // Open Login again
		        }
		    }
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(617, 87, 84, 25);
		contentPane.add(btnNewButton);
		
		JLabel lblAdminOperations = new JLabel("ADMIN OPERATIONS");
		lblAdminOperations.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblAdminOperations.setBounds(305, 159, 162, 21);
		contentPane.add(lblAdminOperations);
		
		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AddGUI().setVisible(true);
			}
		});
		btnAdd.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAdd.setBounds(159, 236, 84, 25);
		contentPane.add(btnAdd);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new UpdateGUI().setVisible(true);
			}
		});
		btnUpdate.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnUpdate.setBounds(338, 236, 98, 25);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				new DeleteGUI().setVisible(true);
				dispose();
			}
		});
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDelete.setBounds(547, 236, 98, 25);
		contentPane.add(btnDelete);
		
		JButton btnViewStatistics = new JButton("VIEW STATISTICS");
		btnViewStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Show choice dialog: GUI or Console
				showStatsChoiceDialog();
			}
		});
		btnViewStatistics.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnViewStatistics.setBounds(305, 368, 173, 25);
		contentPane.add(btnViewStatistics);
	}
	
	/**
	 * Shows a dialog asking admin to choose between GUI or Console stats view
	 */
	private void showStatsChoiceDialog() {
	    String[] options = {"1 - GUI View", "2 - Console Interactive View", "Cancel"};
	    int choice = JOptionPane.showOptionDialog(
	            this,
	            "Choose how to view statistics:",
	            "View Statistics",
	            JOptionPane.DEFAULT_OPTION,
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            options,
	            options[0]
	    );

	    if (choice == 0) {
	        // GUI View
	        dispose();
	        new CompetitionGUI().setVisible(true);
	    } else if (choice == 1) {
	        // Console Interactive View
	        dispose();                // Optional: close AdminGUI when entering console
	        runInteractiveConsoleStats();
	    }
	    // Cancel → do nothing (stay in AdminGUI)
	}
	
	private void runInteractiveConsoleStats() {
	    Scanner scanner = new Scanner(System.in);
	    CompetitorList list = new CompetitorList();

	    System.out.println("\n" + "=".repeat(80));
	    System.out.println("  ADMIN CONSOLE - STATISTICS MENU");
	    System.out.println("=".repeat(80));
	    System.out.println();

	    while (true) {
	        System.out.println("Choose an option:");
	        System.out.println("  1  →  Show full report (all competitors)");
	        System.out.println("  2  →  Show short details for a specific competitor (by ID)");
	        System.out.println("  0  →  Exit console and return to login / end");
	        System.out.print("\nEnter choice: ");

	        String input = scanner.nextLine().trim();

	        if (input.isEmpty()) {
	            System.out.println("Please enter a number.\n");
	            continue;
	        }

	        try {
	            int choice = Integer.parseInt(input);

	            if (choice == 0) {
	                System.out.println("\nExiting console mode. Goodbye.");
	                System.out.println("=".repeat(80) + "\n");
	                break;
	            }

	            else if (choice == 1) {
	                printAllCompetitorsTable(list);
	            }

	            else if (choice == 2) {
	                System.out.print("Enter Competitor ID: ");
	                String idStr = scanner.nextLine().trim();

	                try {
	                    int id = Integer.parseInt(idStr);
	                    SushantCompetitor comp = list.getCompetitorByID(id);

	                    if (comp == null) {
	                        System.out.println("→ No competitor found with ID: " + id + "\n");
	                    } else {
	                        String username = getUsernameById(id);
	                        System.out.println("\n" + "=".repeat(60));
	                        System.out.println("Competitor ID: " + id);
	                        System.out.println("Short Details:");
	                        System.out.println(comp.getShortDetails());
	                        System.out.println("-".repeat(60));
	                        System.out.println("Username: " + (username != null ? username : "N/A"));
	                        System.out.println("Overall Score: " + comp.getOverallScore());
	                        System.out.println("=".repeat(60) + "\n");
	                    }
	                } catch (NumberFormatException ex) {
	                    System.out.println("→ Invalid ID: must be a number.\n");
	                }
	            }

	            else {
	                System.out.println("→ Invalid choice. Please enter 0, 1 or 2.\n");
	            }

	        } catch (NumberFormatException ex) {
	            System.out.println("→ Please enter a valid number.\n");
	        }
	    }

	    scanner.close();

	    // Optional: re-open login screen after console exit
	    EventQueue.invokeLater(() -> new AdminGUI().setVisible(true));
	}
	
	/**
	 * Prints the full table of all competitors (your original table layout)
	 */
	private void printAllCompetitorsTable(CompetitorList list) {
	    System.out.println("\n" + "=".repeat(100));
	    System.out.println(" ALL COMPETITORS REPORT");
	    System.out.println("=".repeat(100));
	    System.out.println();

	    if (list.getCompetitors().isEmpty()) {
	        System.out.println("No users found in the database.\n");
	        return;
	    }

	    System.out.printf("%-10s %-20s %-20s %-20s %-30s %-12s%n",
	            "ID", "FIRST NAME", "LAST NAME", "USERNAME", "SCORES (R1-R5)", "OVERALL");
	    System.out.println("-".repeat(100));

	    for (SushantCompetitor comp : list.getCompetitors()) {
	        String fullName = comp.getCompetitorName().getFullName();
	        String[] nameParts = fullName.split("\\s+");
	        String firstName = nameParts.length > 0 ? nameParts[0] : "";
	        String lastName  = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";

	        String username = getUsernameById(comp.getCompetitorID());

	        int[] scores = comp.getScoreArray();
	        String scoresStr = String.format("%d %d %d %d %d",
	                scores[0], scores[1], scores[2], scores[3], scores[4]);

	        System.out.printf("%-10d %-20s %-20s %-20s %-30s %-12.1f%n",
	                comp.getCompetitorID(),
	                firstName,
	                lastName,
	                username != null ? username : "N/A",
	                scoresStr,
	                comp.getOverallScore()
	        );
	    }

	    System.out.println("-".repeat(100));
	    System.out.println("Total Users: " + list.getCompetitors().size());
	    System.out.println("=".repeat(100) + "\n");
	}
	/**
	 * Displays all user statistics in the console
	 */
	private void displayConsoleStats() {
		System.out.println("\n" + "=".repeat(100));
		System.out.println("                           ADMIN CONSOLE - USER STATISTICS");
		System.out.println("=".repeat(100));
		System.out.println();
		
		// Get all competitors from database
		CompetitorList list = new CompetitorList();
		
		if (list.getCompetitors().isEmpty()) {
			System.out.println("No users found in the database.");
			System.out.println("=".repeat(100));
			return;
		}
		
		System.out.printf("%-10s %-20s %-20s %-20s %-30s %-12s%n",
			"ID", "FIRST NAME", "LAST NAME", "USERNAME", "SCORES (R1-R5)", "OVERALL");
		System.out.println("-".repeat(100));
		
		// Print each competitor's data
		for (SushantCompetitor comp : list.getCompetitors()) {
			String firstName = comp.getCompetitorName().getFullName().split(" ")[0];
			String lastName = "";
			String[] nameParts = comp.getCompetitorName().getFullName().split(" ");
			if (nameParts.length > 1) {
				lastName = nameParts[nameParts.length - 1];
			}
			
			// Get username from database
			String username = getUsernameById(comp.getCompetitorID());
			
			// Format scores
			int[] scores = comp.getScoreArray();
			String scoresStr = String.format("%d %d %d %d %d", 
				scores[0], scores[1], scores[2], scores[3], scores[4]);
			
			// Print row
			System.out.printf("%-10d %-20s %-20s %-20s %-30s %-12.1f%n",
				comp.getCompetitorID(),
				firstName,
				lastName,
				username,
				scoresStr,
				comp.getOverallScore()
			);
		}
		
		System.out.println("-".repeat(100));
		System.out.println("Total Users: " + list.getCompetitors().size());
		System.out.println("=".repeat(100));
	}
	
	/**
	 * Helper method to get username by competitor ID
	 */
	private String getUsernameById(int competitorID) {
		try {
			java.sql.Connection conn = DBHandler.getConnection();
			String query = "SELECT username FROM Competitors WHERE competitorID = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, competitorID);
			java.sql.ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {
				String username = rs.getString("username");
				conn.close();
				return username != null ? username : "N/A";
			}
			conn.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return "N/A";
	}
}