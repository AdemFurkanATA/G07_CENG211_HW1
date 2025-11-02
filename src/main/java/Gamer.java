public class Gamer {

    private int id;
    private String nickname;
    private String name;
    private String phoneNumber;
    private int experienceYears;

    public Gamer(int id, String nickname, String name, String phoneNumber, int experienceYears) {

        if (id <= 0) throw new IllegalArgumentException("Gamer ID must be positive.");
        if (nickname == null) throw new IllegalArgumentException("Nickname cannot be null.");

        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.experienceYears = experienceYears;
    }

    // Copy Constructor
    public Gamer(Gamer other) {
        this.id = other.id;
        this.nickname = other.nickname;
        this.name = other.name;
        this.phoneNumber = other.phoneNumber;
        this.experienceYears = other.experienceYears;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }


    public int getId() { return id; }
    public String getNickname() { return nickname; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getExperienceYears() { return experienceYears; }

    @Override
    public String toString() {
        return String.format("%s (ID: %d | @%d)", nickname, id, this.hashCode());
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