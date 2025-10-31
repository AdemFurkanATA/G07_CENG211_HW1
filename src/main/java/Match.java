import java.util.Arrays;

public final class Match {
    private final int id;
    private final Game[] games; // 3 games
    private final int[] rounds; // round count per game
    private final int rawPoints;
    private final int skillPoints;
    private final int bonusPoints;
    private final int matchPoints;

    public Match(int id, Game[] games, int[] rounds, int experienceYears) {
        if (id <= 0) {
            throw new IllegalArgumentException("Match ID must be positive.");
        }
        if (games == null || games.length != 3) {
            throw new IllegalArgumentException("Exactly 3 games are required.");
        }
        if (rounds == null || rounds.length != 3) {
            throw new IllegalArgumentException("Exactly 3 round counts are required.");
        }
        for (int i = 0; i < 3; i++) {
            if (games[i] == null) {
                throw new IllegalArgumentException("Game at index " + i + " cannot be null.");
            }
            if (rounds[i] <= 0) {
                throw new IllegalArgumentException("Round count must be positive at index " + i + ".");
            }
        }
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Experience years cannot be negative.");
        }

        this.id = id;
        this.games = Arrays.copyOf(games, 3);
        this.rounds = Arrays.copyOf(rounds, 3);

        int computedRaw = 0;
        for (int i = 0; i < 3; i++) {
            computedRaw += this.rounds[i] * this.games[i].getBasePointPerRound();
        }
        this.rawPoints = computedRaw;

        int expYears = Math.min(experienceYears, 10);
        double skillMultiplier = 1 + (expYears * 0.02);
        this.skillPoints = (int) Math.floor(this.rawPoints * skillMultiplier);

        int bonus;
        if (this.rawPoints < 200) {
            bonus = 10;
        } else if (this.rawPoints < 400) {
            bonus = 25;
        } else if (this.rawPoints < 600) {
            bonus = 50;
        } else {
            bonus = 100;
        }
        this.bonusPoints = bonus;
        this.matchPoints = this.skillPoints + this.bonusPoints;
    }

    // Getters (arrays return copies)
    public int getId() { return id; }
    public Game[] getGames() { return Arrays.copyOf(this.games, this.games.length); }
    public int[] getRounds() { return Arrays.copyOf(this.rounds, this.rounds.length); }
    public int getRawPoints() { return rawPoints; }
    public int getSkillPoints() { return skillPoints; }
    public int getBonusPoints() { return bonusPoints; }
    public int getMatchPoints() { return matchPoints; }

    // Contribution of a single game
    public int getGameContribution(int gameIndex) {
        if (gameIndex < 0 || gameIndex >= this.games.length) {
            throw new IllegalArgumentException("Invalid game index: " + gameIndex);
        }
        return this.rounds[gameIndex] * this.games[gameIndex].getBasePointPerRound();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Match m) {
            return this.id == m.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
