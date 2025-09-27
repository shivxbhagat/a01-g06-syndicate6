package comp3350.smile.logic;

import comp3350.smile.objects.User;
import comp3350.smile.persistence.UserProfilePersistence;
import comp3350.smile.application.Services;
import comp3350.smile.logic.UserValidator;

public class UserProfileService {
    private final UserProfilePersistence userProfilePersistence;
    private final UserValidator userValidator; // Use validator for consistency

    public UserProfileService() {
        userProfilePersistence = Services.getUserProfilePersistence();
        userValidator = new UserValidator();
    }

    // Constructor for stub persistence (useful for testing)
    public UserProfileService(UserProfilePersistence userProfilePersistence, UserValidator userValidator) {
        this.userProfilePersistence = userProfilePersistence;
        this.userValidator = userValidator;
    }

    public void updateUser(User user) {

        // Validate the user using the UserValidator (this will throw if invalid)
        userValidator.validate(user);

        User updatedUser = userProfilePersistence.updateUserProfile(user);
        userValidator.nullUser(updatedUser);
        Services.setCurrentUser(updatedUser);
    }



    //Registers a new user after validating all required fields.

    public void signup(User newUser) {
        if (newUser == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        // Validate using UserValidator
        userValidator.validate(newUser);

        // Check if email already exists.
        if (userProfilePersistence.getUserProfileByEmail(newUser.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }

        userProfilePersistence.insertUserProfile(newUser);
        Services.setCurrentUser(newUser);
    }

    //Logs in the user using the provided credentials.

    public void login(String email, String password) {
        if (email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Email and password must not be empty.");
        }

        // Validate email format using UserValidator (this will throw if invalid)
        userValidator.validateEmail(email);

        User user = userProfilePersistence.getUserProfileByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        Services.setCurrentUser(user);
    }

    public void delete(User user) {
        User currentUser = Services.getCurrentUser();
        userValidator.nullUser(currentUser); // Ensure a user is logged in
        userProfilePersistence.deleteUserProfile(user);
        Services.setCurrentUser(null); // Clear current user after deletion
    }

}
