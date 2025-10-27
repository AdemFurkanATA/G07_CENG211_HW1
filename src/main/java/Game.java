public final class Game {

    private final int id;
    private final String gameName;
    private final int basePointPerRound;

    public Game(int id, String gameName, int basePointPerRound) {
        if (id <= 0) {
            throw new IllegalArgumentException("Game ID pozitif olmalı.");
        }
        if (gameName == null || gameName.trim().isEmpty()) {
            throw new IllegalArgumentException("Game name null veya boş olamaz.");
        }
        if (basePointPerRound <= 0) {
            throw new IllegalArgumentException("Base point pozitif olmalı.");
        }

        this.id = id;
        this.gameName = gameName;
        this.basePointPerRound = basePointPerRound;
    }

    public int getId() {
        return id;
    }

    public String getGameName() {
        return gameName;
    }

    public int getBasePointPerRound() {
        return basePointPerRound;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Puan: %d)",
                this.gameName, this.id, this.basePointPerRound);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other instanceof Game game) {
            return this.id == game.id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}