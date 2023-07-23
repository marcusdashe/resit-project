package competitor;

/**

 * @author Jude
 * @version 1.0
 */

import java.util.Arrays;

public class JDCompetitor {
    private int competitorNumber;
    private String name;
    private CompetitorLevel level;
    private int[] scores; // Array to store scores (5 scores between 0 and 5)
    private int age;

    // Constructor
    public JDCompetitor(int competitorNumber, String name, CompetitorLevel level, int age) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.level = level;
        this.scores = new int[5]; // Array to store 5 scores
        this.age = age;
    }

    public static String getScoresString(int[] scores) {
        // Create a string builder to store the output.
        StringBuilder stringBuilder = new StringBuilder();

        // Iterate over the scores array and add each score to the string builder, separated by a space.
        for (int score : scores) {
            stringBuilder.append(score).append(" ");
        }

        // Remove the trailing space from the string builder.
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        // Return the string builder's contents.
        return stringBuilder.toString();
    }
    // Getters and Setters
    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public void setCompetitorNumber(int competitorNumber) {
        this.competitorNumber = competitorNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompetitorLevel getLevel() {
        return level;
    }

    public void setLevel(CompetitorLevel level) {
        this.level = level;
    }

    public int[] getScoreArray() {
        return scores;
    }


    // Add a score to the list of scores
    public void addScore(int score) {
        if (score >= 0 && score <= 5) {
            // Find the first empty slot in the array to add the score
            int index = 0;
            while (index < scores.length && scores[index] != 0) {
                index++;
            }

            if (index < scores.length) {
                scores[index] = score;
            }
        }
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Calculate overall score based on the scores of the competitor
    public double getOverallScore() {
        int[] sortedScores = Arrays.copyOf(scores, scores.length);
        Arrays.sort(sortedScores);

        int n = level.ordinal() + 1; // 'n' is determined by the competitor's level number

        int sum = 0;
        for (int i = scores.length - 1; i >= scores.length - n; i--) {
            sum += sortedScores[i];
        }

        return Math.round((double) sum / n * 10) / 10 ;
    }

    // Print full details of the competitor
    public void getFullDetails() {
        System.out.println("Competitor number " + competitorNumber + ", name " + name + ".");
        System.out.println(name + " is a " + level + " Aged "+ age +" and received these scores: " + getScoresString(scores));
        System.out.println("This gives him an overall score of " + getOverallScore() + ".");
    }

    // Print short details of the competitor
    public void getShortDetails() {
        String[] nameParts = name.split(" ");
        System.out.println("\nCN " + competitorNumber + " (" + Character.toUpperCase(nameParts[0].charAt(0)) + Character.toUpperCase(nameParts[1].charAt(0)) + ") has overall score " + getOverallScore() + ".");
    }

    // Enum for competitor levels
    public enum CompetitorLevel {
        NOVICE,
        INTERMEDIATE,
        EXPERT
    }
}

