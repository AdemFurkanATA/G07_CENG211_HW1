import java.util.Arrays;


public class PointsBoard {

    private Gamer[] gamers;
    private Match[][] matches;
    private int[] totalPoints;
    private double[] averagePoints;
    private String[] medals;

    /**
     * 1. Normal Constructor (Savunmacı Deep Copy ile)
     */
    public PointsBoard(Gamer[] disaridanGelenGamers, Match[][] disaridanGelenMatches) {

        //'gamers' dizisini deep copy yap
        this.gamers = new Gamer[disaridanGelenGamers.length];
        for (int i = 0; i < disaridanGelenGamers.length; i++) {
            this.gamers[i] = new Gamer(disaridanGelenGamers[i]); // Gamer Copy Constructor
        }

        //'matches' dizisini (2D) deep copy yap
        this.matches = new Match[disaridanGelenMatches.length][];
        for (int i = 0; i < disaridanGelenMatches.length; i++) {
            this.matches[i] = new Match[disaridanGelenMatches[i].length];
            for (int j = 0; j < disaridanGelenMatches[i].length; j++) {
                this.matches[i][j] = new Match(disaridanGelenMatches[i][j]); // Match Copy Constructor
            }
        }

        // Hesaplama dizilerini oluştur
        this.totalPoints = new int[this.gamers.length];
        this.averagePoints = new double[this.gamers.length];
        this.medals = new String[this.gamers.length];

        // Hesaplamaları içerideki KOPYA dizilerle yap
        calculateAllPoints(this.matches);
        assignMedals();
    }

    /**
     * 2. Copy Constructor (Deep Copy)
     * Tüm board'u klonlar.
     */
    public PointsBoard(PointsBoard other) {

        // 'gamers' dizisinin 'deep copy'si
        this.gamers = new Gamer[other.gamers.length];
        for (int i = 0; i < other.gamers.length; i++) {
            this.gamers[i] = new Gamer(other.gamers[i]);
        }

        // 'matches' (2D) dizisinin 'deep copy'si
        this.matches = new Match[other.matches.length][];
        for (int i = 0; i < other.matches.length; i++) {
            this.matches[i] = new Match[other.matches[i].length];
            for (int j = 0; j < other.matches[i].length; j++) {
                this.matches[i][j] = new Match(other.matches[i][j]);
            }
        }

        // 'int[]', 'double[]', 'String[]' için 'Arrays.copyOf' güvenlidir
        this.totalPoints = Arrays.copyOf(other.totalPoints, other.totalPoints.length);
        this.averagePoints = Arrays.copyOf(other.averagePoints, other.averagePoints.length);
        this.medals = Arrays.copyOf(other.medals, other.medals.length);
    }


    // Puanları hesapla
    private void calculateAllPoints(Match[][] matches) {
        for (int gamerIndex = 0; gamerIndex < this.gamers.length; gamerIndex++) {
            int total = 0;
            // O oyuncunun tüm maçlarını for-each ile dön
            for (Match match : this.matches[gamerIndex]) {
                total += match.getMatchPoints();
            }
            totalPoints[gamerIndex] = total;
            averagePoints[gamerIndex] = total / 15.0; // 15 maç var
        }
    }

    // Madalyaları ata
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

    // Bu getter'lar primitive döndürdüğü için
    // 'deep copy'ye ihtiyaç duymazlar

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

        // 'this.medals' dizisini (içerideki güvenli kopya) kullan
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