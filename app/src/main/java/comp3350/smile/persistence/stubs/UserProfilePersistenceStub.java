package comp3350.smile.persistence.stubs;

import comp3350.smile.objects.User;
import comp3350.smile.persistence.UserProfilePersistence;
import java.util.ArrayList;
import java.util.List;

public class UserProfilePersistenceStub implements UserProfilePersistence {

    private final List<User> users;

    // Constructor initializes an empty ArrayList (you could pre-populate test data if desired)
    public UserProfilePersistenceStub() {
        this.users = new ArrayList<>();

        users.add(new User("Admin", "Admin", "admin@umanitoba.ca", "2041111111", "1234"));
        users.add(new User("Tony","stark","startt$@umanitoba.ca","2042222222","2345"));
        users.add(new User("Jenny", "Chan", "chanj@myumanitoba.ca", "2043333333", "3456"));
        users.add(new User("Bruce", "Wayne", "bruce.wayne@umanitoba.ca", "2044444444", "4567"));
        users.add(new User("Clark", "Kent", "kentc1@myumanitoba.ca", "2045555555", "5678"));
        users.add(new User("Peter", "Parker", "peter.parker@umanitoba.ca", "2046666666", "6789"));
        users.add(new User("Diana", "Prince", "priced2@myumanitoba.ca", "2047777777", "7890"));
        users.add(new User("Natasha", "Romanoff", "natasha.romanoff@umanitoba.ca", "2048888888", "8901"));
        users.add(new User("Steve", "Rogers", "rogerss@myumanitoba.ca", "2049999999", "9012"));
        users.add(new User("Wanda", "Maximoff", "wanda.maximoff@umanitoba.ca", "2040000000", "0123"));
        users.add(new User("Barry", "Allen", "allenb1@myumanitoba.ca", "2041234567", "1230"));
    }

    // Get user profile by email from the in-memory list
    @Override
    public User getUserProfileByEmail(String email) {
        for (User profile : users) {
            if (profile.getEmail().equals(email)) {
                return profile;
            }
        }
        return null;
    }

    // Insert a new user profile into the list
    @Override
    public void insertUserProfile(User newUser) {
        users.add(newUser);
    }

    // Update an existing user profile in the list and return the updated profile
    @Override
    public User updateUserProfile(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(updatedUser.getEmail())) {
                users.set(i, updatedUser);
                return updatedUser;
            }
        }
        return null;
    }

    // Delete a user profile from the list
    @Override
    public void deleteUserProfile(User userToDelete) {
        users.remove(userToDelete);
    }
}
