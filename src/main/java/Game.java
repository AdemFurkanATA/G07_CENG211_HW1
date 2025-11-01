/**
 * Bir oyunu temsil eder. B
 */
public class Game {

    private int id;
    private String gameName;
    private int basePointPerRound;

    /**
     * 1. Normal Constructor
     */

    public Game(int id, String gameName, int basePointPerRound) {
        // Validasyonları geçiyorum, olduğunu varsayıyorum.
        this.id = id;
        this.gameName = gameName;
        this.basePointPerRound = basePointPerRound;
    }

    /**
     * 2. Copy Constructor
     * Başka bir 'Game' nesnesini alıp, onun bilgilerini
     * YENİ yaratılan bu nesneye kopyalar (klonlar).
     */

    public Game(Game other) {
        this.id = other.id;
        this.gameName = other.gameName;
        this.basePointPerRound = other.basePointPerRound;
    }

    // --- SETTERLAR ---

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setBasePointPerRound(int basePointPerRound) {
        this.basePointPerRound = basePointPerRound;
    }

    public void setId(int id) {
        this.id = id;
    }

    // --- Getter'lar ---

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