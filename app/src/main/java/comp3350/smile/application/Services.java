package comp3350.smile.application;

import android.content.Context;

import java.sql.Connection;
import java.sql.SQLException;

import comp3350.smile.application.StringConfig;
import comp3350.smile.objects.User;
import comp3350.smile.persistence.DBConnection;
import comp3350.smile.persistence.ItemPersistence;
import comp3350.smile.persistence.UserProfilePersistence;
import comp3350.smile.persistence.hsqldb.ItemDb;
import comp3350.smile.persistence.hsqldb.UserDb;
import comp3350.smile.persistence.stubs.ItemPersistenceStub;
import comp3350.smile.persistence.stubs.UserProfilePersistenceStub;

public class Services {
    private static UserProfilePersistence userProfilePersistence = null;
    private static ItemPersistence itemPersistence = null;
    private static User currentUser = null;
    private static DBConnection dbConnection = null;

    /**
     * Initializes the application's service layer by setting up persistence implementations.
     * Can use stub (in-memory) or real database-backed persistence depending on the flag.
     *
     * @param context the Android context, required only when using real database persistence.
     * @param useStub true to use stub (test/in-memory) implementations; false to use real database.
     * @throws IllegalArgumentException if context is null when not using stubs.
     */
    public static synchronized void init(Context context, boolean useStub) {
        if (useStub) {
            userProfilePersistence = new UserProfilePersistenceStub();
            itemPersistence = new ItemPersistenceStub();
        } else {
            if (context == null) {
                throw new IllegalArgumentException(StringConfig.ERROR_CONTEXT_REQUIRED);
            }
            dbConnection = new DBConnection(context);
            userProfilePersistence = new UserDb();
            itemPersistence = new ItemDb();
        }
    }

    /**
     * Retrieves the initialized {@link UserProfilePersistence} instance.
     *
     * @return the current UserProfilePersistence implementation
     * @throws IllegalStateException if the service has not been initialized
     */
    public static synchronized UserProfilePersistence getUserProfilePersistence() {
        if (userProfilePersistence == null) {
            throw new IllegalStateException(StringConfig.ERROR_SERVICES_NOT_INITIALIZED);
        }
        return userProfilePersistence;
    }

    /**
     * Returns the initialized ItemPersistence implementation used for item-related data operations.
     *
     * @return the current ItemPersistence implementation
     * @throws IllegalStateException if the service has not been initialized
     */
    public static synchronized ItemPersistence getItemPersistence() {
        if (itemPersistence == null) {
            throw new IllegalStateException(StringConfig.ERROR_SERVICES_NOT_INITIALIZED);
        }
        return itemPersistence;
    }

    /**
     * Returns the active SQL database connection from the initialized DBConnection instance.
     *
     * @return the SQL Connection object
     * @throws IllegalStateException if the DBConnection has not been initialized
     * @throws SQLException if a database access error occurs
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (dbConnection == null) {
            throw new IllegalStateException(StringConfig.ERROR_DB_NOT_INITIALIZED);
        }
        return dbConnection.getConnection();
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return the current User instance, or null if no user is logged in
     */
    public static synchronized User getCurrentUser() {
        return currentUser;
    }


    /**
     * Sets the currently logged-in user.
     *
     * @param newUser the User instance to set as the current user
     */
    public static synchronized void setCurrentUser(User newUser) {
        currentUser = newUser;
    }

    /**
     * Sets the UserProfilePersistence implementation to be used by the service layer.
     *
     * @param userProfilePersistence the UserProfilePersistence implementation to set
     */
    public static void setUserProfilePersistence(UserProfilePersistence userProfilePersistence) {
        Services.userProfilePersistence = userProfilePersistence;
    }

    /**
     * Tears down the service layer by closing the database connection (if any) and
     * clearing all service-related references.
     */
    public static void teardown() {
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
        userProfilePersistence = null;
        itemPersistence = null;
        currentUser = null;
    }
}