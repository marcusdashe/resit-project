/**

 * @author Jude
 * @version 1.0
 */

import competitor.CompetitorList;

public class Main {
    public static void main(String[] args) {


        CompetitorList competitorList = new CompetitorList();
        competitorList.generateRandomCompetitors(); // Or .readCompetitorDetailsFromFile("path/to/file.csv");
        competitorList.printCompetitorList();

        competitorList.getAndDisplayShortDetails(competitorList);


    }
}