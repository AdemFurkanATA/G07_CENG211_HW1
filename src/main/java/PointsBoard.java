import java.util.Arrays;

/**
 * Puan tablosunu ve hesaplanmış sonuçları tutar.
 * Bu nesne yaratıldıktan sonra içeriği değişmez (immutable).
 */
public final class PointsBoard {

    private final Gamer[] gamers; // Oyuncuların kopyası
    private final int[] totalPoints;
    private final double[] averagePoints;
    private final String[] medals;

    // Constructor
    public PointsBoard(Gamer[] gamers, Match[][] matches) {
        // Dışarıdan gelen 'gamers' dizisinin referansını değil, kopyasını al
        this.gamers = Arrays.copyOf(gamers, gamers.length);

        // Hesaplama sonuçları için dizileri oluştur
        this.totalPoints = new int[this.gamers.length];
        this.averagePoints = new double[this.gamers.length];
        this.medals = new String[this.gamers.length];

        // Constructor'da tüm hesaplamaları yap
        calculateAllPoints(matches);
        assignMedals();
    }

    // Puanları hesapla
    private void calculateAllPoints(Match[][] matches) {
        for (int gamerIndex = 0; gamerIndex < this.gamers.length; gamerIndex++) {
            int total = 0;

            // O oyuncunun tüm maçlarını for-each ile dön
            for (Match match : matches[gamerIndex]) {
                total += match.getMatchPoints();
            }

            totalPoints[gamerIndex] = total;
            averagePoints[gamerIndex] = total / 15.0;
        }
    }

    // Madalyaları ata
    private void assignMedals() {
        for (int gamerIndex = 0; gamerIndex < this.gamers.length; gamerIndex++) {
            if (totalPoints[gamerIndex] >= 2000) {
                medals[gamerIndex] = "GOLD";
            } else if (totalPoints[gamerIndex] >= 1200) {
                medals[gamerIndex] = "SILVER";
            } else if (totalPoints[gamerIndex] >= 700) {
                medals[gamerIndex] = "BRONZE";
            } else {
                medals[gamerIndex] = "NONE";
            }
        }
    }

    // --- Getters ---

    public Gamer[] getGamers() {
        // İçerideki diziyi sızdırma, kopyasını ver
        return Arrays.copyOf(this.gamers, this.gamers.length);
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

    // En yüksek puanlı oyuncunun index'ini bul
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

    // Madalya dağılımını hesapla
    public int[] getMedalDistribution() {
        int[] distribution = new int[4]; // GOLD, SILVER, BRONZE, NONE

        // Madalya dizisini for-each ile dön
        for (String medal : this.medals) {
            switch (medal) {
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
        return distribution; // Yeni yaratılan diziyi döndür
    }
}