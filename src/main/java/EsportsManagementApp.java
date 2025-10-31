public class EsportsManagementApp {

    public static void main(String[] args) {

        // Read CSV Files
        Game[] games = FileIO.readGames("src/main/resources/games.csv");
        Gamer[] gamers = FileIO.readGamers("src/main/resources/gamers.csv");

        if (games == null || gamers == null) {
            System.out.println("Error: Could not load data files!");
            return;
        }

        // Create Matches
        MatchManagement matchManagement = new MatchManagement(gamers, games);

        // Create Point Table
        PointsBoard pointsBoard = new PointsBoard(gamers, matchManagement.getMatches());

        // QUERY RESULTS
        Query query = new Query(matchManagement, pointsBoard);

        query.printHighestScoringMatch();
        query.printLowestScoringMatchAndContributor();
        query.printLowestBonusMatch();
        query.printHighestScoringGamer();
        query.printTotalTournamentPoints();
        query.printMedalDistribution();

    }
}