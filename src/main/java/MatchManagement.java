import java.util.Objects;
import java.util.Random;

public class MatchManagement {
    private Match[][] matches; // [gamerCount][15]
    private Gamer[] gamers;
    private Game[] games;
    private Random random;
    private int matchIdCounter;

    // Constructor
    public MatchManagement(Gamer[] gamers, Game[] games) {
        this.gamers = Objects.requireNonNull(gamers,"Gamer can not be null!");
        this.games = Objects.requireNonNull(games,"Gamer can not be null!");
        this.matches = new Match[gamers.length][15];
        this.random = new Random();
        this.matchIdCounter = 1;

        generateAllMatches();
    }

    // Generates single match for a gamer
    private Match generateMatch(Gamer gamer) {

        Objects.requireNonNull(gamer,"Gamer can not be null!");

        // Selects 3 random games
        Game[] selectedGames = new Game[3];
        int[] rounds = new int[3];

        for (int i = 0; i < 3; i++) {
            // Selects 1 random game
            selectedGames[i] = games[random.nextInt(games.length)];

            // Selects round number between 1 and 10 randomly
            rounds[i] = random.nextInt(10) + 1;
        }

        Match match = new Match(matchIdCounter++, selectedGames, rounds, gamer.getExperienceYears());
        return match;
    }

    // Generates all matches
    private void generateAllMatches() {
        for (int gamerIndex = 0; gamerIndex < gamers.length; gamerIndex++) {
            for (int matchIndex = 0; matchIndex < 15; matchIndex++) {
                matches[gamerIndex][matchIndex] = generateMatch(gamers[gamerIndex]);
            }
        }
    }

    //------------Getters------------\\

    // Returns all matches
    public Match[][] getAllMatches() {
        Match[][] allMatchesCopy = new Match[gamers.length][15];
        for (int gamerIndex = 0; gamerIndex < gamers.length; gamerIndex++) {
            for (int matchIndex = 0; matchIndex < 15; matchIndex++) {
                allMatchesCopy[gamerIndex][matchIndex] = matches[gamerIndex][matchIndex];
            }
        }
        return allMatchesCopy;
    }

    // Returns all games of given gamer
    public Match[] getGamerMatches(int gamerIndex) {
        return getAllMatches()[gamerIndex];
    }

    // Returns specific match according to gamer and match ID
    public Match getMatch(int gamerIndex, int matchIndex) {
        return getAllMatches()[gamerIndex][matchIndex];
    }

    // Returns all matches as a 1D array (for Query class)
    public Match[] getAllMatchesFlat() {
        int totalMatches = gamers.length * 15;
        Match[] flatMatches = new Match[totalMatches];
        int index = 0;

        for (int i = 0; i < gamers.length; i++) {
            for (int j = 0; j < 15; j++) {
                flatMatches[index++] = getAllMatches()[i][j];
            }
        }

        return flatMatches;
    }
}