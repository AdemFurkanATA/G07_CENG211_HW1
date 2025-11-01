import java.util.Arrays;

public class Match {

    private int id;
    private Game[] games; // 3 games
    private int[] rounds; // round count per game
    private int experienceYears; // Puan hesaplaması

    // Hesaplanan alanlar
    private int rawPoints;
    private int skillPoints;
    private int bonusPoints;
    private int matchPoints;

    /**
     * 1. Normal Constructor (Savunmacı Deep Copy ile)
     */
    public Match(int id, Game[] games, int[] rounds, int experienceYears) {

        if (id <= 0) throw new IllegalArgumentException("Match ID must be positive.");
        if (games == null || games.length != 3) throw new IllegalArgumentException("Exactly 3 games are required.");

        this.id = id;

        // --- GÜVENLİK (Constructor Deep Copy) ---

        this.games = new Game[games.length];
        for (int i = 0; i < games.length; i++) {
            if (games[i] == null) throw new IllegalArgumentException("Game at index " + i + " cannot be null.");
            // 'Game'in copy constructor'ını çağırıyoruz.
            this.games[i] = new Game(games[i]);
        }

        // 'int[]' için 'Arrays.copyOf'
        this.rounds = Arrays.copyOf(rounds, rounds.length);
        this.experienceYears = experienceYears;

        // Puanları hesapla
        recalculatePoints();
    }

    /**
     * 2. Copy Constructor (Deep Copy)
     */
    public Match(Match other) {
        this.id = other.id;

        // --- Zincirleme Deep Copy ---
        this.games = new Game[other.games.length];
        for (int i = 0; i < other.games.length; i++) {
            this.games[i] = new Game(other.games[i]); // Game'in copy constructor'ı
        }

        this.rounds = Arrays.copyOf(other.rounds, other.rounds.length);
        this.experienceYears = other.experienceYears;
        this.rawPoints = other.rawPoints;
        this.skillPoints = other.skillPoints;
        this.bonusPoints = other.bonusPoints;
        this.matchPoints = other.matchPoints;
    }

    /**
     * Puanları hesaplamak için private metot
     */
    private void recalculatePoints() {
        int computedRaw = 0;
        for (int i = 0; i < 3; i++) {
            computedRaw += this.rounds[i] * this.games[i].getBasePointPerRound();
        }
        this.rawPoints = computedRaw;

        int expYears = Math.min(this.experienceYears, 10);
        double skillMultiplier = 1 + (expYears * 0.02);
        this.skillPoints = (int) Math.floor(this.rawPoints * skillMultiplier);

        if (this.rawPoints < 200) this.bonusPoints = 10;
        else if (this.rawPoints < 400) this.bonusPoints = 25;
        else if (this.rawPoints < 600) this.bonusPoints = 50;
        else this.bonusPoints = 100;

        this.matchPoints = this.skillPoints + this.bonusPoints;
    }

    // --- Setter'lar ---
    // --- Getter'lar ---

    public int getId() { return id; }

    public Game[] getGames() {
        // Güvenli Getter (Deep Copy)
        Game[] kopyaCikis = new Game[this.games.length];
        for (int i = 0; i < this.games.length; i++) {
            kopyaCikis[i] = new Game(this.games[i]); // Klon ver
        }
        return kopyaCikis;
    }

    public int[] getRounds() { return Arrays.copyOf(this.rounds, this.rounds.length); }
    public int getRawPoints() { return rawPoints; }
    public int getSkillPoints() { return skillPoints; }
    public int getBonusPoints() { return bonusPoints; }
    public int getMatchPoints() { return matchPoints; }

    /**
     * Bir oyunun maça olan katkısını hesaplar.
     */
    public int getGameContribution(int gameIndex) {
        if (gameIndex < 0 || gameIndex >= this.games.length) {
            throw new IllegalArgumentException("Invalid game index: " + gameIndex);
        }
        // İçerideki güvenli 'games' ve 'rounds' dizilerini kullanır
        return this.rounds[gameIndex] * this.games[gameIndex].getBasePointPerRound();
    }

    @Override
    public boolean equals(Object other) { /* ... */ return false; }
    @Override
    public int hashCode() { /* ... */ return 0; }
}