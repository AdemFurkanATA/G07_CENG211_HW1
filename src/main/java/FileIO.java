import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {

    // Creating class constructor for safety
    private FileIO(){}

    // Reading Games files
    public static Game[] readGames(String filePath) {
        Game[] games = null;
        int lineCount = 0;

        try {

            BufferedReader counter = new BufferedReader(new FileReader(filePath));
            while (counter.readLine() != null) {
                lineCount++;
            }
            counter.close();

            // Creating 1D array of games
            if (lineCount>0){
                lineCount--;
            }
            else {
                System.out.println("Line count can not be negative");
                return null;
            }
            games = new Game[lineCount];

            // Reading CSV file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine(); // Header row
            int index = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());  // Chancing ID type from String to integer
                String gameName = parts[1].trim();
                int basePointPerRound = Integer.parseInt(parts[2].trim());   // Chancing base point type from String to integer

                games[index] = new Game(id, gameName, basePointPerRound);
                index++;
            }
            reader.close();  // Closing file

        } catch (IOException e) {
            System.out.println("Error reading games file: " + e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return games;
    }

    // Reading Gamers file
    public static Gamer[] readGamers(String filePath) {
        Gamer[] gamers = null;
        int lineCount = 0;

        try {

            BufferedReader counter = new BufferedReader(new FileReader(filePath));
            while (counter.readLine() != null) {
                lineCount++;
            }
            counter.close();

            // Creating 1D array of gamers
            if (lineCount>0){
                lineCount--;
            }
            else {
                System.out.println("Line count can not be negative");
                return null;
            }
            gamers = new Gamer[lineCount];

            // Reading CSV file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine(); // Header
            int index = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());  // Chancing ID type from String to integer
                String nickname = parts[1].trim();
                String name = parts[2].trim();
                String phone = parts[3].trim();
                int expYears = Integer.parseInt(parts[4].trim());  // Chancing base point type from String to integer

                gamers[index] = new Gamer(id, nickname, name, phone, expYears);
                index++;
            }
            reader.close();  // Closing file

        } catch (IOException e) {
            System.out.println("Error reading gamers file: " + e.getMessage());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return gamers;
    }
}