import java.util.Objects;

public class Query {
    private final MatchManagement matchManagement;
    private final PointsBoard pointsBoard;
    // Constructor
    public Query(MatchManagement matchManagement, PointsBoard pointsBoard) {
        this.matchManagement = Objects.requireNonNull(
                matchManagement, "MatchManagement cannot be null");
        this.pointsBoard = Objects.requireNonNull(
                pointsBoard, "PointsBoard cannot be null");
    }

    // Query 1: Highest Scoring Match
    public Match getHighestScoringMatch() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();
        if (allMatches.length == 0) return null;

        Match highest = allMatches[0];
        for (int i = 1; i < allMatches.length; i++) {
            if (allMatches[i].getMatchPoints() > highest.getMatchPoints()) {
                highest = allMatches[i];
            }
        }
        return highest;
    }

    // Query 2: Lowest Scoring Match And Contributor
    public Match getLowestScoringMatch() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();
        if (allMatches.length == 0) return null;

        Match lowest = allMatches[0];
        for (int i = 1; i < allMatches.length; i++) {
            if (allMatches[i].getMatchPoints() < lowest.getMatchPoints()) {
                lowest = allMatches[i];
            }
        }
        return lowest;
    }

    // Query 3: Lowest Bonus Match
    public int getMostContributingGameIndex(Match match) {
        Objects.requireNonNull(match, "Match cannot be null");

        int maxContribution = match.getGameContribution(0);
        int maxIndex = 0;

        for (int i = 1; i < 3; i++) {
            int contribution = match.getGameContribution(i);
            if (contribution > maxContribution) {
                maxContribution = contribution;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    // Query 4: Highest Scoring Gamer
    public Match getLowestBonusMatch() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();
        if (allMatches.length == 0) return null;

        Match lowestBonus = allMatches[0];
        for (int i = 1; i < allMatches.length; i++) {
            if (allMatches[i].getBonusPoints() < lowestBonus.getBonusPoints()) {
                lowestBonus = allMatches[i];
            }
        }
        return lowestBonus;
    }

    // Query 5: Total Tournament Points
    public int getTotalTournamentPoints() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();
        int total = 0;

        for (Match match : allMatches) {
            total += match.getMatchPoints();
        }
        return total;
    }

    // Print Methods

    public void printHighestScoringMatch() {
        Match match = getHighestScoringMatch();
        if (match == null) {
            System.out.println("No matches found!");
            return;
        }

        System.out.println("1. Highest-Scoring Match");
        System.out.println("Highest-Scoring Match:");
        printMatchDetails(match);
        System.out.println();
    }

    public void printLowestScoringMatchAndContributor() {
        Match match = getLowestScoringMatch();
        if (match == null) {
            System.out.println("No matches found!");
            return;
        }

        System.out.println("2. Lowest-Scoring Match & Most Contributing Game");
        System.out.println("Lowest-Scoring Match:");
        printMatchDetails(match);

        int gameIndex = getMostContributingGameIndex(match);
        Game[] games = match.getGames();
        int[] rounds = match.getRounds();
        int contribution = match.getGameContribution(gameIndex);

        System.out.println("\nMost Contributing Game in this Match:");
        System.out.println("Game: " + games[gameIndex].getGameName());
        System.out.printf("Contribution: %d rounds × %d points = %d%n",
                rounds[gameIndex],
                games[gameIndex].getBasePointPerRound(),
                contribution);
        System.out.println();
    }

    public void printLowestBonusMatch() {
        Match match = getLowestBonusMatch();
        if (match == null) {
            System.out.println("No matches found!");
            return;
        }

        System.out.println("3. Match with the Lowest Bonus Points");
        System.out.println("Match with Lowest Bonus Points:");
        System.out.println("Match ID: " + match.getId());

        Game[] games = match.getGames();
        System.out.print("Games: [");
        for (int i = 0; i < games.length; i++) {
            System.out.print(games[i].getGameName());
            if (i < games.length - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.println("Skill Points: " + match.getSkillPoints());
        System.out.println("Bonus Points: " + match.getBonusPoints());
        System.out.println("Match Points: " + match.getMatchPoints());
        System.out.println();
    }

    public void printHighestScoringGamer() {
        int gamerIndex = pointsBoard.getHighestScoringGamerIndex();
        Gamer[] gamers = pointsBoard.getGamers();

        if (gamerIndex < 0 || gamerIndex >= gamers.length) {
            System.out.println("No gamers found!");
            return;
        }

        Gamer gamer = gamers[gamerIndex];

        System.out.println("4. Highest-Scoring Gamer");
        System.out.println("Highest-Scoring Gamer:");
        System.out.println("Nickname: " + gamer.getNickname());
        System.out.println("Name: " + gamer.getName());
        System.out.println("Total Points: " + pointsBoard.getTotalPoints(gamerIndex));
        System.out.printf("Average Per Match: %.2f%n",
                pointsBoard.getAveragePoints(gamerIndex));
        System.out.println("Medal: " + pointsBoard.getMedal(gamerIndex));
        System.out.println();
    }

    public void printTotalTournamentPoints() {
        int total = getTotalTournamentPoints();

        System.out.println("5. Total Tournament Points");
        System.out.printf("Total Tournament Points across %d matches: %,d%n",
                matchManagement.getAllMatchesFlat().length, total);
        System.out.println();
    }

    public void printMedalDistribution() {
        int[] distribution = pointsBoard.getMedalDistribution();
        Gamer[] gamers = pointsBoard.getGamers();
        int totalGamers = gamers.length;

        if (totalGamers == 0) {
            System.out.println("No gamers found!");
            return;
        }

        System.out.println("6. Medal Distribution");
        System.out.println("Medal Distribution:");
        System.out.printf("GOLD: %d gamers (%.1f%%)%n",
                distribution[0], (distribution[0] * 100.0 / totalGamers));
        System.out.printf("SILVER: %d gamers (%.1f%%)%n",
                distribution[1], (distribution[1] * 100.0 / totalGamers));
        System.out.printf("BRONZE: %d gamers (%.1f%%)%n",
                distribution[2], (distribution[2] * 100.0 / totalGamers));
        System.out.printf("NONE: %d gamers (%.1f%%)%n",
                distribution[3], (distribution[3] * 100.0 / totalGamers));
    }

    // Helper Method to Print Match Details

    private void printMatchDetails(Match match) {
        Game[] games = match.getGames();
        int[] rounds = match.getRounds();

        System.out.println("Match ID: " + match.getId());

        System.out.print("Games: [");
        for (int i = 0; i < games.length; i++) {
            System.out.print(games[i].getGameName());
            if (i < games.length - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.print("Rounds: [");
        for (int i = 0; i < rounds.length; i++) {
            System.out.print(rounds[i]);
            if (i < rounds.length - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.println("Raw Points: " + match.getRawPoints());
        System.out.println("Skill Points: " + match.getSkillPoints());
        System.out.println("Bonus Points: " + match.getBonusPoints());
        System.out.println("Match Points: " + match.getMatchPoints());
    }
}