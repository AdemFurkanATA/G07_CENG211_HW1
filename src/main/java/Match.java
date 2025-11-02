import java.util.Arrays;

public class Match {

    private int id;
    private Game[] games;
    private int[] rounds;
    private int experienceYears;

    // Points To Be Calculated
    private int rawPoints;
    private int skillPoints;
    private int bonusPoints;
    private int matchPoints;

    public Match(int id, Game[] games, int[] rounds, int experienceYears) {

        if (id <= 0) throw new IllegalArgumentException("Match ID must be positive.");
        if (games == null || games.length != 3) throw new IllegalArgumentException("Exactly 3 games are required.");

        this.id = id;


        this.games = new Game[games.length];
        for (int i = 0; i < games.length; i++) {
            if (games[i] == null) throw new IllegalArgumentException("Game at index " + i + " cannot be null.");
            this.games[i] = new Game(games[i]);
        }

        this.rounds = Arrays.copyOf(rounds, rounds.length);
        this.experienceYears = experienceYears;

        recalculatePoints();
    }

    // Copy Constructor
    public Match(Match other) {
        this.id = other.id;

        this.games = new Game[other.games.length];
        for (int i = 0; i < other.games.length; i++) {
            this.games[i] = new Game(other.games[i]);
        }

        this.rounds = Arrays.copyOf(other.rounds, other.rounds.length);
        this.experienceYears = other.experienceYears;
        this.rawPoints = other.rawPoints;
        this.skillPoints = other.skillPoints;
        this.bonusPoints = other.bonusPoints;
        this.matchPoints = other.matchPoints;
    }

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

    // --- Getters ---

    public int getId() { return id; }

    public Game[] getGames() {
        Game[] copyOutput = new Game[this.games.length];
        for (int i = 0; i < this.games.length; i++) {
            copyOutput[i] = new Game(this.games[i]); // Klon ver
        }
        return copyOutput;
    }

    public int[] getRounds() { return Arrays.copyOf(this.rounds, this.rounds.length); }
    public int getRawPoints() { return rawPoints; }
    public int getSkillPoints() { return skillPoints; }
    public int getBonusPoints() { return bonusPoints; }
    public int getMatchPoints() { return matchPoints; }

    public int getGameContribution(int gameIndex) {
        if (gameIndex < 0 || gameIndex >= this.games.length) {
            throw new IllegalArgumentException("Invalid game index: " + gameIndex);
        }
        return this.rounds[gameIndex] * this.games[gameIndex].getBasePointPerRound();
    }

    @Override
    public boolean equals(Object other) {return false;}
    @Override
    public int hashCode() {return 0;}
}