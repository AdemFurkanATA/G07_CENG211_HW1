public class Match {
    private int id;
    private Game[] games; // 3 oyun
    private int[] rounds; // Her oyun için round sayısı
    private int rawPoints;
    private int skillPoints;
    private int bonusPoints;
    private int matchPoints;

    // Constructor
    public Match(int id, Game[] games, int[] rounds, int experienceYears) {
        this.id = id;
        this.games = games;
        this.rounds = rounds;
        calculatePoints(experienceYears);
    }

    // Puanları hesaplayan metod
    private void calculatePoints(int experienceYears) {
        // 1. Raw Points hesapla
        rawPoints = 0;
        for (int i = 0; i < 3; i++) {
            rawPoints += rounds[i] * games[i].getBasePointPerRound();
        }

        // 2. Skill Points hesapla
        int expYears = Math.min(experienceYears, 10);
        double skillMultiplier = 1 + (expYears * 0.02);
        skillPoints = (int) Math.floor(rawPoints * skillMultiplier);

        // 3. Bonus Points hesapla
        if (rawPoints >= 0 && rawPoints < 200) {
            bonusPoints = 10;
        } else if (rawPoints >= 200 && rawPoints < 400) {
            bonusPoints = 25;
        } else if (rawPoints >= 400 && rawPoints < 600) {
            bonusPoints = 50;
        } else {
            bonusPoints = 100;
        }

        // 4. Match Points hesapla
        matchPoints = skillPoints + bonusPoints;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Game[] getGames() {
        return games;
    }

    public int[] getRounds() {
        return rounds;
    }

    public int getRawPoints() {
        return rawPoints;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public int getMatchPoints() {
        return matchPoints;
    }

    // Bir oyunun katkısını hesapla
    public int getGameContribution(int gameIndex) {
        return rounds[gameIndex] * games[gameIndex].getBasePointPerRound();
    }
}