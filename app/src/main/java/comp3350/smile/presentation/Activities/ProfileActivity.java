package comp3350.smile.presentation.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;

import comp3350.smile.R;
import comp3350.smile.application.Services;
import comp3350.smile.logic.UserProfileService;
import comp3350.smile.objects.User;

public class ProfileActivity extends NavigationActivity {
    //this activity handles user profile

    private EditText firstNameEditText, lastNameEditText, phoneNumberEditText, emailEditText, passwordEditText;
    private Button updateButton, deleteButton, logoutButton;
    private UserProfileService userService;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_profile);
        bottomNavigationView.setSelectedItemId(R.id.nav_user);
        userService = new UserProfileService();

        initializeViews();
        setupPasswordField();
        loadUserData();

        updateButton.setOnClickListener(v -> updateProfile());
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());
        logoutButton.setOnClickListener(v -> logout());

        handleBackPress();

    }

    /**
     * Initializes all profile view elements.
     */
    private void initializeViews() {
        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        phoneNumberEditText = findViewById(R.id.phoneNumber);
        emailEditText = findViewById(R.id.userEmail);
        passwordEditText = findViewById(R.id.userPassword);
        updateButton = findViewById(R.id.btnUpdate);
        deleteButton = findViewById(R.id.btnDelete);
        logoutButton = findViewById(R.id.logoutButton);
    }

    /**
     * Sets up password field to toggle visibility on focus and editor actions.
     */
    private void setupPasswordField() {
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            return false;
        });
    }

    /**
     * Loads the current user's data into the profile form fields.
     */
    private void loadUserData() {
        currentUser = Services.getCurrentUser();
        if (currentUser != null) {
            firstNameEditText.setText(currentUser.getFirstName());
            lastNameEditText.setText(currentUser.getLastName());
            phoneNumberEditText.setText(currentUser.getPhone());
            emailEditText.setText(currentUser.getEmail());
            passwordEditText.setText(currentUser.getPassword());
        }
    }

    /**
     * Updates the current user's profile using values from the input fields.
     * Displays a toast message on success or failure.
     */
    private void updateProfile() {
        String updatedFirstName = firstNameEditText.getText().toString();
        String updatedLastName = lastNameEditText.getText().toString();
        String updatedPhoneNumber = phoneNumberEditText.getText().toString();
        String updatedEmail = emailEditText.getText().toString();
        String updatedPassword = passwordEditText.getText().toString();

        // Build the updated User.
        User updatedUser = new User(updatedFirstName, updatedLastName, updatedEmail, updatedPhoneNumber, updatedPassword);

        try {
            userService.updateUser(updatedUser);
            Toast.makeText(ProfileActivity.this, getString(R.string.user_profile_updated), Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ProfileActivity.this, "An error occurred while updating the profile.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Shows a confirmation dialog before deleting the user account.
     */
    private void showDeleteConfirmationDialog() {
        new android.app.AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_deletion))
                .setMessage(getString(R.string.delete_account_message))
                .setCancelable(false)
                .setPositiveButton((getString(R.string.yes)), (dialog, which) -> deleteAccount())
                .setNegativeButton(getString(R.string.no), null)
                .show();
    }

    /**
     * Deletes the current user's account and logs them out.
     */
    private void deleteAccount() {
        userService.delete(currentUser);
        clearLoginCredentials();
        logout();
    }

    /**
     * Logs out the user and navigates back to the login screen.
     */
    private void logout() {
        Services.setCurrentUser(null);
        Intent mainPageIntent = new Intent(ProfileActivity.this, LoginActivity.class);
        mainPageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainPageIntent);
        finishAffinity();
    }

    /**
     * Clears saved login credentials from shared preferences.
     */
    private void clearLoginCredentials() {
        SharedPreferences prefs = getSharedPreferences("LoginSaved", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("savedEmail");
        editor.remove("savedPassword");
        editor.apply();
    }

    /**
     * Handles back press behavior to exit app if on root task or go back otherwise.
     */
    private void handleBackPress() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isTaskRoot()) {
                    moveTaskToBack(true);
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    protected int getNavBarItemId() {
        return R.id.nav_user;
    }
}