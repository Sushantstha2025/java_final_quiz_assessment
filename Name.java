package finalAssesment3;

/**
 * Represents a person's name consisting of a first and last name.
 */
public class Name {
    private String firstName;
    private String lastName;

    /**
     * Initializes a new Name object.
     * @param firstName The person's first name.
     * @param lastName The person's last name.
     */
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Combines the first and last name.
     * @return The full name as a single String.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Extracts the first letter of the first and last name.
     * @return Uppercase initials (e.g., "JD").
     */
    public String getInitials() {
        String initials = "" + firstName.charAt(0) + lastName.charAt(0);
        return initials.toUpperCase();
    }
}