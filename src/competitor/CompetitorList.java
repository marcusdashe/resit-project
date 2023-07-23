package competitor;

/**
*
 * The CompetitorList class represents a list of JDCompetitor objects and provides methods for managing and processing them.
 * It allows adding competitors, generating random competitors, reading competitors' details from a file, and displaying
 * competitor information.
 * @author Jude
 * @version 1.0
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CompetitorList {

    private ArrayList<JDCompetitor> competitorList;
    CompetitorReport report = new CompetitorReport();

    /**
     * Generates a random real name.
     *
     * @return A randomly generated real name.
     */
    private String generateRandomName() {
        Random random = new Random();
        String[] firstNames = {"Ntiem", "Teishei", "Shagog", "Dalo", "Katket"};
        String[] lastNames = {"Dashe", "Dajen", "Najen", "Puntwei", "Puntehl"};
        return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }

    /**
     * Creates a new CompetitorList object with an empty list of competitors.
     */
    public CompetitorList() {
        competitorList = new ArrayList<>();
    }

    /**
     * Adds a JDCompetitor object to the list of competitors.
     *
     * @param competitor The JDCompetitor object to be added to the list.
     */
    public void addCompetitor(JDCompetitor competitor) {
        competitorList.add(competitor);
    }

    /**
     * Retrieves the list of competitors.
     *
     * @return An ArrayList containing JDCompetitor objects.
     */
    public ArrayList<JDCompetitor> getCompetitorList() {
        return competitorList;
    }

    /**
     * Prints the full and short details of all competitors in the list and generates a competitor report.
     */
    public void printCompetitorList() {
        for (JDCompetitor competitor : competitorList) {
            competitor.getFullDetails();
            competitor.getShortDetails();
            report.addCompetitor(competitor);
        }
        report.generateReport();
    }

    /**
     * Retrieves a JDCompetitor object based on the provided competitor number.
     *
     * @param competitorNumber The number of the competitor to retrieve.
     * @return The JDCompetitor object matching the competitor number, or null if not found.
     */
    public JDCompetitor getCompetitorByNumber(int competitorNumber) {
        for (JDCompetitor competitor : competitorList) {
            if (competitor.getCompetitorNumber() == competitorNumber) {
                return competitor;
            }
        }
        return null;
    }

    /**
     * Generates 10 random competitors and adds them to the competitor list.
     * Note: The random competitors will have random names, levels, ages, and scores.
     */
    public void generateRandomCompetitors() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int competitorNumber = i + 1;
            String name = generateRandomName();
            JDCompetitor.CompetitorLevel level = JDCompetitor.CompetitorLevel.values()[random.nextInt(3)];
            int age = random.nextInt(100);
            int[] scores = new int[5];
            for (int j = 0; j < scores.length; j++) {
                scores[j] = random.nextInt(6);
            }

            JDCompetitor competitor = new JDCompetitor(competitorNumber, name, level, age);
            for(int sc : scores)
                competitor.addScore(sc);

            competitorList.add(competitor);
        }
    }

    /*Please note that this code assumes the input file is in CSV format with the following columns:
    competitorNumber, name, level, age, and five columns for scores (score1, score2, score3, score4, score5).*/

    /**
     * Reads competitor details from a text file in CSV format and populates the competitor list accordingly.
     * The expected format of each line in the file is: competitorNumber,name,level,age,score1,score2,score3,score4,score5
     *
     * @param filePath The path to the input file.
     */
    public void readCompetitorDetailsFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                // Check if there are enough columns for a valid competitor entry
                if (parts.length < 9) {
                    System.out.println("Error in input file: Insufficient columns for a valid competitor entry.");
                    continue;
                }

                try {
                int competitorNumber = Integer.parseInt(parts[0]);
                String name = parts[1];

                JDCompetitor.CompetitorLevel level = JDCompetitor.CompetitorLevel.valueOf(parts[2]);
                int age = Integer.parseInt(parts[3]);
                int[] scores = new int[5];

                for (int i = 0; i < 5; i++) {
                    scores[i] = Integer.parseInt(parts[i + 4]);
                    if (scores[i] < 0 || scores[i] > 5) {
                        System.out.println("Error in input file: Invalid score. Scores should be between 0 and 5.");
                        continue;
                    }
                }

                // Create and add the competitor to the list
                JDCompetitor competitor = new JDCompetitor(competitorNumber, name, level, age);
                for (int sc : scores)
                    competitor.addScore(sc);
                competitorList.add(competitor);
            } catch (NumberFormatException e) {
                    System.out.println("Error in input file: Invalid number format. Check competitorNumber, age, or scores.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error in input file: Invalid CompetitorLevel. Check the level field.");
                }
            scanner.close(); }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format in the file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid CompetitorLevel in the file: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to enter a competitor number and displays the short details of the corresponding competitor.
     *
     * @param competitorList The CompetitorList object containing the list of competitors.
     */
    public static void getAndDisplayShortDetails(CompetitorList competitorList) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter a competitor number: ");
        int competitorNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        JDCompetitor competitor = competitorList.getCompetitorByNumber(competitorNumber);
        if (competitor != null) {
            competitor.getShortDetails();
        } else {
            System.out.println("Invalid competitor number.");
        }
        scanner.close();
    }
}
