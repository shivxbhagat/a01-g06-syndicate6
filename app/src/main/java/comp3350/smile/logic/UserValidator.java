package comp3350.smile.logic;

import comp3350.smile.logic.Interfaces.IValidator;
import comp3350.smile.objects.User;

public class UserValidator implements IValidator<User> {

    // for empty/null fields
    public boolean emptyFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }


    public void nullUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
    }

    public void validateEmail(String email) {
        if (emptyFields(email)) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        int atPosition = email.indexOf("@");
        if (atPosition <= 0 || atPosition == email.length() - 1) {
            throw new IllegalArgumentException("Email is invalid.");
        }
        if (!email.endsWith("@myumanitoba.ca") && !email.endsWith("@umanitoba.ca")) {
            throw new IllegalArgumentException("Email must be a university email address.");
        }
    }


    void validateFirstName(String firstName) {
        if (emptyFields(firstName)) {
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
    }


    void validateLastName(String lastName) {
        if (emptyFields(lastName)) {
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
    }


    void validatePhone(String phone) {
        if (emptyFields(phone)) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
    }


    void validatePassword(String password) {
        if (emptyFields(password)) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
    }


    void validateID(int id) {
        if(id < -1){ // Assuming IDs should be non-negative
            throw new IllegalArgumentException("User ID is not valid.");
        }
    }

    // Validate user: checks that none of the required fields are null or empty.
    @Override
    public void validate(User user) {
        nullUser(user);
        validateEmail(user.getEmail());
        validateFirstName(user.getFirstName());
        validateLastName(user.getLastName());
        validatePhone(user.getPhone());
        validatePassword(user.getPassword());
        validateID(user.getId());
    }
}
