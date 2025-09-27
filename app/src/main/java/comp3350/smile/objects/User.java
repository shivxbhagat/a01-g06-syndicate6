package comp3350.smile.objects;

import java.util.Objects;

public class User {
    private final int id; //(assigned by the database)
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;


    // Constructor for creating a new profile (used during signup)
    public User(String firstName, String lastName, String email, String phone, String password) {
        this(-1, firstName, lastName, email, phone, password);
    }

    // Constructor for creating a user with an ID (used when retrieving from the database)
    public User(int id, String firstName, String lastName, String email, String phone, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        boolean isEqual = false;
        if (other instanceof User) {
            User otherUser = (User) other;
            isEqual = Objects.equals(this.email, otherUser.email);
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}