public class PointsBoard {
    private Gamer[] gamers;
    private int[] totalPoints;
    private double[] averagePoints;
    private String[] medals;
    private Match[][] matches;

    // Constructor
    public PointsBoard(Gamer[] gamers, Match[][] matches) {
        this.gamers = gamers;
        this.matches = matches;
        this.totalPoints = new int[gamers.length];
        this.averagePoints = new double[gamers.length];
        this.medals = new String[gamers.length];

        calculateAllPoints();
        assignMedals();
    }

    // Tüm oyuncuların puanlarını hesapla
    private void calculateAllPoints() {
        for (int i = 0; i < gamers.length; i++) {
            int total = 0;

            // 15 maçın puanlarını topla
            for (int j = 0; j < 15; j++) {
                total += matches[i][j].getMatchPoints();
            }

            totalPoints[i] = total;
            averagePoints[i] = total / 15.0;
        }
    }

    // Madalyaları ata
    private void assignMedals() {
        for (int i = 0; i < gamers.length; i++) {
            if (totalPoints[i] >= 2000) {
                medals[i] = "GOLD";
            } else if (totalPoints[i] >= 1200) {
                medals[i] = "SILVER";
            } else if (totalPoints[i] >= 700) {
                medals[i] = "BRONZE";
            } else {
                medals[i] = "NONE";
            }
        }
    }

    // Getters
    public Gamer[] getGamers() {
        return gamers;
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

    // En yüksek puanlı oyuncuyu bul
    public int getHighestScoringGamerIndex() {
        int maxIndex = 0;
        int maxPoints = totalPoints[0];

        for (int i = 1; i < totalPoints.length; i++) {
            if (totalPoints[i] > maxPoints) {
                maxPoints = totalPoints[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    // Madalya dağılımını hesapla
    public int[] getMedalDistribution() {
        int[] distribution = new int[4]; // GOLD, SILVER, BRONZE, NONE

        for (int i = 0; i < medals.length; i++) {
            switch (medals[i]) {
                case "GOLD":
                    distribution[0]++;
                    break;
                case "SILVER":
                    distribution[1]++;
                    break;
                case "BRONZE":
                    distribution[2]++;
                    break;
                case "NONE":
                    distribution[3]++;
                    break;
            }
        }

        return distribution;
    }
}