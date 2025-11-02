import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileIO {

    // Constants
    private static final int MAX_GAME_NAME_LENGTH = 100;
    private static final int MAX_NICKNAME_LENGTH = 50;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_PHONE_LENGTH = 20;
    private static final int MIN_PHONE_LENGTH = 10;
    private static final int MAX_BASE_POINTS = 1000;
    private static final int MAX_EXPERIENCE_YEARS = 50;
    private static final int MAX_LINE_LENGTH = 1000;

    private FileIO() {}

    // ============= GAME READING =============

    public static Game[] readGames(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.err.println("Error: File path cannot be null or empty");
            return null;
        }

        // First pass: Count valid lines
        int lineCount = countLines(filePath);

        if (lineCount < 0) {
            System.err.println("Error: Could not read file");
            return null;
        }

        if (lineCount == 0) {
            System.err.println("Warning: No data lines found in file");
            return new Game[0];
        }

        // Create array with maximum possible size
        Game[] games = new Game[lineCount];
        int validCount = 0;
        int lineNumber = 0;

        // Second pass: Parse data
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // Skip header
            lineNumber++;

            if (line == null) {
                System.err.println("Error: File is empty");
                return new Game[0];
            }

            // Read and parse each line
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Security: Check line length
                if (line.length() > MAX_LINE_LENGTH) {
                    System.err.println("Line " + lineNumber + ": Line too long, skipping");
                    continue;
                }

                try {
                    Game game = parseGameLine(line, lineNumber);
                    if (game != null) {
                        games[validCount] = game;
                        validCount++;
                    }
                } catch (Exception e) {
                    System.err.println("Line " + lineNumber + ": Unexpected error - " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading games file: " + e.getMessage());
            return null;
        }

        // If no valid data found
        if (validCount == 0) {
            System.err.println("Warning: No valid games found in file");
            return new Game[0];
        }

        // Shrink array to actual size (remove nulls)
        return shrinkArray(games, validCount);
    }

    private static Game parseGameLine(String line, int lineNumber) {
        // Replaced manual split with standard String.split()
        String[] parts = line.split(",", -1);

        if (parts.length != 3) {
            System.err.println("Line " + lineNumber + ": Invalid format - expected 3 fields, found " + parts.length);
            return null;
        }

        try {
            // Parse ID
            int id = Integer.parseInt(parts[0].trim());
            if (id <= 0) {
                System.err.println("Line " + lineNumber + ": ID must be positive, found: " + id);
                return null;
            }

            // Parse game name
            String gameName = sanitizeField(parts[1]);
            if (gameName.isEmpty()) {
                System.err.println("Line " + lineNumber + ": Game name cannot be empty");
                return null;
            }
            if (gameName.length() > MAX_GAME_NAME_LENGTH) {
                System.err.println("Line " + lineNumber + ": Game name too long (max " + MAX_GAME_NAME_LENGTH + ")");
                return null;
            }

            // Parse base points
            int basePointPerRound = Integer.parseInt(parts[2].trim());
            if (basePointPerRound < 0) {
                System.err.println("Line " + lineNumber + ": Base points cannot be negative: " + basePointPerRound);
                return null;
            }
            if (basePointPerRound > MAX_BASE_POINTS) {
                System.err.println("Line " + lineNumber + ": Base points too high (max " + MAX_BASE_POINTS + "): " + basePointPerRound);
                return null;
            }

            return new Game(id, gameName, basePointPerRound);

        } catch (NumberFormatException e) {
            System.err.println("Line " + lineNumber + ": Invalid number format: " + e.getMessage());
            return null;
        }
    }

    // ============= GAMER READING =============

    public static Gamer[] readGamers(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.err.println("Error: File path cannot be null or empty");
            return null;
        }

        // First pass: Count valid lines
        int lineCount = countLines(filePath);

        if (lineCount < 0) {
            System.err.println("Error: Could not read file");
            return null;
        }

        if (lineCount == 0) {
            System.err.println("Warning: No data lines found in file");
            return new Gamer[0];
        }

        // Create array with maximum possible size
        Gamer[] gamers = new Gamer[lineCount];
        int validCount = 0;
        int lineNumber = 0;

        // Second pass: Parse data
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine(); // Skip header
            lineNumber++;

            if (line == null) {
                System.err.println("Error: File is empty");
                return new Gamer[0];
            }

            // Read and parse each line
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Security: Check line length
                if (line.length() > MAX_LINE_LENGTH) {
                    System.err.println("Line " + lineNumber + ": Line too long, skipping");
                    continue;
                }

                try {
                    Gamer gamer = parseGamerLine(line, lineNumber);
                    if (gamer != null) {
                        gamers[validCount] = gamer;
                        validCount++;
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Line " + lineNumber + ": " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Line " + lineNumber + ": Unexpected error - " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading gamers file: " + e.getMessage());
            return null;
        }

        // If no valid data found
        if (validCount == 0) {
            System.err.println("Warning: No valid gamers found in file");
            return new Gamer[0];
        }

        // Shrink array to actual size
        return shrinkArray(gamers, validCount);
    }

    private static Gamer parseGamerLine(String line, int lineNumber) {
        // Replaced manual split with standard String.split()
        String[] parts = line.split(",", -1);

        if (parts.length != 5) {
            System.err.println("Line " + lineNumber + ": Invalid format - expected 5 fields, found " + parts.length);
            return null;
        }

        try {
            // Parse ID
            int id = Integer.parseInt(parts[0].trim());
            if (id <= 0) {
                System.err.println("Line " + lineNumber + ": ID must be positive, found: " + id);
                return null;
            }

            // Parse nickname
            String nickname = sanitizeField(parts[1]);
            if (nickname.isEmpty()) {
                System.err.println("Line " + lineNumber + ": Nickname cannot be empty");
                return null;
            }
            if (nickname.length() > MAX_NICKNAME_LENGTH) {
                System.err.println("Line " + lineNumber + ": Nickname too long (max " + MAX_NICKNAME_LENGTH + ")");
                return null;
            }

            // Parse name
            String name = sanitizeField(parts[2]);
            if (name.isEmpty()) {
                System.err.println("Line " + lineNumber + ": Name cannot be empty");
                return null;
            }
            if (name.length() > MAX_NAME_LENGTH) {
                System.err.println("Line " + lineNumber + ": Name too long (max " + MAX_NAME_LENGTH + ")");
                return null;
            }

            // Parse phone
            String phone = sanitizeField(parts[3]);
            if (!phone.isEmpty()) {
                if (phone.length() < MIN_PHONE_LENGTH || phone.length() > MAX_PHONE_LENGTH) {
                    System.err.println("Line " + lineNumber + ": Invalid phone length (must be " + MIN_PHONE_LENGTH + "-" + MAX_PHONE_LENGTH + ")");
                    return null;
                }
                if (!isValidPhone(phone)) {
                    System.err.println("Line " + lineNumber + ": Invalid phone format");
                    return null;
                }
            }

            // Parse experience years
            int experienceYears = Integer.parseInt(parts[4].trim());
            if (experienceYears < 0) {
                System.err.println("Line " + lineNumber + ": Experience years cannot be negative: " + experienceYears);
                return null;
            }
            if (experienceYears > MAX_EXPERIENCE_YEARS) {
                System.err.println("Line " + lineNumber + ": Experience years too high (max " + MAX_EXPERIENCE_YEARS + "): " + experienceYears);
                return null;
            }

            return new Gamer(id, nickname, name, phone, experienceYears);

        } catch (NumberFormatException e) {
            System.err.println("Line " + lineNumber + ": Invalid number format: " + e.getMessage());
            return null;
        }
    }

    // ============= HELPER METHODS ============= \\

    /**
     * Counts non-empty data lines in the file (excluding header)
     * Returns -1 if file cannot be read
     */
    private static int countLines(String filePath) {
        int count = 0;

        try (BufferedReader counter = new BufferedReader(new FileReader(filePath))) {
            String line = counter.readLine(); // Skip header

            if (line == null) {
                return 0; // Empty file
            }

            while ((line = counter.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    count++;
                }
            }

        } catch (IOException e) {
            System.err.println("Error counting lines: " + e.getMessage());
            return -1;
        }

        return count;
    }

    /**
     * Sanitizes CSV field to prevent CSV injection attacks
     */
    private static String sanitizeField(String field) {
        if (field == null) {
            return "";
        }

        field = field.trim();

        // Remove carriage return and line feed
        field = removeChar(field, '\r');
        field = removeChar(field, '\n');

        // CSV Injection Protection
        if (field.length() > 0) {
            char firstChar = field.charAt(0);
            if (firstChar == '=' || firstChar == '+' || firstChar == '-' ||
                    firstChar == '@' || firstChar == '\t') {
                field = "'" + field;
            }
        }

        return field;
    }

    /**
     * Removes all occurrences of a character from a string
     */
    private static String removeChar(String str, char ch) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        char[] chars = str.toCharArray();
        char[] result = new char[chars.length];
        int resultIndex = 0;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != ch) {
                result[resultIndex] = chars[i];
                resultIndex++;
            }
        }

        return new String(result, 0, resultIndex);
    }

    /**
     * Validates phone number format
     */
    private static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return true;
        }

        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if (!Character.isDigit(c) && c != ' ' && c != '+' && c != '-' && c != '(' && c != ')') {
                return false;
            }
        }

        return true;
    }

    /**
     * Shrinks an array to remove null elements at the end
     */
    private static Game[] shrinkArray(Game[] array, int validCount) {
        if (validCount == array.length) {
            return array; // No need to shrink
        }

        Game[] result = new Game[validCount];
        for (int i = 0; i < validCount; i++) {
            result[i] = array[i];
        }
        return result;
    }

    /**
     * Shrinks an array to remove null elements at the end (Gamer version)
     */
    private static Gamer[] shrinkArray(Gamer[] array, int validCount) {
        if (validCount == array.length) {
            return array; // No need to shrink
        }

        Gamer[] result = new Gamer[validCount];
        for (int i = 0; i < validCount; i++) {
            result[i] = array[i];
        }
        return result;
    }
}