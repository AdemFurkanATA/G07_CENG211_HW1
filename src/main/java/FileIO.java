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
                } catch (ParseException e) {
                    System.err.println("Line " + lineNumber + ": " + e.getMessage());
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

    private static Game parseGameLine(String line, int lineNumber) throws ParseException {
        String[] parts = splitCSV(line, 3);

        if (parts.length != 3) {
            throw new ParseException("Invalid format - expected 3 fields, found " + parts.length);
        }

        // Parse ID
        int id;
        try {
            id = Integer.parseInt(parts[0].trim());
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid ID format: " + parts[0]);
        }

        if (id <= 0) {
            throw new ParseException("ID must be positive, found: " + id);
        }

        // Parse game name
        String gameName = sanitizeField(parts[1]);

        if (gameName.isEmpty()) {
            throw new ParseException("Game name cannot be empty");
        }

        if (gameName.length() > MAX_GAME_NAME_LENGTH) {
            throw new ParseException("Game name too long (max " + MAX_GAME_NAME_LENGTH + ")");
        }

        // Parse base points
        int basePointPerRound;
        try {
            basePointPerRound = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid base points format: " + parts[2]);
        }

        if (basePointPerRound < 0) {
            throw new ParseException("Base points cannot be negative: " + basePointPerRound);
        }

        if (basePointPerRound > MAX_BASE_POINTS) {
            throw new ParseException("Base points too high (max " + MAX_BASE_POINTS + "): " + basePointPerRound);
        }

        return new Game(id, gameName, basePointPerRound);
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
                } catch (ParseException e) {
                    System.err.println("Line " + lineNumber + ": " + e.getMessage());
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

    private static Gamer parseGamerLine(String line, int lineNumber) throws ParseException {
        String[] parts = splitCSV(line, 5);

        if (parts.length != 5) {
            throw new ParseException("Invalid format - expected 5 fields, found " + parts.length);
        }

        // Parse ID
        int id;
        try {
            id = Integer.parseInt(parts[0].trim());
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid ID format: " + parts[0]);
        }

        if (id <= 0) {
            throw new ParseException("ID must be positive, found: " + id);
        }

        // Parse nickname
        String nickname = sanitizeField(parts[1]);

        if (nickname.isEmpty()) {
            throw new ParseException("Nickname cannot be empty");
        }

        if (nickname.length() > MAX_NICKNAME_LENGTH) {
            throw new ParseException("Nickname too long (max " + MAX_NICKNAME_LENGTH + ")");
        }

        // Parse name
        String name = sanitizeField(parts[2]);

        if (name.isEmpty()) {
            throw new ParseException("Name cannot be empty");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            throw new ParseException("Name too long (max " + MAX_NAME_LENGTH + ")");
        }

        // Parse phone
        String phone = sanitizeField(parts[3]);

        if (!phone.isEmpty()) {
            if (phone.length() < MIN_PHONE_LENGTH || phone.length() > MAX_PHONE_LENGTH) {
                throw new ParseException("Invalid phone length (must be " + MIN_PHONE_LENGTH + "-" + MAX_PHONE_LENGTH + ")");
            }

            if (!isValidPhone(phone)) {
                throw new ParseException("Invalid phone format");
            }
        }

        // Parse experience years
        int experienceYears;
        try {
            experienceYears = Integer.parseInt(parts[4].trim());
        } catch (NumberFormatException e) {
            throw new ParseException("Invalid experience years format: " + parts[4]);
        }

        if (experienceYears < 0) {
            throw new ParseException("Experience years cannot be negative: " + experienceYears);
        }

        if (experienceYears > MAX_EXPERIENCE_YEARS) {
            throw new ParseException("Experience years too high (max " + MAX_EXPERIENCE_YEARS + "): " + experienceYears);
        }

        return new Gamer(id, nickname, name, phone, experienceYears);
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
     * Splits a CSV line into fields
     * Manual implementation to avoid String.split() issues with trailing empty strings
     */
    private static String[] splitCSV(String line, int expectedFields) {
        String[] result = new String[expectedFields];
        int fieldIndex = 0;
        int startIndex = 0;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',') {
                if (fieldIndex < expectedFields) {
                    result[fieldIndex] = line.substring(startIndex, i);
                    fieldIndex++;
                    startIndex = i + 1;
                }
            }
        }

        // Add last field
        if (fieldIndex < expectedFields && startIndex <= line.length()) {
            result[fieldIndex] = line.substring(startIndex);
            fieldIndex++;
        }

        // If we didn't find enough fields, return what we have
        if (fieldIndex != expectedFields) {
            String[] truncated = new String[fieldIndex];
            for (int i = 0; i < fieldIndex; i++) {
                truncated[i] = result[i];
            }
            return truncated;
        }

        return result;
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

    // ============= CUSTOM EXCEPTION =============

    /**
     * Simple exception class for parsing errors
     */
    private static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }
    }
}