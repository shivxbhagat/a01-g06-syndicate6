package comp3350.smile.logic;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import comp3350.smile.application.Services;
import comp3350.smile.objects.User;
import comp3350.smile.persistence.UserProfilePersistence;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {

    @Mock
    private UserProfilePersistence mockUserPersistence;

    @InjectMocks
    private UserProfileService userProfileService;

    private UserValidator userValidator;

    private User testUser;

    @Before
    public void setUp() {
        testUser = new User(
                "Alice", "Smith", "alice.smith@myumanitoba.ca", "2041112233", "securePass123"
        );
        userValidator = new UserValidator();
        userProfileService = new UserProfileService(mockUserPersistence, userValidator);
        Services.setCurrentUser(null);
    }

    @Test
    public void testSignup_Success() {
        when(mockUserPersistence.getUserProfileByEmail(testUser.getEmail())).thenReturn(null);

        userProfileService.signup(testUser);
        verify(mockUserPersistence).insertUserProfile(testUser);
        assertEquals(testUser, Services.getCurrentUser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSignup_EmailAlreadyExists() {
        when(mockUserPersistence.getUserProfileByEmail(testUser.getEmail())).thenReturn(testUser);

        userProfileService.signup(testUser);
    }

    @Test
    public void testLogin_Success() {
        User validUser = new User("Bob", "Lee", "bob.lee@myumanitoba.ca", "2044445566", "pass123");
        when(mockUserPersistence.getUserProfileByEmail("bob.lee@myumanitoba.ca")).thenReturn(validUser);

        userProfileService.login("bob.lee@myumanitoba.ca", "pass123");
        assertEquals(validUser, Services.getCurrentUser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLogin_InvalidPassword() {
        User validUser = new User("Bob", "Lee", "bob.lee@myumanitoba.ca", "2044445566", "pass123");
        when(mockUserPersistence.getUserProfileByEmail("bob.lee@myumanitoba.ca")).thenReturn(validUser);

        userProfileService.login("bob.lee@myumanitoba.ca", "wrongPass");
    }

    @Test
    public void testUpdateUser_Success() {
        User originalUser = new User("John", "Doe", "john@myumanitoba.ca", "2041234567", "oldPass");
        User updatedUser = new User("John", "Doe", "john@myumanitoba.ca", "2041234567", "newPass");

        // Mock the update to return the updated user
        when(mockUserPersistence.updateUserProfile(updatedUser)).thenReturn(updatedUser);
        Services.setCurrentUser(originalUser); // Simulate logged-in user

        userProfileService.updateUser(updatedUser);
        verify(mockUserPersistence).updateUserProfile(updatedUser);
        assertEquals(updatedUser, Services.getCurrentUser());
    }

    @Test
    public void testDeleteUser_Success() {
        User user = new User("test", "user", "test@myumanitoba.ca", "1234567890", "pass");
        Services.setCurrentUser(user); // Set current user

        userProfileService.delete(user);
        verify(mockUserPersistence).deleteUserProfile(user);
        assertNull(Services.getCurrentUser());
    }
}