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

    // Query 1: En yüksek puanlı maç
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

    // Query 2: En düşük puanlı maç ve en çok katkı sağlayan oyun
    public void printLowestScoringMatchAndContributor() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();
        Match lowestMatch = allMatches[0];

        for (int i = 1; i < allMatches.length; i++) {
            if (allMatches[i].getMatchPoints() < lowestMatch.getMatchPoints()) {
                lowestMatch = allMatches[i];
            }
        }

        // En çok katkı sağlayan oyunu bul
        int maxContribution = lowestMatch.getGameContribution(0);
        int maxIndex = 0;

        for (int i = 1; i < 3; i++) {
            int contribution = lowestMatch.getGameContribution(i);
            if (contribution > maxContribution) {
                maxContribution = contribution;
                maxIndex = i;
            }
        }

        System.out.println("2. Lowest-Scoring Match & Most Contributing Game");
        System.out.println("Lowest-Scoring Match:");
        System.out.println("Match ID: " + lowestMatch.getId());
        System.out.print("Games: [");
        Game[] games = lowestMatch.getGames();
        for (int i = 0; i < games.length; i++) {
            System.out.print(games[i].getGameName());
            if (i < games.length - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.print("Rounds: [");
        int[] rounds = lowestMatch.getRounds();
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

    // Query 3: En düşük bonus puanlı maç
    public void printLowestBonusMatch() {
        Match[] allMatches = matchManagement.getAllMatchesFlat();
        Match lowestBonusMatch = allMatches[0];

        for (int i = 1; i < allMatches.length; i++) {
            if (allMatches[i].getBonusPoints() < lowestBonusMatch.getBonusPoints()) {
                lowestBonusMatch = allMatches[i];
            }
        }

        System.out.println("3. Match with the Lowest Bonus Points");
        System.out.println("Match with Lowest Bonus Points:");
        System.out.println("Match ID: " + lowestBonusMatch.getId());
        System.out.print("Games: [");
        Game[] games = lowestBonusMatch.getGames();
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

    // Query 4: En yüksek puanlı oyuncu
    public void printHighestScoringGamer() {
        int gamerIndex = pointsBoard.getHighestScoringGamerIndex();
        Gamer gamer = pointsBoard.getGamers()[gamerIndex];

        System.out.println("4. Highest-Scoring Gamer");
        System.out.println("Highest-Scoring Gamer:");
        System.out.println("Nickname: " + gamer.getNickname());
        System.out.println("Name: " + gamer.getName());
        System.out.println("Total Points: " + pointsBoard.getTotalPoints(gamerIndex));
        System.out.printf("Average Per Match: %.2f\n", pointsBoard.getAveragePoints(gamerIndex));
        System.out.println("Medal: " + pointsBoard.getMedal(gamerIndex));
        System.out.println();
    }

    // Query 5: Toplam turnuva puanı
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

    // Query 6: Madalya dağılımı
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