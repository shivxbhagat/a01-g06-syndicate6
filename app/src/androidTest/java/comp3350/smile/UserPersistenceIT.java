package comp3350.smile;
import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;

import comp3350.smile.application.Services;
import comp3350.smile.objects.User;
import comp3350.smile.persistence.UserProfilePersistence;
import comp3350.smile.persistence.hsqldb.UserDb;

@RunWith(AndroidJUnit4.class)
public class UserPersistenceIT {
    private UserProfilePersistence userProfilePersistence;

    @Before
    public void setUp() throws SQLException {
        Context context = ApplicationProvider.getApplicationContext();
        Services.init(context, false);
        userProfilePersistence = Services.getUserProfilePersistence(); // Use real database
    }

    @Test
    public void testDatabaseConnection() {
        assertNotNull("Database connection should be established", userProfilePersistence);
    }


    @Test
    public void testGetUserProfileByEmail() {
        User user = userProfilePersistence.getUserProfileByEmail("admin@myumanitoba.ca");
        assertNotNull("User profile should exist", user);
        assertEquals("Admin", user.getFirstName());
    }

    @Test
    public void testInsertUserProfile() {
        User newUser = new User("John", "Doe", "john.doe@example.com", "1234567890", "password123");
        userProfilePersistence.insertUserProfile(newUser);
        User insertedUser = userProfilePersistence.getUserProfileByEmail("john.doe@example.com");
        assertNotNull("Inserted user profile should exist", insertedUser);
        assertEquals("First name should match","John", insertedUser.getFirstName());
        assertEquals("Last name should match","Doe", insertedUser.getLastName());
    }

    @Test
    public void testUpdateUserProfile() {

        User userToUpdate = userProfilePersistence.getUserProfileByEmail("john.doe@example.com");
        User result = userProfilePersistence.updateUserProfile(userToUpdate);
        assertNotNull("Updated user profile should exist", result);
        assertEquals("First name should match","John", result.getFirstName());
        assertEquals("Last name should match","Doe", result.getLastName());
    }

    @Test
    public void testDeleteUserProfile() {
        User userToDelete = userProfilePersistence.getUserProfileByEmail("john.doe@example.com");
        userProfilePersistence.deleteUserProfile(userToDelete);

        User deletedUser = userProfilePersistence.getUserProfileByEmail("john.doe@example.com");
        assertNull("Deleted user profile should not exist", deletedUser);


    }


}