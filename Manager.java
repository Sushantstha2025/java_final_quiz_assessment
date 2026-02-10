package finalAssesment3;

import java.util.Scanner;
import java.util.Map;

/**
 * The main controller class that manages the flow of the application.
 * It handles report generation and user interaction.
 */
public class Manager {
    private CompetitorList list;

    /**
     * Initializes the manager and loads the list of competitors.
     */
    public Manager() {
        this.list = new CompetitorList();
    }

    /**
     * Prints a detailed table of all competitors, statistics, 
     * and score frequencies to the console.
     */
    public void generateConsoleReport() {
        System.out.println("\n--- COMPETITOR REPORT ---");
        System.out.printf("%-5s %-20s %-15s %-10s\n", "ID", "Name", "Scores", "Overall");
        System.out.println("------------------------------------------------------");

        for (SushantCompetitor c : list.getCompetitors()) {
            String scores = "";
            for (int s : c.getScoreArray()) scores += s + " ";
            System.out.printf("%-5d %-20s %-15s %-10.1f\n",
                    c.getCompetitorID(),
                    c.getCompetitorName().getFullName(),
                    scores.trim(),
                    c.getOverallScore());
        }

        System.out.println("\n--- STATISTICAL SUMMARY ---");
        System.out.println("Total Competitors: " + list.getCompetitors().size());

        SushantCompetitor top = list.getTopPerformer();
        if (top != null) {
            System.out.println("Top Performer: " + top.getCompetitorName().getFullName() +
                    " with a score of " + top.getOverallScore());
        }

        System.out.println("\nFrequency of Individual Scores:");
        Map<Integer, Integer> freqs = list.getScoreFrequencies();
        System.out.print("Score:      ");
        for (int i = 0; i <= 5; i++) System.out.print(i + " ");
        System.out.print("\nFrequency: ");
        for (int i = 0; i <= 5; i++) System.out.print(freqs.getOrDefault(i, 0) + " ");
        System.out.println("\n");
    }

    /**
     * Starts the command-line interface for the user to choose report 
     * formats and search for competitors by ID.
     */
    public void runInteraction() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Competition Manager.");
        System.out.println("Enter 1 to generate report in GUI (Coming soon)");
        System.out.println("Enter 2 to generate report in Console");

        int choice = sc.nextInt();
        if (choice == 2) {
            generateConsoleReport();
        } else {
            System.out.println("GUI Report triggered! (We will build this in the next step)");
        }

        System.out.print("Enter Competitor ID to search: ");
        int id = sc.nextInt();
        SushantCompetitor found = list.getCompetitorByID(id);
        if (found != null) {
            System.out.println(found.getShortDetails());
        } else {
            System.out.println("Invalid ID. Competitor not found.");
        }
        
        sc.close();
    }

    public static void main(String[] args) {
        Manager mgr = new Manager();
        mgr.runInteraction();
    }
}