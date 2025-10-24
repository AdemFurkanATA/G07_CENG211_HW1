public class EsportsManagementApp {

    public static void main(String[] args) {
        System.out.println("=== E-Sports Tournament Management System ===\n");

        // Working directory'yi göster
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
        System.out.println();

        // 1. CSV dosyalarını oku
        System.out.println("Loading data from CSV files...");
        Game[] games = FileIO.readGames("src/main/resources/games.csv");
        Gamer[] gamers = FileIO.readGamers("src/main/resources/gamers.csv");

        if (games == null || gamers == null) {
            System.out.println("Error: Could not load data files!");
            return;
        }

        System.out.println("Loaded " + games.length + " games and " + gamers.length + " gamers.");
        System.out.println();

        // 2. Maçları oluştur
        System.out.println("Generating matches...");
        MatchManagement matchManagement = new MatchManagement(gamers, games);
        System.out.println("Generated 1500 matches (15 matches per gamer).");
        System.out.println();

        // 3. Puan tablosunu oluştur
        System.out.println("Calculating points and assigning medals...");
        PointsBoard pointsBoard = new PointsBoard(gamers, matchManagement.getMatches());
        System.out.println("Points calculated and medals assigned.");
        System.out.println();

        // 4. Sorguları çalıştır
        System.out.println("=== QUERY RESULTS ===\n");
        Query query = new Query(matchManagement, pointsBoard);

        query.printHighestScoringMatch();
        query.printLowestScoringMatchAndContributor();
        query.printLowestBonusMatch();
        query.printHighestScoringGamer();
        query.printTotalTournamentPoints();
        query.printMedalDistribution();

        System.out.println("\n=== END OF REPORT ===");
    }
}