
public final class Gamer {

    private final int id;
    private final String nickname;
    private final String name;
    private final String phoneNumber;
    private final int experienceYears;

    public Gamer(int id, String nickname, String name, String phoneNumber, int experienceYears) {
        if (id <= 0) {
            throw new IllegalArgumentException("Gamer ID must be positive.");
        }
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (experienceYears < 0) {
            throw new IllegalArgumentException("Experience years cannot be negative.");
        }

        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.experienceYears = experienceYears;
    }

    public int getId() { return id; }
    public String getNickname() { return nickname; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getExperienceYears() { return experienceYears; }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Name: %s)", nickname, id, name);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other instanceof Gamer g) {
            return this.id == g.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
