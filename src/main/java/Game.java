/**
 * Bir oyunu temsil eder. Bu sınıf 'immutable' (değişmez) olarak tasarlandı.
 * Bir nesne yaratıldıktan sonra içeriği değiştirilemez.
 */

public final class Game { // Sınıf final, extend edilemez

    // Alanlar final, sadece constructor'da atanabilir
    private final int id;
    private final String gameName;
    private final int basePointPerRound;

    /**
     * Yeni bir Game nesnesi yaratır.
     * Geçersiz parametrelere karşı constructor'da validasyon yapılır.
     */
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

    // --- Getter'lar (Setter'lar yok) ---

    public int getId() {
        return id;
    }

    public String getGameName() {
        // String immutable (değişmez) olduğu için kopyalamaya gerek yok.
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

    /**
     * İki Game nesnesini 'id'lerine göre karşılaştırır.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        // Modern 'instanceof' ile tip kontrolü ve cast
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