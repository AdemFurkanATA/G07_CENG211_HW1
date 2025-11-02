public class EsportsManagementApp {

    private static final String GAMES_PATH = "src/main/resources/games.csv";
    private static final String GAMERS_PATH = "src/main/resources/gamers.csv";

    public static void main(String[] args) {
        try {
            runTournament();
        } catch (Exception e) {
            System.err.println("Application failed: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runTournament() {
        // Read CSV Files
        Game[] games = FileIO.readGames(GAMES_PATH);
        Gamer[] gamers = FileIO.readGamers(GAMERS_PATH);

        if (games == null || gamers == null) {
            System.out.println("Error: Could not load data files!");
            return;
        }
        // Create Matches
        MatchManagement matchManagement = new MatchManagement(gamers, games);
        // Create Point Table
        PointsBoard pointsBoard = new PointsBoard(gamers, matchManagement.getAllMatches());
        Query query = new Query(matchManagement, pointsBoard);

        printAllQueries(query);
    }

    // QUERY RESULTS
    private static void printAllQueries(Query query) {
        System.out.println();
        query.printHighestScoringMatch();
        query.printLowestScoringMatchAndContributor();
        query.printLowestBonusMatch();
        query.printHighestScoringGamer();
        query.printTotalTournamentPoints();
        query.printMedalDistribution();
    }
}