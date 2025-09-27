package comp3350.smile.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import comp3350.smile.application.Services;
import comp3350.smile.application.StringConfig;
import comp3350.smile.objects.User;
import comp3350.smile.persistence.UserProfilePersistence;

public class UserDb implements UserProfilePersistence {

    @Override
    public User getUserProfileByEmail(String email) {
        User user = null;
        String query = StringConfig.SQL_USERS_SELECT_EMAIL;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("USER_ID");
                String firstName = resultSet.getString("FIRST_NAME");
                String lastName = resultSet.getString("LAST_NAME");
                String phone = resultSet.getString("PHONE");
                String password = resultSet.getString("PASSWORD");

                user = new User(userId, firstName, lastName, email, phone, password);
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_USER, StringConfig.LOG_USER_PROFILE_ERROR, e);
        }
        return user;
    }

    @Override
    public void insertUserProfile(User newUser) {
        String query = StringConfig.SQL_USERS_INSERT;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newUser.getFirstName());
            stmt.setString(2, newUser.getLastName());
            stmt.setString(3, newUser.getEmail());
            stmt.setString(4, newUser.getPhone());
            stmt.setString(5, newUser.getPassword());

            stmt.executeUpdate();
            Log.d(StringConfig.LOG_TAG_USER, StringConfig.LOG_USER_INSERT_SUCCESS);
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_USER, StringConfig.LOG_USER_INSERT_ERROR, e);
        }
    }

    @Override
    public User updateUserProfile(User updatedUser) {
        String query = StringConfig.SQL_USERS_UPDATE;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, updatedUser.getFirstName());
            stmt.setString(2, updatedUser.getLastName());
            stmt.setString(3, updatedUser.getPhone());
            stmt.setString(4, updatedUser.getPassword());
            stmt.setString(5, updatedUser.getEmail());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                Log.d(StringConfig.LOG_TAG_USER, StringConfig.LOG_USER_UPDATE_SUCCESS);
                return updatedUser;
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_USER, StringConfig.LOG_USER_UPDATE_ERROR, e);
        }
        return null;
    }

    @Override
    public void deleteUserProfile(User userToDelete) {
        String query = StringConfig.SQL_USERS_DELETE;

        try (Connection connection = Services.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userToDelete.getEmail());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                Log.d(StringConfig.LOG_TAG_USER, StringConfig.LOG_USER_DELETE_SUCCESS);
            }
        } catch (SQLException e) {
            Log.e(StringConfig.LOG_TAG_USER, StringConfig.LOG_USER_DELETE_ERROR, e);
        }
    }
}