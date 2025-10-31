import java.util.Objects;

public class Query {
    private final MatchManagement matchManagement;
    private final PointsBoard pointsBoard;

    // Constructor
    public Query(MatchManagement matchManagement, PointsBoard pointsBoard) {
        // Null check
        this.matchManagement = Objects.requireNonNull(matchManagement,"MatchManagement cannot be null!");
        this.pointsBoard = Objects.requireNonNull(pointsBoard,"PointsBoard cannot be null!");
    }

    // Query 1: Highest Scoring Match
    public void printHighestScoringMatch() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();

        Objects.requireNonNull(allMatches, "Match array cannot be null!");

        if (allMatches.length == 0) {
            throw new IllegalStateException("No matches found!");
        }

        Objects.requireNonNull(allMatches[0], "First match cannot be null!");

        Match highestMatch = allMatches[0];

        for (int i = 1; i < allMatches.length; i++) {
            Objects.requireNonNull(allMatches[i],
                    "A null element was found in the match array! Index: " + i);

            if (allMatches[i].getMatchPoints() > highestMatch.getMatchPoints()) {
                highestMatch = allMatches[i];
            }
        }

        Objects.requireNonNull(highestMatch.getGames(),"Games cannot be null!");
        Objects.requireNonNull(highestMatch.getRounds(),"Rounds cannot be null!");

