import java.util.Objects;
import java.util.Random;

/**
 * Maçları oluşturur ve yönetir.
 */
public class MatchManagement {
    private Match[][] matches; // [gamerCount][15]
    private Gamer[] gamers;    // Güvenli, içerideki kopya
    private Game[] games;      // Güvenli, içerideki kopya
    private Random random;
    private int matchIdCounter;

    /**
     * Constructor: Gelen 'gamers' ve 'games' dizilerinin 'deep copy'sini alır.
     */
    public MatchManagement(Gamer[] gamers, Game[] games) {
        // Null kontrolleri
        Objects.requireNonNull(gamers,"Gamer array can not be null!");
        Objects.requireNonNull(games,"Game array can not be null!");


        // Dışarıdaki 'gamers' dizisinin referansını değil, klonunu al
        this.gamers = new Gamer[gamers.length];
        for (int i = 0; i < gamers.length; i++) {
            // Gamer'ın Copy Constructor'ını çağır
            this.gamers[i] = new Gamer(gamers[i]);
        }

        // Dışarıdaki 'games' dizisinin referansını değil, klonunu al
        this.games = new Game[games.length];
        for (int i = 0; i < games.length; i++) {
            // Game'in Copy Constructor'ını çağır
            this.games[i] = new Game(games[i]);
        }

        // Artık 'this.gamers' ve 'this.games' dışarıdan bağımsız, GÜVENLİ.

        this.matches = new Match[gamers.length][15];
        this.random = new Random();
        this.matchIdCounter = 1;

        // Maçları içerideki güvenli dizilerle oluştur
        generateAllMatches();
    }

    /**
     * 'this.gamers' ve 'this.games' (içerideki güvenli kopyalar) ile maç yaratır.
     */

    private Match generateMatch(Gamer gamer) {
        Game[] selectedGames = new Game[3];
        int[] rounds = new int[3];

        for (int i = 0; i < 3; i++) {
            // 'this.games' içinden seç
            selectedGames[i] = this.games[random.nextInt(this.games.length)];
            rounds[i] = random.nextInt(10) + 1;
        }

        Match match = new Match(matchIdCounter++, selectedGames, rounds, gamer.getExperienceYears());
        return match;
    }

    /**
     * 'this.gamers' (içerideki güvenli kopya) ile tüm maçları yaratır.
     */
    private void generateAllMatches() {
        for (int gamerIndex = 0; gamerIndex < this.gamers.length; gamerIndex++) {
            for (int matchIndex = 0; matchIndex < 15; matchIndex++) {
                matches[gamerIndex][matchIndex] = generateMatch(this.gamers[gamerIndex]);
            }
        }
    }

    //------------Getters (Güvenli, Deep Copy'li ve Verimli)------------\\

    public Match[][] getAllMatches() {
        Match[][] allMatchesCopy = new Match[this.gamers.length][15];
        for (int gamerIndex = 0; gamerIndex < this.gamers.length; gamerIndex++) {
            for (int matchIndex = 0; matchIndex < 15; matchIndex++) {
                // Referansı değil, YENİ BİR KOPYASINI (KLONUNU) ver
                // Match'in Copy Constructor'ını çağır
                allMatchesCopy[gamerIndex][matchIndex] = new Match(this.matches[gamerIndex][matchIndex]);
            }
        }
        return allMatchesCopy;
    }

    /**
     * Belirli bir oyuncunun maçlarının 'deep copy' kopyasını 1D dizi olarak döndürür.
     */
    public Match[] getGamerMatches(int gamerIndex) {
        // Hata kontrolü
        if (gamerIndex < 0 || gamerIndex >= this.gamers.length) {
            throw new IllegalArgumentException("Invalid gamer index: " + gamerIndex);
        }

        Match[] gamerMatchesCopy = new Match[15];
        for (int matchIndex = 0; matchIndex < 15; matchIndex++) {
            // Klon ver
            gamerMatchesCopy[matchIndex] = new Match(this.matches[gamerIndex][matchIndex]);
        }
        return gamerMatchesCopy;
    }

    /**
     * Belirli bir maçın 'deep copy' kopyasını (klonunu) döndürür.
     */
    public Match getMatch(int gamerIndex, int matchIndex) {
        // Hata kontrolü
        if (gamerIndex < 0 || gamerIndex >= this.gamers.length || matchIndex < 0 || matchIndex >= 15) {
            throw new IllegalArgumentException("Invalid match indices.");
        }

        // Doğrudan 'this.matches'e eriş ve SADECE o 1 nesneyi klonla
        return new Match(this.matches[gamerIndex][matchIndex]);
    }

    /**
     * Tüm maçların 'deep copy' kopyasını 1D (düzleştirilmiş) dizi olarak döndürür.
     */
    public Match[] getAllMatchesFlat() {
        int totalMatches = this.gamers.length * 15;
        Match[] flatMatches = new Match[totalMatches];
        int index = 0;

        for (int i = 0; i < this.gamers.length; i++) {
            for (int j = 0; j < 15; j++) {
                // Klonla
                flatMatches[index++] = new Match(this.matches[i][j]);
            }
        }
        return flatMatches;
    }
}