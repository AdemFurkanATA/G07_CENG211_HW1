import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileIO {
    public static Game[] readGames(String filePath) {

        try {

            Scanner fileScanner = new Scanner(new File(filePath));
            Game[] games = new Game[10];
            fileScanner.nextLine();
            int count = 0;

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] gameInfo = new String[3];
                gameInfo = line.split(",");

                int id = Integer.parseInt(gameInfo[0]);
                String gameName = gameInfo[1];
                int basePointPerRound = Integer.parseInt(gameInfo[2]);

                games[count] = new Game(id, gameName, basePointPerRound);
                count++;
            }

            fileScanner.close();
            return games;
        }

        catch (IOException e) {
            System.out.println("Error reading games file: " + e.getMessage());
            return null;
        }
    }

    public static Gamer[] readGamers(String filePath) {

        try {

            Scanner fileScanner = new Scanner(new File(filePath));

            Gamer[] gamers = new Gamer[100];
            fileScanner.nextLine();
            int count = 0;
            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] gamerInfo = new String[5];
                gamerInfo = line.split(",");

                int id = Integer.parseInt(gamerInfo[0]);
                String nickName = gamerInfo[1];
                String name = gamerInfo[2];
                String phoneNumber = gamerInfo[3];
                int experience = Integer.parseInt(gamerInfo[4]);

                gamers[count] = new Gamer(id, nickName, name, phoneNumber, experience);
                count++;
            }

            fileScanner.close();
            return gamers;
        }
        catch (IOException e){
            System.out.println("Error reading gamers file: " + e.getMessage());
            return null;
        }
    }
}