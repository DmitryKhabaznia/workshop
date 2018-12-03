package workshop.constants;

public enum Gender {
    MALE, FEMALE;

    public static boolean getBooleanValue(String gender) {
        return gender.equalsIgnoreCase(MALE.name());
    }

}
