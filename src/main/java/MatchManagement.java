import java.util.Random;

public class MatchManagement {
    private Match[][] matches; // [gamerCount][15]
    private Gamer[] gamers;
    private Game[] games;
    private Random random;
    private int matchIdCounter;

    // Constructor
    public MatchManagement(Gamer[] gamers, Game[] games) {
        this.gamers = gamers;
        this.games = games;
        this.matches = new Match[gamers.length][15];
        this.random = new Random();
        this.matchIdCounter = 1;

        generateAllMatches();
    }

    // Tüm maçları oluştur
    private void generateAllMatches() {
        for (int gamerIndex = 0; gamerIndex < gamers.length; gamerIndex++) {
            for (int matchIndex = 0; matchIndex < 15; matchIndex++) {
                matches[gamerIndex][matchIndex] = generateMatch(gamers[gamerIndex]);
            }
        }
    }

    // Tek bir maç oluştur
    private Match generateMatch(Gamer gamer) {
        // 3 rastgele oyun seç
        Game[] selectedGames = new Game[3];
        int[] rounds = new int[3];

        for (int i = 0; i < 3; i++) {
            // Rastgele bir oyun seç
            selectedGames[i] = games[random.nextInt(games.length)];

            // 1 ile 10 arasında rastgele round sayısı
            rounds[i] = random.nextInt(10) + 1;
        }

        Match match = new Match(matchIdCounter++, selectedGames, rounds, gamer.getExperienceYears());
        return match;
    }

    // Getter
    public Match[][] getMatches() {
        return matches;
    }

    // Belirli bir oyuncunun maçlarını al
    public Match[] getGamerMatches(int gamerIndex) {
        return matches[gamerIndex];
    }

    // Belirli bir maçı al
    public Match getMatch(int gamerIndex, int matchIndex) {
        return matches[gamerIndex][matchIndex];
    }

    // Tüm maçları tek boyutlu dizi olarak döndür (Query için)
    public Match[] getAllMatchesFlat() {
        int totalMatches = gamers.length * 15;
        Match[] flatMatches = new Match[totalMatches];
        int index = 0;

        for (int i = 0; i < gamers.length; i++) {
            for (int j = 0; j < 15; j++) {
                flatMatches[index++] = matches[i][j];
            }
        }

        return flatMatches;
    }
}