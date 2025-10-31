import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileIO {

    // Read CSV Files
    public static Game[] readGames(String filePath) {
        Game[] games = null;
        int lineCount = 0;

        try {
            // Önce satır sayısını say
            BufferedReader counter = new BufferedReader(new FileReader(filePath));
            while (counter.readLine() != null) {
                lineCount++;
            }
            counter.close();

            // Header'ı çıkar
            lineCount--;
            games = new Game[lineCount];

            // Şimdi verileri oku
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine(); // Header'ı atla
            int index = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String gameName = parts[1].trim();
                int basePoint = Integer.parseInt(parts[2].trim());

                games[index] = new Game(id, gameName, basePoint);
                index++;
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading games file: " + e.getMessage());
        }

        return games;
    }

    // gamers.csv dosyasını oku
    public static Gamer[] readGamers(String filePath) {
        Gamer[] gamers = null;
        int lineCount = 0;

        try {
            // Önce satır sayısını say
            BufferedReader counter = new BufferedReader(new FileReader(filePath));
            while (counter.readLine() != null) {
                lineCount++;
            }
            counter.close();

            // Header'ı çıkar
            lineCount--;
            gamers = new Gamer[lineCount];

            // Şimdi verileri oku
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine(); // Header'ı atla
            int index = 0;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String nickname = parts[1].trim();
                String name = parts[2].trim();
                String phone = parts[3].trim();
                int expYears = Integer.parseInt(parts[4].trim());

                gamers[index] = new Gamer(id, nickname, name, phone, expYears);
                index++;
            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading gamers file: " + e.getMessage());
        }

        return gamers;
    }
}