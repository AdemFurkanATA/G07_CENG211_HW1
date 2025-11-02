import java.util.Arrays;


public class PointsBoard {

    private Gamer[] gamers;
    private Match[][] matches;
    private int[] totalPoints;
    private double[] averagePoints;
    private String[] medals;

    public PointsBoard(Gamer[] fromOutsideGamers, Match[][] fromOutsideMatches) {

        this.gamers = new Gamer[fromOutsideGamers.length];
        for (int i = 0; i < fromOutsideGamers.length; i++) {
            this.gamers[i] = new Gamer(fromOutsideGamers[i]); // Gamer Copy Constructor
        }

        this.matches = new Match[fromOutsideMatches.length][];
        for (int i = 0; i < fromOutsideMatches.length; i++) {
            this.matches[i] = new Match[fromOutsideMatches[i].length];
            for (int j = 0; j < fromOutsideMatches[i].length; j++) {
                this.matches[i][j] = new Match(fromOutsideMatches[i][j]); // Match Copy Constructor
            }
        }

        this.totalPoints = new int[this.gamers.length];
        this.averagePoints = new double[this.gamers.length];
        this.medals = new String[this.gamers.length];

        calculateAllPoints(this.matches);
        assignMedals();
    }

    // Copy Constructor
    public PointsBoard(PointsBoard other) {

        this.gamers = new Gamer[other.gamers.length];
        for (int i = 0; i < other.gamers.length; i++) {
            this.gamers[i] = new Gamer(other.gamers[i]);
        }

        this.matches = new Match[other.matches.length][];
        for (int i = 0; i < other.matches.length; i++) {
            this.matches[i] = new Match[other.matches[i].length];
            for (int j = 0; j < other.matches[i].length; j++) {
                this.matches[i][j] = new Match(other.matches[i][j]);
            }
        }

        this.totalPoints = Arrays.copyOf(other.totalPoints, other.totalPoints.length);
        this.averagePoints = Arrays.copyOf(other.averagePoints, other.averagePoints.length);
        this.medals = Arrays.copyOf(other.medals, other.medals.length);
    }

    // Calculations
    private void calculateAllPoints(Match[][] matches) {
        for (int gamerIndex = 0; gamerIndex < this.gamers.length; gamerIndex++) {
            int total = 0;
            for (Match match : this.matches[gamerIndex]) {
                total += match.getMatchPoints();
            }
            totalPoints[gamerIndex] = total;
            averagePoints[gamerIndex] = total / 15.0; // 15 maç var
        }
    }

    private void assignMedals() {
        for (int gamerIndex = 0; gamerIndex < this.gamers.length; gamerIndex++) {
            if (totalPoints[gamerIndex] >= 4400) {
                medals[gamerIndex] = "GOLD";
            } else if (totalPoints[gamerIndex] >= 3800) {
                medals[gamerIndex] = "SILVER";
            } else if (totalPoints[gamerIndex] >= 3200) {
                medals[gamerIndex] = "BRONZE";
            } else {
                medals[gamerIndex] = "NONE";
            }
        }
    }

    // --- Getters

    public Gamer[] getGamers() {
        Gamer[] kopyaCikis = new Gamer[this.gamers.length];
        for (int i = 0; i < this.gamers.length; i++) {
            kopyaCikis[i] = new Gamer(this.gamers[i]); // Klon ver
        }
        return kopyaCikis;
    }


    public int getTotalPoints(int gamerIndex) {
        return totalPoints[gamerIndex];
    }

    public double getAveragePoints(int gamerIndex) {
        return averagePoints[gamerIndex];
    }

    public String getMedal(int gamerIndex) {
        return medals[gamerIndex];
    }

    public int getHighestScoringGamerIndex() {
        int maxIndex = 0;
        int maxPoints = totalPoints[0];

        for (int index = 1; index < totalPoints.length; index++) {
            if (totalPoints[index] > maxPoints) {
                maxPoints = totalPoints[index];
                maxIndex = index;
            }
        }
        return maxIndex;
    }

    public int[] getMedalDistribution() {
        int[] distribution = new int[4]; // GOLD, SILVER, BRONZE, NONE

        for (String medal : this.medals) {
            switch (medal) {
                case "GOLD": distribution[0]++; break;
                case "SILVER": distribution[1]++; break;
                case "BRONZE": distribution[2]++; break;
                case "NONE": distribution[3]++; break;
            }
        }
        return distribution;
    }
}