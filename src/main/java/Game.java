public class Game {

    private int id;
    private String gameName;
    private int basePointPerRound;

    public Game(int id, String gameName, int basePointPerRound) {
        this.id = id;
        this.gameName = gameName;
        this.basePointPerRound = basePointPerRound;
    }

    // Copy Constructor
    public Game(Game other) {
        this.id = other.id;
        this.gameName = other.gameName;
        this.basePointPerRound = other.basePointPerRound;
    }

    // Setters

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setBasePointPerRound(int basePointPerRound) {
        this.basePointPerRound = basePointPerRound;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters

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
        return String.format("%s (ID: %d | @%d)",
                this.gameName, this.id, this.hashCode());
    }
}