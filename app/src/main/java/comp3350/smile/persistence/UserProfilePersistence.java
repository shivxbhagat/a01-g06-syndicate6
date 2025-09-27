package comp3350.smile.persistence;

import comp3350.smile.objects.User;

public interface UserProfilePersistence {
    // Get a user profile by email
    User getUserProfileByEmail(String email);

    // Insert a new user profile
    void insertUserProfile(User newUser);

    // Update an existing user profile
    User updateUserProfile(User updatedUser);

    // Delete a user profile
    void deleteUserProfile(User userToDelete);
}
