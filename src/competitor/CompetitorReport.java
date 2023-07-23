package competitor;

/**

 * @author Jude
 * @version 1.0
 */


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CompetitorReport {
    private List<JDCompetitor> competitors;

    public CompetitorReport() {
        competitors = new ArrayList<>();
    }

    public void addCompetitor(JDCompetitor competitor){
        competitors.add(competitor);
    }

    public void generateReport(){
        printCompetitorDetails();
        printStatistics();
    }

    public void generateReportFile(){
        try{
            // Create a FileWriter and PrintWriter to write the report to a text file
            FileWriter fileWriter = new FileWriter("competitor_report.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printCompetitorDetails(printWriter);
            printStatistics(printWriter);

            // Close the PrintWriter and FileWriter
            printWriter.close();
            fileWriter.close();
        } catch (IOException e){
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

    private void printCompetitorDetails(PrintWriter printWriter) {
        // Write the table of competitors with full details to the file
        printWriter.println("Competitor Level Scores Overall");
        for (JDCompetitor competitor : competitors) {
            printWriter.printf("%d %s %s %.1f%n",
                    competitor.getCompetitorNumber(),
                    competitor.getName(),
                    competitor.getLevel(),
                    competitor.getOverallScore());
        }
        printWriter.println(); // Empty line for better readability in the file
    }

    private void printStatistics(PrintWriter printWriter) {
        // Write details of the competitor with the highest overall score to the file
        JDCompetitor highestScoreCompetitor = getWinner();
        printWriter.println("Competitor with the highest overall score:");
        printWriter.println(highestScoreCompetitor.getName() + " with a score of " + highestScoreCompetitor.getOverallScore());
        printWriter.println(); // Empty line for better readability in the file

        // Write the frequency report for individual scores to the file
        printWriter.println("Frequency report for individual scores:");
        Map<Integer, Integer> scoreFrequency = calculateScoreFrequency();
        for (int score = 1; score <= 5; score++) {
            int frequency = scoreFrequency.getOrDefault(score, 0);
            printWriter.printf("Score %d: %d times%n", score, frequency);
        }
        printWriter.println(); // Empty line for better readability in the file

        // Write three other summary statistics of your choice to the file
        // For example, you can calculate totals, averages, max, and min of overall scores
        double totalOverallScore = calculateTotalOverallScore();
        double averageOverallScore = calculateAverageOverallScore();
        double maxOverallScore = calculateMaxOverallScore();
        double minOverallScore = calculateMinOverallScore();

        printWriter.println("Summary Statistics:");
            printWriter.println("Total Overall Score: " + totalOverallScore);
        printWriter.println("Average Overall Score: " + averageOverallScore);
        printWriter.println("Max Overall Score: " + maxOverallScore);
        printWriter.println("Min Overall Score: " + minOverallScore);
    }

    private double calculateTotalOverallScore() {
        double totalScore = 0.0;
        for (JDCompetitor competitor : competitors) {
            totalScore += competitor.getOverallScore();
        }
        return totalScore;
    }

    private double calculateAverageOverallScore() {
        double totalScore = calculateTotalOverallScore();
        return totalScore / competitors.size();
    }

    private double calculateMaxOverallScore() {
        double maxScore = Double.MIN_VALUE;
        for (JDCompetitor competitor : competitors) {
            double overallScore = competitor.getOverallScore();
            if (overallScore > maxScore) {
                maxScore = overallScore;
            }
        }
        return maxScore;
    }

    private double calculateMinOverallScore() {
        double minScore = Double.MAX_VALUE;
        for (JDCompetitor competitor : competitors) {
            double overallScore = competitor.getOverallScore();
            if (overallScore < minScore) {
                minScore = overallScore;
            }
        }
        return minScore;
    }

    private void printCompetitorDetails() {
        System.out.println("Competitor Level Scores Overall");
        for (JDCompetitor competitor : competitors) {
            System.out.printf("%d %s %s %.1f%n",
                    competitor.getCompetitorNumber(),
                    competitor.getName(),
                    competitor.getLevel(),
                    competitor.getOverallScore());
        }
    }

    private void printStatistics() {
        int totalCompetitors = competitors.size();
        System.out.println("\nSTATISTICAL");
        System.out.println("There are " + totalCompetitors + " competitors.");

        JDCompetitor winner = getWinner();
        System.out.println("The person with the highest score is " + winner.getName() +
                " with a score of " + winner.getOverallScore() + ".");

        Map<Integer, Integer> scoreFrequency = calculateScoreFrequency();
        System.out.println("The following individual scores were awarded:");
        System.out.println("Score : 1 2 3 4 5");
        System.out.print("Frequency: ");
        for (int score = 1; score <= 5; score++) {
            int frequency = scoreFrequency.getOrDefault(score, 0);
            System.out.print(frequency + " ");
        }
    }

    private JDCompetitor getWinner() {
        return Collections.max(competitors, (c1, c2) -> Double.compare(c1.getOverallScore(), c2.getOverallScore()));
    }

    private Map<Integer, Integer> calculateScoreFrequency() {
        Map<Integer, Integer> scoreFrequency = new HashMap<>();
        for (JDCompetitor competitor : competitors) {
            int[] scores = competitor.getScoreArray();
            for (int score : scores) {
                int roundedScore = (int) Math.round(score);
                scoreFrequency.put(roundedScore, scoreFrequency.getOrDefault(roundedScore, 0) + 1);
            }
        }
        return scoreFrequency;
    }
}