        System.out.println("1. Highest-Scoring Match");
        System.out.println("Highest-Scoring Match:");
        System.out.println("Match ID: " + highestMatch.getId());
        System.out.print("Games: [");
        Game[] games = highestMatch.getGames();
        for (int i = 0; i < games.length; i++) {
            System.out.print(games[i].getGameName());
            if (i < games.length - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.print("Rounds: [");
        int[] rounds = highestMatch.getRounds();
        for (int i = 0; i < rounds.length; i++) {
            System.out.print(rounds[i]);
            if (i < rounds.length - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.println("Raw Points: " + highestMatch.getRawPoints());
        System.out.println("Skill Points: " + highestMatch.getSkillPoints());
        System.out.println("Bonus Points: " + highestMatch.getBonusPoints());
        System.out.println("Match Points: " + highestMatch.getMatchPoints());
        System.out.println();
    }

    // Query 2: Lowest Scoring Match And Contributor
    public void printLowestScoringMatchAndContributor() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();

        Objects.requireNonNull(allMatches, "Match array cannot be null!");

        if (allMatches.length == 0) {
            throw new IllegalStateException("No  matches found!");
        }

        Objects.requireNonNull(allMatches[0], "First match cannot be null!");

        Match lowestMatch = allMatches[0];

        for (int i = 1; i < allMatches.length; i++) {
            Objects.requireNonNull(allMatches[i],
                    "A null element was found in the Match array! Index: " + i);

            if (allMatches[i].getMatchPoints() < lowestMatch.getMatchPoints()) {
                lowestMatch = allMatches[i];
            }
        }

        Game[] games = lowestMatch.getGames();
        Objects.requireNonNull(games, "Games cannot be null!");

        if (games.length < 3) {
            throw new IllegalStateException(
                    "Match must contain at least 3 games! Founded: " + games.length);
        }

        for (int i = 0; i < 3; i++) {
            Objects.requireNonNull(games[i],
                    "A null element was found in the Game array! Index: " + i);
        }

        int maxContribution = lowestMatch.getGameContribution(0);
        int maxIndex = 0;

        for (int i = 1; i < 3; i++) {
            int contribution = lowestMatch.getGameContribution(i);
            if (contribution > maxContribution) {
                maxContribution = contribution;
                maxIndex = i;
            }
        }

        int[] rounds = lowestMatch.getRounds();
        Objects.requireNonNull(rounds, "Rounds cannot be null!");

        if (rounds.length < 3) {
            throw new IllegalStateException(
                    "Rounds must contain at least 3 games! Bulunan: " + rounds.length);
        }

        if (maxIndex < 0 || maxIndex >= games.length) {
            throw new IllegalStateException(
                    "Invalid maxIndex: " + maxIndex);
        }

        if (maxIndex >= rounds.length) {
            throw new IllegalStateException(
                    "maxIndex is outside the rounds array: " + maxIndex);
        }

        System.out.println("2. Lowest-Scoring Match & Most Contributing Game");
        System.out.println("Lowest-Scoring Match:");
        System.out.println("Match ID: " + lowestMatch.getId());
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

        System.out.println("Raw Points: " + lowestMatch.getRawPoints());
        System.out.println("Skill Points: " + lowestMatch.getSkillPoints());
        System.out.println("Bonus Points: " + lowestMatch.getBonusPoints());
        System.out.println("Match Points: " + lowestMatch.getMatchPoints());

        System.out.println("\nMost Contributing Game in this Match:");
        System.out.println("Game: " + games[maxIndex].getGameName());
        System.out.println("Contribution: " + rounds[maxIndex] + " rounds × " +
                games[maxIndex].getBasePointPerRound() + " points = " + maxContribution);
        System.out.println();
    }

    // Query 3: Lowest Bonus Match
    public void printLowestBonusMatch() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();

        Objects.requireNonNull(allMatches, "Match array cannot be null!");

        if (allMatches.length == 0) {
            throw new IllegalStateException("No matches found!");
        }

        Objects.requireNonNull(allMatches[0], "First match cannot be null!");

        Match lowestBonusMatch = allMatches[0];

        for (int i = 1; i < allMatches.length; i++) {
            Objects.requireNonNull(allMatches[i],
                    "A null element was found in the match array! Index: " + i);

            if (allMatches[i].getBonusPoints() < lowestBonusMatch.getBonusPoints()) {
                lowestBonusMatch = allMatches[i];
            }
        }

        Game[] games = lowestBonusMatch.getGames();
        Objects.requireNonNull(games, "Games cannot be null!");

        for (int i = 0; i < games.length; i++) {
            Objects.requireNonNull(games[i],
                    "A null element was found in the games array! Index: " + i);
        }

        System.out.println("3. Match with the Lowest Bonus Points");
        System.out.println("Match with Lowest Bonus Points:");
        System.out.println("Match ID: " + lowestBonusMatch.getId());
        System.out.print("Games: [");
        for (int i = 0; i < games.length; i++) {
            System.out.print(games[i].getGameName());
            if (i < games.length - 1) System.out.print(", ");
        }
        System.out.println("]");
        System.out.println("Skill Points: " + lowestBonusMatch.getSkillPoints());
        System.out.println("Bonus Points: " + lowestBonusMatch.getBonusPoints());
        System.out.println("Match Points: " + lowestBonusMatch.getMatchPoints());
        System.out.println();
    }

    // Query 4: Highest Scoring Gamer
    public void printHighestScoringGamer() {
        int gamerIndex = pointsBoard.getHighestScoringGamerIndex();

        Gamer[] gamers = pointsBoard.getGamers();
        Objects.requireNonNull(gamers, "Gamers array cannot be null!");

        if (gamers.length == 0) {
            throw new IllegalStateException("No gamers found!");
        }

        if (gamerIndex < 0 || gamerIndex >= gamers.length) {
            throw new IllegalStateException(
                    "Invalid gamer index: " + gamerIndex + " (Array length: " + gamers.length + ")");
        }

        Gamer gamer = gamers[gamerIndex];
        Objects.requireNonNull(gamer, "Gamer cannot be null at index: " + gamerIndex);

        System.out.println("4. Highest-Scoring Gamer");
        System.out.println("Highest-Scoring Gamer:");
        System.out.println("Nickname: " + gamer.getNickname());
        System.out.println("Name: " + gamer.getName());
        System.out.println("Total Points: " + pointsBoard.getTotalPoints(gamerIndex));
        System.out.printf("Average Per Match: %.2f\n", pointsBoard.getAveragePoints(gamerIndex));
        System.out.println("Medal: " + pointsBoard.getMedal(gamerIndex));
        System.out.println();
    }

    // Query 5: Total Tournament Points
    public void printTotalTournamentPoints() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();
        int total = 0;

        for (int i = 0; i < allMatches.length; i++) {
            total += allMatches[i].getMatchPoints();
        }

        System.out.println("5. Total Tournament Points");
        System.out.printf("Total Tournament Points across 1500 matches: %,d\n", total);
        System.out.println();
    }

    // Query 6: Medal Distribution
    public void printMedalDistribution() {
        int[] distribution = pointsBoard.getMedalDistribution();
        int totalGamers = pointsBoard.getGamers().length;

        System.out.println("6. Medal Distribution");
        System.out.println("Medal Distribution:");
        System.out.printf("GOLD: %d gamers (%.1f%%)\n",
                distribution[0], (distribution[0] * 100.0 / totalGamers));
        System.out.printf("SILVER: %d gamers (%.1f%%)\n",
                distribution[1], (distribution[1] * 100.0 / totalGamers));
        System.out.printf("BRONZE: %d gamers (%.1f%%)\n",
                distribution[2], (distribution[2] * 100.0 / totalGamers));
        System.out.printf("NONE: %d gamers (%.1f%%)\n",
                distribution[3], (distribution[3] * 100.0 / totalGamers));
    }
}